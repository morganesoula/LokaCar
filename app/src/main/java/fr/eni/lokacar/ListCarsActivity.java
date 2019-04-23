package fr.eni.lokacar;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fr.eni.lokacar.adapter.CarRecyclerAdapter;
import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.model.CarType;
import fr.eni.lokacar.model.Location;
import fr.eni.lokacar.view_model.CarTypesViewModel;
import fr.eni.lokacar.view_model.ListCarsViewModel;
import fr.eni.lokacar.view_model.LocationsViewModel;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ListCarsActivity extends AppCompatActivity {


    public static final int REQUEST_CODE_ADD = 1;
    public static final int REQUEST_CODE_EDIT = 2;

    public static final int REQUEST_ADD_LOCATION = 3;

    public static final String EXTRA_ID_CAR = "EXTRA_ID_CAR";

    private ListCarsViewModel carsViewModel;
    private LocationsViewModel locationsViewModel;

    private TextView emptyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Toutes les voitures");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cars);

        emptyList = findViewById(R.id.empty_list_txt_view);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cars_recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        final CarRecyclerAdapter adapter = new CarRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carsViewModel = ViewModelProviders.of(this).get(ListCarsViewModel.class);

        carsViewModel.getAll().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(@Nullable List<Car> cars) {
                adapter.setCars(cars);
            }
        });
        adapter.notifyItemChanged(0);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListCarsActivity.this, CarFormActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }

        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT ) {


            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

                new RecyclerViewSwipeDecorator.Builder(ListCarsActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(ListCarsActivity.this, R.color.gray))
                        .addSwipeLeftActionIcon(R.drawable.ic_group_add_white_24dp)
                     /*   .addSwipeRightBackgroundColor(ContextCompat.getColor(ListCarsActivity.this, R.color.colorAccent))
                        .addSwipeRightActionIcon(R.drawable.ic_directions_car_black_24dp)
                        .addSwipeRightLabel("Location ")
                        .setSwipeRightLabelColor(Color.WHITE)*/
                        .addSwipeLeftLabel("Location")
                        .setSwipeLeftLabelColor(Color.WHITE)
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
                adapter.notifyItemChanged(0);
                Intent intent = new Intent(ListCarsActivity.this, LocationFormActivity.class);

                int id = adapter.getCar(viewHolder.getAdapterPosition()).getIdCar();
                String carmodel = adapter.getCar(viewHolder.getAdapterPosition()).getModel();
                String immat = adapter.getCar(viewHolder.getAdapterPosition()).getImmatriculation();


                intent.putExtra(CarFormActivity.EXTRA_ID, id);
                intent.putExtra(CarFormActivity.EXTRA_MODEL, carmodel);
                intent.putExtra(CarFormActivity.EXTRA_IMMAT, immat);
                startActivityForResult(intent, REQUEST_ADD_LOCATION);

            }
        }).attachToRecyclerView(recyclerView);

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
                CarType carType = (CarType) data.getSerializableExtra(CarFormActivity.EXTRA_TYPE);

                Car car = new Car(id, immatriculation, price, isRestore, null, model, carType);
                carsViewModel.update(car);
            }
        }else if (requestCode == REQUEST_ADD_LOCATION && resultCode == RESULT_OK)
        { Log.i("xxx","coucou" );
            try {
            Date dateStart = new SimpleDateFormat("dd/MM/yyyy").parse(data.getStringExtra(LocationFormActivity.EXTRA_DATE_START));
            Date dateEnd = new SimpleDateFormat("dd/MM/yyyy").parse(data.getStringExtra(LocationFormActivity.EXTRA_DATE_END));
            int idCar = data.getIntExtra(LocationFormActivity.EXTRA_ID_CAR,0);
            int idUser = data.getIntExtra(LocationFormActivity.EXTRA_ID_USER,0);
            Location location = new Location(dateStart,dateEnd,idCar,idUser);
            Log.i("xxx","location à sauvegarder" + location);

            //locationsViewModel.insert(location);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "Saving failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menu1 = getMenuInflater();
        menu1.inflate(R.menu.menu_log_out, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_log_out:
                launchLoginActivity();
                break;
        }

        return true;
    }

    private void launchLoginActivity()
    {
        finish();
        Intent intent = new Intent(ListCarsActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
