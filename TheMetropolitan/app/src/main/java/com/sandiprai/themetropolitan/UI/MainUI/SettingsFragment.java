package com.sandiprai.themetropolitan.UI.MainUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sandiprai.themetropolitan.AboutPageActivity;
import com.sandiprai.themetropolitan.FontSizeActivity;
import com.sandiprai.themetropolitan.R;
import com.sandiprai.themetropolitan.ReportBugsActivity;
import com.sandiprai.themetropolitan.UI.UserSettings.SignInActivity;
import com.sandiprai.themetropolitan.UI.UserSettings.SignUpActivity;
import com.sandiprai.themetropolitan.UI.UserSettings.UserSettingsActivity;

public class SettingsFragment extends Fragment implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private View view;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Since this is a fragment, we have to get the view first and then can access the
        //buttons, switches and other components
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPreferences.edit();

                //View view = getView();
        Button buttonSignIn = view.findViewById(R.id.buttonSignIn);
        Button buttonSignUp = view.findViewById(R.id.buttonSignUp);
        buttonSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);

        TextView accountView = view.findViewById(R.id.accountView);
        accountView.setOnClickListener(this);

        //using AppCompatDelegate with a switch in android; still not working

        //Set listener to the switches; see the onCheckChangedListener method below
        Switch switchTheme = view.findViewById(R.id.themeSwitch);
        switchTheme.setChecked(sharedPreferences.getBoolean("ThemeSwitch", false));
        switchTheme.setOnCheckedChangeListener(this);

        Switch switchNotification = view.findViewById(R.id.notificationSwitch);
        //switchNotification.setChecked(false);
        switchNotification.setChecked(sharedPreferences.getBoolean("NotificationSwitch", false));
        switchNotification.setOnCheckedChangeListener(this);

        /*//Setting listener to textviews
        TextView fontSizeOption = view.findViewById(R.id.fontSizeView);
        TextView reportBugsOption = view.findViewById(R.id.reportBugsView);
        TextView aboutOption = view.findViewById(R.id.aboutView);
        fontSizeOption.setOnClickListener(this);
        reportBugsOption.setOnClickListener(this);
        aboutOption.setOnClickListener(this);*/

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
                startActivity(new Intent(view.getContext(), SignUpActivity.class));

                //Snackbar.make(getView().findViewById(R.id.buttonSignUp), text1, Snackbar.LENGTH_SHORT).show();
                break;

            case R.id.accountView:
                startActivity(new Intent(view.getContext(), UserSettingsActivity.class));
                break;

           /* case R.id.fontSizeView:
                CharSequence text2 = "Font size clicked!";
                showToast(text2);

                startActivity(new Intent(view.getContext(), FontSizeActivity.class));
                break;

            case R.id.reportBugsView:
                CharSequence text3 = "Report bugs clicked!";
                showToast(text3);

                startActivity(new Intent(view.getContext(), ReportBugsActivity.class));
                break;

            case R.id.aboutView:
                CharSequence text4 = "About clicked!";
                showToast(text4);

                startActivity(new Intent(view.getContext(), AboutPageActivity.class));
                break;*/
        }

    }

    //Method that listens for switches clicks
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.themeSwitch:
                if(isChecked){
                    editor.putBoolean("ThemeSwitch", true);
                    editor.commit();

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    getActivity().recreate();

                    /*try {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_saved, SettingsFragment.class.newInstance()).commit();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    }*/
                    //((MainActivity)getActivity()).themeDarkOn();
                } else {
                    editor.putBoolean("ThemeSwitch", false);
                    editor.commit();

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    /*try {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_saved, SettingsFragment.class.newInstance()).commit();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    }*/

                    getActivity().recreate();
                    //((MainActivity)getActivity()).themeLightOn();
                }
                break;
            case R.id.notificationSwitch:
                //implement the code implementation here by replacing these
                if(isChecked){
                    CharSequence text1 = "Notification Switch On!";
                    showToast(text1);

                    editor.putBoolean("NotificationSwitch", true);
                    editor.commit();

                } else{
                    CharSequence text2 = "Notification Switch Off!";
                    showToast(text2);

                    editor.putBoolean("NotificationSwitch", false);
                    editor.commit();
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
