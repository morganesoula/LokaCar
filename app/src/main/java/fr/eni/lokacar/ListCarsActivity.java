package fr.eni.lokacar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.adapter.CarRecyclerAdapter;
import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.model.CarType;
import fr.eni.lokacar.view_model.ListCarsViewModel;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ListCarsActivity extends AppCompatActivity {


    public static final int REQUEST_CODE_ADD = 1;
    public static final int REQUEST_CODE_EDIT = 2;

    private ListCarsViewModel carsViewModel;
    public List<Car> listCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Toutes les voitures");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cars);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cars_recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        final CarRecyclerAdapter adapter = new CarRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listCars = new ArrayList<>();
        listCars.add(new Car(1, "CM245MJ", 1500f, true, "www.google.fr", "Citroen C3", new CarType("Berline")));
        listCars.add(new Car(2, "BZ123ZA", 10200f, false, "www.nevermind.fr", "Renault Twingo", new CarType("SUV")));
        listCars.add(new Car(3, "AA132ZZ", 60f, true, "www.nevermind.fr", "Citroen Evasion", new CarType("SUV")));

        carsViewModel = ViewModelProviders.of(this).get(ListCarsViewModel.class);

        carsViewModel.get().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(@Nullable List<Car> cars) {
                adapter.setCars(listCars);
            }
        });

        adapter.setOnItemClickListener(new CarRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Car car) {
                Intent intent = new Intent(ListCarsActivity.this, CarFormActivity.class);
                intent.putExtra(CarFormActivity.EXTRA_ID, car.getIdCar());
                intent.putExtra(CarFormActivity.EXTRA_MODEL, car.getModel());
                intent.putExtra(CarFormActivity.EXTRA_IMMAT, car.getImmatriculation());
                intent.putExtra(CarFormActivity.EXTRA_PRICE, car.getPrice());
                intent.putExtra(CarFormActivity.EXTRA_TYPE, car.getCarType().getLabel());
                intent.putExtra(CarFormActivity.EXTRA_ISRESTORE, car.isRestore());
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListCarsActivity.this, CarFormActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }

        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {

            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

                new RecyclerViewSwipeDecorator.Builder(ListCarsActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(ListCarsActivity.this, R.color.colorAccent))
                        .addActionIcon(R.drawable.ic_group_add_white_24dp)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //carsViewModel.insert(adapter.getCar(viewHolder.getAdapterPosition()));
                Intent intent = new Intent(ListCarsActivity.this, LocationFormActivity.class);
                startActivity(intent);
            }
        }).attachToRecyclerView(recyclerView);


    }

}
