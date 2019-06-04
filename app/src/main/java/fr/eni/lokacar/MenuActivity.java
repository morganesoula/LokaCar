package fr.eni.lokacar;

import android.support.annotation.NonNull;
import android.view.MenuItem;

interface MenuActivity {

    void onBackPressed();

    boolean onNavigationItemSelected(@NonNull MenuItem item);

    void configureToolbar();

    void configureDrawerLayout();

    void configureNavigationView();
}
