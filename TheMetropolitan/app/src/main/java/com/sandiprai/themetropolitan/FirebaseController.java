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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

class FirebaseController
{
    //Since the main class will need to reference the firebase
    //We might need to pass the firebase instance as a parameter
    //For all methods
    FirebaseFirestore fire = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    Editor editor;


    public FirebaseController(Context context){
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
                            String dateStr;
                            int dateInt = 0;
                            int tmpDateInt;
                            int tmpID = 0;
                            String tmpIDStr;
                            List<Integer> dateList = new ArrayList<>();
                            List<Integer> articleList = new ArrayList<>();
                            List<Integer> newArticleList = new ArrayList<>();
                            String fireNewestArticleID = "";
                            //Map map = new TreeMap();
                            Map<Integer,Integer> map = new HashMap<>();

                            for (DocumentSnapshot doc: task.getResult()){
                                //Get the article, convert it to Article class object, and add to the list
                                /*Article article = doc.toObject(Article.class);
                                article.setId(doc.getId());
                                articleList.add(article);*/
                                newArticleList.add(Integer.parseInt(doc.getString("author")));
                                dateStr = doc.getString("date");
                                dateStr = dateStr.replaceAll("-","");
                                dateStr = dateStr.replaceAll(":","");
                                tmpID = Integer.valueOf(doc.getId());
                                dateList.add(Integer.parseInt(dateStr));
                                articleList.add(tmpID);
                            }

                            tmpID = 0;

                            int i = 0;
                            //int index = 0;
                            while(newArticleList.iterator().hasNext()){
                                tmpDateInt = dateList.get(i);
                                if(tmpDateInt > dateInt){
                                    dateInt = tmpDateInt;
                                    tmpID = articleList.get(i);
                                    //index = i;
                                }
//                            tmpDateInt = Integer.parseInt(dateStr);
//                            tmpID = articleList.get(i);
//                            map.put(tmpDateInt,tmpID);
                                i++;
                            }
                            newArticleList.clear();
                            //newArticleList.add(0,tmpID);
                            newArticleList.add(tmpID);

//                            Map<Integer,Integer> sortedMap = new TreeMap<>(Collections.reverseOrder());
//                            // Get a set of the entries
//                            Set set = sortedMap.entrySet();
//
//                            // Get an iterator
//                            Iterator i = set.iterator();
//
//                            // Display elements
//                            while(i.hasNext()) {
//                                Map.Entry me = (Map.Entry)i.next();
//                                tmpIDStr = me.getValue().toString();
//                                articleList.add(Integer.valueOf(tmpIDStr));
//                            }
                            //articleList.add(111111);
                            //order the articleList on descending order
                            //Collections.sort(articleList, Collections.reverseOrder());
                            fireNewestArticleID = newArticleList.get(0).toString();

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
