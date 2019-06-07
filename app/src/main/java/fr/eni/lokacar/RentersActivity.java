package fr.eni.lokacar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import fr.eni.lokacar.adapter.RenterRecyclerAdapter;
import fr.eni.lokacar.model.Renter;
import fr.eni.lokacar.view_model.RenterViewModel;

public class RentersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MenuActivity {

    private RenterViewModel rentersViewModel;

    private TextView emptyList;
    private RenterRecyclerAdapter adapter;

    private RecyclerView recyclerView;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renters);
        setTitle(R.string.renters);

        configureToolbar();
        configureDrawerLayout();
        configureNavigationView();

        rentersViewModel = ViewModelProviders.of(this).get(RenterViewModel.class);

        emptyList = (TextView) findViewById(R.id.empty_list_renters_txt_view);
        adapter = new RenterRecyclerAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.renters_recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        rentersViewModel.getAll().observe(this, new Observer<List<Renter>>() {
            @Override
            public void onChanged(@Nullable List<Renter> renters) {
                if (renters.isEmpty())
                {
                    emptyList.setVisibility(View.VISIBLE);
                    emptyList.setText(R.string.empty_renters_list);

                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.setRenter(renters);

                    emptyList.setVisibility(View.GONE);
                }
            }
        });

    }


    /**
     *
     * Related to toolbar, menu and item menu
     *
     */

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

    @Override
    public void configureToolbar() {
        toolbar = (Toolbar) findViewById(R.id.activity_renters_toolbar);
        toolbar.setElevation(0);
        setSupportActionBar(toolbar);
    }

    @Override
    public void configureDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_activity_renters_nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void configureNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.activity_renters_nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);
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
}
