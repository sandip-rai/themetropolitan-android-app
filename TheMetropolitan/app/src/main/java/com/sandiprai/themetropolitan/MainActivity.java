package com.sandiprai.themetropolitan;

import android.app.ProgressDialog;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    //String url = "http://themetropolitan.metrostate.edu/wp-json/wp/v2/posts?fields=id,excerpt,title,content,date";
    String url = "http://themetropolitan.metrostate.edu/wp-json/wp/v2/posts/1489";
    TextView articleList;
    Bitmap testImg;
    ImageView theImg;
    ProgressDialog progressDialog;
    //private ListView postList;
    private RequestQueue rQueue;
    int postID;
    String postExerpt[];
    String postTitle[];
    String postContent[];
    Date postDate[];
    String author = null;
    String authorURL = null;
    String picURL = null;
    String mainArticleContent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //This line changes the app's theme. Change the MODE_NIGHT to NO for light theme.

        super.onCreate(savedInstanceState);


        //setContentView(R.layout.activity_main);
//        if (InitApplication.getInstance().isNightModeEnabled()) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
        setContentView(R.layout.activity_main);

        articleList = (TextView)findViewById(R.id.articles);
        theImg = (ImageView)findViewById(R.id.imageView);
        rQueue = Volley.newRequestQueue(MainActivity.this);
        //rQueue.start();
        //progressDialog = new ProgressDialog(MainActivity.this);
        ///progressDialog.setMessage("Loading...");
       // progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.show();
        jsonParse();
        //doAuthorAndPicStuff(getPicURL(),getAuthorURL());
        getAuthorFromURL("sadf");
        //getTextImageFromURL("afs");
        printArticle(getAuthorURL(), "agrerag~sdaff ~ererwerv~454343gg~4g34ggg~ghahrah~reaeharae~rheja5j~kaaykak~ajja~ahreh~aehh6jh");



        //Get the toolbar and load it as the main toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        //Following code is for having the title in the center; otherwise it would be aligned to
        //left as default
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String appName = "The Metropolitan";
        toolbarTitle.setText(appName.toUpperCase());

        //Test for article page button
//        MaterialButton articleButton = findViewById(R.id.goto_article_page_button);
//        articleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,ArticlePage.class);
//                MainActivity.this.startActivity(intent);
//            }
//        });

//        These code are being used as test for changing app's theme and loading fragment purposes.

        //Switch switchCompat = findViewById(R.id.themeSwitch);

//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
//            switchCompat.setChecked(true);
//        }


/*        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    InitApplication.getInstance().setIsNightModeEnabled(true);
                   Intent intent = getIntent();
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                   finish();
 startActivity(intent);
                } else {
                   InitApplication.getInstance().setIsNightModeEnabled(false);
                   Intent intent = getIntent();
                   //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                   finish();
                   startActivity(intent);
                }


            }
        });*/

//



//        TabLayout tabLayout = findViewById(R.id.newsTab);
//        LinearLayout container = findViewById(R.id.fragmentContainerInNews);
//
//        tabLayout.addTab(tabLayout.newTab().setText("s"));


//        ViewPager viewPager = findViewById(R.id.viewPager);
//        setupViewPager(viewPager);

//        TabLayout tabLayout = findViewById(R.id.newsTab);
//        tabLayout.setupWithViewPager(viewPager);


//        Fragment fragment = new HelloFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.frame_content, fragment);
//        fragmentTransaction.commit();

