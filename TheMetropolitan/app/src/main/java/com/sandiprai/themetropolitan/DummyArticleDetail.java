package com.sandiprai.themetropolitan;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class DummyArticleDetail extends AppCompatActivity {

    public static final String EXTRA_ARTICLE_ID ="articleId";
    private String articleTitle;
    private String articleContent;
    private int articleImage;
    DummyArticle mainActivity = new DummyArticle();//"1489"

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public int getArticleImage() {
        return articleImage;
    }

    public void setArticleImage(int articleImage) {
        this.articleImage = articleImage;
    }

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

        //int articleId = (int) getIntent().getExtras().get(EXTRA_ARTICLE_ID);
//        for (int i = 0; i < 4; i++) {
//            articleTitle = mainActivity.articles.get().getArticleTitle();
//            articleContent = mainActivity.articles[i].getArticleContent();
//            articleImage = mainActivity.articles[i].getImageId();
//        }
        Iterator ix = mainActivity.articles.entrySet().iterator();
        String article;
        while(ix.hasNext()) {
            Map.Entry pair = (Map.Entry)ix.next();
            article = pair.getKey() + "#" + pair.getValue();
            String[] currArticle = article.split("#");
            //structure of article: articleID#title#content#imageID

            TextView titleView = findViewById(R.id.article_title);
            titleView.setText(currArticle[1]);

            TextView contentView = findViewById(R.id.article_content);
            contentView.setText(currArticle[2]);

            ImageView imageView = findViewById(R.id.article_image);
            imageView.setImageDrawable(ContextCompat.getDrawable(this, Integer.parseInt(currArticle[3])));
        }





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
