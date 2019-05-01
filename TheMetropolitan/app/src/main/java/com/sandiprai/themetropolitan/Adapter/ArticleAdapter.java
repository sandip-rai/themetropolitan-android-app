package com.sandiprai.themetropolitan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sandiprai.themetropolitan.ArticlePage;
import com.sandiprai.themetropolitan.DummyArticleDetail;
import com.sandiprai.themetropolitan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private String[] articleTitles;
    private int[] imageIds;

    //private List<Article> articleList;
    private List<Integer> articleList;
    private Context context;
    private FirebaseFirestore firestore;

    /**********READ CHAPTER 13 OF HEAD FIRST ANDROID FOR LEARNING THIS EASILY********/

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private MaterialCardView cardView;

        public ViewHolder(MaterialCardView cardView){
            super(cardView);
            this.cardView = cardView;
        }
    }

    public ArticleAdapter(List<Integer> articleList, Context context, FirebaseFirestore firestore) {
        this.articleList = articleList;
        this.context = context;
        this.firestore = firestore;
    }

    /*public DummyArticleAdapter(String[] articleTitles, int[] imageIds) {
        this.articleTitles = articleTitles;
        this.imageIds = imageIds;
    }*/

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MaterialCardView cardView = (MaterialCardView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.dummy_article_card,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String article = String.valueOf(articleList.get(position));
        final MaterialCardView cardView = holder.cardView;
        final ImageView imageView = cardView.findViewById(R.id.dummyarticle_image);
        /*Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(drawable);*/
        final TextView textView = cardView.findViewById(R.id.dummyarticle_title);
        final TextView textViewAuthor = cardView.findViewById(R.id.dummyarticle_author);
        final TextView textViewIssue = cardView.findViewById(R.id.dummyarticle_issue);
        //textView.setText(articleTitles[position]);
       // textView.setText(article.getTitle());
        firestore.collection("Articles")
                .document(article)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();

                            if(doc.exists()){
                                /*Picasso.get().load(doc.getString("featuredPhoto")).
                                        into(imageView);*/
                                textView.setText(doc.getString("title"));
                                textViewAuthor.setText(doc.getString("author"));
                                //For the image
                                ArrayList<String> imageList = (ArrayList<String>) doc.get("tags");
                                if(!imageList.get(0).equalsIgnoreCase("")){
                                    String photoURL = imageList.get(0);
                                    Picasso.get().load(photoURL).into(imageView);
                                }

                                textViewIssue.setText(doc.getId());
                            }
                        }
                    }
                });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cardView.getContext(), ArticlePage.class);
                //intent.putExtra(DummyArticleDetail.EXTRA_ARTICLE_ID, position);
                intent.putExtra(DummyArticleDetail.EXTRA_ARTICLE_ID, article);
                cardView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    /*@Override
    public int getItemCount() {
        return articleTitles.length;
    }*/
}
