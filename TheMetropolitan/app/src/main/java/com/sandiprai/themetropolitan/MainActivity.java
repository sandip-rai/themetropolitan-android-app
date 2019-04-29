package com.sandiprai.themetropolitan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    //String url = "http://themetropolitan.metrostate.edu/wp-json/wp/v2/posts?fields=id,excerpt,title,content,date";
    String url;
    TextView articleList;
    Bitmap articleImg;
    Bitmap testImg;
    NetworkImageView theImg;
    ArrayList<String> allArticles = new ArrayList<>();
    //String articleContents = "[contents here]\n";
    ProgressDialog progressDialog;
    //private ListView postList;
    private RequestQueue rQueue;
    public SharedPreferences sharedPreferences;
    int postID = 1489;
    String author = null;
    String authorURL = null;
    String picURL = "";
    String mainPicURL = "";
    String titlePic = null;
    String mainArticleContent = null;
    StringRequest request;
    public static final int NOTIFICATION_ID = 5453;
    public static final String CHANNEL_ID = "new_articles";
    NotificationsHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //This line changes the app's theme. Change the MODE_NIGHT to NO for light theme.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);

        //setContentView(R.layout.activity_main);
//        if (InitApplication.getInstance().isNightModeEnabled()) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
        setContentView(R.layout.activity_main);

        articleList = (TextView)findViewById(R.id.articles);
        theImg = (NetworkImageView)findViewById(R.id.imageView);
        rQueue = Volley.newRequestQueue(MainActivity.this);
        //rQueue.start();
        //progressDialog = new ProgressDialog(MainActivity.this);
        ///progressDialog.setMessage("Loading...");
       // progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.show();

        jsonParse(postID);
        //articleList.append(articleContents);
        //getAuthorFromURL("sadf");
        //getTextImageFromURL("afs");
        //printArticle(getAuthorURL(), "testing~testificate~ererwerv~454343gg~placeholder~ghahrah~reaeharae~rheja5j~kaaykak~dummy data~ahreh~aehh6jh");

        //none of the getters working at all
        //printArticle(tmpVar+"", "test~testing~tst~moar test~tests n stuff~moar tests and stuff~test~test~even more testing~testing initiative~aperture~science");
        //Notifications newArticleNotify = new Notifications();

        //start the background service to periodically check for new articles, prints after every one minute currently
        PeriodicArticleCheck.enqueueWork(MainActivity.this,new Intent());

        //newArticleNotify.makeNotification();

        //Get the toolbar and load it as the main toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        //Following code is for having the title in the center; otherwise it would be aligned to
        //left as default
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String appName = "The Metropolitan";
        toolbarTitle.setText(appName.toUpperCase());

        //createNotificationChannel();
        //sendNotification(findViewById(R.id.notify)); //send a notification from the main activity method

        //create a notification from the main activity
        helper = new NotificationsHelper(this);
        String textTitle = "Some title text";
        String textContent = "The content text";
        NotificationCompat.Builder builder = helper.getChannelNotification(textTitle,textContent);
        //helper.getManager().notify(NOTIFICATION_ID,builder.build());

        //Call this method to setup the bottom navbar
        setupBottomNavigationView();

    }




//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(NotificationsHelper.CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }

