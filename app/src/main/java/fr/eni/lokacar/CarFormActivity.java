package fr.eni.lokacar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.model.CarType;


public class CarFormActivity extends AppCompatActivity {


    public static final String EXTRA_MODEL = "cle_car_model";
    public static final String EXTRA_IMMAT = "cle_car_immat";
    public static final String EXTRA_PRICE = "cle_car_price";
    public static final String EXTRA_TYPE = "cle_car_type";
    public static final String EXTRA_ISRESTORE = "cle_is_restore";
    public static final String EXTRA_PHOTO = "cle_photo";
    public static final String EXTRA_ID = "string_car_id";

    private static final int REQUEST_IMAGE_CAPTURE= 1;


    EditText tvmodel;
    EditText tvimmat;
    EditText tvprice;
    Spinner tvtype;
    Switch tvisrestore;
    ImageView tvphoto;

    List voitureType = new ArrayList<>();

    Car car;

    String cheminLocalImage;
    File photoVide = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_form);


        tvmodel = findViewById(R.id.car_model);
        tvimmat = findViewById(R.id.car_immatriculation);
        tvtype = findViewById(R.id.spinner_car_type);
        tvisrestore = findViewById(R.id.car_is_restore);
        tvphoto = findViewById(R.id.ivPhotoPrise);
        tvprice = findViewById(R.id.car_price);

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
        intent.getParcelableExtra("car");

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Modifier");

            //String id = intent.getStringExtra(EXTRA_ID);
            String model = intent.getStringExtra(EXTRA_MODEL);
            String price = String.valueOf(intent.getFloatExtra(CarFormActivity.EXTRA_PRICE,0));
            String immatriculation = intent.getStringExtra(EXTRA_IMMAT);

            String type = intent.getStringExtra(EXTRA_TYPE);
            Boolean isrestore = intent.getBooleanExtra(EXTRA_ISRESTORE, true);

            tvmodel.setText(model);
            tvimmat.setText(immatriculation);
            tvprice.setText(price);

            Bitmap imageBitmap = BitmapFactory.decodeFile(this.photoVide.getAbsolutePath());
            tvphoto = findViewById(R.id.ivPhotoPrise);
            tvphoto.setImageBitmap(imageBitmap);


            tvtype.setSelection(Integer.parseInt(type.toString()));
            tvisrestore.setText(String.valueOf(isrestore));

        } else {
            setTitle("Ajouter une voiture");
        }
    }

    public void addPhoto(View view)
    {
        //Utilisation de l'appareil photo
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Vérification que le telephone a bien un appareil photo
        if (intent.resolveActivity(getPackageManager()) != null) {
            //Création d'un fichier image vide
            this.photoVide = createImageFile();

            Uri uriPhotoVide = FileProvider.getUriForFile(CarFormActivity.this,
                    "EXTRA_PHOTO",
                    this.photoVide);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhotoVide);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }



    /**
     * Permet de créer un fichier image vide.
     * @return
     */
    private File createImageFile()  {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            cheminLocalImage = image.getAbsolutePath();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
