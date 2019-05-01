package com.sandiprai.themetropolitan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestWordPress extends AppCompatActivity {
    //String url = "http://themetropolitan.metrostate.edu/wp-json/wp/v2/posts?fields=id,excerpt,title,content,date";
    String url;
    TextView articleTitle;
    TextView articleList;
    Bitmap articleImg;
    NetworkImageView theImg;
    ArrayList<String> allArticles = new ArrayList<>();
    //String tmpList;
    ProgressDialog progressDialog;
    //private ListView postList;
    private RequestQueue rQueue;
    private ImageLoader mImageLoader;
    int postID = 1751; //1489 and 1768 are odd ones
    String postExerpt[];
    String postTitle[];
    String postContent;
    String postIDs[] = new String[1000];
    int postIndex;
    String postElements[];
    Date postDate[];
    String author = null;
    String authorURL = null;
    String picURL = "";
    String mainPicURL = "";
    String titlePic = null;

    String mainArticleContent = null;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    FirebaseController controller;
    Articles[] articleArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_word_press);

        articleArr = new Articles[1000];
        sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        //editor = sharedpreferences.edit();
        editor = sharedpreferences.edit();

        controller = new FirebaseController();
        articleTitle = findViewById(R.id.textViewWordPressTitle);
        articleList = findViewById(R.id.textViewWordPressContent);
        //theImg = (NetworkImageView)findViewById(R.id.imageView);
        rQueue = Volley.newRequestQueue(this);
        //rQueue.start();
        //progressDialog = new ProgressDialog(MainActivity.this);
        ///progressDialog.setMessage("Loading...");
        // progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.show();
        articleList.clearComposingText();

        ArrayList<String> testList = new ArrayList<>();
        testList.add("url");
        //boolean result = controller.add_Tech_Article("id","title","author", "date","excerpt","body",testList);

