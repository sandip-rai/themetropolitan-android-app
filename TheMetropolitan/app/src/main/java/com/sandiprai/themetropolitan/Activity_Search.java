package com.sandiprai.themetropolitan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity_Search extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__search);
    }


    public void onClickClear(View view){
        TextView fieldText = (TextView) findViewById(R.id.searchText);
        fieldText.setText("test");
    }
}
