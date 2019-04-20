package com.sandiprai.themetropolitan.UI.UserSettings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sandiprai.themetropolitan.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    EditText editTextEmail, editTextPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Get the toolbar and load it as the main toolbar
        Toolbar toolbar =findViewById(R.id.toolbarInSignIn);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        //Following code is for having the title in the center; otherwise it would be aligned to
        //left as default
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String appName = "Sign In";
        toolbarTitle.setText(appName.toUpperCase());

        editTextEmail =  findViewById(R.id.editTextInputSignInEmail);
        editTextPassword =  findViewById(R.id.editTextPasswordSignIn);

        mAuth = FirebaseAuth.getInstance();

        Button buttonSignIn = findViewById(R.id.buttonSignInConfirm);
        Button buttonCancel = findViewById(R.id.buttonCancelSignIn);
        buttonSignIn.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, UserSettingsActivity.class));
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    Intent intent = new Intent(SignInActivity.this, UserSettingsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignInConfirm:
                userLogin();
                /*Intent intent = new Intent(view.getContext(), UserSettingsActivity.class);
                startActivity(intent);*/
                break;

            case R.id.buttonCancelSignIn:
                Toast.makeText(getApplicationContext(), "Cancel button clicked!",
                        Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
