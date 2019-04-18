package com.sandiprai.themetropolitan.UI.NewsCategories;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sandiprai.themetropolitan.Adapter.DummyArticleAdapter;
import com.sandiprai.themetropolitan.DummyArticle;
import com.sandiprai.themetropolitan.R;


public class TechFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Create the recycler
        RecyclerView techArticlesRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_saved,
                container, false);

        //Copy the array contents to the two arrays which are then passed to the adapter
        String[] articleNames = new String[DummyArticle.articles.length];
        for(int i = 0; i < articleNames.length; i++){
            articleNames[i] = DummyArticle.articlesTech[i].getArticleTitle();
        }

        int[] articleImages = new int[DummyArticle.articles.length];
        for(int i = 0; i < articleImages.length; i++){
            articleImages[i] = DummyArticle.articlesTech[i].getImageId();
        }

        //Pass the two arrays to the adapter and set the adapter to the recycleview
        DummyArticleAdapter dummyArticleAdapter = new DummyArticleAdapter(articleNames, articleImages);
        techArticlesRecycler.setAdapter(dummyArticleAdapter);

        //Set the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        techArticlesRecycler.setLayoutManager(layoutManager);

        return techArticlesRecycler;
    }
}
