package com.example.spotify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.spotify.botnav_menu.HomeFragment;
import com.example.spotify.botnav_menu.LibaryFragment;
import com.example.spotify.botnav_menu.PremiumFragment;
import com.example.spotify.botnav_menu.SearchFragment;
import com.example.spotify.leftnav_menu.HistoryFragment;
import com.example.spotify.leftnav_menu.NewsFragment;
import com.example.spotify.leftnav_menu.ProfileFragment;
import com.example.spotify.leftnav_menu.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNav;
    DrawerLayout drawerLayout;
    ActionBar actionBar;
    MenuItem btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadFunction();

        addBottomNavEvents();
        LoadNavigation();
    }

    private void addBottomNavEvents() {
        //Sự kiện khi chọn các Item trong Bottom Nav
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            //Sự kiện với Item đã chọn
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Khi nhấn Home
                if(item.getItemId()==R.id.botNav_Home){
                    loadFragment(new HomeFragment());
                    return true ;
                }
                //Khi nhấn Search
                if(item.getItemId()==R.id.botNav_Search){
                    loadFragment(new SearchFragment());
                    return true ;
                }
                //Khi nhấn Library
                if(item.getItemId()==R.id.botNav_Library){
                    loadFragment(new LibaryFragment());
                    return true ;
                }
                //Khi nhấn Premium
                if(item.getItemId()==R.id.botNav_Premium){
                    loadFragment(new PremiumFragment());
                    return true ;
                }
                return true;
            }
        });
    }

    //Tải fragment lên Main Activity
    private void loadFragment(Fragment fmNew) {
        FragmentTransaction fmTran = getSupportFragmentManager().beginTransaction();
        fmTran.replace(R.id.fragment_container,fmNew);
        fmTran.addToBackStack(null);
        fmTran.commit();
    }

    private void LoadFunction() {
        bottomNav = findViewById(R.id.bottomNav);
        actionBar = getSupportActionBar();
        loadFragment(new HomeFragment()); //Chạy fragment mặc định là Home
    }

    private void LoadNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void LoadNavFrag(Fragment fmnav) {
        FragmentTransaction fmTran=getSupportFragmentManager().beginTransaction();
        fmTran.replace(R.id.fragment_container,fmnav);
        fmTran.commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Load các fragment khi ấn vào các item trong left nav
        if(item.getItemId()==R.id.nav_Profile){
            loadFragment(new ProfileFragment());
            drawerLayout.closeDrawer(GravityCompat.START);
            return true ;
        }
        if(item.getItemId()==R.id.nav_News){
            loadFragment(new NewsFragment());
            drawerLayout.closeDrawer(GravityCompat.START);
            return true ;
        }
        if(item.getItemId()==R.id.nav_history){
            loadFragment(new HistoryFragment());
            drawerLayout.closeDrawer(GravityCompat.START);
            return true ;
        }
        if(item.getItemId()==R.id.nav_setting){
            loadFragment(new SettingsFragment());
            drawerLayout.closeDrawer(GravityCompat.START);
            return true ;
        }
        if(item.getItemId()==R.id.nav_login){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
            return true ;
        }
        if(item.getItemId()==R.id.nav_logout){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
            return true ;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}