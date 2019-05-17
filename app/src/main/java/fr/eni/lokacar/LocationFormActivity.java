package fr.eni.lokacar;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.eni.lokacar.model.CarType;
import fr.eni.lokacar.model.Location;
import fr.eni.lokacar.model.User;
import fr.eni.lokacar.view_model.CarTypesViewModel;
import fr.eni.lokacar.view_model.UsersViewModel;

public class LocationFormActivity extends AppCompatActivity {

    private TextView tvDateStart, tvDateEnd, tvCar;
    private ImageButton btnAddUser;

    private Spinner tvusers;

    static final int REQUEST_ADD_USER_FORM = 200;

    public static final String EXTRA_DATE_START = "EXTRA_DATE_START";
    public static final String EXTRA_DATE_END = "EXTRA_DATE_END";

    public static final String EXTRA_ID_USER = "EXTRA_ID_USER";
    public static final String EXTRA_ID_CAR = "EXTRA_ID_CAR";

    public static final String KEY_FULL_USER_NAME = "KEY_FULL_USER_NAME";


    private UsersViewModel usersViewModel;

    String idCar;
    String modelCar;
    String immatCar;

    ArrayAdapter ad;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_form);

        setTitle(R.string.location);

        // Get data from EXTRA
        Intent intent = getIntent();
        idCar = String.valueOf(intent.getIntExtra(CarFormActivity.EXTRA_ID, 0));
        modelCar = String.valueOf(intent.getStringExtra(CarFormActivity.EXTRA_MODEL));
        immatCar = String.valueOf(intent.getStringExtra(CarFormActivity.EXTRA_IMMAT));


        tvCar = findViewById(R.id.car);
        tvDateStart = (TextView) findViewById(R.id.tv_date_debut);
        tvDateEnd = (TextView) findViewById(R.id.tv_date_fin);
        btnAddUser = findViewById(R.id.add_user_button);
        // Creation of spinner of potential renters
        tvusers = findViewById(R.id.spinner_user);

        tvCar.setText(modelCar + " - " + immatCar);

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
        int id = Integer.valueOf(idCar);
        int idUser = tvusers.getSelectedItemPosition() + 1;

        if (tvusers.getSelectedItem() == null) {
            Toast.makeText(this, R.string.field_missing, Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DATE_START, dateStartString);
            intent.putExtra(EXTRA_DATE_END, dateEndString);
            intent.putExtra(EXTRA_ID_CAR, id);
            intent.putExtra(EXTRA_ID_USER, idUser);

            setResult(RESULT_OK, intent);
            finish();
        }


    }


}
