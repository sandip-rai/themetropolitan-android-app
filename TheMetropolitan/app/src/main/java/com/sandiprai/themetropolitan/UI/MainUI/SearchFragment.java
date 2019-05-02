package com.sandiprai.themetropolitan.UI.MainUI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sandiprai.themetropolitan.Adapter.ArticleAdapter;
import com.sandiprai.themetropolitan.ArticlePage;
import com.sandiprai.themetropolitan.R;
import com.sandiprai.themetropolitan.TestWordPress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    private Spinner spinner;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        spinner = view.findViewById(R.id.spinnerArticleSearch);

        //Get the buttons and start their listener
        Button buttonSearch = view.findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this);

        //Create the recycler
        recyclerView = (RecyclerView) view.findViewById(R.id.search_recyclerview);

        //Firebase Firestore part below
        firestore = FirebaseFirestore.getInstance();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSearch:

                String monthSelected = String.valueOf(spinner.getSelectedItem());
                String startDate="";
                String endDate = "";
                if(monthSelected.equalsIgnoreCase("Jan 2019")){
                    startDate = "2019-01-01";
                    endDate = "2019-01-31";
                }
                if(monthSelected.equalsIgnoreCase("Feb 2019")){
                    startDate = "2019-02-01";
                    endDate = "2019-02-28";
                }
                if(monthSelected.equalsIgnoreCase("Mar 2019")){
                    startDate = "2019-03-01";
                    endDate = "2019-03-31";
                }
                if(monthSelected.equalsIgnoreCase("Apr 2019")){
                    startDate = "2019-04-01";
                    endDate = "2019-04-30";
                }

                firestore.collection("Articles")
                        .whereGreaterThanOrEqualTo("date", startDate)
                        .whereLessThanOrEqualTo("date",endDate)
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
