package fr.eni.lokacar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.model.CarType;
import fr.eni.lokacar.view_model.CarTypesViewModel;
import fr.eni.lokacar.view_model.ListCarsViewModel;


public class CarFormActivity extends AppCompatActivity {

    public static final String EXTRA_MODEL = "cle_car_model";
    public static final String EXTRA_IMMAT = "cle_car_immat";
    public static final String EXTRA_PRICE = "cle_car_price";
    public static final String EXTRA_TYPE = "cle_car_type";
    public static final String EXTRA_ISRESTORE = "cle_is_restore";
    public static final String EXTRA_PHOTO = "cle_photo";
    public static final String EXTRA_ID = "string_car_id";

    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int REQUEST_CAR_TYPE_FORM = 200;

    private CarTypesViewModel carTypesViewModel;

    private EditText tvmodel;
    private EditText tvimmat;
    private EditText tvprice;
    private Spinner tvtype;
    private Switch tvisrestore;

    private ImageView tvphoto;
    private Button btnAddPhoto;
    private ImageButton btnAddCarType;

    private int typePosition;

    String type;
    Bundle extras;
    Bitmap bitmap;

    ArrayAdapter ad;


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
        tvphoto = findViewById(R.id.ivPhotoPrise);

        btnAddCarType = findViewById(R.id.add_car_type_button);

        carTypesViewModel = ViewModelProviders.of(this).get(CarTypesViewModel.class);
        carTypesViewModel.getAll().observe(this, new Observer<List<CarType>>() {
            @Override
            public void onChanged(@Nullable List<CarType> carTypes) {
                ArrayList labels = new ArrayList();
                for (CarType labelType : carTypes) {

                    labels.add(labelType.getLabel());

                }
                ad = new ArrayAdapter<>(CarFormActivity.this, R.layout.type_spinner, labels);
                tvtype.setAdapter(ad);
            }
        });

        Intent intent = getIntent();
        intent.getParcelableExtra("car");

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Modifier");

            String model = intent.getStringExtra(EXTRA_MODEL);
            String price = String.valueOf(intent.getFloatExtra(CarFormActivity.EXTRA_PRICE,0));
            String immatriculation = intent.getStringExtra(EXTRA_IMMAT);
            Boolean isrestore = intent.getBooleanExtra(EXTRA_ISRESTORE, true);

            CarType carType = (CarType) intent.getSerializableExtra(EXTRA_TYPE);
            typePosition = carType.getIdCarType();

            tvmodel.setText(model);
            tvimmat.setText(immatriculation);
            tvprice.setText(price);
            tvphoto.setImageBitmap(bitmap);
            tvisrestore.setChecked(isrestore);

            tvtype.post(new Runnable() {
                @Override
                public void run() {
                    tvtype.setSelection(typePosition);
                }
            });

        } else {
            setTitle("Ajouter une voiture");
        }

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

        btnAddCarType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CarFormActivity.this, CarTypeFormActivity.class);
                startActivityForResult(intent1, REQUEST_CAR_TYPE_FORM);
            }
        });

    }


    private void takePhoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            tvphoto.setImageBitmap(bitmap);
        }

        if (requestCode == REQUEST_CAR_TYPE_FORM && resultCode == RESULT_OK)
        {
            type = data.getStringExtra(CarTypeFormActivity.EXTRA_CAR_TYPE);

            CarType carType = new CarType(0, type);
            carTypesViewModel.insert(carType);
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
                saveCar();
                break;
        }
        return true;
    }

    private void saveCar()
    {
        String model = tvmodel.getText().toString();
        String immatriculation = tvimmat.getText().toString();
        Boolean isRestore = tvisrestore.isChecked();
        Float price = Float.valueOf(tvprice.getText().toString());
        CarType carType = new CarType(tvtype.getSelectedItemPosition(), tvtype.getSelectedItem().toString());

        if (tvtype.getSelectedItem() == null)
        {
            Toast.makeText(this, "Please fill everything", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();

            intent.putExtra(EXTRA_MODEL, model);
            intent.putExtra(EXTRA_IMMAT, immatriculation);
            intent.putExtra(EXTRA_ISRESTORE, isRestore);
            intent.putExtra(EXTRA_PRICE, price);
            intent.putExtra(EXTRA_TYPE, (Serializable) carType);

            int id = getIntent().getIntExtra(EXTRA_ID, 0);

            if (id != 0)
            {
                intent.putExtra(EXTRA_ID, id);
            }

            setResult(RESULT_OK, intent);
            finish();
        }

    }
}
