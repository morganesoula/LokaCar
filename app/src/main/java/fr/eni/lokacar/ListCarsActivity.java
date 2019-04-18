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
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import fr.eni.lokacar.adapter.CarRecyclerAdapter;
import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.model.CarType;
import fr.eni.lokacar.view_model.CarTypesViewModel;
import fr.eni.lokacar.view_model.ListCarsViewModel;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ListCarsActivity extends AppCompatActivity {


    public static final int REQUEST_CODE_ADD = 1;
    public static final int REQUEST_CODE_EDIT = 2;

    private ListCarsViewModel carsViewModel;

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

        carsViewModel = ViewModelProviders.of(this).get(ListCarsViewModel.class);

        carsViewModel.get().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(@Nullable List<Car> cars) {
                adapter.setCars(cars);
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
                intent.putExtra(CarFormActivity.EXTRA_TYPE, (Serializable) car.getCarType());
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
                Intent intent = new Intent(ListCarsActivity.this, LocationFormActivity.class);
                startActivity(intent);
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK)
        {
            String model = data.getStringExtra(CarFormActivity.EXTRA_MODEL);
            String immatriculation = data.getStringExtra(CarFormActivity.EXTRA_IMMAT);
            Float price = data.getFloatExtra(CarFormActivity.EXTRA_PRICE, 0);
            Boolean isRestore = data.getBooleanExtra(CarFormActivity.EXTRA_ISRESTORE, true);
            CarType carType = (CarType) data.getSerializableExtra(CarFormActivity.EXTRA_TYPE);

            Car car = new Car(0, immatriculation, price, isRestore, null, model, carType);
            carsViewModel.insert(car);

        } else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra(CarFormActivity.EXTRA_ID, 0);

            if (id == 0)
            {
                Toast.makeText(this, "Updating problem", Toast.LENGTH_LONG).show();
            } else {
                String model = data.getStringExtra(CarFormActivity.EXTRA_MODEL);
                String immatriculation = data.getStringExtra(CarFormActivity.EXTRA_IMMAT);
                Float price = data.getFloatExtra(CarFormActivity.EXTRA_PRICE, 0);
                Boolean isRestore = data.getBooleanExtra(CarFormActivity.EXTRA_ISRESTORE, true);
                CarType carType = data.getParcelableExtra(CarFormActivity.EXTRA_TYPE);

                Car car = new Car(id, immatriculation, price, isRestore, null, model, carType);
                carsViewModel.update(car);
            }
        } else {
            Toast.makeText(this, "Saving failed", Toast.LENGTH_LONG).show();
        }
    }
}
