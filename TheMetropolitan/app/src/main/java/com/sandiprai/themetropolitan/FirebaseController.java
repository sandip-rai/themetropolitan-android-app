package com.sandiprai.themetropolitan;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FirebaseController
{

    SharedPreferences sharedPreferences;
    Editor editor;

    //Since the main class will need to reference the firebase
    //We might need to pass the firebase instance as a parameter
    //For all methods
    FirebaseFirestore fire;

    public FirebaseController(Context context){
        fire = FirebaseFirestore.getInstance();
        sharedPreferences = context.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        fire.collection("Articles")
                .whereEqualTo("category", "Opinion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            //List<Article> articleList = new ArrayList<>();
                            List<Integer> articleList = new ArrayList<>();
                            String fireNewestArticleID = "";

                            for (DocumentSnapshot doc: task.getResult()){
                                //Get the article, convert it to Article class object, and add to the list
                                /*Article article = doc.toObject(Article.class);
                                article.setId(doc.getId());
                                articleList.add(article);*/

                                articleList.add(Integer.valueOf(doc.getId()));
                            }
                            //order the articleList on descending order
                            Collections.sort(articleList, Collections.reverseOrder());
                            fireNewestArticleID = articleList.get(0).toString();

                            editor.putString("newestFirebaseID",fireNewestArticleID);
                            editor.apply();

                        } else {
                            Log.d("Firestore", "ERROR GETTING DOCUMENTS", task.getException());
                        }
                    }
                });
    }



    //Adds an article to the firebase cloud storage
    //@Parameter String, String, String, String, String, String, String, ArrayList<String>
    //@Return if the article was added to the firebase or not
    boolean add_Article(String ID, String title, String author, String date, String excerpt, String body, String category, ArrayList<String> tags)
    {
        int likes = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date creation = new Date();
        Map<String, Object> newArticle = new HashMap();

        newArticle.put("title", title);
        newArticle.put("author", author);
        newArticle.put("category", category);
        newArticle.put("date", date);
        newArticle.put("likes", likes);
        newArticle.put("created", dateFormat.format(creation));
        newArticle.put("update", dateFormat.format(creation));
        newArticle.put("excerpt", excerpt);
        newArticle.put("body", body);
        newArticle.put("tags", tags);

        //document("category").collection("News").
        boolean result = fire.collection("Articles").document(ID).set(newArticle).isSuccessful();

        if(!result)
        {
            Log.d(TAG, "Article added");
        }
        else
        {
            Log.v(TAG, "Article creation failed");
        }

        return result;
    }


    //adds a like to the total value for an article
    boolean add_Article_Like(String ID, int currLikes)
    {
        currLikes++;
        Map<String, Object> newArticle = new HashMap();

        newArticle.put("likes", currLikes);


        //document("category").collection("News").
        boolean result = fire.collection("Articles").document(ID).set(newArticle).isSuccessful();

        if(!result)
        {
            Log.d(TAG, "Article like added");
        }
        else
        {
            Log.v(TAG, "Article like failed");
        }

        return result;
    }


    //takes away a like from the total value for the article
    boolean sub_Article_Like(String ID, int currLikes)
    {
        currLikes--;
        Map<String, Object> newArticle = new HashMap();

        newArticle.put("likes", currLikes);


        //document("category").collection("News").
        boolean result = fire.collection("Articles").document(ID).set(newArticle).isSuccessful();

        if(!result)
        {
            Log.d(TAG, "Article like subtracted");
        }
        else
        {
            Log.v(TAG, "Article like removal failed");
        }

        return result;
    }


    boolean add_Comment(String ID, String articleID, String accountID, String body)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date creation = new Date();
        Map<String, Object> newComment = new HashMap<>();

        newComment.put("articleID", articleID);
        newComment.put("accountID", accountID);
        newComment.put("body", body);
        newComment.put("created", creation);

        boolean result = fire.collection("Comment").document(ID).set(newComment).isSuccessful();

        if(result == true)
        {
            Log.d(TAG, "Comment added");
        }
        else
        {
            Log.v(TAG, "Comment creation failed");
        }

        return result;
    }


    public String get_Newest_Article_Id() {
        String newestID;
        newestID = sharedPreferences.getString("newestFirebaseID","0");

        return newestID;
    }


}
