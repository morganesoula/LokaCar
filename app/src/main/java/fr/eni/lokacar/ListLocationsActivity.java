package fr.eni.lokacar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.eni.lokacar.adapter.LocationRecyclerAdapter;
import fr.eni.lokacar.model.Location;
import fr.eni.lokacar.model.Renter;
import fr.eni.lokacar.view_model.LocationsViewModel;
import fr.eni.lokacar.view_model.RenterViewModel;

public class ListLocationsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MenuActivity {

    public static final int REQUEST_EDIT_LOCATION = 2;

    private LocationsViewModel locationsViewModel;
    private RenterViewModel rentersViewModel;
    private LocationRecyclerAdapter adapter;

    private TextView emptyList;

    public int idCarAvailable;
    public int idCarRented;

    public int carId;
    public Renter renter;

    private RecyclerView recyclerView;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_locations);
        setTitle(R.string.locations);

        configureToolbar();
        configureDrawerLayout();
        configureNavigationView();

        rentersViewModel = ViewModelProviders.of(this).get(RenterViewModel.class);
        locationsViewModel = ViewModelProviders.of(this).get(LocationsViewModel.class);

        // Locations' list is empty
        emptyList = (TextView) findViewById(R.id.empty_list_locations_txt_view);
        adapter = new LocationRecyclerAdapter(this, null);

        recyclerView = (RecyclerView) findViewById(R.id.locations_recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        final Intent intent = getIntent();

        // Get id of the locations' car
        // Get id from the good Fragment
        idCarAvailable = intent.getIntExtra(CarsAvailableFragment.EXTRA_ID_CAR_AVAILABLE,0);
        idCarRented = intent.getIntExtra(CarsRentedFragment.EXTRA_ID_CAR_RENTED, 0);

        // Observer on viewModel to update list of locations
        // Request to select locations of the car's id
        if (idCarAvailable == 0) // Then, coming from the RentedFragment
        {
            locationsViewModel.getAllByCar(idCarRented).observe(this, new Observer<List<Location>>() {
                @Override
                public void onChanged(@Nullable List<Location> locations) {

                    if (locations.isEmpty())
                    {
                        emptyList.setVisibility(View.VISIBLE);
                        emptyList.setText(R.string.empty_location_list);

                        recyclerView.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.setLocations(locations);

                        emptyList.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            // Then, coming from the AvailableFragment
            locationsViewModel.getAllByCar(idCarAvailable).observe(this, new Observer<List<Location>>() {
                @Override
                public void onChanged(@Nullable List<Location> locations) {

                    if (locations.isEmpty())
                    {
                        emptyList.setVisibility(View.VISIBLE);
                        emptyList.setText(R.string.empty_location_list);
                    } else {
                        emptyList.setVisibility(View.GONE);
                        adapter.setLocations(locations);
                    }

                }
            });
        }

        adapter.setOnItemClickListener(new LocationRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Location location) {
                Intent intent1 = new Intent(ListLocationsActivity.this, LocationFormActivity.class);
                intent1.putExtra(LocationFormActivity.EXTRA_ID_LOCATION, location.getIdLocation());

                int idLocation = location.getIdLocation();

                if (idCarAvailable == 0)
                {
                    intent1.putExtra(LocationFormActivity.EXTRA_ID_CAR_RENTED, location.getCarId());
                } else {
                    intent1.putExtra(LocationFormActivity.EXTRA_ID_CAR_AVAILABLE, location.getCarId());
                }

                intent1.putExtra(LocationFormActivity.EXTRA_DATE_START, (Serializable) location.getDateStart());
                intent1.putExtra(LocationFormActivity.EXTRA_DATE_END, (Serializable) location.getDateEnd());

                renter = rentersViewModel.getRenter(location.getRenterId());
                intent1.putExtra(LocationFormActivity.EXTRA_RENTER, (Serializable) renter);

                startActivityForResult(intent1, REQUEST_EDIT_LOCATION);
            }
        });

        // Method to delete selected location on long click
        adapter.setOnItemLongClickListener(new LocationRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(final int position) {
                new AlertDialog.Builder(ListLocationsActivity.this)
                        .setMessage(R.string.delete_location)
                        .setNegativeButton(R.string.no, null)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Location location = adapter.getLocation(position);
                                locationsViewModel.delete(location);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(ListLocationsActivity.this, "Location deleted", Toast.LENGTH_LONG).show();
                            }
                        }).show();
                return true;
            }
        });


    }

    // Got data from LocationFormActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EDIT_LOCATION && resultCode == RESULT_OK)
        {
            // If location already exists
            int idLocation = data.getIntExtra(LocationFormActivity.EXTRA_ID_LOCATION, 0);

        if (idLocation == 0)
        {
            Toast.makeText(this, R.string.update_problem, Toast.LENGTH_LONG).show();
        } else {
            // In order to use method "parse", you need to Try/Catch
            try {

                Date dateStart = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(data.getStringExtra(LocationFormActivity.EXTRA_DATE_START));
                Date dateEnd = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(data.getStringExtra(LocationFormActivity.EXTRA_DATE_END));

                Renter renter = (Renter) data.getSerializableExtra(LocationFormActivity.EXTRA_FULL_USER_NAME);
                int renterId = data.getIntExtra(LocationFormActivity.EXTRA_RENTER_ID, 0);

                int idCarAvailableUpdated = data.getIntExtra(LocationFormActivity.EXTRA_ID_CAR_AVAILABLE, 0);
                int idCarRentedUpdated = data.getIntExtra(LocationFormActivity.EXTRA_ID_CAR_RENTED, 0);

                if (idCarAvailableUpdated == 0)
                {
                    carId = idCarRentedUpdated;
                } else {
                    carId = idCarAvailableUpdated;
                }

                Location location = new Location(idLocation, dateStart, dateEnd, renterId, carId);
                locationsViewModel.update(location);
            } catch (java.text.ParseException e)
            {
                e.printStackTrace();
            }
        }
        } else {
            Toast.makeText(this, R.string.save_failure, Toast.LENGTH_LONG).show();
        }
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
        toolbar = (Toolbar) findViewById(R.id.activity_list_locations_toolbar);
        toolbar.setElevation(0);
        setSupportActionBar(toolbar);
    }

    @Override
    public void configureDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_activity_list_locations_nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void configureNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.activity_list_locations_nav_drawer);
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