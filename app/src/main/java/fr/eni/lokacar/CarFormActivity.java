package fr.eni.lokacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class CarFormActivity extends AppCompatActivity {



    public static final String EXTRA_MODEL = "car_model";
    public static final String EXTRA_ID = "car_id";


    EditText tvmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_form);



        tvmodel = findViewById(R.id.car_model);

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_edit_black_24dp);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID))
        {
            setTitle("Editer");
            tvmodel.setText(intent.getStringExtra(EXTRA_MODEL));
        }
        else
        {
            setTitle("Ajouter");
        }
    }
}
