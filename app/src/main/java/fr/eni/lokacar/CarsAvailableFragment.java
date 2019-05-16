package fr.eni.lokacar;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import fr.eni.lokacar.view_model.ListCarsViewModel;
import fr.eni.lokacar.view_model.LocationsViewModel;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CarsAvailableFragment extends Fragment {

    private View view;

    public static final int REQUEST_CODE_ADD = 1;
    public static final int REQUEST_CODE_EDIT = 2;

    public static final int REQUEST_ADD_LOCATION = 3;

    public static final String EXTRA_ID_CAR = "EXTRA_ID_CAR";

    private ListCarsViewModel carsViewModel;
    private LocationsViewModel locationsViewModel;

    private TextView emptyList;
    private String photoPath;

    private CarRecyclerAdapter adapter;

    public CarsAvailableFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cars_available, container, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cars_available_recycler);
        recyclerView.setHasFixedSize(true);

        adapter = new CarRecyclerAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

                new RecyclerViewSwipeDecorator.Builder(getActivity(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.gray))
                        .addSwipeLeftActionIcon(R.drawable.ic_group_add_white_24dp)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent))
                        .addSwipeRightActionIcon(R.drawable.ic_list_white_24dp)
                        .addSwipeRightLabel("Locations")
                        .setSwipeRightLabelColor(Color.WHITE)
                        .addSwipeLeftLabel("Add a location")
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

                if (direction == ItemTouchHelper.LEFT){
                    Intent intent = new Intent(getActivity(), LocationFormActivity.class);
                    int id = adapter.getCar(viewHolder.getAdapterPosition()).getId();
                    String carmodel = adapter.getCar(viewHolder.getAdapterPosition()).getModel();
                    String immat = adapter.getCar(viewHolder.getAdapterPosition()).getImmatriculation();

                    intent.putExtra(CarFormActivity.EXTRA_ID, id);
                    intent.putExtra(CarFormActivity.EXTRA_MODEL, carmodel);
                    intent.putExtra(CarFormActivity.EXTRA_IMMAT, immat);
                    startActivityForResult(intent, REQUEST_ADD_LOCATION);
                }

                if (direction == ItemTouchHelper.RIGHT){
                    Intent intent = new Intent(getActivity(), ListLocationsActivity.class);
                    int id = adapter.getCar(viewHolder.getAdapterPosition()).getId();
                    intent.putExtra(CarsAvailableFragment.EXTRA_ID_CAR, id);

                    startActivity(intent);
                }
                adapter.notifyDataSetChanged();

            }
        }).attachToRecyclerView(recyclerView);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_car_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CarFormActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }

        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        emptyList = view.findViewById(R.id.empty_list_cars_available_txt_view);

        carsViewModel = ViewModelProviders.of(this).get(ListCarsViewModel.class);
        carsViewModel.getAll().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(@Nullable List<Car> cars) {
                if (cars.isEmpty())
                {
                    emptyList.setVisibility(View.VISIBLE);
                    emptyList.setText(R.string.empty_cars_available_list);
                } else {
                    emptyList.setVisibility(View.GONE);
                    adapter.setCars(cars);
                }
            }
        });



        adapter.setOnItemClickListener(new CarRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Car car) {
                Intent intent = new Intent(getActivity(), CarFormActivity.class);
                intent.putExtra(CarFormActivity.EXTRA_ID, car.getId());
                intent.putExtra(CarFormActivity.EXTRA_MODEL, car.getModel());
                intent.putExtra(CarFormActivity.EXTRA_IMMAT, car.getImmatriculation());
                intent.putExtra(CarFormActivity.EXTRA_PRICE, car.getPrice());
                intent.putExtra(CarFormActivity.EXTRA_TYPE, (Serializable) car.getCarType());
                intent.putExtra(CarFormActivity.EXTRA_ISRESTORE, car.isRestore());

                if (car.getImagePath() != null)
                {
                    intent.putExtra(CarFormActivity.EXTRA_PHOTO, car.getImagePath());
                }

                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        adapter.setOnItemLongClickListener(new CarRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(final int position) {
                new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.delete_car)
                        .setNegativeButton(R.string.no, null)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                carsViewModel.delete(adapter.getCar(position));
                                adapter.notifyDataSetChanged();
                            }
                        }).show();
                return true;
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_ADD && resultCode == Activity.RESULT_OK)
        {
            String model = data.getStringExtra(CarFormActivity.EXTRA_MODEL);
            String immatriculation = data.getStringExtra(CarFormActivity.EXTRA_IMMAT);
            Float price = data.getFloatExtra(CarFormActivity.EXTRA_PRICE, 0);
            Boolean isRestore = data.getBooleanExtra(CarFormActivity.EXTRA_ISRESTORE, true);
            CarType carType = (CarType) data.getSerializableExtra(CarFormActivity.EXTRA_TYPE);

            if (CarFormActivity.EXTRA_PHOTO != null)
            {
                photoPath = data.getStringExtra(CarFormActivity.EXTRA_PHOTO);
            } else {
                photoPath = null;
            }


            Car car = new Car(0, immatriculation, price, isRestore, photoPath, model, carType);
            carsViewModel.insert(car);

        } else if (requestCode == REQUEST_CODE_EDIT && resultCode == Activity.RESULT_OK)
        {
            int id = data.getIntExtra(CarFormActivity.EXTRA_ID, 0);

            if (id == 0)
            {
                Toast.makeText(getActivity(), R.string.update_problem, Toast.LENGTH_LONG).show();
            } else {
                String model = data.getStringExtra(CarFormActivity.EXTRA_MODEL);
                String immatriculation = data.getStringExtra(CarFormActivity.EXTRA_IMMAT);
                Float price = data.getFloatExtra(CarFormActivity.EXTRA_PRICE, 0);
                Boolean isRestore = data.getBooleanExtra(CarFormActivity.EXTRA_ISRESTORE, true);
                CarType carType = (CarType) data.getSerializableExtra(CarFormActivity.EXTRA_TYPE);

                if (CarFormActivity.EXTRA_PHOTO != null)
                {
                    photoPath = data.getStringExtra(CarFormActivity.EXTRA_PHOTO);
                } else {
                    photoPath = null;
                }

                Car car = new Car(id, immatriculation, price, isRestore, photoPath, model, carType);
                carsViewModel.update(car);
            }
        } else if (requestCode == REQUEST_ADD_LOCATION && resultCode == Activity.RESULT_OK)
        {
            try {
                Date dateStart = new SimpleDateFormat("dd/MM/yyyy").parse(data.getStringExtra(LocationFormActivity.EXTRA_DATE_START));
                Date dateEnd = new SimpleDateFormat("dd/MM/yyyy").parse(data.getStringExtra(LocationFormActivity.EXTRA_DATE_END));
                int idCar = data.getIntExtra(LocationFormActivity.EXTRA_ID_CAR,0);
                int idUser = data.getIntExtra(LocationFormActivity.EXTRA_ID_USER,0);

                Location location = new Location(dateStart, dateEnd, idUser, idCar);

                locationsViewModel = ViewModelProviders.of(this).get(LocationsViewModel.class);
                locationsViewModel.insert(location);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), R.string.save_failure, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_log_out, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        adapter.notifyDataSetChanged();
        switch (item.getItemId()) {
            case R.id.item_log_out:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }



}