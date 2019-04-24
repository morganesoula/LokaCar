package fr.eni.lokacar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import fr.eni.lokacar.adapter.CarRecyclerAdapter;
import fr.eni.lokacar.adapter.LocationRecyclerAdapter;
import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.model.Location;
import fr.eni.lokacar.view_model.LocationsViewModel;

public class ListLocationsActivity extends AppCompatActivity {

    private LocationsViewModel locationsViewModel;
    final LocationRecyclerAdapter locationAdapter = new LocationRecyclerAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_locations);
        setTitle("Toutes les locations");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.locations_recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(locationAdapter);

        Intent intent = getIntent();
        int idCar = intent.getIntExtra(ListCarsActivity.EXTRA_ID_CAR,0);

        locationsViewModel = ViewModelProviders.of(this).get(LocationsViewModel.class);
        locationsViewModel.getAllByCar(idCar).observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                locationAdapter.setLocations(locations);
            }
        });

    }

}