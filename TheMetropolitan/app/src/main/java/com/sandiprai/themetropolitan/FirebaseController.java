package com.sandiprai.themetropolitan;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

class FirebaseController
{
    //Since the main class will need to reference the firebase
    //We might need to pass the firebase instance as a parameter
    //For all methods
    FirebaseFirestore fire = FirebaseFirestore.getInstance();

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

}
