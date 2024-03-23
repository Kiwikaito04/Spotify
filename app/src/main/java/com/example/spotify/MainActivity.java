package com.example.spotify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.spotify.botnav_menu.HomeFragment;
import com.example.spotify.botnav_menu.LibaryFragment;
import com.example.spotify.botnav_menu.PremiumFragment;
import com.example.spotify.botnav_menu.SearchFragment;
import com.example.spotify.leftnav_menu.HistoryFragment;
import com.example.spotify.leftnav_menu.NewsFragment;
import com.example.spotify.leftnav_menu.ProfileFragment;
import com.example.spotify.leftnav_menu.SettingsFragment;
import com.example.spotify.loginregister.AuthorizeHelper;
import com.example.spotify.loginregister.UserAdapter;
import com.example.spotify.musichelper.MusicHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNav;
    DrawerLayout drawerLayout;
    ActionBar actionBar;
    MenuItem MILogin;
    MenuItem MILogout;
    MusicHelper musicHelper;
    AuthorizeHelper authorize;
    UserAdapter User;
    SharedPreferences SECTION;
    String KEY_SECTION = "user";
    String KEY_USERNAME = "user_username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadFunction();
        LoadUserSection();
        addBottomNavEvents();
        LoadNavigation();
    }

    private void LoadUserSection() {
        SECTION = getSharedPreferences(KEY_SECTION, Context.MODE_PRIVATE);
        String User_Username = SECTION.getString(KEY_USERNAME, null);
        if(User_Username != null) {
            Cursor _user = authorize.isAvailableUserName(User_Username);
            if(_user.moveToNext()) {
                User = new UserAdapter(_user.getString(0),
                        _user.getString(1),
                        _user.getString(2));
            }
        }
    }
    private void ClearSection() {
        SharedPreferences.Editor editor = SECTION.edit();
        editor.clear();
        editor.apply();
    }
    private void addBottomNavEvents() {
        //Sự kiện khi chọn các Item trong Bottom Nav
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            //Sự kiện với Item đã chọn
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Khi nhấn Home
                if(item.getItemId() == R.id.botNav_Home){
                    loadFragment(new HomeFragment());
                    return true ;
                }
                //Khi nhấn Search
                if(item.getItemId() == R.id.botNav_Search){
                    loadFragment(new SearchFragment());
                    return true ;
                }
                //Khi nhấn Library
                if(item.getItemId() == R.id.botNav_Library){
                    loadFragment(new LibaryFragment());
                    return true ;
                }
                //Khi nhấn Premium
                if(item.getItemId() == R.id.botNav_Premium){
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
        authorize = new AuthorizeHelper(this);
        musicHelper = new MusicHelper(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.nav_menu,menu);
        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);
        Menu menu_left = navigationView.getMenu();
        MILogin = menu_left.findItem(R.id.nav_login);
        MILogout = menu_left.findItem(R.id.nav_logout);
        LoginCheck(User);
        return true;
    }

    private void LoginCheck(UserAdapter user) {
        if (user != null) {
            MILogin.setVisible(false);
            MILogout.setVisible(true);
            TextView test = findViewById(R.id.headName);
            test.setText(String.format("Xin chào, %s",user.getUsername()));
        }
        else{
            MILogin.setVisible(true);
            MILogout.setVisible(false);
        }
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
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
            }
        if (item.getItemId() == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Đăng xuất !");
            builder.setMessage("Bạn có chắc là muốn đăng xuất tài khoản ?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    ClearSection();
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {dialog.cancel();}
            });
            builder.create().show();
            return true;
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
}