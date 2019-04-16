package fr.eni.lokacar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.ListCarsActivity;
import fr.eni.lokacar.R;
import fr.eni.lokacar.model.Car;

public class CarRecyclerAdapter extends RecyclerView.Adapter<CarRecyclerAdapter.ViewHolder>{

    //TODO onBindViewHolder

    List<Car> listCars;
    Context context;

    private OnItemClickListener listener;

    public CarRecyclerAdapter(ArrayList<Car> listCars, Context context) {
        this.listCars = listCars;
        this.context = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView carImage;
        public TextView carModel;
        public TextView carPrice;
        public TextView carImmatriculation;
        public TextView carType;
        public Switch carIsRestore;


        public ViewHolder(View carLine) {
            super(carLine);
            carImage = carLine.findViewById(R.id.car_image_view);
            carModel = carLine.findViewById(R.id.car_model_txt_view);
            carPrice = carLine.findViewById(R.id.car_price_txt_view);
            carImmatriculation = carLine.findViewById(R.id.car_immatriculation_txt_view);
            carType = carLine.findViewById(R.id.car_type_txt_view);
            carIsRestore = carLine.findViewById(R.id.car_is_restore_switch);

            carLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(getCar(getAdapterPosition()));
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ListCarsActivity.class);

            Car car = listCars.get(getAdapterPosition());
            //intent.putExtra("car", car);
            context.startActivity(intent);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View carLine = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_line, parent, false);
        return new ViewHolder(carLine);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Car car = listCars.get(position);


        //holder.carImage.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(localFile)));
        holder.carModel.setText(car.getModel());
        holder.carPrice.setText(String.valueOf(car.getPrice()));
        holder.carImmatriculation.setText(car.getImmatriculation());
        holder.carType.setText(car.getType().toString());
        holder.carIsRestore.setChecked(car.isRestore());

    }

    @Override
    public int getItemCount() {
        return listCars.size();
    }


    public void setCars(List<Car> cars) {
        listCars = cars;
        notifyDataSetChanged();
    }


    public Car getCar(int position) {

        return listCars.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(Car car);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
