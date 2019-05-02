package fr.eni.lokacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class UserFormActivity extends AppCompatActivity {

    //public static final String EXTRA_ID = "string_user_id";

    public static final String EXTRA_NAME = "string_user_name";
    public static final String EXTRA_FIRSTNAME = "string_user_firstname";
    public static final String EXTRA_EMAIL = "string_user_email";
    public static final String EXTRA_PHONE = "string_user_phone";

    private EditText tvname, tvfirstname, tvemail, tvphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        setTitle(R.string.add_renter);

        tvname = findViewById(R.id.user_name);
        tvfirstname = findViewById(R.id.user_firstname);
        tvemail = findViewById(R.id.user_email);
        tvphone = findViewById(R.id.user_phone);

        Intent intent = getIntent();
        intent.getParcelableExtra("user");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save:
                saveUser();
                break;
        }
        return true;
    }

    private void saveUser()
    {
        String name  = tvname.getText().toString();
        String firstname  = tvfirstname.getText().toString();
        String email  = tvemail.getText().toString();
        String phone  = tvphone.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_FIRSTNAME, firstname);
        intent.putExtra(EXTRA_EMAIL, email);
        intent.putExtra(EXTRA_PHONE, phone);

        setResult(RESULT_OK, intent);
        finish();
    }
}
