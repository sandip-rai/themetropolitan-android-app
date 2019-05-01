package com.sandiprai.themetropolitan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticlePage extends AppCompatActivity {
    public static final String EXTRA_ARTICLE_ID ="articleId";
    private FirebaseFirestore firestore;
    private String articleTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_page);

        //Get the article toolbar and load it as the main toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        String articleId = String.valueOf(getIntent().getExtras().get(EXTRA_ARTICLE_ID));
        //Firebase Firestore part below
        firestore = FirebaseFirestore.getInstance();

        final TextView textViewArticleTitle = findViewById(R.id.textViewArticleTitle);
        final ImageView imageViewArticlePhoto = findViewById(R.id.imageviewArticlePhoto);
        final TextView textViewPhotoCaption = findViewById(R.id.textViewPhotoCaption);
        final TextView textViewArticleContent = findViewById(R.id.textViewArticleContent);

        firestore.collection("Articles")
                .document(articleId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();

                            if(doc.exists()){
                                textViewArticleTitle.setText(doc.getString("title"));

                                //For the image
                                ArrayList<String> imageList = (ArrayList<String>) doc.get("tags");
                                if(!imageList.get(0).equalsIgnoreCase("")){
                                    String photoURL = imageList.get(0);
                                    Picasso.get().load(photoURL).into(imageViewArticlePhoto);
                                }

                                textViewPhotoCaption.setText(doc.getString("author"));
                                //textViewPhotoCaption.setText(doc.getId());
                                textViewArticleContent.setText(doc.getString("body"));
                            }
                        } else {
                            Log.d("Firestore", "ERROR GETTING DOCUMENTS", task.getException());
                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
