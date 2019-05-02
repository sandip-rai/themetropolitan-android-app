package com.sandiprai.themetropolitan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticlePage extends AppCompatActivity {
    public static final String EXTRA_ARTICLE_ID ="articleId";
    private FirebaseFirestore firestore;
    private String articleTitle;
    String articleId;
    String url = "https://themetropolitan.metrostate.edu/?p=";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_page);

        sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        //Get the article toolbar and load it as the main toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        articleId = String.valueOf(getIntent().getExtras().get(EXTRA_ARTICLE_ID));
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
                                String title = doc.getString("title");
                                textViewArticleTitle.setText(title);

                                //For the image
                                ArrayList<String> imageList = (ArrayList<String>) doc.get("tags");
                                if(!imageList.get(0).equalsIgnoreCase("")){
                                    String photoURL = imageList.get(0);
                                    Picasso.get().load(photoURL).into(imageViewArticlePhoto);
                                }

                                textViewPhotoCaption.setText(doc.getString("author"));
                                //textViewPhotoCaption.setText(doc.getId());
                                textViewArticleContent.setText(doc.getString("body"));

                                editor.putString("currArticleTitle", title);
                                editor.apply();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        Intent commentIntent = new Intent(this,CommentController.class);
        commentIntent.putExtra("ARTICLE_ID",articleId);
        myIntent.setType("text/plain");
        String webAddress = url+articleId; //+EXTRA_ARTICLE_ID
        String shareBody = "share body";
        myIntent.putExtra(Intent.EXTRA_TEXT,webAddress);
        String articleTitle = sharedpreferences.getString("currArticleTitle","The Metropolitan Newspaper");
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.like_article:
                this.startActivity(commentIntent);
                CharSequence text1 = articleTitle + " liked!";
                showToast(text1);
                return true;
            case R.id.save_article:
                //verify login
                //save to SQLite
                //save to Firebase
                CharSequence text2 = articleTitle + " saved!";
                showToast(text2);
                return true;
            case R.id.share_article:
                startActivity(Intent.createChooser(myIntent,"Share to"));
                //CharSequence text3 = articleTitle + " shared!";
                //showToast(text3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Shows the passed string as a toast
    public void showToast(CharSequence text) {
        int duration1 = Toast.LENGTH_SHORT;

        Toast toast1 = Toast.makeText(getApplicationContext(), text, duration1);
        toast1.show();
    }
}
