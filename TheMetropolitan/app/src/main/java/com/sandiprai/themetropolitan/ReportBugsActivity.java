package com.sandiprai.themetropolitan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sandiprai.themetropolitan.UI.MainUI.MainActivity;

public class ReportBugsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bugs);

        //Get the toolbar and load it as the main toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInReportBugsPage);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        //Following code is for having the title in the center; otherwise it would be aligned to
        //left as default

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String appName = "Report Bugs";
        toolbarTitle.setText(appName.toUpperCase());

        Button report = findViewById(R.id.buttonReport);
        report.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonReport:

                startActivity(new Intent(this, MainActivity.class));

                break;
        }

    }
}