//        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
//                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();


        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        //Call this method to setup the bottom navbar
        setupBottomNavigationView();

    }


    public void setTestImg(Bitmap testImg) {
        this.testImg = testImg;
    }

    public Bitmap getTestImg() {
        return testImg;
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

    private void jsonParse() {

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

                    //get the author's name from the article's name url
                    String name1 = mainObject.getString("_links");
                    JSONObject nameMain = new JSONObject(name1);
                    String name2 = nameMain.getString("author");
                    name2 = name2.substring(1,name2.length()-1);
                    JSONObject nameMain2 = new JSONObject(name2);
                    String name3 = nameMain2.getString("href");
                    setAuthorURL(name3); //call the name url to get the author's name from it
                    //String Author = getAuthor();

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
                    String txt = "";
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
                            //imageArr[i] = getTextImageFromURL(pic[i]);
                        } else {
                            picPosStart[i] = content.length();
                        }
                        // set to be the String of where end is plus 1 of that position
                        lastPicEnd += 7;

                        //build the text content back up delimited by a square
                        if (i != count) {
                            txt += content.substring(lastPicEnd, picPosStart[i])+"\u25a1";
                        } else {
                            txt += content.substring(lastPicEnd, picPosStart[i]);
                        }

                        lastPicStart = picPosStart[i];
                        lastPicEnd = picPosEnd[i];
                    }

                    setPicURL(pic[0]);

                    String tisNull = "Pic is null";
                    Bitmap cpyPic = getTestImg();
                    theImg.setImageBitmap(cpyPic);
                    if (cpyPic != null){ tisNull = "Pic not null\n"; }

                    //String outpt = "pic: " + imageArr[1] + ", ";
                    //articleList.append(outpt);

                    Spanned str = HtmlCompat.fromHtml(txt, HtmlCompat.FROM_HTML_MODE_LEGACY);
                    content = str.toString();
                    //append the pieces together to print
                    String output = id + "~" + title + "~" + date + "~" + time;
                    output += "~" + dateFormater.format(now) + "~" + cat + "~" + excerpt;
                    output += "~" + content + "~"+name3+"~"+pic[0];//output += "~" + content + "~"+tisNull+"~"+pic[0];

                    setMainArticleContent(output);

                } catch (JSONException e){
                    articleList.append("Response is: error!");
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "URL GET error occurred", Toast.LENGTH_LONG).show();
            }
        });



        rQueue.add(request);
    }

//    private void doAuthorAndPicStuff(String authorURL, String picURL) {
//        String authURL = getAuthorURL();
//        String pictURL = getPicURL();
//        getAuthorFromURL(authURL);
//        getTextImageFromURL(pictURL);
//
//        String author = getAuthor();
//        String articleContents = getMainArticleContent();
//        Bitmap pic = getTestImg();
//
//        printArticle(author, articleContents, pic);
//    }

    private void printArticle(String author, String mainContent){//, Bitmap pic
//        String output = "Id: " + id + " \nTitle: " + title + " \n\n\nDate made: " + date + " \nTime made: " + time;
//        output += "\nRetrieved: " + dateFormater.format(now) + "\n\n\n\n\n\n\nCategory: " + cat + "\nExcerpt: " + excerpt;
//        output += "\n\nContent: " + content + "\n"+tisNull+"\n"+pic[0]+"\n\n\n";

        String[] theContents = mainContent.split("~");
        String toPrint = "Id: " + theContents[0] + " \nTitle: " + theContents[2] + " \n\n\nDate made: " + theContents[3] + " \nTime made: " + theContents[4];
        toPrint += "\nRetrieved: " + theContents[5] + "\n\n\n\n\n\nAuthor: "+author+"\nCategory: " + theContents[6] + "\nExcerpt: " + theContents[7];
        toPrint += "\n\nContent: " + theContents[8] + "\nPic null? "+theContents[9]+"\n\n\n\n";
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

                setAuthor("[author name here]");
                //articleList.append(output);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error occurred in getAuthor", Toast.LENGTH_LONG).show();
            }
        });

        rQueue.add(requestA);
    }

    //gets an in-line article image and returns a bitmap as a string
    private void getTextImageFromURL(String imgURL) {
        ImageRequest request = new ImageRequest(imgURL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Bitmap test = null;
                        setTestImg(test);
                    }
                }, 1920, 1080, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error occurred in getImage", Toast.LENGTH_LONG).show();
                    }
                });

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
//                        Intent intent = new Intent(MainActivity.this, TestSavedActivity.class);
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
}

