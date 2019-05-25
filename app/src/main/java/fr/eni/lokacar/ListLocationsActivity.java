package fr.eni.lokacar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import fr.eni.lokacar.model.User;
import fr.eni.lokacar.view_model.LocationsViewModel;
import fr.eni.lokacar.view_model.UsersViewModel;

public class ListLocationsActivity extends AppCompatActivity {

    public static final int REQUEST_EDIT_LOCATION = 2;

    private LocationsViewModel locationsViewModel;
    private UsersViewModel usersViewModel;
    private LocationRecyclerAdapter adapter;

    private TextView emptyList;

    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_locations);
        setTitle(R.string.locations);

        // Locations' list is empty
        emptyList = (TextView) findViewById(R.id.empty_list_locations_txt_view);
        adapter = new LocationRecyclerAdapter(this, null);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.locations_recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        final Intent intent = getIntent();

        // Get id of the locations' car
        final int idCar = intent.getIntExtra(CarsAvailableFragment.EXTRA_ID_CAR,0);

        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        locationsViewModel = ViewModelProviders.of(this).get(LocationsViewModel.class);

        // Observer on viewModel to update list of locations
        // Request to select locations of the car's id
        locationsViewModel.getAllByCar(idCar).observe(this, new Observer<List<Location>>() {
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

        adapter.setOnItemClickListener(new LocationRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final Location location) {
                Intent intent1 = new Intent(ListLocationsActivity.this, LocationFormActivity.class);
                intent1.putExtra(LocationFormActivity.EXTRA_ID_LOCATION, location.getId());
                intent1.putExtra(LocationFormActivity.EXTRA_ID_CAR, idCar);
                intent1.putExtra(LocationFormActivity.EXTRA_DATE_START, (Serializable) location.getDateStart());
                intent1.putExtra(LocationFormActivity.EXTRA_DATE_END, (Serializable) location.getDateEnd());

                user = usersViewModel.getUser(location.getUserId());
                intent1.putExtra(LocationFormActivity.EXTRA_USER, (Serializable) user);

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
                                locationsViewModel.delete(adapter.getLocation(position));
                                adapter.notifyDataSetChanged();
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

                User user = (User) data.getSerializableExtra(LocationFormActivity.EXTRA_FULL_USER_NAME);
                int userId = data.getIntExtra(LocationFormActivity.EXTRA_USER_ID, 0);
                int carId = data.getIntExtra(LocationFormActivity.EXTRA_ID_CAR, 0);

                Location location = new Location(idLocation, dateStart, dateEnd, userId, carId);
                locationsViewModel.update(location);
            } catch (java.text.ParseException e )
            {
                e.printStackTrace();
            }
        }
        }
    }
}