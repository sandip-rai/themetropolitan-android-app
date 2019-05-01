package com.sandiprai.themetropolitan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sandiprai.themetropolitan.Adapter.DummyArticleAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DummyArticleDetail extends AppCompatActivity {

    public static final String EXTRA_ARTICLE_ID ="articleId";
    private String articleTitle;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_article_detail);

        //Get the article toolbar and load it as the main toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_article);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String articleId = String.valueOf(getIntent().getExtras().get(EXTRA_ARTICLE_ID));
        //int articleId = (int) getIntent().getExtras().get(EXTRA_ARTICLE_ID);

        //Firebase Firestore part below
        firestore = FirebaseFirestore.getInstance();

        final TextView titleView = findViewById(R.id.article_title);
        final TextView contentView = findViewById(R.id.article_content);
        final ImageView imageView = findViewById(R.id.article_image);

        firestore.collection("Articles")
                .document(articleId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();

                            if(doc.exists()){
                                articleTitle = doc.getString("title");
                                titleView.setText(doc.getString("title"));
                                Picasso.get().load(doc.getString("featuredPhoto")).
                                        into(imageView);
                                contentView.setText(doc.getString("content"));
                            }
                        }
                    }
                });

        /*int articleId = (int) getIntent().getExtras().get(EXTRA_ARTICLE_ID);
        articleTitle = DummyArticle.articles[articleId].getArticleTitle();
        String articleContent = DummyArticle.articles[articleId].getArticleContent();
        int articleImage = DummyArticle.articles[articleId].getImageId();

        TextView titleView = findViewById(R.id.article_title);
        titleView.setText(articleTitle);

        TextView contentView = findViewById(R.id.article_content);
        contentView.setText(articleContent);

        ImageView imageView = findViewById(R.id.article_image);
        imageView.setImageDrawable(ContextCompat.getDrawable(this,articleImage));*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.like_article:
                CharSequence text1 = articleTitle + " liked!";
                showToast(text1);
                return true;
            case R.id.save_article:
                CharSequence text2 = articleTitle + " saved!";
                showToast(text2);
                return true;
            case R.id.share_article:
                CharSequence text3 = articleTitle + " shared!";
                showToast(text3);
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
