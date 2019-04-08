package com.sandiprai.themetropolitan;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DummyArticleAdapter extends RecyclerView.Adapter<DummyArticleAdapter.ViewHolder> {
    private String[] articleTitles;
    private int[] imageIds;

    /**********READ CHAPTER 13 OF HEAD FIRST ANDROID FOR LEARNING THIS EASILY********/

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private MaterialCardView cardView;

        public ViewHolder(MaterialCardView cardView){
            super(cardView);
            this.cardView = cardView;
        }
    }

    public DummyArticleAdapter(String[] articleTitles, int[] imageIds) {
        this.articleTitles = articleTitles;
        this.imageIds = imageIds;
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