//    public void sendNotification(View view) {
//        Intent actionIntent = new Intent(this, MainActivity.class);
//        PendingIntent actionPendingIntent = PendingIntent.getActivity(this,0,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this, Notifications.CHANNEL_ID)
//                        .setSmallIcon(R.drawable.logo2)
//                        .setContentTitle("When app opens")
//                        .setContentText("This will check for new articles.")
//                        .setAutoCancel(true);
//
//
//        mBuilder.setContentIntent(actionPendingIntent);
//        // Gets an instance of the NotificationManager service//
//
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // When you issue multiple notifications about the same type of event,
//        // it’s best practice for your app to try to update an existing notification
//        // with this new information, rather than immediately creating a new notification.
//        // If you want to update this notification at a later date, you need to assign it an ID.
//        // You can then use this ID whenever you issue a subsequent notification.
//        // If the previous notification is still visible, the system will update this existing notification,
//        // rather than create a new one. In this example, the notification’s ID is 001//
//
//        mNotificationManager.notify(001, mBuilder.build());
//    }


    public void setTestImg(Bitmap testImg) {
        this.testImg = testImg;
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


    private void jsonParse(int postID) {
        //final ArrayList<String> theArticles = new ArrayList<String>();
        String theArticles;
        url = "https://themetropolitan.metrostate.edu/wp-json/wp/v2/posts/"+postID;
        //String articleContents;

        request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.charAt(0) == '['){
                    response = response.substring(1,response.length()-1);
                }

                int id = 0;
                String titleFull;
                String title = "";
                String links;
                String name2;
                String authorName = null;
                String dateMain;
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz"); //HH (0-23 hours), hh (normal hrs), a (AM/PM), z (timezone);
                String date = "";
                String time = "";
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
                String tisNull;
                String txt;
                String link;
                String subLink;
                String pic[] = new String[10];

                //articleList.setText(response);
                try {
                    //turning the full string response from the url get into a JSON object
                    JSONObject mainObject = new JSONObject(response);
                    //get the first article's ID
                    id = Integer.parseInt(mainObject.getString("id"));

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
                    //articleList.append(author+"\n");
                    //authorName = getAuthor();


                    //get the date the article was made
                    dateMain = mainObject.getString("date");
                    String dateSub [] = dateMain.split("T");
                    date = dateSub[0];
                    time = dateSub[1];
                    datetimeP = date + " " + time;

                    //dateFormater.format(now);

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
                    //articleList.append(picURL+"\n");
                    getImageURL(picURL);//the current implementation without a class does not allow the variable to be set in the scope of the method called here
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
                    count++; //offset for the loop starting at 1
                    //strin += count + ", ";
                    //articleList.append(strin);

                    for (int i = 1; i <= count; i++) {
                        if (i != count) {
                            picPosStart[i] = content.indexOf("<figure",lastPicStart+1); // find picture start position
                            picPosEnd[i] = content.indexOf("figure>", lastPicEnd+1); // find picture start position
                            pic[i] = content.substring(picPosStart[i], picPosEnd[i] + 1);
                            tmpPic = pic[i].split("srcset=");
                            tmpPic = tmpPic[1].split("w,");
                            pic[i] = tmpPic[1].substring(1, tmpPic[1].length()-4); //variable for in-text pic URLs
                            //imageArr[i] = getImageFromURL(pic[i]);
                        } else {
                            picPosStart[i] = content.length();
                        }
                        // set to be the String of where end is plus 1 of that position
                        if (i != 1) {
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
                    //getImageFromURL(pic[0]);

                    //mImageLoader = new processImage();
                    //rQueue.add(processImage.getInstance().getmRequestQueue());
//                    mImageLoader = processImage.getInstance().getmImageLoader();
//                    theImg.setDefaultImageResId(R.drawable.logo2);
                    //theImg.setErrorImageResId(720);
                    //theImg.setImageUrl(pic[0],mImageLoader);


                    tisNull = "Pic is null";
                    //Bitmap cpyPic = getArticleImg();
                    //theImg.setImageBitmap(cpyPic);
                    Bitmap bit = getArticleImg();
                    if (bit != null){ tisNull = "Pic not null\n"; }

                    //String outpt = "pic: " + imageArr[1] + ", ";
                    //articleList.append(outpt);

                    Spanned str = HtmlCompat.fromHtml(txt, HtmlCompat.FROM_HTML_MODE_LEGACY);
                    content = str.toString();
                    //append the pieces together to print
//                    String output = id + "~" + title + "~" + date + "~" + time;
//                    output += "~" + dateFormater.format(now) + "~" + cat + "~" + excerpt;
//                    output += "~" + content + "~"+getAuthor()+"~"+pic[0];//output += "~" + content + "~"+tisNull+"~"+pic[0];

                    //create a string output for all the information delimited by tilde
                    String output = id + "~" + authorName + "~" + title + "~" + datetimeP;
                    output += "~" + dateFormater.format(now) + "~" + cat + "~" + excerpt;
                    output += "~" + content +"~"+pic[0]+"~"+pic[1]+"~"+pic[2];
                    //articleList.append(output+"\n");

                    String articles = output;
                    //theArticles.add(output);
                    //articleList.append("This is an inner append\n"+theArticles.get(0)+"\n");
                    //allArticles.add(output);
//                    if (allArticles.isEmpty()) {
//                        articleList.append("This inner content is empty\n");
//                    }
                    //end of article main get print all pieces from a delimited string
                    printArticle(Integer.toString(id), articles);

                    //theArticles.add(output);
                    //articleList.append("This is an inner append\n"+theArticles.get(0)+"\n");
                    //allArticles.add(output);
//                    if (allArticles.isEmpty()) {
//                        articleList.append("This inner content is empty\n");
//                    }
                //printArticle(Integer.toString(id), theArticles);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "URL GET error occurred", Toast.LENGTH_LONG).show();
            }
        });


        rQueue.add(request);
        //return theArticles[0];
    }


    //break the variables in the string out and display them
    private void printArticle(String id, String mainContent) {//, Bitmap pic
//        String output = "Id: " + id + " \nTitle: " + title + " \n\n\nDate made: " + date + " \nTime made: " + time;
//        output += "\nRetrieved: " + dateFormater.format(now) + "\n\n\n\n\n\n\nCategory: " + cat + "\nExcerpt: " + excerpt;
//        output += "\n\nContent: " + content + "\n"+tisNull+"\n"+pic[0]+"\n\n\n";
        String theContents[] = mainContent.split("~");
        //articleContents = mainContent;
        //grab the article with the given ID from the List of articles
//        for (String article: mainContent) {
//            theContents = article.split("~");
//            if (theContents[0] == id) {
//                continue;
//            }
//        }

        theImg.setDefaultImageResId(R.drawable.logo2);
        String toPrint = "Id: " + theContents[0] + " \nTitle: " + theContents[2] + " \n\nDatetime: " + theContents[3];
        toPrint += "\nRetrieved: " + theContents[4] + "\n\n\n"+"Excerpt: " + theContents[6]+"\n\nAuthor: "+theContents[1];
        toPrint += "                                                Category: " + theContents[5] + "\n\n";
        toPrint += "\n\nContent: " + theContents[7] +"\n\n";
        toPrint += "first picture: "+theContents[8]+"\nIn-text pic1: "+theContents[9]+"\nIn-text pic2: "+ theContents[10]+"\n\n";
                //String toPrint = "test string";
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
                articleList.append("In getAuthorFromURL, the author's name is: "+name+"\n\n\n\n");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error occurred in getAuthor", Toast.LENGTH_LONG).show();
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

                String picURLFull;
                String pictureURL;
                String captionFull;
                String caption;
                String tmpUrlPieces[];


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
                    pictureURL = "https:"+tmpUrlPieces[1];

                    articleList.append(pictureURL+" stuff\n\n\n\n\n\n\n");
                    //add the image url to the first spot in the database's article picture array


                } catch (JSONException e){
                    articleList.append("Error, picture URL GET ran into an error.");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error occurred in getImageURL", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(MainActivity.this, "Error occurred in getting the image", Toast.LENGTH_LONG).show();
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


//    private void setupViewPager(ViewPager viewPager){
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new TopStoriesFragment(), "Top Stories");
////        adapter.addFragment(new TwoFragment(), "TWO");
////        adapter.addFragment(new ThreeFragment(), "THREE");
//        viewPager.setAdapter(adapter);
//    }

