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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.model.Renter;
import fr.eni.lokacar.view_model.CarsViewModel;
import fr.eni.lokacar.view_model.RenterViewModel;

public class LocationFormActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MenuActivity {

    private TextView tvDateStart, tvDateEnd, tvCar;
    private ImageButton btnAddRenter;

    private Spinner tvrenters;

    static final int REQUEST_ADD_RENTER_FORM = 200;

    public static final String EXTRA_DATE_START = "EXTRA_DATE_START";
    public static final String EXTRA_DATE_END = "EXTRA_DATE_END";
    public static final String EXTRA_RENTER = "EXTRA_RENTER";
    public static final String EXTRA_ID_CAR_RENTED = "EXTRA_ID_CAR_RENTED";
    public static final String EXTRA_ID_CAR_AVAILABLE = "EXTRA_ID_CAR_AVAILABLE";
    public static final String EXTRA_FULL_USER_NAME = "KEY_FULL_USER_NAME";
    public static final String EXTRA_RENTER_ID = "EXTRA_RENTER_ID";
    public static final String EXTRA_ID_LOCATION = "EXTRA_ID_LOCATION";
    public static final String EXTRA_CAR_MODEL = "EXTRA_CAR_MODEL";
    public static final String EXTRA_CAR_IMMAT = "EXTRA_CAR_IMMAT";

    private CarsViewModel listCarsViewModel;
    private RenterViewModel rentersViewModel;

    private Car car;

    int idCar;
    Date dateStart;
    Date dateEnd;

    private int renterPosition;

