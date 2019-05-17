package fr.eni.lokacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class CarTypeFormActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "string_car_type_id";
    public static final String EXTRA_CAR_TYPE = "string_car_type_label";
    private EditText carTypeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type_form);

        carTypeEditText = findViewById(R.id.car_type_edit_text);

        Intent intent = getIntent();
        intent.getParcelableExtra("carType");

        // If car already exists
        if (intent.hasExtra(EXTRA_ID))
        {
            setTitle(R.string.update_type_value);

            String type = intent.getStringExtra(EXTRA_CAR_TYPE);

            carTypeEditText.setText(type);
        } else {
            setTitle(R.string.add_car_type);
        }
    }

    // Creation of the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save, menu);

        return true;
    }

    // Initialization of the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save:
                saveCarType();
                break;
        }
        return true;
    }

    // Method to save car's type
    private void saveCarType()
    {
        String type = carTypeEditText.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_CAR_TYPE, type);

        setResult(RESULT_OK, intent);
        finish();
    }
}
