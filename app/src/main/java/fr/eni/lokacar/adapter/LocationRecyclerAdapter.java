package fr.eni.lokacar.adapter;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.ListLocationsActivity;
import fr.eni.lokacar.LocationFormActivity;
import fr.eni.lokacar.R;
import fr.eni.lokacar.model.Location;
import fr.eni.lokacar.model.User;
import fr.eni.lokacar.repository.UserRepository;
import fr.eni.lokacar.view_model.UsersViewModel;

public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.ViewHolder>{


    private List<Location> listLocations;
    private Context context;
    private UsersViewModel userViewModel;

    User user;

    private OnItemClickListener listener;
    private OnItemLongClickListener listenerLongClick;


    public LocationRecyclerAdapter(Context context, List<Location> locations)
    {
        this.context = context;
        this.listLocations = locations;

        userViewModel = ViewModelProviders.of((FragmentActivity) context).get(UsersViewModel.class);
    }

    @Override
    public LocationRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View locationLine = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_line, parent, false);

        return new ViewHolder(locationLine);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Location location = listLocations.get(position);
        user = userViewModel.getUser(location.getUserId());

        holder.locationStart.setText("Start date: " + new SimpleDateFormat("dd MMMM yyyy").format(location.getDateStart()));
        holder.locationEnd.setText("End date: " + new SimpleDateFormat("dd MMMM yyyy").format(location.getDateEnd()));

        if (user != null)
        {
            holder.locationUserFullName.setText("Renter: " + user.getName() + " " + user.getFirstname());
        }

    }

    @Override
    public int getItemCount() {
        if (listLocations != null)
        {
            return listLocations.size();
        } else {
            return 0;
        }

    }

    public void setLocations(List<Location> locations) {
        this.listLocations = locations;
        notifyDataSetChanged();
    }


    public Location getLocation(int position) {

        return listLocations.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView locationStart;
        private TextView locationEnd;
        private TextView locationUserFullName;


        public ViewHolder(View locationLine) {
            super(locationLine);

            locationStart = (TextView) locationLine.findViewById(R.id.tv_date_start);
            locationEnd = (TextView) locationLine.findViewById(R.id.tv_date_end);
            locationUserFullName = (TextView) locationLine.findViewById(R.id.tv_location_user_full_name);


            locationLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(getLocation(getAdapterPosition()));
                    }
                }
            });

            locationLine.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener != null)
                    {
                        listenerLongClick.onItemLongClicked(getAdapterPosition());
                        return true;
                    } else {
                        return false;
                    }

                }
            });

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ListLocationsActivity.class);

            Location location = listLocations.get(getAdapterPosition());
            context.startActivity(intent);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Location location);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listenerLongClick)
    {
        this.listenerLongClick = listenerLongClick;
    }

}
