package com.sandiprai.themetropolitan;

import android.text.Spanned;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

public class DummyArticle extends AppCompatActivity {
    //This class can be used as reference and this is used to just test the RecycleView
    private static String articleTitle;
    private static String articleContent;
    private int imageId;


    public static final DummyArticle[]  articles = {
            new DummyArticle(articleTitle+"",  articleContent+"",
                    R.drawable.logo2),
            new DummyArticle(articleTitle+"",  articleContent+"",
                    R.drawable.logo2),
            new DummyArticle(articleTitle+"",  articleContent+"",
                    R.drawable.logo2),
            new DummyArticle(articleTitle+"",  articleContent+"",
                    R.drawable.logo2),
            new DummyArticle(articleTitle+"",  articleContent+"",
                    R.drawable.logo2),
    };

    private DummyArticle(String articleTitle, String articleContent, int imageId){
        this.articleContent = articleContent;
        this.articleTitle = articleTitle;
        this.imageId = imageId;
    }

    private void Article(){
        final TextView titleContent = (TextView)findViewById(R.id.title);
        final TextView mainContent = (TextView)findViewById(R.id.textBody);

        String url = "http://themetropolitan.metrostate.edu/wp-json/wp/v2/posts/1489";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.charAt(0) == '['){
                    response = response.substring(1,response.length()-1);
                }

                //articleList.setText(response);
                try {
                    //turning the full string response from the url get into a JSON object
                    JSONObject mainObject = new JSONObject(response);
                    //get the first article's ID
                    String id = mainObject.getString("id");

                    //get the title
                    String titleFull = mainObject.getString("title");
                    JSONObject titleMain = new JSONObject(titleFull);
                    String title = titleMain.getString("rendered");

                 /*   //get the author's name from the article's name url
                    String name1 = mainObject.getString("_links");
                    JSONObject nameMain = new JSONObject(name1);
                    String name2 = nameMain.getString("author");
                    name2 = name2.substring(1,name2.length()-1);
                    JSONObject nameMain2 = new JSONObject(name2);
                    String name3 = nameMain2.getString("href");
                    getAuthorFromURL(name3); //call the name url to get the author's name from it
                    String Author = nestedArticleInfo.getAuthor();
*/
                    //get the date the article was made
                    String dateMain = mainObject.getString("date");
                    String dateSub [] = dateMain.split("T");
                    String date = dateSub[0];
                    String time = dateSub[1];

                    //get the current date
                    Date now = new Date();
                    SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz"); //HH (0-23 hours), hh (normal hrs), a (AM/PM), z (timezone)
                    //dateFormater.format(now);

                    //get the category
                    String cat = mainObject.getString("categories");
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
                    String excerptFull = mainObject.getString("excerpt");
                    JSONObject excerptMain = new JSONObject(excerptFull);
                    String excerpt = excerptMain.getString("rendered");
                    excerpt = excerpt.substring(3,excerpt.length()-5);
                    Spanned excerptStr = HtmlCompat.fromHtml(excerpt, HtmlCompat.FROM_HTML_MODE_LEGACY);
                    excerpt = excerptStr.toString();

                    //get the article's content
                    String contentFull = mainObject.getString("content");
                    JSONObject contentMain = new JSONObject(contentFull);
                    String content = contentMain.getString("rendered");
                    String contentArr [] = content.split("clearfix"); //find beginning of article content
                    String contentArr2 [] = contentArr[2].split("/div>"); //find end of article content
                    content = contentArr2[0].substring(5, contentArr2[0].length()-5);

                    //find and get any pictures in the article's content
                    Pattern pattern = Pattern.compile("<figure");
                    Matcher matcher = pattern.matcher(content);
                    String txt[] = new String[10];
                    String pic[] = new String[10];
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
                    strin += count + ", ";
                    mainContent.append(strin);

                    for (int i = 0; i <= count; i++) {
                        if (i != count) {
                            picPosStart[i] = content.indexOf("<figure",lastPicStart+1); // find picture start position
                            picPosEnd[i] = content.indexOf("figure>", lastPicEnd+1); // find picture start position
                            pic[i] = content.substring(picPosStart[i], picPosEnd[i] + 1);
                            tmpPic = pic[i].split("srcset=");
                            tmpPic = tmpPic[1].split("w,");
                            pic[i] = tmpPic[1].substring(1, tmpPic[1].length()-4);
                            //imageArr[i] = getTextImageFromURL(pic[i]);
                        } else {
                            picPosStart[i] = content.length();
                        }
                        // set to be the String of where end is plus 1 of that position

                        if (i > 0){
                            lastPicEnd += 7;
                        }
                        txt[i] = content.substring(lastPicEnd, picPosStart[i]);
                        lastPicStart = picPosStart[i];
                        lastPicEnd = picPosEnd[i];
                    }


                    String outpt = "pic: " + imageArr[1] + ", ";
                    mainContent.append(outpt);


                    Spanned str = HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY);
                    content = str.toString();
                    //append the pieces together to print
                    //String output = "Response is: success! \nId: " + id + " \nTitle: " + title + " \n\n\nDate made: " + date + " \nTime made: " + time;
                   // output += "\nRetrieved: " + dateFormater.format(now) + "\n\n\n\n\n\n\nCategory: " + cat + "\nExcerpt: " + excerpt + "\n\nContent: " + content + "\n\n\n\n";

                    //new DummyArticle(title, content, 1000);
                    putArticleTitle(title);
                    putArticleContent(content);


                } catch (JSONException e){
                    mainContent.append("Response is: error!");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DummyArticle.this, "Error getting article", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void putArticleTitle(String title) { this.articleTitle = title; }

    public void putArticleContent(String content) { this.articleContent = content; }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public int getImageId() {
        return imageId;
    }
}