    ArrayAdapter ad;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_form);

        configureToolbar();
        configureDrawerLayout();
        configureNavigationView();

        tvCar = findViewById(R.id.car);
        tvDateStart = (TextView) findViewById(R.id.tv_date_debut);
        tvDateEnd = (TextView) findViewById(R.id.tv_date_fin);
        btnAddRenter = findViewById(R.id.add_renter_button);
        // Creation of spinner of potential renters
        tvrenters = findViewById(R.id.spinner_renter);

        listCarsViewModel = ViewModelProviders.of(this).get(CarsViewModel.class);
        rentersViewModel = ViewModelProviders.of(this).get(RenterViewModel.class);

        // Observer on list of renters
        // Set new renters into array
        rentersViewModel.getAll().observe(this, new Observer<List<Renter>>() {
            @Override
            public void onChanged(@Nullable List<Renter> renters) {
                ArrayList labels = new ArrayList();
                for (Renter renter : renters) {

                    labels.add(renter.getName() + " " + renter.getFirstname());

                }

                // Add array into spinner
                ad = new ArrayAdapter<>(LocationFormActivity.this, R.layout.renter_spinner, labels);
                tvrenters.setAdapter(ad);
            }

        });


        // Get data from EXTRA
        Intent intent = getIntent();

        // If location has already been registered
        if (intent.hasExtra(EXTRA_ID_LOCATION))
        {
            setTitle(R.string.update_location);

            int idCarRentedUpdated = intent.getIntExtra(EXTRA_ID_CAR_RENTED, 0);
            int idCarAvailableUpdated = intent.getIntExtra(EXTRA_ID_CAR_AVAILABLE, 0);

            if (idCarAvailableUpdated == 0)
            {
                idCar = intent.getIntExtra(EXTRA_ID_CAR_RENTED, 0);
            } else {
                idCar = intent.getIntExtra(EXTRA_ID_CAR_AVAILABLE, 0);
            }


            if (idCar != 0)
            {
                car = listCarsViewModel.getCar(idCar);
            } else {
                Toast.makeText(this, "No car found", Toast.LENGTH_SHORT).show();
            }

            if (car != null)
            {
                tvCar.setText(car.getModel() + " - " + car.getImmatriculation());
            }

            Renter renter = (Renter) intent.getSerializableExtra(EXTRA_RENTER);
            renterPosition = renter.getIdRenter();

            tvrenters.post(new Runnable() {
                @Override
                public void run() {
                    tvrenters.setSelection(renterPosition - 1);
                }
            });

            dateStart = (Date) intent.getSerializableExtra(LocationFormActivity.EXTRA_DATE_START);
            dateEnd = (Date) intent.getSerializableExtra(LocationFormActivity.EXTRA_DATE_END);

            // Date converter in order to display it
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            tvDateStart.setText(dateFormat.format(dateStart));
            tvDateEnd.setText(dateFormat.format(dateEnd));

            // Method to select dateStart and dateEnd of location
            Button btnDateRange = (Button) findViewById(R.id.btn_date_range_picker);
            btnDateRange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Locale.setDefault(Locale.FRANCE);
                    SmoothDateRangePickerFragment smoothDateRangePickerFragment =
                            SmoothDateRangePickerFragment
                                    .newInstance(new SmoothDateRangePickerFragment.OnDateRangeSetListener() {
                                        @Override
                                        public void onDateRangeSet(SmoothDateRangePickerFragment view,
                                                                   int yearStart, int monthStart,
                                                                   int dayStart, int yearEnd,
                                                                   int monthEnd, int dayEnd) {

                                            String datedebut = dayStart + "/" + (++monthStart)
                                                    + "/" + yearStart;
                                            String datefin = dayEnd + "/" + (++monthEnd)
                                                    + "/" + yearEnd;
                                            tvDateStart.setText(datedebut);
                                            tvDateEnd.setText(datefin);
                                        }
                                    });
                    smoothDateRangePickerFragment.show(getFragmentManager(), "Datepickerdialog");
                }
            });


        } else {
            setTitle(R.string.location);

            tvCar.setText(intent.getStringExtra(EXTRA_CAR_MODEL) + " - " + intent.getStringExtra(EXTRA_CAR_IMMAT));

            // Method to select dateStart and dateEnd of location
            Button btnDateRange = (Button) findViewById(R.id.btn_date_range_picker);
            btnDateRange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Locale.setDefault(Locale.FRANCE);
                    SmoothDateRangePickerFragment smoothDateRangePickerFragment =
                            SmoothDateRangePickerFragment
                                    .newInstance(new SmoothDateRangePickerFragment.OnDateRangeSetListener() {
                                        @Override
                                        public void onDateRangeSet(SmoothDateRangePickerFragment view,
                                                                   int yearStart, int monthStart,
                                                                   int dayStart, int yearEnd,
                                                                   int monthEnd, int dayEnd) {

                                        String datedebut = dayStart + "/" + (++monthStart)
                                                + "/" + yearStart;
                                        String datefin = dayEnd + "/" + (++monthEnd)
                                                + "/" + yearEnd;
                                            tvDateStart.setText(datedebut);
                                            tvDateEnd.setText(datefin);
                                        }
                                    });
                    smoothDateRangePickerFragment.show(getFragmentManager(), "Datepickerdialog");
                }
            });
        }

        btnAddRenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationFormActivity.this, RenterFormActivity.class);
                startActivityForResult(intent, REQUEST_ADD_RENTER_FORM);
            }
        });
    }

    // Creation of the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    // Initialization of the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save:
                saveLocation();
                break;
        }
        return true;
    }

    // Get data from RenterFormActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_RENTER_FORM && resultCode == RESULT_OK) {
            String name = data.getStringExtra(RenterFormActivity.EXTRA_NAME);
            String firstname = data.getStringExtra(RenterFormActivity.EXTRA_FIRSTNAME);
            String phone = data.getStringExtra(RenterFormActivity.EXTRA_PHONE);
            String email = data.getStringExtra(RenterFormActivity.EXTRA_EMAIL);

            Renter renter = new Renter(name, firstname, phone, email);
            rentersViewModel.insert(renter);
            Toast.makeText(this, R.string.renter_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.renter_failure, Toast.LENGTH_SHORT).show();
        }
    }

    // Method to save location
    private void saveLocation() {
        String dateStartString = tvDateStart.getText().toString();
        String dateEndString = tvDateEnd.getText().toString();

        // To get full renter data from spinner's selected option
        int renterId = tvrenters.getSelectedItemPosition() + 1;
        Renter renter = rentersViewModel.getRenter(renterId);

        int idCarAvailable = getIntent().getIntExtra(CarsAvailableFragment.EXTRA_ID_CAR_AVAILABLE, 0);
        int idCarRented = getIntent().getIntExtra(CarsRentedFragment.EXTRA_ID_CAR_RENTED, 0);

        if (idCarAvailable == 0)
        {
            idCar = idCarRented;
        } else {
            idCar = idCarAvailable;
        }


        if (tvrenters.getSelectedItem() == null) {
            Toast.makeText(this, R.string.field_missing, Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();

            intent.putExtra(EXTRA_DATE_START, dateStartString);
            intent.putExtra(EXTRA_DATE_END, dateEndString);
            intent.putExtra(EXTRA_RENTER_ID, renterId);
            intent.putExtra(EXTRA_FULL_USER_NAME, (Serializable) renter);

            if (idCar != 0)
            {
                if (idCarAvailable == 0)
                {
                    intent.putExtra(EXTRA_ID_CAR_RENTED, idCar);
                } else {
                    intent.putExtra(EXTRA_ID_CAR_AVAILABLE, idCar);
                }
            } else {
                Toast.makeText(this, "Problem with id's car", Toast.LENGTH_LONG).show();
            }

            int idLocation = getIntent().getIntExtra(EXTRA_ID_LOCATION, 0);

            if (idLocation != 0)
            {
                intent.putExtra(EXTRA_ID_LOCATION, idLocation);
            }

            setResult(RESULT_OK, intent);
            finish();
        }

    }

    /**
     *
     * Related to toolbar, menu and item menu
     *
     */

    public void configureToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setElevation(0);
        setSupportActionBar(toolbar);
    }


    public void configureDrawerLayout()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_activity_location_nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void configureNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.activity_location_nav_drawer);
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
