package com.sandiprai.themetropolitan;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
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
    //@Parameter String, String, String, String, int
    //@Return if the article was added to the firebase or not
    boolean add_News_Article(String ID, String title, String author, String date, String excerpt, String body, ArrayList<String> tags)
    {
        int likes = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MMdd HH:mm:ss");
        Date creation = new Date();
        Map<String, Object> newArticle = new HashMap();

        newArticle.put("title", title);
        newArticle.put("author", author);
        newArticle.put("date", date);
        newArticle.put("likes", likes);
        newArticle.put("created", dateFormat.format(creation));
        newArticle.put("update", dateFormat.format(creation));
        newArticle.put("excerpt", excerpt);
        newArticle.put("body", body);
        newArticle.put("tags", tags);

        boolean result = fire.collection("Articles").document("category")
                .collection("News").document(ID).set(newArticle).isSuccessful();

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

    boolean add_Art_Article(String ID, String title, String author, String date, String excerpt, String body, ArrayList<String> tags)
    {
        int likes = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MMdd HH:mm:ss");
        Date creation = new Date();
        Map<String, Object> newArticle = new HashMap();

        newArticle.put("title", title);
        newArticle.put("author", author);
        newArticle.put("date", date);
        newArticle.put("likes", likes);
        newArticle.put("created", dateFormat.format(creation));
        newArticle.put("update", dateFormat.format(creation));
        newArticle.put("excerpt", excerpt);
        newArticle.put("body", body);
        newArticle.put("tags", tags);

        boolean result = fire.collection("Articles").document("category")
                .collection("Art & Entertainment").document(ID).set(newArticle).isSuccessful();

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

    boolean add_Opinion_Article(String ID, String title, String author, String date, String excerpt, String body, ArrayList<String> tags)
    {
        int likes = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MMdd HH:mm:ss");
        Date creation = new Date();
        Map<String, Object> newArticle = new HashMap();

        newArticle.put("title", title);
        newArticle.put("author", author);
        newArticle.put("date", date);
        newArticle.put("likes", likes);
        newArticle.put("created", dateFormat.format(creation));
        newArticle.put("update", dateFormat.format(creation));
        newArticle.put("excerpt", excerpt);
        newArticle.put("body", body);
        newArticle.put("tags", tags);

        boolean result = fire.collection("Articles").document("category")
                .collection("Opinion").document(ID).set(newArticle).isSuccessful();

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

    boolean add_Life_Article(String ID, String title, String author, String date, String excerpt, String body, ArrayList<String> tags)
    {
        int likes = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MMdd HH:mm:ss");
        Date creation = new Date();
        Map<String, Object> newArticle = new HashMap();

        newArticle.put("title", title);
        newArticle.put("author", author);
        newArticle.put("date", date);
        newArticle.put("likes", likes);
        newArticle.put("created", dateFormat.format(creation));
        newArticle.put("update", dateFormat.format(creation));
        newArticle.put("excerpt", excerpt);
        newArticle.put("body", body);
        newArticle.put("tags", tags);

        boolean result = fire.collection("Articles").document("category")
                .collection("Student Life").document(ID).set(newArticle).isSuccessful();

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

    boolean add_Tech_Article(String ID, String title, String author, String date, String excerpt, String body, ArrayList<String> tags)
    {
        int likes = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MMdd HH:mm:ss");
        Date creation = new Date();
        Map<String, Object> newArticle = new HashMap();

        newArticle.put("title", title);
        newArticle.put("author", author);
        newArticle.put("date", date);
        newArticle.put("likes", likes);
        newArticle.put("created", dateFormat.format(creation));
        newArticle.put("update", dateFormat.format(creation));
        newArticle.put("excerpt", excerpt);
        newArticle.put("body", body);
        newArticle.put("tags", tags);

        boolean result = fire.collection("Articles").document("category")
                .collection("Tech").document(ID).set(newArticle).isSuccessful();

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


    boolean add_Account(String ID, String email, String username)
    {
        String[] liked = new String[100];
        String[] saved = new String[10];
        boolean verified = false;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MMdd HH:mm:ss");
        Date creation = new Date();

        Map<String, Object> newAccount = new HashMap<>();
        newAccount.put("email", email);
        newAccount.put("username", username);
        newAccount.put("liked", liked);
        newAccount.put("saved", saved);
        newAccount.put("verified", verified);
        newAccount.put("created", creation);
        newAccount.put("updated", creation);

        boolean result = fire.collection("Account").document(ID).set(newAccount).isSuccessful();

        if(result == true)
        {
            Log.d(TAG, "Account added");
        }
        else
        {
            Log.v(TAG, "Account creation failed");
        }

        return result;
    }

    boolean add_Comment(String ID, String articleID, String accountID, String body)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MMdd HH:mm:ss");
        Date creation = new Date();
        Map<String, Object> newComment = new HashMap<>();

        newComment.put("articleID", articleID);
        newComment.put("accountID", accountID);
        newComment.put("body", body);

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

    Articles get_News_Article(String ID, Articles[] returned)
    {
        final Articles[] temp = returned;
        fire.collection("Articles").document("category").collection("News").document(ID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot snap = task.getResult();
                    Map map = snap.getData();

                    if (map != null) {
                        String title = (String) map.get("title");
                        String author = (String) map.get("author");
                        String date = (String) map.get("date");
                        long likes = (long) map.get("likes");
                        String excerpt = (String) map.get("excerpt");
                        String body = (String) map.get("body");
                        String[] tags = (String[]) map.get("tags");


                        Articles article = new Articles(title, author, date, likes, excerpt, body, tags);
                        temp[0] = article;
                        Log.d(TAG, "Article returned");
                    }
                }
                else
                {
                    Log.v(TAG, "Article retrival failed");
                }
            }
        });
        return returned[0];
    }

    Articles get_Art_Article(String ID, Articles[] returned)
    {
        final Articles[] temp = returned;
        fire.collection("Articles").document("category").collection("Art & Entertainment").document(ID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot snap = task.getResult();
                    Map map = snap.getData();

                    if (map != null) {
                        String title = (String) map.get("title");
                        String author = (String) map.get("author");
                        String date = (String) map.get("date");
                        long likes = (long) map.get("likes");
                        String excerpt = (String) map.get("excerpt");
                        String body = (String) map.get("body");
                        String[] tags = (String[]) map.get("tags");


                        Articles article = new Articles(title, author, date, likes, excerpt, body, tags);
                        temp[0] = article;
                        Log.d(TAG, "Article returned");
                    }
                }
                else
                {
                    Log.v(TAG, "Article retrival failed");
                }
            }
        });
        return returned[0];
    }

    Articles get_Opinion_Article(String ID, Articles[] returned)
    {
        final Articles[] temp = returned;
        fire.collection("Articles").document("category").collection("Opinion").document(ID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot snap = task.getResult();
                    Map map = snap.getData();

                    if (map != null) {
                        String title = (String) map.get("title");
                        String author = (String) map.get("author");
                        String date = (String) map.get("date");
                        long likes = (long) map.get("likes");
                        String excerpt = (String) map.get("excerpt");
                        String body = (String) map.get("body");
                        String[] tags = (String[]) map.get("tags");


                        Articles article = new Articles(title, author, date, likes, excerpt, body, tags);
                        temp[0] = article;
                        Log.d(TAG, "Article returned");
                    }
                }
                else
                {
                    Log.v(TAG, "Article retrival failed");
                }
            }
        });
        return returned[0];
    }

    Articles get_Life_Article(String ID, Articles[] returned)
    {
        final Articles[] temp = returned;
        fire.collection("Articles").document("category").collection("Student Life").document(ID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot snap = task.getResult();
                    Map map = snap.getData();

                    if (map != null) {
                        String title = (String) map.get("title");
                        String author = (String) map.get("author");
                        String date = (String) map.get("date");
                        long likes = (long) map.get("likes");
                        String excerpt = (String) map.get("excerpt");
                        String body = (String) map.get("body");
                        String[] tags = (String[]) map.get("tags");


                        Articles article = new Articles(title, author, date, likes, excerpt, body, tags);
                        temp[0] = article;
                        Log.d(TAG, "Article returned");
                    }
                }
                else
                {
                    Log.v(TAG, "Article retrival failed");
                }
            }
        });
        return returned[0];
    }

    Articles get_Tech_Article(String ID, Articles[] returned)
    {
        final Articles[] temp = returned;
        fire.collection("Articles").document("category").collection("News").document(ID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot snap = task.getResult();
                    Map map = snap.getData();

                    if (map != null) {
                        String title = (String) map.get("title");
                        String author = (String) map.get("author");
                        String date = (String) map.get("date");
                        long likes = (long) map.get("likes");
                        String excerpt = (String) map.get("excerpt");
                        String body = (String) map.get("body");
                        String[] tags = (String[]) map.get("tags");


                        Articles article = new Articles(title, author, date, likes, excerpt, body, tags);
                        temp[0] = article;
                        Log.d(TAG, "Article returned");
                    }
                }
                else
                {
                    Log.v(TAG, "Article retrival failed");
                }
            }
        });
        return returned[0];
    }

    Accounts get_Account(String ID, Accounts[] returned) {
        final Accounts[] temp = returned;
        fire.collection("Account").document(ID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snap = task.getResult();
                    Map map = snap.getData();

                    if (map != null) {
                        String email = (String) map.get("email");
                        String username = (String) map.get("username");
                        String[] liked = (String[]) map.get("liked");
                        String[] saved = (String[]) map.get("saved");
                        boolean verified = (boolean) map.get("verified");

                        Accounts account = new Accounts(email, username, liked, saved, verified);

                        temp[0] = account;
                    }


                }
            }
        });
        return returned[0];
    }

    Comments get_Comment(String ID, Comments[] returned)
    {
        final Comments[] temp = returned;
        fire.collection("Comment").document(ID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    DocumentSnapshot snap = task.getResult();
                    Map map = snap.getData();

                    if(map != null)
                    {
                        String articleID = (String) map.get("articleID");
                        String accountID = (String) map.get("accountID");
                        String body = (String) map.get("body");

                        Comments comment = new Comments(articleID, accountID, body);

                        temp[0] = comment;
                    }
                }
            }
        });
        return returned[0];
    }

    boolean remove_News_Article(String ID)
    {
        boolean result = fire.collection("Articles").document("category")
                .collection("News").document(ID).delete().isSuccessful();

        if(!result)
        {
            Log.d(TAG, "Article removed");
        }
        else
        {
            Log.v(TAG, "Article removal failed");
        }
        return result;
    }

    boolean remove_Art_Article(String ID)
    {
        boolean result = fire.collection("Articles").document("category")
                .collection("Art & Entertainment").document(ID).delete().isSuccessful();

        if(!result)
        {
            Log.d(TAG, "Article removed");
        }
        else
        {
            Log.v(TAG, "Article removal failed");
        }
        return result;
    }

    boolean remove_Opinion_Article(String ID)
    {
        boolean result = fire.collection("Articles").document("category")
                .collection("Opinion").document(ID).delete().isSuccessful();

        if(!result)
        {
            Log.d(TAG, "Article removed");
        }
        else
        {
            Log.v(TAG, "Article removal failed");
        }
        return result;
    }

    boolean remove_Life_Article(String ID)
    {
        boolean result = fire.collection("Articles").document("category")
                .collection("Student Life").document(ID).delete().isSuccessful();

        if(!result)
        {
            Log.d(TAG, "Article removed");
        }
        else
        {
            Log.v(TAG, "Article removal failed");
        }
        return result;
    }

    boolean remove_Tech_Article(String ID)
    {
        boolean result = fire.collection("Articles").document("category")
                .collection("Tech").document(ID).delete().isSuccessful();

        if(!result)
        {
            Log.d(TAG, "Article removed");
        }
        else
        {
            Log.v(TAG, "Article removal failed");
        }
        return result;
    }

    boolean remove_Account(String ID)
    {
        boolean result = fire.collection("Account").document().delete().isSuccessful();

        if(result)
        {
            Log.d(TAG, "Account removed");
        }
        else
        {
            Log.v(TAG, "Account removal failed");
        }
        return result;
    }

    boolean remove_Comment(String ID)
    {
        boolean result = fire.collection("Comment").document(ID).delete().isSuccessful();

        if(result)
        {
            Log.d(TAG, "Comment removed");
        }
        else
        {
            Log.v(TAG, "Comment removal failed");
        }

        return result;
    }
}
