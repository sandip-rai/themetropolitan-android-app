package com.sandiprai.themetropolitan;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsFragment extends Fragment implements View.OnClickListener {

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

        //using AppCompatDelegate with a switch in android; still working
//        Switch switchTheme = view.findViewById(R.id.themeSwitch);
//        switchTheme.setChecked(true);
//        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if(isChecked){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    getActivity().recreate();
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    getActivity().recreate();
//                }
//            }
//        });
//
//        Button darkButton = view.findViewById(R.id.button2);
//        darkButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Code here executes on main thread after user presses button
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            }
//        });


        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignIn:
                CharSequence text = "Sign In button clicked!";
                showToast(text);
                break;
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            case R.id.buttonSignUp:
                CharSequence text1 = "Sign Up button clicked!";
                showToast(text1);
                break;
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

    }

    //Shows the passed string as a toast
    public void showToast(CharSequence text) {
        int duration1 = Toast.LENGTH_SHORT;

        Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), text, duration1);
        toast1.show();
    }


}
