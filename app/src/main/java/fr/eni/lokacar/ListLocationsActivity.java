package fr.eni.lokacar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.eni.lokacar.adapter.CarRecyclerAdapter;
import fr.eni.lokacar.adapter.LocationRecyclerAdapter;
import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.model.Location;
import fr.eni.lokacar.view_model.LocationsViewModel;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ListLocationsActivity extends AppCompatActivity {

    private LocationsViewModel locationsViewModel;
    private LocationRecyclerAdapter adapter;

    private TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_locations);
        setTitle(R.string.locations);

        emptyList = (TextView) findViewById(R.id.empty_list_locations_txt_view);
        adapter = new LocationRecyclerAdapter(this, null);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.locations_recycler);
        recyclerView.setHasFixedSize(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        final Intent intent = getIntent();
        int idCar = intent.getIntExtra(CarsAvailableFragment.EXTRA_ID_CAR,0);

        locationsViewModel = ViewModelProviders.of(this).get(LocationsViewModel.class);

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

            }
        });

        // TEST - Method delete() is not working
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

}