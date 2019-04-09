package com.sandiprai.themetropolitan;

import android.app.Application;

public class nestedArticleInfo extends Application {

    static private String Author;

    static public String getAuthor(){
        return Author;
    }

    public static void setAuthor(String Author){
        nestedArticleInfo.Author = Author;
    }

}
