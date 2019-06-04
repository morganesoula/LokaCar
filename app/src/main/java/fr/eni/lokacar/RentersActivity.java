package fr.eni.lokacar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import fr.eni.lokacar.adapter.RenterRecyclerAdapter;
import fr.eni.lokacar.model.Renter;
import fr.eni.lokacar.view_model.RenterViewModel;

public class RentersActivity extends AppCompatActivity {

    private RenterViewModel rentersViewModel;

    private TextView emptyList;
    private RenterRecyclerAdapter adapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renters);
        setTitle(R.string.renters);

        rentersViewModel = ViewModelProviders.of(this).get(RenterViewModel.class);

        emptyList = (TextView) findViewById(R.id.empty_list_renters_txt_view);
        adapter = new RenterRecyclerAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.renters_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        rentersViewModel.getAll().observe(this, new Observer<List<Renter>>() {
            @Override
            public void onChanged(@Nullable List<Renter> renters) {
                if (renters.isEmpty())
                {
                    emptyList.setVisibility(View.VISIBLE);
                    emptyList.setText(R.string.empty_renters_list);

                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.setRenter(renters);

                    emptyList.setVisibility(View.GONE);
                }
            }
        });

    }


}
