package com.sandiprai.themetropolitan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class CommentController extends Fragment implements View.OnClickListener {

    //Intent intent = getIntent();
    //String articleID = intent.getStringExtra("ARTICLE_ID");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view =  inflater.inflate(R.layout.comment_page, container, false);


        Button commentButton = view.findViewById(R.id.commentButton);


        return view;
    }

//    public Intent getIntent() {
//        return intent;
//    }

    @Override
    public void onClick(View view) {
        TextInputEditText searchText = getView().findViewById(R.id.textInputComment);
        String comment = searchText.getText().toString();
        //database controller
    }
}