//        wpGetArticleByID(postID);
        //do not set the amount for wpGetArticleByAge above 10
        //wpGetArticleByAge(5,134);//thru 84
        //wpGetArticleByAge(1,132);

        String newestID = wpGetArticleIDByAge(1);
        articleList.append(newestID);
        articleArr[0] = controller.get_Article(newestID, articleArr);
        //articleList.append(articleArr[0].getTitle());
        //articleTitle.append(wpGetArticleIDByAge(1));

    }


    private String wpGetArticleIDByAge (int num) {
        parseID(num);
        String newestID = sharedpreferences.getString("NewestWPArticle", "");
        //on non-articles, returns "invalid post type"
        return newestID;
    }

    private void wpGetArticleByID (int postID) {
        jsonParse(postID, "id");
    }


    //number = article from the newest to start getting them from, increments, starts at 1 = newest
    private void wpGetArticleByAge (int amount, int number) {
        int max = number + amount;
        for (; number < max; number++) {
            jsonParse(number, "age");
        }
    }


    private void jsonParse(int postID, String type) {
        final String[] theArticles = new String[1];
        if (type == "age") {
            url = "https://themetropolitan.metrostate.edu/wp-json/wp/v2/posts/?orderby=date&per_page=1&page="+postID;
        } else if (type == "id") {
            url = "https://themetropolitan.metrostate.edu/wp-json/wp/v2/posts/"+postID;
        } else  {
            url = "https://themetropolitan.metrostate.edu/wp-json/wp/v2/posts/?orderby=date&per_page=1&page=1";
        }

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.charAt(0) == '[') {
                    response = response.substring(1, response.length() - 1);
                }

                //variable declarations
                String id = "";
                String titleFull;
                String title = "";
                String links;
                String name2;
                String authorName = null;
                String dateMain;
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz"); //HH (0-23 hours), hh (normal hrs), a (AM/PM), z (timezone);
                String date;
                String time;
                String datetimeP = "";
                //get the current date
                Date now = new Date();
                String cat = "";
                String excerptFull;
                String excerpt = "";
                Spanned excerptStr;
                String contentFull;
                String content = "";
                Pattern pattern;
                Matcher matcher;
                String tmpMainPic[] = new String[2];
                String txt;
                String link;
                String subLink;
                String pic[] = new String[10];
                boolean result;

                //articleList.setText(response);
                try {
                    //turning the full string response from the url get into a JSON object
                    JSONObject mainObject = new JSONObject(response);
                    //get the first article's ID
                    id = mainObject.getString("id");

                    cat = mainObject.getString("categories");
                    if (cat.indexOf(",") > -1) {
                        String[] endFirstArray = cat.split(",");
                        cat = endFirstArray[0]+"]";
                    }

                    if (cat.indexOf("159") == -1) {
                        //Toast.makeText(getApplicationContext(), "Not a job post", Toast.LENGTH_LONG).show();
                        //get the title
                        titleFull = mainObject.getString("title");
                        JSONObject titleMain = new JSONObject(titleFull);
                        title = titleMain.getString("rendered");
                        Spanned stri = HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_LEGACY);
                        title = stri.toString();

                        //get the author's name from the article's name url
                        links = mainObject.getString("_links");
                        JSONObject nameMain = new JSONObject(links);
                        name2 = nameMain.getString("author");
                        name2 = name2.substring(1, name2.length() - 1);
                        JSONObject nameMain2 = new JSONObject(name2);
                        authorURL = nameMain2.getString("href");
                        //setAuthorURL(name3); //call the name url to get the author's name from it

                        //authorName = author;
                        //articleList.append(authorName);
                        //String Author = getAuthor();


                        //get the date the article was made
                        dateMain = mainObject.getString("date");
                        String dateSub[] = dateMain.split("T");
                        date = dateSub[0];
                        time = dateSub[1];
                        datetimeP = date + " " + time;




                        //get the excerpt
                        excerptFull = mainObject.getString("excerpt");
                        JSONObject excerptMain = new JSONObject(excerptFull);
                        excerpt = excerptMain.getString("rendered");
                        excerpt = excerpt.substring(3, excerpt.length() - 5);
                        excerpt = excerpt.replaceAll("\\u00ad","");
                        excerptStr = HtmlCompat.fromHtml(excerpt, HtmlCompat.FROM_HTML_MODE_LEGACY);
                        excerpt = excerptStr.toString();

                        //get the article's content
                        String contentArr2[];
                        contentFull = mainObject.getString("content");
                        JSONObject contentMain = new JSONObject(contentFull);
                        content = contentMain.getString("rendered");
                        String contentArr[] = content.split("clearfix"); //find beginning of article content
                        //for (int i = 2; i < contentArr.length; i = i+2) {
                        int contentLength = contentArr.length-1;
                        content = "";
                        if (contentLength < 3) {
                            contentLength = 3;
                        }
                        for (int i = 2; i < contentLength; i = i+2) {
                            contentArr2 = contentArr[i].split("/div>"); //find end of article content
                            content += contentArr2[0].substring(2, contentArr2[0].length() - 1) + " <br/><br/>";
                        }
                        content = content.replaceAll("\\u00ad","");

                        //get the main article picture by first getting the URL from the JSON got by the its URL in the Main JSON
                        JSONObject picMain = new JSONObject(links);
                        if(picMain.has("wp:featuredmedia")) {
                            link = picMain.getString("wp:featuredmedia");
                            subLink = link.substring(1, link.length() - 1);
                            JSONObject link2 = new JSONObject(subLink);
                            picURL = link2.getString("href");
                            tmpMainPic = picURL.split(":");
                            mainPicURL = "https:" + tmpMainPic[1];//the current implementation without a class does not allow the variable to be set in the scope of the method called here
                            pic[0] = mainPicURL;
                        }


                        //find and get any pictures in the article's content
                        pattern = Pattern.compile("<figure");
                        matcher = pattern.matcher(content);
                        txt = "";
                        String imageArr[] = new String[10];
                        int picPosStart[] = new int[10];
                        int picPosEnd[] = new int[10];
                        String tmpPic[] = new String[20];
                        int lastPicStart = 0;
                        int lastPicEnd = 0;
                        String strin = "";

                        int count = 0;
                        while (matcher.find()) {
                            //picPosStart[count] = content.indexOf("<figure",0); // find picture start position
                            count++;
                        }
                        count++; //offset for the loop starting at 1
                        //strin += count + ", ";
                        //articleList.append(strin);

                        String regexStart = "src="; //srcset=\\\\\"  or 768w,\s
                        String regexEnd = "alt="; //w,\\s   or \\s1600w
                        String picLink = "";

                        for (int i = 1; i <= count; i++) {
                            if (i != count) {
                                picPosStart[i] = content.indexOf("<figure", lastPicStart + 1); // find picture start position
                                picPosEnd[i] = content.indexOf("figure>", lastPicEnd + 1); // find picture start position
                                pic[i] = content.substring(picPosStart[i], picPosEnd[i] + 1);
                                tmpPic = pic[i].split(regexStart);
                                tmpPic = tmpPic[1].split(regexEnd);
                                picLink = tmpPic[0].substring(1,tmpPic[0].length()-2);
                                pic[i] = picLink;
                                tmpPic = pic[i].split(":");
                                pic[i] = "https:" + tmpPic[1];
                            } else {
                                picPosStart[i] = content.length();
                            }
                            // set to be the String of where end is plus 1 of that position
                            if (i != 1) {
                                lastPicEnd += 7;
                            }
                            //lastPicEnd += 7;

                            //build the text content back up delimited by a square
//                            if (i != count) {
//                                txt += content.substring(lastPicEnd, picPosStart[i]) + "\u25a1";
//                            } else {
                                txt += content.substring(lastPicEnd, picPosStart[i]);
//                            }

                            lastPicStart = picPosStart[i];
                            lastPicEnd = picPosEnd[i];
                        }

                        Spanned str = HtmlCompat.fromHtml(txt, HtmlCompat.FROM_HTML_MODE_LEGACY);
                        content = str.toString();

                        //append the pieces together
                        String articleVals = id + "~" + title + "~" + datetimeP;
                        articleVals += "~" + excerpt + "~" + content + "~" + cat;
                        getImageURL(pic, authorURL, articleVals);

                    } else {
                        Log.i("The MNA - jsonParse","Post category is job. Skipping retrieval");
                        Toast.makeText(getApplicationContext(), "Cannot retrieve job posts", Toast.LENGTH_LONG).show();
                    }

            } catch (JSONException e) {
                articleList.append("Error, article URL GET ran into an error.");
            }


                String output = id + "~" + authorName + "~" + title + "~" + datetimeP;
                output += "~" + dateFormater.format(now) + "~" + cat + "~" + excerpt;
                output += "~" + content;
                String urls = mainPicURL + "~" + pic[1] + "~" + pic[2];
                //articleTitle.append(pic[1]);
                //articleList.append(output+"\n");


                articleTitle.append(" Article: "+id+" ");
                //allArticles.set(id, output);
                //theArticles[0] = output;
                allArticles.add(output);

                //this will be a database insert/update
                //printArticle(id, allArticles, urls);
                //put into shared preferences
                //editor.putString("NewestWPArticle", id);
                //editor.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "URL GET error occurred", Toast.LENGTH_LONG).show();
            }
        });

        rQueue.add(request);

    }


    private void parseID (int postNum) {
        url = "https://themetropolitan.metrostate.edu/wp-json/wp/v2/posts/?orderby=date&per_page=1&page="+postNum;


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.charAt(0) == '[') {
                    response = response.substring(1, response.length() - 1);
                }

                //variable declarations
                JSONObject mainObject;
                String id;
                String cat;

                try {
                    mainObject = new JSONObject(response);
                    //get the first article's ID
                    id = mainObject.getString("id");

                    cat = mainObject.getString("categories");
                    switch (cat) {
                        case "[12]":
                            cat = "good"; //Tech
                            break;
                        case "[11]":
                            cat = "good"; //Student Life
                            break;
                        case "[10]":
                            cat = "good"; //Opinion
                            break;
                        case "[5]":
                            cat = "good"; //Arts & Entertainment
                            break;
                        case "[8]":
                            cat = "good"; //News
                            break;
                        case "[159]":
                            cat = "bad"; //Jobs
                            break;
                        case "[1]":
                            cat = "bad"; //Uncategorized
                            break;
                        default:
                            cat = "bad"; //unknown
                    }
                    if (cat == "bad") {
                        id = "invalid post type";
                    }

                    //put into shared preferences
                    editor.putString("NewestWPArticle", id);
                    editor.commit();
                } catch (JSONException e){
                    articleList.append("Error, article ID URL GET ran into an error.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ID URL GET error occurred", Toast.LENGTH_LONG).show();
            }
        });

        rQueue.add(request);
    }


