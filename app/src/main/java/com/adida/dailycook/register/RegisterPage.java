package com.adida.dailycook.register;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adida.dailycook.R;
import com.adida.dailycook.config.Config;
import com.adida.dailycook.helpers.TextHelper;
import com.adida.dailycook.login.LoginPage;
import com.adida.dailycook.retrofit2.ServiceManager;
import com.adida.dailycook.retrofit2.entities.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import at.favre.lib.crypto.bcrypt.BCrypt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPage extends AppCompatActivity {
    TextInputEditText email, pass, repass;
    Button register;
    TextView signin;
    Intent signinIntent;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.edit_email);
        pass = findViewById(R.id.edit_password);
        repass = findViewById(R.id.re_password);
        register = findViewById(R.id.btn_register);
        signin = findViewById(R.id.text_signin);
        signinIntent = new Intent(this, LoginPage.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        setupEditTextListen();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
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

        repass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        signin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(signinIntent);
                return true;
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterButtonClicked();
            }
        });
    }

    private void onRegisterButtonClicked() {
        if (TextHelper.isTextEmpty(email.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextHelper.isTextEmpty(pass.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextHelper.isTextEmpty(repass.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please retype password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.getText().toString().equals(repass.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Your retype password not match with your password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();
        ServiceManager.getInstance().getUserService().getUserByName(email.getText().toString()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Username was existed.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (response.code() == 403) {
                    registerUserAccount();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void registerUserAccount() {
        Map<String, Object> params = new HashMap<>();
        String passCr = BCrypt.withDefaults().hashToString(Config.LOG_ROUND_SALT, pass.getText().toString().toCharArray());
        params.put("username", email.getText().toString());
        params.put("password", passCr);

        ServiceManager.getInstance().getUserService().registerAccount(params).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_SHORT).show();
                startActivity(signinIntent);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}