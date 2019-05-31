package fr.eni.lokacar;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.lokacar.model.CarType;
import fr.eni.lokacar.view_model.CarTypesViewModel;


public class CarFormActivity extends AppCompatActivity {

    public static final String EXTRA_MODEL = "cle_car_model";
    public static final String EXTRA_IMMAT = "cle_car_immat";
    public static final String EXTRA_PRICE = "cle_car_price";
    public static final String EXTRA_TYPE = "cle_car_type";
    public static final String EXTRA_ISRESTORE = "cle_is_restore";
    public static final String EXTRA_PHOTO = "cle_photo";
    public static final String EXTRA_ID_CAR = "string_car_id";

    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int REQUEST_CAR_TYPE_FORM = 200;

    private CarTypesViewModel carTypesViewModel;

    private EditText tvmodel;
    private EditText tvimmat;
    private EditText tvprice;
    private Spinner tvtype;
    private Switch tvisrestore;

    private String photopath;
    private File imgFile;


    private Button btnAddPhoto;
    private ImageButton btnAddCarType;

    private String photoPath;
    private File photoFile = null;
    private ImageView tvphoto;
    private ImageView tvphotocours;
    private TextView tvphotopath;

    private int typePosition;

    String type;

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
        tvphotocours = findViewById(R.id.ivPhoto);
        btnAddPhoto = findViewById(R.id.btn_add_photo);
        tvphotopath = findViewById(R.id.car_photo_path);
        tvphoto = findViewById(R.id.ivPhotoPrise);

        btnAddCarType = findViewById(R.id.add_car_type_button);

        carTypesViewModel = ViewModelProviders.of(this).get(CarTypesViewModel.class);

        // Observer on ViewModel to update list of cars
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

        // If car has already been registered
        if (intent.hasExtra(EXTRA_ID_CAR)) {
            setTitle(R.string.update);

            btnAddPhoto.setText("Update photo");

            String model = intent.getStringExtra(EXTRA_MODEL);
            String price = String.valueOf(intent.getFloatExtra(CarFormActivity.EXTRA_PRICE, 0));
            String immatriculation = intent.getStringExtra(EXTRA_IMMAT);
            Boolean isrestore = intent.getBooleanExtra(EXTRA_ISRESTORE, true);

            if (intent.getStringExtra(EXTRA_PHOTO) != null) {
                photopath = intent.getStringExtra(EXTRA_PHOTO);
            } else {
                photopath = null;
            }


            CarType carType = (CarType) intent.getSerializableExtra(EXTRA_TYPE);
            typePosition = carType.getIdCarType();

            tvmodel.setText(model);
            tvimmat.setText(immatriculation);
            tvprice.setText(price);

            if (photopath != null) {
                imgFile = new File(photopath);
            } else {
                imgFile = null;
            }


            if (imgFile != null) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                tvphotocours.setImageBitmap(myBitmap);
            }


            tvisrestore.setChecked(isrestore);
            tvtype.post(new Runnable() {
                @Override
                public void run() {
                    tvtype.setSelection(typePosition);
                }
            });

        } else {
            setTitle(R.string.add_car);
            btnAddPhoto.setText("Add a photo");
        }

        btnAddPhoto.setVisibility(View.VISIBLE);

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


    // Method to take a picture with the camera
    private void takePhoto() {

        if (isStoragePermissionGranted()) {
            //Use of camera
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            //Check if device has a camera
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                try {
                    photoFile = createImageFile();

                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.i("xxx", "IOException");
                    ex.printStackTrace();
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, "fr.eni.lokacar.provider", photoFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }

    // When updating the car, retrieve the saved picture
    // Set spinner on the saved car's type
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {

            Bitmap imageBitmap = BitmapFactory.decodeFile(this.photoFile.getAbsolutePath());
            tvphoto.setImageBitmap(imageBitmap);
        }

        if (requestCode == REQUEST_CAR_TYPE_FORM && resultCode == RESULT_OK) {
            type = data.getStringExtra(CarTypeFormActivity.EXTRA_CAR_TYPE);

            CarType carType = new CarType(0, type);
            carTypesViewModel.insert(carType);
        }
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
                saveCar();
                break;
        }
        return true;
    }

    // Method to save a car
    private void saveCar() {
        String model = tvmodel.getText().toString();
        String immatriculation = tvimmat.getText().toString();
        Boolean isRestore = tvisrestore.isChecked();
        Float price = Float.valueOf(tvprice.getText().toString());
        CarType carType = new CarType(tvtype.getSelectedItemPosition(), tvtype.getSelectedItem().toString());


        // To avoid NullPointerException error
        if (photoFile != null) {
            photoPath = photoFile.getAbsolutePath();
        } else {
            photoPath = null;
        }

        if (tvtype.getSelectedItem() == null) {
            Toast.makeText(this, R.string.field_missing, Toast.LENGTH_LONG).show();
        } else {

            // Save data into EXTRA to send to the Fragment
            Intent intent = new Intent();

            intent.putExtra(EXTRA_MODEL, model);
            intent.putExtra(EXTRA_IMMAT, immatriculation);
            intent.putExtra(EXTRA_ISRESTORE, isRestore);
            intent.putExtra(EXTRA_PRICE, price);
            intent.putExtra(EXTRA_TYPE, (Serializable) carType);
            intent.putExtra(EXTRA_PHOTO, photoPath);

            int id = getIntent().getIntExtra(EXTRA_ID_CAR, 0);

            if (id != 0) {
                intent.putExtra(EXTRA_ID_CAR, id);
            }

            setResult(RESULT_OK, intent);
            finish();
        }

    }


    // Method to create a file where you save pictures from the camera
    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    // Method to ask for permission to use camera
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

}
