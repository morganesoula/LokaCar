package fr.eni.lokacar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.eni.lokacar.R;
import fr.eni.lokacar.RentersActivity;
import fr.eni.lokacar.model.Renter;

public class RenterRecyclerAdapter extends RecyclerView.Adapter<RenterRecyclerAdapter.ViewHolder> {

    private OnItemClickListener listener;

    Context context;
    List<Renter> listRenters;


    @NonNull
    @Override
    public RenterRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View renterLine = LayoutInflater.from(parent.getContext()).inflate(R.layout.renter_line, parent, false);
        return new ViewHolder(renterLine);
    }

    @Override
    public void onBindViewHolder(@NonNull RenterRecyclerAdapter.ViewHolder holder, int position) {
        Renter renter = listRenters.get(position);

        holder.renterFullUsername.setText(renter.getFirstname()+ " " + renter.getName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setRenter(List<Renter> renters)
    {
        this.listRenters = renters;
        notifyDataSetChanged();
    }

    public Renter getRenter(int position)
    {
        return listRenters.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView renterFullUsername;

        public ViewHolder(View renterLine) {
            super(renterLine);

            renterFullUsername = (TextView) renterLine.findViewById(R.id.full_name_renters_txt_view);


            renterLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                    {
                        listener.onItemClick(getRenter(getAdapterPosition()));
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, RentersActivity.class);
            Renter renter = listRenters.get(getAdapterPosition());

            context.startActivity(intent);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Renter user);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }
}
