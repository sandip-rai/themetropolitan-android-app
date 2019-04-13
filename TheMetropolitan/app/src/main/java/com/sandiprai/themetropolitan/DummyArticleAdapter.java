package com.sandiprai.themetropolitan;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DummyArticleAdapter extends RecyclerView.Adapter<DummyArticleAdapter.ViewHolder> {
    public String[] id = null;
    private String[] articleTitles = new String[10];
    private int[] imageIds = new int[10];
    private String[] keyArr;
    private String[] strArr;
    private DummyArticle dArticle = new DummyArticle();

    /**********READ CHAPTER 13 OF HEAD FIRST ANDROID FOR LEARNING THIS EASILY********/

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private MaterialCardView cardView;

        public ViewHolder(MaterialCardView cardView){
            super(cardView);
            this.cardView = cardView;
        }
    }

    public DummyArticleAdapter(Map allArticles) {
//        Iterator<Map.Entry<String,String>> itter = allArticles.entrySet().iterator();
//        String articleMain;
//        String[] articleArr;
//        int counter = 0;
//
//        while (itter.hasNext()){
//            Map.Entry<String,String> entry = itter.next();
//            this.id[counter] = entry.getKey();
//            articleMain = entry.getValue();
//            articleArr = articleMain.split("#");
//            this.articleTitles[counter] = articleArr[0];
//            this.imageIds[counter] = Integer.parseInt(articleArr[2]);
//            counter++;
//        }
//        for (Map.Entry<String, String> entry : allArticles.entrySet()) {
//            keyArr[itter] = entry.getKey();
//            strArr[itter] = entry.getValue();
//            itter++;
//        }
        //this.articleTitles = articleTitles;
        //this.imageIds = imageIds;
        String[] teststrarr = new String[] {"safaf","afdja","fqhosd","aioaa"};
        int[] testintarr = new int[] {1092,3498,3422,3242};
        this.articleTitles = teststrarr;
        this.imageIds = testintarr;
    }

    @NonNull
    @Override
    public DummyArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.dummy_article_card,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final MaterialCardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.dummyarticle_image);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(drawable);
        TextView textView = cardView.findViewById(R.id.dummyarticle_title);
        textView.setText(articleTitles[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cardView.getContext(), DummyArticleDetail.class);
                intent.putExtra(DummyArticleDetail.EXTRA_ARTICLE_ID, position);
                cardView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleTitles.length;
    }
}
