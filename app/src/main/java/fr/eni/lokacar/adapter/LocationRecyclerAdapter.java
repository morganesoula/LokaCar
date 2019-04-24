package fr.eni.lokacar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.ListLocationsActivity;
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


        holder.locationStart.setText(String.valueOf(location.getDateStart()));

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



        public ViewHolder(View locationLine) {
            super(locationLine);

            locationStart = (TextView) locationLine.findViewById(R.id.tv_date_start);


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
