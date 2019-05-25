package fr.eni.lokacar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
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
import fr.eni.lokacar.model.User;
import fr.eni.lokacar.view_model.CarsViewModel;
import fr.eni.lokacar.view_model.UsersViewModel;

public class LocationFormActivity extends AppCompatActivity {

    private TextView tvDateStart, tvDateEnd, tvCar;
    private ImageButton btnAddUser;

    private Spinner tvusers;

    static final int REQUEST_ADD_USER_FORM = 200;

    public static final String EXTRA_DATE_START = "EXTRA_DATE_START";
    public static final String EXTRA_DATE_END = "EXTRA_DATE_END";
    public static final String EXTRA_USER = "EXTRA_USER";
    public static final String EXTRA_ID_CAR = "EXTRA_ID_CAR";
    public static final String EXTRA_FULL_USER_NAME = "KEY_FULL_USER_NAME";
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    public static final String EXTRA_ID_LOCATION = "EXTRA_ID_LOCATION";
    public static final String EXTRA_CAR_MODEL = "EXTRA_CAR_MODEL";
    public static final String EXTRA_CAR_IMMAT = "EXTRA_CAR_IMMAT";

    private CarsViewModel listCarsViewModel;
    private UsersViewModel usersViewModel;

    private Car car;

    int idCar;
    Date dateStart;
    Date dateEnd;

    private int userPosition;

    ArrayAdapter ad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_form);

        tvCar = findViewById(R.id.car);
        tvDateStart = (TextView) findViewById(R.id.tv_date_debut);
        tvDateEnd = (TextView) findViewById(R.id.tv_date_fin);
        btnAddUser = findViewById(R.id.add_user_button);
        // Creation of spinner of potential renters
        tvusers = findViewById(R.id.spinner_user);

        listCarsViewModel = ViewModelProviders.of(this).get(CarsViewModel.class);
        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);

        // Observer on list of renters
        // Set new renters into array
        usersViewModel.getAll().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                ArrayList labels = new ArrayList();
                for (User user : users) {

                    labels.add(user.getName() + " " + user.getFirstname());

                }

                // Add array into spinner
                ad = new ArrayAdapter<>(LocationFormActivity.this, R.layout.user_spinner, labels);
                tvusers.setAdapter(ad);
            }

        });


        // Get data from EXTRA
        Intent intent = getIntent();

        // If location has already been registered
        if (intent.hasExtra(EXTRA_ID_LOCATION))
        {
            setTitle(R.string.updateLoation);

            if (intent.getIntExtra(EXTRA_ID_CAR, 0) != 0)
            {
                idCar = intent.getIntExtra(EXTRA_ID_CAR, 0);
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

            User user = (User) intent.getSerializableExtra(EXTRA_USER);
            userPosition = user.getId();

            tvusers.post(new Runnable() {
                @Override
                public void run() {
                    tvusers.setSelection(userPosition - 1);
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

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationFormActivity.this, UserFormActivity.class);
                startActivityForResult(intent, REQUEST_ADD_USER_FORM);
            }
        });
    }

    // Creation of the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_save, menu);
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

    // Get data from UserFormActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_USER_FORM && resultCode == RESULT_OK) {
            String name = data.getStringExtra(UserFormActivity.EXTRA_NAME);
            String firstname = data.getStringExtra(UserFormActivity.EXTRA_FIRSTNAME);
            String phone = data.getStringExtra(UserFormActivity.EXTRA_PHONE);
            String email = data.getStringExtra(UserFormActivity.EXTRA_EMAIL);

            User user = new User(name, firstname, phone, email);
            usersViewModel.insert(user);
            Toast.makeText(this, R.string.renter_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.renter_failure, Toast.LENGTH_SHORT).show();
        }
    }

    // Method to save location
    private void saveLocation() {
        String dateStartString = tvDateStart.getText().toString();
        String dateEndString = tvDateEnd.getText().toString();

        // To get full user data from spinner's selected option
        int userId = tvusers.getSelectedItemPosition() + 1;
        User user = usersViewModel.getUser(userId);

        idCar = getIntent().getIntExtra(EXTRA_ID_CAR, 0);


        if (tvusers.getSelectedItem() == null) {
            Toast.makeText(this, R.string.field_missing, Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();

            intent.putExtra(EXTRA_DATE_START, dateStartString);
            intent.putExtra(EXTRA_DATE_END, dateEndString);
            intent.putExtra(EXTRA_USER_ID, userId);
            intent.putExtra(EXTRA_FULL_USER_NAME, (Serializable) user);

            if (idCar != 0)
            {
                intent.putExtra(EXTRA_ID_CAR, idCar);
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

}
