package com.adida.dailycook.Upload;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.adida.dailycook.R;
import com.adida.dailycook.Upload.Fragment.Upload.UploadFragment;

public class UploadActivity extends AppCompatActivity {
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        loadFragment(new UploadFragment());

    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutUpload, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}