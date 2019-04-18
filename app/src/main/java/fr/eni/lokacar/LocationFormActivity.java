package fr.eni.lokacar;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.util.ArrayList;
import java.util.Calendar;
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


    private UsersViewModel usersViewModel;

    ArrayAdapter ad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_form);

        setTitle("Location");

        Intent intent = getIntent();


        tvCar = findViewById(R.id.welcome);
        tvDateStart = (TextView) findViewById(R.id.tv_date_debut);
        tvDateEnd = (TextView) findViewById(R.id.tv_date_fin);
        btnAddUser = findViewById(R.id.add_user_button);
        tvusers = findViewById(R.id.spinner_user);

        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        usersViewModel.getAll().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                ArrayList labels = new ArrayList();
                for (User user : users) {

                    labels.add(user.getName()+ " " + user.getFirstname());

                }
                Log.i("xxx", labels.toString());
                ad = new ArrayAdapter<>(LocationFormActivity.this, R.layout.user_spinner, labels);
                tvusers.setAdapter(ad);
            }

        });

        //tvDate = (TextView) findViewById(R.id.tv_date);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save:
                //TODO METHOD
                Log.i("XXX","Je rentre dans la methode save");
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_ADD_USER_FORM && resultCode == RESULT_OK)
        {
            String name = data.getStringExtra(UserFormActivity.EXTRA_NAME);
            String firstname = data.getStringExtra(UserFormActivity.EXTRA_FIRSTNAME);
            String phone = data.getStringExtra(UserFormActivity.EXTRA_PHONE);
            String email = data.getStringExtra(UserFormActivity.EXTRA_EMAIL);

            User user = new User(name, firstname,phone,email);
            Log.i("SAVE", String.valueOf(user));
            usersViewModel.insert(user);
            Toast.makeText(this, "Sauvegarde effectuée", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Sauvegarde non effectuée", Toast.LENGTH_SHORT).show();
        }
    }


}
