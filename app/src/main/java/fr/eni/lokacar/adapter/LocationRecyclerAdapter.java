package fr.eni.lokacar.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.ListLocationsActivity;
import fr.eni.lokacar.LocationFormActivity;
import fr.eni.lokacar.R;
import fr.eni.lokacar.model.Location;

public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.ViewHolder>{


    List<Location> listLocations = new ArrayList<>();
    Context context;

    private OnItemClickListener listener;

    @Override
    public LocationRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View locationLine = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_line, parent, false);
        return new ViewHolder(locationLine);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Location location = listLocations.get(position);

        holder.locationStart.setText(R.string.start_date + new SimpleDateFormat("dd MMMM yyyy").format(location.getDateStart()));
        holder.locationEnd.setText(R.string.end_date + new SimpleDateFormat("dd MMMM yyyy").format(location.getDateEnd()));
        // holder.locationUserFullName.setText(String.valueOf(location.getUserId()));

    }

    @Override
    public int getItemCount() {
        return listLocations.size();
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
        //private TextView locationUserFullName;


        public ViewHolder(View locationLine) {
            super(locationLine);

            locationStart = (TextView) locationLine.findViewById(R.id.tv_date_start);
            locationEnd = (TextView) locationLine.findViewById(R.id.tv_date_end);
            //locationUserFullName = (TextView) locationLine.findViewById(R.id.tv_location_user_full_name);


            locationLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(getLocation(getAdapterPosition()));
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
