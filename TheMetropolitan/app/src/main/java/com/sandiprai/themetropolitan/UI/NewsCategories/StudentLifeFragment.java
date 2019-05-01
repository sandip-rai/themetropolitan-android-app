package com.sandiprai.themetropolitan.UI.NewsCategories;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sandiprai.themetropolitan.Adapter.ArticleAdapter;
import com.sandiprai.themetropolitan.Adapter.DummyArticleAdapter;
import com.sandiprai.themetropolitan.DummyArticle;
import com.sandiprai.themetropolitan.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StudentLifeFragment extends Fragment {
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        //Create the recycler
        recyclerView = (RecyclerView) view.findViewById(R.id.saved_articles_recycler);


        //Firebase Firestore part below
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("Articles")
                .whereEqualTo("category", "Student Life")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //List<Article> articleList = new ArrayList<>();
                            List<Integer> articleList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                //Get the article, convert it to Article class object, and add to the list
                                /*Article article = doc.toObject(Article.class);
                                article.setId(doc.getId());
                                articleList.add(article);*/

                                articleList.add(Integer.valueOf(doc.getId()));
                            }
                            //order the articleList on descending order
                            Collections.sort(articleList, Collections.reverseOrder());

                            //Pass the two arrays to the adapter and set the adapter to the recycleview
                            ArticleAdapter articleAdapter = new
                                    ArticleAdapter(articleList, getActivity(), firestore);
                            recyclerView.setAdapter(articleAdapter);

                        } else {
                            Log.d("Firestore", "ERROR GETTING DOCUMENTS", task.getException());
                        }
                    }
                });

        //Set the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }
}
