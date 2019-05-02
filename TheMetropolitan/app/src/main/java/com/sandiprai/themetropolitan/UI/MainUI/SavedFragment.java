package com.sandiprai.themetropolitan.UI.MainUI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sandiprai.themetropolitan.Adapter.ArticleAdapter;
import com.sandiprai.themetropolitan.Adapter.SavedArticleAdapter;
import com.sandiprai.themetropolitan.DummyArticle;
import com.sandiprai.themetropolitan.Adapter.DummyArticleAdapter;
import com.sandiprai.themetropolitan.R;
import com.sandiprai.themetropolitan.SharedPreferenceForSaved;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SavedFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<String> articleList;
    private SharedPreferenceForSaved sharedPreferenceForSaved;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        sharedPreferenceForSaved = new SharedPreferenceForSaved();

        //Create the recycler
        recyclerView = (RecyclerView) view.findViewById(R.id.saved_articles_recycler);

        /*articleList = new ArrayList<>();
        articleList.add("1814");
        articleList.add("1808");*/

        articleList = sharedPreferenceForSaved.getFavorites(getActivity());

        //Pass the two arrays to the adapter and set the adapter to the recycleview
        SavedArticleAdapter articleAdapter = new
                SavedArticleAdapter(articleList, getActivity());
        recyclerView.setAdapter(articleAdapter);


        //Set the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }
}
