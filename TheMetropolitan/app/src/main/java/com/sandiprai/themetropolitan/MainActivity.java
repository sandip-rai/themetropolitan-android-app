package com.sandiprai.themetropolitan;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //String appName = String.valueOf(R.string.app_name);
        String appName = "The Metropolitan";
        toolbarTitle.setText(appName.toUpperCase());

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
        setupBottomNavigationView();

    }

    private void setupBottomNavigationView(){
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                FragmentTransaction fragmentTransaction;
                switch (item.getItemId()){
                    case R.id.bottom_news:
                        fragment = new NewsFragment();
                        loadFragment(fragment);
//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.frame_saved, fragment);
//                        fragmentTransaction.commit();
                        break;
                    case R.id.bottom_saved:
                        fragment = new SavedFragment();
                        loadFragment(fragment);
//                        Intent intent = new Intent(MainActivity.this, TestSavedActivity.class);
//                        startActivity(intent);
//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.frame_saved, fragment);
//                        fragmentTransaction.commit();
                        break;
                    case R.id.bottom_search:
                        fragment = new SearchFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.bottom_settings:
                        fragment = new SettingsFragment();
                        loadFragment(fragment);
                        break;
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_saved, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
