package com.sandiprai.themetropolitan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;


public class CommentController extends Fragment implements View.OnClickListener {

    //Intent intent = getIntent();
    //String articleID = intent.getStringExtra("ARTICLE_ID");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view =  inflater.inflate(R.layout.fragment_comment_controller, container, false);


        Button commentButton = view.findViewById(R.id.commentButton);
        commentButton.setOnClickListener(this);


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
