package com.sandiprai.themetropolitan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SavedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Create the recycler
        RecyclerView savedArticlesRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_saved,
                container, false);

        //Copy the array contents to the two arrays which are then passed to the adapter
        String[] articleNames = new String[DummyArticle.articles.length];
        for(int i = 0; i < articleNames.length; i++){
            articleNames[i] = DummyArticle.articles[i].getArticleTitle();
        }

        int[] articleImages = new int[DummyArticle.articles.length];
        for(int i = 0; i < articleImages.length; i++){
            articleImages[i] = DummyArticle.articles[i].getImageId();
        }

        //Pass the two arrays to the adapter and set the adapter to the recycleview
        DummyArticleAdapter dummyArticleAdapter = new DummyArticleAdapter(articleNames, articleImages);
        savedArticlesRecycler.setAdapter(dummyArticleAdapter);

        //Set the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        savedArticlesRecycler.setLayoutManager(layoutManager);

        return savedArticlesRecycler;
    }

}