//    class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }

    /*This method setsup the bottom nav bar*/
    private void setupBottomNavigationView(){
        //Get the bottom navbar
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.bottom_navigation);


        //Listener for the bottom navbar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                FragmentTransaction fragmentTransaction;
                switch (item.getItemId()){
                    case R.id.bottom_news:
                        //If news is clicked, load the NewsFragment to the Framelayout named
                        //frame_saved in the activity_main xml
                        fragment = new NewsFragment();
                        loadFragment(fragment);
//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.frame_saved, fragment);
//                        fragmentTransaction.commit();
                        break;
                    case R.id.bottom_saved:
                        //For the saved
                        fragment = new SavedFragment();
                        loadFragment(fragment);

//                        startActivity(intent);
//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.frame_saved, fragment);
//                        fragmentTransaction.commit();
                        break;
                    case R.id.bottom_search:
                        //For the search
                        fragment = new SearchFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.bottom_settings:
                        //For the settings
                        fragment = new SettingsFragment();
                        loadFragment(fragment);
                        break;
                }
                return true;
            }
        });

        //Set the default view in the bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.bottom_news);
    }

    /*This method loads a passed fragment to the layout frame_saved in the activity_main xml
    * Will be changed eventually to be more dynamic */
    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.replace(R.id.frame_saved, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*Below methods are automatically created*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_sync_now:
//                CharSequence text = "Sync Now button clicked!";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(this,text,duration);
//                toast.show();
//                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void checkForNewArticleCards() {
        
        return;
    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        return false;
//    }

    private void toggleTheme(boolean darkTheme){
        SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        editor.putBoolean("dark_theme", darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }

    public void themeDarkOn(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Intent i = new Intent(MainActivity.this, ArticlePage.class);
        startActivity(i);
    }

    public void themeLightOn(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Intent i = new Intent(MainActivity.this, ArticlePage.class);
        startActivity(i);
    }
}

