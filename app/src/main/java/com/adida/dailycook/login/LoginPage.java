package com.adida.dailycook.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.adida.dailycook.Main.MainActivity;
import com.adida.dailycook.R;
import com.adida.dailycook.SharedPreference.SharedPreference;
import com.adida.dailycook.config.Config;
import com.adida.dailycook.helpers.TextHelper;
import com.adida.dailycook.register.RegisterPage;
import com.adida.dailycook.retrofit2.ServiceManager;
import com.adida.dailycook.retrofit2.entities.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import at.favre.lib.crypto.bcrypt.BCrypt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    TextInputEditText email, pass;
    Button login;
    TextView register;
    Intent signupIntent;
    Intent loginIntent;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activivity_login);
        email = findViewById(R.id.login_edit_email);
        pass = findViewById(R.id.login_edit_password);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.text_register);
        signupIntent = new Intent(this, RegisterPage.class);
        loginIntent = new Intent(this, MainActivity.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        setupEditTextListen();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    void setupEditTextListen() {
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        register.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(signupIntent);
                return true;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButtonClicked();
            }
        });
    }

    private void onLoginButtonClicked() {
        if (TextHelper.isTextEmpty(email.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextHelper.isTextEmpty(pass.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("username", email.getText().toString());
        progressDialog.show();
        ServiceManager.getInstance().getUserService().authentication(params).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, final Response<Map<String, String>> response) {
                if (response.body() == null) {
                    Toast.makeText(getApplicationContext(), "User do not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                BCrypt.Result result = BCrypt.verifyer().verify(pass.getText().toString().toCharArray(), response.body().get("pass"));
                if (result.verified) {
                    ///TODO: Open Homepage
                    Toast.makeText(getApplicationContext(), "Loading user data...", Toast.LENGTH_SHORT).show();
                    GetUserInformation(response.body().get("id"));
                } else {
                    Toast.makeText(getApplicationContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void GetUserInformation(String id) {
        ServiceManager.getInstance().getUserService().getUserByID(id).enqueue(new Callback<User>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).put(Config.SHARED_PREFERENCES.USER.ID, response.body().getId());
                    SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).put(Config.SHARED_PREFERENCES.USER.NAME, response.body().getName());
                    SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).put(Config.SHARED_PREFERENCES.USER.EMAIL, response.body().getEmail());
                    SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).put(Config.SHARED_PREFERENCES.USER.AGE, response.body().getAge());

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            startActivity(loginIntent);
                            finish();
                        }
                    }, 1000);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Can not connect to server. \n Please retry!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                return;
            }
        });
    }
}