//    private String isAnArticle (int postID, String type) {
//        if (type == "age") {
//            url = "https://themetropolitan.metrostate.edu/wp-json/wp/v2/posts/?orderby=date&per_page=1&page="+postID;
//        } else if (type == "id") {
//            url = "https://themetropolitan.metrostate.edu/wp-json/wp/v2/posts/"+postID;
//        } else {
//            url = "https://themetropolitan.metrostate.edu/wp-json/wp/v2/posts/?orderby=date&per_page=1&page=1";
//        }
//
//        sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
//        editor = sharedpreferences.edit();
//
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.charAt(0) == '[') {
//                    response = response.substring(1, response.length() - 1);
//                }
//
//                //variable declarations
//                JSONObject mainObject;
//                String cat = "";
//
//                try {
//                    mainObject = new JSONObject(response);
//                    //get the category
//                    cat = mainObject.getString("categories");
//                    switch (cat) {
//                        case "[12]":
//                            cat = "yes"; //Tech
//                            break;
//                        case "[11]":
//                            cat = "yes"; //Student Life
//                            break;
//                        case "[10]":
//                            cat = "yes"; //Opinion
//                            break;
//                        case "[5]":
//                            cat = "yes"; //Arts & Entertainment
//                            break;
//                        case "[8]":
//                            cat = "yes"; //News
//                            break;
//                        case "[159]":
//                            cat = "no"; //Jobs
//                            break;
//                        case "[1]":
//                            cat = "no"; //Uncategorized
//                            break;
//                        default:
//                            cat = "no"; //unknown
//                    }
//
//
//
//                } catch (JSONException e){
//                    articleList.append("Error, article ID URL GET ran into an error.");
//                }
//                editor.putString("IsAnArticle", cat);
//                editor.apply();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "ID URL GET error occurred", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        rQueue.add(request);
//        articleTitle.append(sharedpreferences.getString("IsAnArticle",""));
//        return "no";
//    }


    private void printArticle(String id, ArrayList<String> mainContent, String urls){//, Bitmap pic
//        mainContent = id + "~" + authorName + "~" + title + "~" + datetimeP;
//        mainContent += "~" + dateFormater.format(now) + "~" + cat + "~" + excerpt;
//        mainContent += "~" + content;
//        urls = mainPicURL+"~"+pic[0]+"~"+pic[1];

        String theContents[] = new String[12];
        String theLinks[] = new String[3];

        //grab the article with the given ID from the List of articles
        for (String article: mainContent) {
            theContents = article.split("~");
            theLinks = urls.split("~");
            if (theContents[0] == id) {
                continue;
            }
        }

//        String theContents[] = new String[12];
//        String tmpArticle;
//        String articleElements[];
//
//        for (int i = 0; i < mainContent.size(); i++) {
//            tmpArticle = mainContent.get(i);
//            articleElements = tmpArticle.split("~");
//            if (articleElements[0] == id) {
//                theContents = articleElements;
//            }
//        }

        String toPrint = "Id: " + theContents[0] + " \n\nDatetime published: " + theContents[3];
        toPrint += "\nRetrieved: " + theContents[4] + "\n\n\n"+"Excerpt: " + theContents[6]+"\n\nAuthor: "+theContents[1];
        toPrint += "                                                Category: " + theContents[5] + "\n\n";
        toPrint += "\n\nContent: " + theContents[7] +"\n\n";
        toPrint += "Article Image JSON URL: "+theLinks[0]+"\nIn-text pic1: "+theLinks[1]+"\nIn-text pic2: "+ theLinks[2] +"\n\n";
        //String toPrint = "test string";
        articleTitle.append("Title: " + theContents[2]); //made a separate field for the title so this is writing it there
        articleList.append(toPrint);
    }


    //gets an article author's name and prints it
    private void getAuthorFromURL(final String [] picURLs, String authorURL, final String articleVals) {
        StringRequest requestA = new StringRequest(Request.Method.GET, authorURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String values[] = new String[6];
                boolean result;
                String category;
                String name = response.substring(10,52);
                String nameSub [] = name.split(":");
                String nameSub2 [] = nameSub[1].split(",");
                name = nameSub2[0].substring(1, nameSub2[0].length()-1);
                //String output = "\nAuthor: " + name + "\n";

                ArrayList<String> articleURLList = new ArrayList<>();
                for (int i = 0; i < picURLs.length; i++){
                    articleURLList.add(picURLs[i]);
                }

//                String articleVals = id + "~" + title + "~" + datetimeP;
//                articleVals += "~" + excerpt + "~" + content + "~" + cat;
                values = articleVals.split("~");


                switch (values[5]) {
                    case "[12]": //Tech
                        result = controller.add_Tech_Article(values[0],values[1],name, values[2],values[3],values[4], "Tech",articleURLList);
                        if (result) {
                            Toast.makeText(getApplicationContext(), "Error putting article into Firebase - Tech", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Tech article to Firebase", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "[11]": //Student Life
                        result = controller.add_Life_Article(values[0],values[1],name, values[2],values[3],values[4], "Student Life",articleURLList);
                        if (result) {
                            Toast.makeText(getApplicationContext(), "Error putting article into Firebase - Student Life", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Student Life article to Firebase", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "[10]": //Opinion
                        result = controller.add_Opinion_Article(values[0],values[1],name, values[2],values[3],values[4], "Opinion",articleURLList);
                        if (result) {
                            Toast.makeText(getApplicationContext(), "Error putting article into Firebase - Opinion", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Opinion article to Firebase", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "[5]": //Arts & Entertainment
                        result = controller.add_Art_Article(values[0],values[1],name, values[2],values[3],values[4], "Arts & Entertainment",articleURLList);
                        if (result) {
                            Toast.makeText(getApplicationContext(), "Error putting article into Firebase - A & E", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Arts & Entertainment article to Firebase", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "[8]": //News
                        result = controller.add_News_Article(values[0],values[1],name, values[2],values[3],values[4], "News",articleURLList);
                        if (result) {
                            Toast.makeText(getApplicationContext(), "Error putting article into Firebase - News", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "News article to Firebase", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "[159]": //Jobs
                        Toast.makeText(getApplicationContext(), "Jobs post, do nothing", Toast.LENGTH_LONG).show();
                        break;
                    case "[1]": //Uncategorized
                        Toast.makeText(getApplicationContext(), "Uncategorized post, do nothing", Toast.LENGTH_LONG).show();
                        break;
                    default: //unknown
                        Toast.makeText(getApplicationContext(), "Unknown post, do nothing", Toast.LENGTH_LONG).show();
                }
                //setAuthor(name);
                author = name;
                //articleList.append(name);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error occurred in getAuthor", Toast.LENGTH_LONG).show();
            }
        });

        rQueue.add(requestA);
    }


    private void getImageURL(final String [] imgURLs, final String authorURL, final String articleVals) {
        String mainImageJSON = imgURLs[0];

        if (mainImageJSON == null) {
            imgURLs[0] = "";
            getAuthorFromURL(imgURLs, authorURL, articleVals);
        } else {
            StringRequest request = new StringRequest(Request.Method.GET, mainImageJSON, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String pic[] = new String[10];
                    if (response.charAt(0) == '[') {
                        response = response.substring(1, response.length() - 1);
                    }

                    String picURLFull;
                    String pictureURL;
                    String captionFull;
                    String caption;
                    String tmpUrlPieces[];

                    //articleList.setText(response);
                    try {
                        //turning the full string response from the url get into a JSON object
                        JSONObject mainObject = new JSONObject(response);

                        //get the caption
//                    captionFull = mainObject.getString("caption");
//                    JSONObject captionMain = new JSONObject(captionFull);
//                    caption = captionMain.getString("rendered");
//                    Spanned str = HtmlCompat.fromHtml(caption, HtmlCompat.FROM_HTML_MODE_LEGACY);
//                    caption = str.toString();

                        //get the final picture's URL as https
                        picURLFull = mainObject.getString("guid");
                        JSONObject picMain = new JSONObject(picURLFull);
                        pictureURL = picMain.getString("rendered");
                        //turn url into https from http
                        tmpUrlPieces = pictureURL.split(":");
                        pic[0] = "https:" + tmpUrlPieces[1];
                        for (int i = 1; i < imgURLs.length; i++) {
                            pic[i] = imgURLs[i];
                        }


                        getAuthorFromURL(pic, authorURL, articleVals);
                        //articleList.append(pictureURL+" stuff\n\n\n\n\n\n\n");
                        //add the image url to the first spot in the database's article picture array

                    } catch (JSONException e) {
                        articleList.append("Error, picture URL GET ran into an error.");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error occurred in getImageURL", Toast.LENGTH_LONG).show();
                }
            });

            rQueue.add(request);
        }
    }


}
