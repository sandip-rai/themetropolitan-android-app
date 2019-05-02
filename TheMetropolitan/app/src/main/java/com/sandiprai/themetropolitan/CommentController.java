package com.sandiprai.themetropolitan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class CommentController extends Fragment {

    Intent intent = getIntent();
    String articleID = intent.getStringExtra("ARTICLE_ID");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view =  inflater.inflate(R.layout.comment_page, container, false);




        Button commentButton = view.findViewById(R.id.commentButton);


        return view;
    }

    public Intent getIntent() {
        return intent;
    }
}
