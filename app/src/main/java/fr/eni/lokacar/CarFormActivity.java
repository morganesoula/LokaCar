package fr.eni.lokacar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.model.CarType;


public class CarFormActivity extends AppCompatActivity {

    public static final String EXTRA_MODEL = "cle_car_model";
    public static final String EXTRA_IMMAT = "cle_car_immat";
    public static final String EXTRA_PRICE = "cle_car_price";
    public static final String EXTRA_TYPE = "cle_car_type";
    public static final String EXTRA_ISRESTORE = "cle_is_restore";
    public static final String EXTRA_PHOTO = "cle_photo";
    public static final String EXTRA_ID = "string_car_id";

    static final int REQUEST_IMAGE_CAPTURE = 100;

    private EditText tvmodel;
    private EditText tvimmat;
    private EditText tvprice;
    private Spinner tvtype;
    private Switch tvisrestore;

    private ImageView tvphoto;
    private Button btnAddPhoto;

    Bundle extras;

    List voitureType = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_form);

        tvmodel = findViewById(R.id.car_model);
        tvimmat = findViewById(R.id.car_immatriculation);
        tvtype = findViewById(R.id.spinner_car_type);
        tvisrestore = findViewById(R.id.car_is_restore);
        tvprice = findViewById(R.id.car_price);

        btnAddPhoto = findViewById(R.id.btn_add_photo);
        tvphoto = (ImageView) findViewById(R.id.ivPhotoPrise);

        List<CarType> liste_type = new ArrayList<>();
        liste_type.add(new CarType(0, "Berline"));
        liste_type.add(new CarType(1, "SUV"));
        liste_type.add(new CarType(2, "Citadine"));
        liste_type.add(new CarType(3, "Sportive"));


        for (int i = 0; i < liste_type.size(); i++) {
            voitureType.add(liste_type.get(i).getLabel());
        }

        ArrayAdapter ad = new ArrayAdapter<CarType>(this, R.layout.type_spinner, voitureType);
        tvtype.setAdapter(ad);

        Intent intent = getIntent();
        intent.getParcelableExtra("car");

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Modifier");

            //String id = intent.getStringExtra(EXTRA_ID);
            String model = intent.getStringExtra(EXTRA_MODEL);
            String price = String.valueOf(intent.getFloatExtra(CarFormActivity.EXTRA_PRICE, 0));
            String immatriculation = intent.getStringExtra(EXTRA_IMMAT);

            String type = intent.getStringExtra(EXTRA_TYPE);
            Boolean isrestore = intent.getBooleanExtra(EXTRA_ISRESTORE, true);

            tvmodel.setText(model);
            tvimmat.setText(immatriculation);
            tvprice.setText(price);
            //tvphoto.setImageBitmap(imageBitmap);
            tvtype.setSelection(ad.getPosition(type));
            tvisrestore.setChecked(isrestore);

        } else {
            setTitle("Ajouter une voiture");
        }

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            tvphoto.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putBundle("extras", extras);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        extras = savedInstanceState.getBundle("extras");
    }

}
