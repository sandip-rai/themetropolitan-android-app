package com.sandiprai.themetropolitan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticlePage extends AppCompatActivity {
    public static final String EXTRA_ARTICLE_ID ="articleId";
    private FirebaseFirestore firestore;
    private String articleId;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    private SharedPreferenceForSaved sharedPreferenceForSaved;
    private SharedPreferenceForLiked sharedPreferenceForLiked;
    private SharedPreferences sharedPreferenceForShare;
    Editor editor;
    String url = "https://themetropolitan.metrostate.edu/?p=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_page);

        //Used to save the articleId to the savedList
        sharedPreferenceForSaved = new SharedPreferenceForSaved();
        sharedPreferenceForLiked = new SharedPreferenceForLiked();
        sharedPreferenceForShare = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        editor = sharedPreferenceForShare.edit();

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
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Check if this article is already liked,saved and update the menuItem color
        if(checkLikedItem(articleId)){
            menu.getItem(0).setIcon(R.drawable.like1);
        }

        if(checkFavoriteItem(articleId)){
            menu.getItem(1).setIcon(R.drawable.saved1);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        String webAddress = url + articleId;
        myIntent.setType("text/plain");
        String articleTitle = sharedPreferenceForShare.getString("currArticleTitle","The Metropolitan Article");
        //String shareBody = "share body";
        myIntent.putExtra(Intent.EXTRA_SUBJECT,articleTitle);
        myIntent.putExtra(Intent.EXTRA_TEXT,webAddress);

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.like_article:
                Boolean liked;
                if(checkLikedItem(articleId)){
                    liked = true;
                } else{
                    liked = false;
                }

                if (liked == false){ //If article is not liked yet
                    item.setIcon(R.drawable.like1);

                    //use the sharedPreferenceForSaved to add this articleId to savedList
                    sharedPreferenceForLiked.addFavorite(getApplicationContext(), articleId);

                    //increment the likes on the firestore database
                    firestore.collection("Articles")
                            .document(articleId).update("likes", FieldValue.increment(1));

                    CharSequence text2 = articleTitle + " liked!";
                    showToast(text2);
                } else {//if it was liked and it is clicked, this will remove the article from savedList
                    item.setIcon(R.drawable.like);
                    //use the sharedPreferenceForSaved to delete this articleId from savedList
                    sharedPreferenceForLiked.removeFavorite(getApplicationContext(), articleId);

                    //Since it was already liked, clicking it again will decrement the likes
                    firestore.collection("Articles")
                            .document(articleId).update("likes", FieldValue.increment(-1));
                }

                return true;

            case R.id.save_article:
                Boolean saved;
                if(checkFavoriteItem(articleId)){
                    saved = true;
                } else{
                    saved = false;
                }

                if (saved == false){
                    /*MenuItem saveArticleItem = findViewById(R.id.save_article);
                saveArticleItem.setIcon(R.drawable.saved1);*/
                    item.setIcon(R.drawable.saved1);

                    //use the sharedPreferenceForSaved to add this articleId to savedList
                    sharedPreferenceForSaved.addFavorite(getApplicationContext(), articleId);

                    CharSequence text2 = articleTitle + " saved!";
                    showToast(text2);
                } else {//if it was saved and it is clicked, this will remove the article from savedList
                    item.setIcon(R.drawable.saved);
                    //use the sharedPreferenceForSaved to delete this articleId from savedList
                    sharedPreferenceForSaved.removeFavorite(getApplicationContext(), articleId);
                    CharSequence text2 = articleTitle + " unsaved!";
                    showToast(text2);
                }

                return true;
            case R.id.share_article:
                startActivity(Intent.createChooser(myIntent,"Share to"));
                //CharSequence text3 = articleId + " shared!";
                CharSequence text3 = webAddress + " shared!";
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

    /*This method loads a passed fragment to the layout frame_saved in the activity_main xml
     * Will be changed eventually to be more dynamic */
    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_saved, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*Checks whether a particular product exists in SharedPreferences*/
    public boolean checkFavoriteItem(String checkArticleId) {
        boolean check = false;
        List<String> favorites = sharedPreferenceForSaved.getFavorites(getApplicationContext());
        if (favorites != null) {
            for (String articleId : favorites) {
                if (articleId.equals(checkArticleId)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    /*Checks whether a particular product exists in SharedPreferences*/
    public boolean checkLikedItem(String checkArticleId) {
        boolean check = false;
        List<String> favorites = sharedPreferenceForLiked.getFavorites(getApplicationContext());
        if (favorites != null) {
            for (String articleId : favorites) {
                if (articleId.equals(checkArticleId)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }
}
