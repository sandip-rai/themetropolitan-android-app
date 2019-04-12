package com.sandiprai.themetropolitan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends Fragment implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Since this is a fragment, we have to get the view first and then can access the
        //buttons, switches and other components
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        //View view = getView();
        Button buttonSignIn = view.findViewById(R.id.buttonSignIn);
        Button buttonSignUp = view.findViewById(R.id.buttonSignUp);
        buttonSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);

        //using AppCompatDelegate with a switch in android; still not working

        //Set listener to the switches; see the onCheckChangedListener method below
        Switch switchTheme = view.findViewById(R.id.themeSwitch);
        switchTheme.setChecked(true);
        switchTheme.setOnCheckedChangeListener(this);

        Switch switchNotification = view.findViewById(R.id.notificationSwitch);
        switchNotification.setChecked(false);
        switchNotification.setOnCheckedChangeListener(this);

        //Setting listener to textviews
        TextView fontSizeOption = view.findViewById(R.id.fontSizeView);
        TextView reportBugsOption = view.findViewById(R.id.reportBugsView);
        TextView aboutOption = view.findViewById(R.id.aboutView);
        fontSizeOption.setOnClickListener(this);
        reportBugsOption.setOnClickListener(this);
        aboutOption.setOnClickListener(this);

        return view;
    }


    //Method that listens for button clicks
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignIn:
                /*CharSequence text = "Sign In button clicked!";
                showToast(text);*/
                Intent intent = new Intent(getContext(), SignInActivity.class);
                startActivity(intent);
                break;
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            case R.id.buttonSignUp:
                CharSequence text1 = "Sign Up button clicked!";
                showToast(text1);

                //Snackbar.make(getView().findViewById(R.id.buttonSignUp), text1, Snackbar.LENGTH_SHORT).show();
                break;
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            case R.id.fontSizeView:
                CharSequence text2 = "Font size clicked!";
                showToast(text2);
                break;

            case R.id.reportBugsView:
                CharSequence text3 = "Report bugs clicked!";
                showToast(text3);
                break;

            case R.id.aboutView:
                CharSequence text4 = "About clicked!";
                showToast(text4);
                break;
        }

    }

    //Method that listens for switches clicks
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.themeSwitch:
                if(isChecked){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    getActivity().recreate();
                    ((MainActivity)getActivity()).themeDarkOn();
                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    getActivity().recreate();
                    ((MainActivity)getActivity()).themeLightOn();
                }
                break;
            case R.id.notificationSwitch:
                //implement the code implementation here by replacing these
                if(isChecked){
                    CharSequence text1 = "Notification Switch On!";
                    showToast(text1);
                } else{
                    CharSequence text2 = "Notification Switch Off!";
                    showToast(text2);
                }
                break;
        }

    }

    //Shows the passed string as a toast
    public void showToast(CharSequence text) {
        int duration1 = Toast.LENGTH_SHORT;

        Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), text, duration1);
        toast1.show();
    }


}
