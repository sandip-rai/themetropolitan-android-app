package com.sandiprai.themetropolitan.UI.UserSettings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import com.sandiprai.themetropolitan.R;
import com.sandiprai.themetropolitan.UI.MainUI.MainActivity;

public class UserSettingsActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    EditText username;
    TextView emailAddress;
    TextView textView;

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

        mAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.textNameinSettings);
        emailAddress = findViewById(R.id.textEmailinSettings);
        textView = findViewById(R.id.textViewVerified);

        setUpUserSettings();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }
    }

    public void setUpUserSettings() {
        TextView username = findViewById(R.id.textNameinSettings);
        //username.setText("Dummy Name");

        TextView email = findViewById(R.id.textEmailinSettings);
        email.setText("DummyEmail@yahoo.com");

        Button buttonSignOut = findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(this);

        findViewById(R.id.buttonSaveinUserSettings).setOnClickListener(this);
    }

    private void saveUserInformation() {

        String displayName = username.getText().toString();

        if (displayName.isEmpty()) {

            username.setError("Name required");
            username.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UserSettingsActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));

                Toast.makeText(getApplicationContext(), "Sign Out button clicked!",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.buttonSaveinUserSettings:
                saveUserInformation();
                break;

        }

    }
}
