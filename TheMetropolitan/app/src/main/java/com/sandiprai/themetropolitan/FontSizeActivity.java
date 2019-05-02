package com.sandiprai.themetropolitan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.Toast;

import android.os.Bundle;

import com.sandiprai.themetropolitan.UI.MainUI.MainActivity;

public class FontSizeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private ProgressBar progressBar;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_size);

        //Get the toolbar and load it as the main toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInFontPage);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        //Following code is for having the title in the center; otherwise it would be aligned to
        //left as default

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String appName = "Font Size";
        toolbarTitle.setText(appName.toUpperCase());

        textView = (TextView) findViewById(R.id.progressBarTextView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

      //  seekBar.getThumb().setColorFilter(Accent, PorterDuff.Mode.MULTIPLY);

        Button buttonFont = findViewById(R.id.buttonFontChange);
        buttonFont.setOnClickListener(this);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress);
                textView.setText("" + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonFontChange:
                Toast.makeText(getApplicationContext(), "Font Size Changed!",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));


                break;
        }

    }
}
