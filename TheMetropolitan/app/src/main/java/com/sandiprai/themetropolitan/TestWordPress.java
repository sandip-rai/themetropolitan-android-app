package com.sandiprai.themetropolitan;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
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
    int postID = 1489;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_word_press);

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
        jsonParse(postID);
        //String itter = tmpList.iterator().toString();
        //allArticles.set(Integer.parseInt(itter),tmpList.get(Integer.parseInt(itter)));
        //getAuthorFromURL("http://themetropolitan.metrostate.edu/wp-json/wp/v2/users/22");
//        if(tmpList == null){
//            articleList.append("The list is empty in onCreate\n");
////        } else {
////            for (int i = 0; i < allArticles.size(); i++) {
////                postContent = allArticles.get(i);
////                postElements = postContent.split("~");
////                postIDs[postIDs.length-1] = postElements[0];
////                postIndex = i;
////                //printArticle(postIDs[i],allArticles);
////            }
//        }
    }

    public void setArticleImg(Bitmap articleImg) {
        this.articleImg = articleImg;
    }

    public Bitmap getArticleImg() {
        return articleImg;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorURL() {
        return authorURL;
    }

    public void setAuthorURL(String authorURL) {
        this.authorURL = authorURL;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getMainArticleContent() {
        return mainArticleContent;
    }

    public void setMainArticleContent(String mainArticleContent) {
        this.mainArticleContent = mainArticleContent;
    }



    private String jsonParse(int postID) {
        final String[] theArticles = new String[1];
        url = "https://themetropolitan.metrostate.edu/wp-json/wp/v2/posts/"+postID;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.charAt(0) == '['){
                    response = response.substring(1,response.length()-1);
                }


                JSONObject mainObject;
                int id = 0;
                String articleLink = "";
                String titleFull;
                String title = "";
                String links;
                String name2;
                String authorName = null;
                String dateMain;
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz"); //HH (0-23 hours), hh (normal hrs), a (AM/PM), z (timezone);
                String date = "";
                String time = "";
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
                String tisNull;
                String txt;
                String link;
                String subLink;
                String pic[] = new String[10];

                //articleList.setText(response);
                try {
                    //turning the full string response from the url get into a JSON object
                    mainObject = new JSONObject(response);
                    //get the first article's ID
                    id = Integer.parseInt(mainObject.getString("id"));

                    articleLink = mainObject.getString("link");

                    //get the title
                    titleFull = mainObject.getString("title");
                    JSONObject titleMain = new JSONObject(titleFull);
                    title = titleMain.getString("rendered");

                    //get the author's name from the article's name url
                    links = mainObject.getString("_links");
                    JSONObject nameMain = new JSONObject(links);
                    name2 = nameMain.getString("author");
                    name2 = name2.substring(1,name2.length()-1);
                    JSONObject nameMain2 = new JSONObject(name2);
                    authorURL = nameMain2.getString("href");
                    //setAuthorURL(name3); //call the name url to get the author's name from it
                    getAuthorFromURL(authorURL);
                    authorName = author;
                    //articleList.append(authorName);
                    //String Author = getAuthor();


                    //get the date the article was made
                    dateMain = mainObject.getString("date");
                    String dateSub [] = dateMain.split("T");
                    date = dateSub[0];
                    time = dateSub[1];


                    //get the category
                    cat = mainObject.getString("categories");
                    switch (cat){
                        case "[12]":
                            cat = "Tech";
                            break;
                        case "[11]":
                            cat = "Student Life";
                            break;
                        case "[10]":
                            cat = "Opinion";
                            break;
                        case "[5]":
                            cat = "Arts & Entertainment";
                            break;
                        case "[8]":
                            cat = "News";
                            break;
                        case "[159]":
                            cat = "Jobs";
                            break;
                        default:
                            cat = "unknown";
                    }

                    //get the excerpt
                    excerptFull = mainObject.getString("excerpt");
                    JSONObject excerptMain = new JSONObject(excerptFull);
                    excerpt = excerptMain.getString("rendered");
                    excerpt = excerpt.substring(3,excerpt.length()-5);
                    excerptStr = HtmlCompat.fromHtml(excerpt, HtmlCompat.FROM_HTML_MODE_LEGACY);
                    excerpt = excerptStr.toString();

                    //get the article's content
                    contentFull = mainObject.getString("content");
                    JSONObject contentMain = new JSONObject(contentFull);
                    content = contentMain.getString("rendered");
                    String contentArr [] = content.split("clearfix"); //find beginning of article content
                    String contentArr2 [] = contentArr[2].split("/div>"); //find end of article content
                    content = contentArr2[0].substring(5, contentArr2[0].length()-5);

                    //get the main article picture by first getting the URL from the JSON got by the its URL in the Main JSON
                    JSONObject picMain = new JSONObject(links);
                    link = picMain.getString("wp:featuredmedia");
                    subLink = link.substring(1,link.length()-1);
                    JSONObject link2 = new JSONObject(subLink);
                    picURL = link2.getString("href");
                    //getImageURL(mainPicURL);//the current implementation without a class does not allow the variable to be set in the scope of the method called here
                    mainPicURL = picURL;

                } catch (JSONException e){
                    articleList.append("Error, article URL GET ran into an error.");
                }
                //find and get any pictures in the article's content
                pattern = Pattern.compile("<figure");
                matcher = pattern.matcher(content);
                txt = "";
                String imageArr[] = new String[10];
                int picPosStart[] = new int[10];
                int picPosEnd[] = new int[10];
                String tmpPic [] = new String[6];
                int lastPicStart = 0;
                int lastPicEnd = 0;
                String strin = "";

                int count = 0;
                while (matcher.find()){
                    //picPosStart[count] = content.indexOf("<figure",0); // find picture start position
                    count++;
                }
                //strin += count + ", ";
                //articleList.append(strin);

                for (int i = 0; i <= count; i++) {
                    if (i != count) {
                        picPosStart[i] = content.indexOf("<figure",lastPicStart+1); // find picture start position
                        picPosEnd[i] = content.indexOf("figure>", lastPicEnd+1); // find picture start position
                        pic[i] = content.substring(picPosStart[i], picPosEnd[i] + 1);
                        tmpPic = pic[i].split("srcset=");
                        tmpPic = tmpPic[1].split("w,");
                        pic[i] = tmpPic[1].substring(1, tmpPic[1].length()-4);
                        //imageArr[i] = getImageFromURL(pic[i]);
                    } else {
                        picPosStart[i] = content.length();
                    }
                    // set to be the String of where end is plus 1 of that position
                    if (i != 0) {
                        lastPicEnd += 7;
                    }
                    //lastPicEnd += 7;

                    //build the text content back up delimited by a square
                    if (i != count) {
                        txt += content.substring(lastPicEnd, picPosStart[i])+"\u25a1";
                    } else {
                        txt += content.substring(lastPicEnd, picPosStart[i]);
                    }

                    lastPicStart = picPosStart[i];
                    lastPicEnd = picPosEnd[i];
                }

                //setPicURL(pic[0]);
//                getImageFromURL(pic[0]);
//                //mImageLoader = new processImage();
//                //rQueue.add(processImage.getInstance().getmRequestQueue());
//                mImageLoader = processImage.getInstance().getmImageLoader();
//                theImg.setDefaultImageResId(R.drawable.logo2);
                //theImg.setErrorImageResId(720);
                //theImg.setImageUrl(pic[0],mImageLoader);


                tisNull = "Pic is null";
                //Bitmap cpyPic = getArticleImg();
                //theImg.setImageBitmap(cpyPic);
//                Bitmap bit = getArticleImg();
//                if (bit != null){ tisNull = "Pic not null\n"; }

                //String outpt = "pic: " + imageArr[1] + ", ";
                //articleList.append(outpt);

                Spanned str = HtmlCompat.fromHtml(txt, HtmlCompat.FROM_HTML_MODE_LEGACY);
                content = str.toString();
                //append the pieces together to print
//                    String output = id + "~" + title + "~" + date + "~" + time;
//                    output += "~" + dateFormater.format(now) + "~" + cat + "~" + excerpt;
//                    output += "~" + content + "~"+getAuthor()+"~"+pic[0];//output += "~" + content + "~"+tisNull+"~"+pic[0];

                String output = id + "~" + authorName + "~" + title + "~" + date + "~" + time;
                output += "~" + dateFormater.format(now) + "~" + cat + "~" + excerpt;
                output += "~" + content;
                String urls = mainPicURL+"~"+pic[1] + "~" + articleLink;
                //articleList.append(output+"\n");


                //allArticles.set(id, output);
                //theArticles[0] = output;
                allArticles.add(output);

                //this will be a database insert/update
                printArticle(Integer.toString(id), allArticles, urls);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "URL GET error occurred", Toast.LENGTH_LONG).show();
            }
        });


        rQueue.add(request);
        return theArticles[0];
    }

    private void printArticle(String id, ArrayList<String> mainContent, String urls){//, Bitmap pic
//        String output = "Id: " + id + " \nTitle: " + title + " \n\n\nDate made: " + date + " \nTime made: " + time;
//        output += "\nRetrieved: " + dateFormater.format(now) + "\n\n\n\n\n\n\nCategory: " + cat + "\nExcerpt: " + excerpt;
//        output += "\n\nContent: " + content + "\n"+tisNull+"\n"+pic[0]+"\n\n\n";

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

        String toPrint = "Id: " + theContents[0] + " \n\nDate made: " + theContents[3] + " \nTime made: " + theContents[4];
        toPrint += "\nRetrieved: " + theContents[5] + "\n\n\n"+"Excerpt: " + theContents[7]+"\n\nAuthor: "+theContents[1];
        toPrint += "                                                Category: " + theContents[6] + "\n\n";
        toPrint += "\n\nContent: " + theContents[8] +"\n\n";
        toPrint += "Article Image URL: "+theLinks[0]+"\nIn-text pic: "+theContents[1]+"\nLink to article: "+theContents[2]+"\n\n";
        //String toPrint = "test string";
        articleTitle.append(theContents[2]);
        articleList.append(toPrint);
    }


    //gets an article author's name and prints it
    private void getAuthorFromURL(String authorURL) {
        StringRequest requestA = new StringRequest(Request.Method.GET, authorURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name = response.substring(10,52);
                String nameSub [] = name.split(":");
                String nameSub2 [] = nameSub[1].split(",");
                name = nameSub2[0].substring(1, nameSub2[0].length()-1);
                //String output = "\nAuthor: " + name + "\n";

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


    private void getImageURL(String imgURL) {
        StringRequest request = new StringRequest(Request.Method.GET, imgURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.charAt(0) == '['){
                    response = response.substring(1,response.length()-1);
                }

                int id = 0;
                String picURLFull;
                String pictureURL;
                String captionFull;
                String caption;
                //String txt;
                //String pic[] = new String[10];

                //articleList.setText(response);
                try {
                    //turning the full string response from the url get into a JSON object
                    JSONObject mainObject = new JSONObject(response);
                    //get the first article's ID
                    id = Integer.parseInt(mainObject.getString("id"));

                    //get the caption
                    captionFull = mainObject.getString("caption");
                    JSONObject captionMain = new JSONObject(captionFull);
                    caption = captionMain.getString("rendered");
                    Spanned str = HtmlCompat.fromHtml(caption, HtmlCompat.FROM_HTML_MODE_LEGACY);
                    caption = str.toString();

                    //get the final picture's URL
                    picURLFull = mainObject.getString("description");
                    JSONObject picMain = new JSONObject(picURLFull);
                    pictureURL = picMain.getString("rendered");
                    //pictureURL = pictureURL.substring(1,name2.length()-1);
                    mainPicURL = pictureURL;
                    //setAuthorURL(name3); //call the name url to get the author's name from it
                    //getImageFromURL(picURL);
                    // = articleImg;

                } catch (JSONException e){
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


    //gets an in-line article image and returns a bitmap as a string
    private void getImageFromURL(String imgJsonURL) {
        ImageRequest request = new ImageRequest(imgJsonURL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        //Bitmap test = null;
                        //setArticleImg(response);
                        articleImg = response;
                        theImg.setImageBitmap(response);
                        //theImg.getDrawable();
                    }
                }, 1920, 1080, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred in getting the image", Toast.LENGTH_LONG).show();
                    }
                });

        // https://cypressnorth.com/mobile-application-development/setting-android-google-volley-imageloader-networkimageview/
//        theImg.setImageUrl(imgURL,mImageLoader);
//
//        mImageLoader = new ImageLoader(this.rQueue, new ImageLoader.ImageCache() {
//            private final HashMap<String, Bitmap> mCache = new HashMap<>(10);
//            @Override
//            public Bitmap getBitmap(String url) {
//                return mCache.get(url);
//            }
//
//            @Override
//            public void putBitmap(String url, Bitmap bitmap) {
//                mCache.put(url, bitmap);
//            }
//        });

        rQueue.add(request);//*/
        //Bitmap blank = new Bitmap();
        //return "[image here]";
    }
}
