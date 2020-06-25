package com.ardam.clientappsub;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ardam.clientappsub.models.Series;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.BuildConfig;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    public  static Series series;
    public static String mediumFrag = "";
    public static String currentFrag="";
    public static String prevFrag="";
    DrawerLayout drawerLayout;
    Fragment activeFrag;
    NavigationView navMenu;
    public static Toolbar toolbar;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navMenu = findViewById(R.id.navMenu);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        toggle.syncState();

        View headerView = navMenu.getHeaderView(0);
        TextView txtAppName, txtVersion;
        txtAppName = headerView.findViewById(R.id.txtappName);
        txtVersion = headerView.findViewById(R.id.txtVersion);

        txtAppName.setText(getString(R.string.app_name));
        txtVersion.setText(BuildConfig.VERSION_NAME);

        setFragment(new HomeFragment());
        setTitle("Home");
        currentFrag = "Home";
        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home_menu:
                      setFragment(new HomeFragment());
                      currentFrag = getString(R.string.Home_frag);
                        setTitle("Home");
                        break;
                    case R.id.movie_menu:
                        setFragment(new MovieFragment());
                        setTitle("Movie");
                        currentFrag = getString(R.string.Movie_frag);
                        prevFrag = getString(R.string.Home_frag);
                        break;
                    case R.id.series_menu:
                        setFragment(new SeriesFragment());
                        setTitle("Series");
                        currentFrag = getString(R.string.Series_frag);
                        prevFrag = getString(R.string.Home_frag);
                        break;
                    case R.id.share_menu:
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.khhs.clientapp");
                        shareIntent.setType("text/plain");
                        startActivity(shareIntent);
                        break;
                    case R.id.search_menu:
                        setFragment(new SearchFragment());
                        setTitle("Search");
                        currentFrag = getString(R.string.Search_frag);
                        prevFrag = getString(R.string.Home_frag);
                        break;
                    case R.id.request_menu:
                        setFragment(new RequestFragment());
                        setTitle("Request");
                        currentFrag = getString(R.string.Request_frag);
                        prevFrag = getString(R.string.Home_frag);
                        break;
                    case R.id.about_menu:
                        setFragment(new AboutFragment());
                        setTitle("About");
                        currentFrag = getString(R.string.About_frag);
                        prevFrag = getString(R.string.Home_frag);
                }

                   drawerLayout.closeDrawer(Gravity.LEFT);


                return false;
            }
        });


    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT))
        {
            drawerLayout.closeDrawer(Gravity.LEFT);

        }else{
            if (prevFrag.equals(getString(R.string.Home_frag))
                    || currentFrag.equals(getString(R.string.Home_frag)))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmation !");
                builder.setMessage("Are You Sure To Exit!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
            else if (currentFrag.equals(getString(R.string.Movie_frag))
            || currentFrag.equals(getString(R.string.Series_frag))
            || currentFrag.equals(getString(R.string.Search_frag))
            || currentFrag.equals(getString(R.string.Request_frag))
            || currentFrag.equals(getString(R.string.About_frag)))
            {
                setFragment(new HomeFragment());
                setTitle(getString(R.string.Home_frag));
                currentFrag = getString(R.string.Home_frag);
                prevFrag = getString(R.string.Home_frag);
            }

            else if (currentFrag.equals(getString(R.string.video_det))
            && currentFrag.equals(getString(R.string.series_dat)))
            {
                SeriesDatFragment datFragment = new SeriesDatFragment();
                datFragment.myModel = MainActivity.series;
                series = null;
                setFragment(datFragment);
                setTitle(getString(R.string.Series_frag));
                currentFrag = getString(R.string.series_dat);
                prevFrag =MainActivity.mediumFrag;
            }
            else if (currentFrag.equals(getString(R.string.Home_frag)))
            {
                setFragment(new HomeFragment());
                setTitle(getString(R.string.Home_frag));
                currentFrag = getString(R.string.Home_frag);
                prevFrag = getString(R.string.Home_frag);
            }
            else if (currentFrag.equals(getString(R.string.Movie_frag)))
            {
                setFragment(new MovieFragment());
                setTitle(getString(R.string.Movie_frag));
                currentFrag = getString(R.string.Movie_frag);
                prevFrag = getString(R.string.video_det);
            }
            else if (currentFrag.equals(getString(R.string.Search_frag)))
            {
                setFragment(new SearchFragment());
                setTitle(getString(R.string.Search_frag));
                currentFrag = getString(R.string.Search_frag);
                prevFrag = getString(R.string.video_det);
            }

        }
        if (VideoDetailFragment.player!=null)
        {
            VideoDetailFragment.player.stop();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (VideoDetailFragment.player!=null)
        {
        VideoDetailFragment.player.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (VideoDetailFragment.player!=null){
            VideoDetailFragment.player.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (VideoDetailFragment.player!=null){
            VideoDetailFragment.player.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (VideoDetailFragment.player!=null){
            VideoDetailFragment.player.stop();
        }
    }
    public void setFragment(Fragment f)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_fragment, f);
        ft.commit();
    }
}
