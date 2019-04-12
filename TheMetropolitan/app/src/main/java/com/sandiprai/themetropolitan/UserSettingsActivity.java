package com.sandiprai.themetropolitan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserSettingsActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        //Get the toolbar and load it as the main toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInUserSettings);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        //Following code is for having the title in the center; otherwise it would be aligned to
        //left as default
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String appName = "User Settings";
        toolbarTitle.setText(appName.toUpperCase());


        setUpUserSettings();
    }

    public void setUpUserSettings(){
        TextView username = findViewById(R.id.textNameinSettings);
        username.setText("Dummy Name");

        TextView email = findViewById(R.id.textEmailinSettings);
        email.setText("DummyEmail@yahoo.com");

        Button buttonSignOut = findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignOut:
                Toast.makeText(getApplicationContext(), "Sign Out button clicked!",
                        Toast.LENGTH_SHORT).show();
                break;

        }

    }
}
