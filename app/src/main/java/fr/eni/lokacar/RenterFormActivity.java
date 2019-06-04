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

public class RenterFormActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MenuActivity {

    public static final String EXTRA_NAME = "string_user_name";
    public static final String EXTRA_FIRSTNAME = "string_user_firstname";
    public static final String EXTRA_EMAIL = "string_user_email";
    public static final String EXTRA_PHONE = "string_user_phone";

    private EditText tvname, tvfirstname, tvemail, tvphone;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_form);
        setTitle(R.string.add_renter);

        configureToolbar();
        configureDrawerLayout();
        configureNavigationView();

        tvname = findViewById(R.id.user_name);
        tvfirstname = findViewById(R.id.user_firstname);
        tvemail = findViewById(R.id.user_email);
        tvphone = findViewById(R.id.user_phone);

        Intent intent = getIntent();
        intent.getParcelableExtra("renter");
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
                saveUser();
                break;
        }
        return true;
    }


    // Method to save renter
    private void saveUser()
    {
        String name  = tvname.getText().toString();
        String firstname  = tvfirstname.getText().toString();
        String email  = tvemail.getText().toString();
        String phone  = tvphone.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_FIRSTNAME, firstname);
        intent.putExtra(EXTRA_EMAIL, email);
        intent.putExtra(EXTRA_PHONE, phone);

        setResult(RESULT_OK, intent);
        finish();
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
        toolbar = (Toolbar) findViewById(R.id.activity_renter_toolbar);
        toolbar.setElevation(0);
        setSupportActionBar(toolbar);
    }

    @Override
    public void configureDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_activity_renter_nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void configureNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.activity_renter_nav_drawer);
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
