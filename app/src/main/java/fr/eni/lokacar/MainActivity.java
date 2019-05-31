package fr.eni.lokacar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.facebook.stetho.Stetho;

import fr.eni.lokacar.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;

    private Toolbar toolbar;
    private NavigationView navigationView;

    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        setTitle(R.string.title_home_page);

        configureToolbar();
        configureDrawerLayout();
        configureNavigationView();

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add fragments to adapter
        viewPagerAdapter.addFragment(new CarsAvailableFragment(), "Cars available");
        viewPagerAdapter.addFragment(new CarsRentedFragment(), "Cars rented");

        // Attach adapter
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Remove shadow
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setElevation(0);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_main_drawer_home:
                Intent intentHome = new Intent(this, CarsAvailableFragment.class);
                startActivity(intentHome);

            case R.id.activity_main_drawer_renters :
                Intent intentRenters = new Intent(this, RentersActivity.class);
                startActivity(intentRenters);
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    private void configureToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setElevation(0);
        setSupportActionBar(toolbar);
    }


    private void configureDrawerLayout()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView()
    {
        navigationView = (NavigationView) findViewById(R.id.activity_main_nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);

    }




}
