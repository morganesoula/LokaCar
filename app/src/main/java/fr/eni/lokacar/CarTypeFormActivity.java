package fr.eni.lokacar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class CarTypeFormActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MenuActivity {

    public static final String EXTRA_ID = "string_car_type_id";
    public static final String EXTRA_CAR_TYPE = "string_car_type_label";

    private EditText carTypeEditText;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type_form);

        configureToolbar();
        configureDrawerLayout();
        configureNavigationView();

        carTypeEditText = findViewById(R.id.car_type_edit_text);

        Intent intent = getIntent();
        intent.getParcelableExtra("carType");

        // If car already exists
        if (intent.hasExtra(EXTRA_ID))
        {
            setTitle(R.string.update_type_value);

            String type = intent.getStringExtra(EXTRA_CAR_TYPE);

            carTypeEditText.setText(type);
        } else {
            setTitle(R.string.add_car_type);
        }
    }

    // Creation of the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save, menu);

        return true;
    }

    // Initialization of the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save:
                saveCarType();
                break;
        }
        return true;
    }

    // Method to save car's type
    private void saveCarType()
    {
        String type = carTypeEditText.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_CAR_TYPE, type);

        setResult(RESULT_OK, intent);
        finish();
    }



    /**
     *
     * Related to toolbar, menu and item menu
     *
     */

    public void configureToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.car_type_form_toolbar);
        toolbar.setElevation(0);
        setSupportActionBar(toolbar);
    }


    public void configureDrawerLayout()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_activity_car_type_nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void configureNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.activity_car_type_nav_drawer);
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
}
