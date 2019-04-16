package fr.eni.lokacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.model.CarType;

public class CarFormActivity extends AppCompatActivity {


    public static final String EXTRA_MODEL = "string_car_model";
    public static final String EXTRA_ID = "string_car_id";


    EditText tvmodel;
    Spinner tvtype;

    List voitureType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_form);


        tvmodel = findViewById(R.id.car_model);
        tvtype = findViewById(R.id.spinner_car_type);

        List<CarType> liste_type = new ArrayList<>();
        liste_type.add(new CarType(0,"Berline"));
        liste_type.add(new CarType(1,"SUV"));
        liste_type.add(new CarType(2,"Citadine"));
        liste_type.add(new CarType(3,"Sportive"));

        for (int i = 0; i < liste_type.size(); i++)
        {
            voitureType.add(liste_type.get(i).getLabel());
        }

        ArrayAdapter ad = new ArrayAdapter<CarType>(this,R.layout.type_spinner,voitureType);
        tvtype.setAdapter(ad);



        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Modifier");
            tvmodel.setText(intent.getStringExtra(EXTRA_MODEL));
        } else {
            setTitle("Ajouter une voiture");
        }
    }

    public void addPhoto(View view) {
    }
}
