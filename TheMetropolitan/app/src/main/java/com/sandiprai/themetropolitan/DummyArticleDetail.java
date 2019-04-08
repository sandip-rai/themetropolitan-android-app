package com.sandiprai.themetropolitan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class DummyArticleDetail extends AppCompatActivity {

    public static final String EXTRA_ARTICLE_ID ="articleId";

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

        int articleId = (int) getIntent().getExtras().get(EXTRA_ARTICLE_ID);
        String articleTitle = DummyArticle.articles[articleId].getArticleTitle();
        String articleContent = DummyArticle.articles[articleId].getArticleContent();
        int articleImage = DummyArticle.articles[articleId].getImageId();

        TextView titleView = findViewById(R.id.article_title);
        titleView.setText(articleTitle);

        TextView contentView = findViewById(R.id.article_content);
        contentView.setText(articleContent);

        ImageView imageView = findViewById(R.id.article_image);
        imageView.setImageDrawable(ContextCompat.getDrawable(this,articleImage));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
