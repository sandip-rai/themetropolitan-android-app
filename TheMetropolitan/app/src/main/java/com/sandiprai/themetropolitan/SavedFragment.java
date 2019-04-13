package com.sandiprai.themetropolitan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SavedFragment extends Fragment {
    private DummyArticle dArticle = new DummyArticle();
    //private String[] keyArr;
    //private String[] strArr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Create the recycler
        RecyclerView savedArticlesRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_saved,
                container, false);


        //String articleID = dArticle.getID();
        //String article = dArticle.makeArticle(articleID);
        //String[] pieces = article.split("#");

        //dArticle.
//        int itter = 0;
//        for (Map.Entry<String, String> entry : dArticle.articles.entrySet()) {
//            keyArr[itter] = entry.getKey();
//            strArr[itter] = entry.getValue();
//            itter++;
//        }
        //Copy the array contents to the two arrays which are then passed to the adapter
        //String[] articleNames = new String[itter];
//        for(int i = 0; i < articleNames.length; i++){
//            articleNames[i] = DummyArticle.articles[i].getArticleTitle();
//        }
//
//        int[] articleImages = new int[DummyArticle.articles.length];
//        for(int i = 0; i < articleImages.length; i++){
//            articleImages[i] = DummyArticle.articles[i].getImageId();
//        }



        //Pass the two arrays to the adapter and set the adapter to the recycleview
        DummyArticleAdapter dummyArticleAdapter = new DummyArticleAdapter(dArticle.articles);
        savedArticlesRecycler.setAdapter(dummyArticleAdapter);

        //Set the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        savedArticlesRecycler.setLayoutManager(layoutManager);

        return savedArticlesRecycler;
    }

}
