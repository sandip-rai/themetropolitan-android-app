package com.sandiprai.themetropolitan.UI.UserSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sandiprai.themetropolitan.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Get the toolbar and load it as the main toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInSignIn);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        //Following code is for having the title in the center; otherwise it would be aligned to
        //left as default
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String appName = "Sign In";
        toolbarTitle.setText(appName.toUpperCase());

        Button buttonSignIn = findViewById(R.id.buttonSignInConfirm);
        Button buttonCancel = findViewById(R.id.buttonCancelSignIn);
        buttonSignIn.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignInConfirm:
                Intent intent = new Intent(view.getContext(), UserSettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.buttonCancelSignIn:
                Toast.makeText(getApplicationContext(), "Cancel button clicked!",
                        Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
