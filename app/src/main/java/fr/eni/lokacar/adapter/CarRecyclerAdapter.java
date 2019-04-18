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


    List<Car> listCars = new ArrayList<>();
    Context context;

    private OnItemClickListener listener;

    @Override
    public CarRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        holder.carType.setText(String.valueOf(car.getCarTypeId()));
        holder.carIsRestore.setChecked(car.isRestore());

        System.out.println("La voiture est " + car);
    }

    @Override
    public int getItemCount() {
        return listCars.size();
    }

    public void setCars(List<Car> cars) {
        this.listCars = cars;
        notifyDataSetChanged();
    }


    public Car getCar(int position) {

        return listCars.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView carImage;
        private TextView carModel;
        private TextView carPrice;
        private TextView carImmatriculation;
        private TextView carType;
        private Switch carIsRestore;


        public ViewHolder(View carLine) {
            super(carLine);
            //carImage = (ImageView) carLine.findViewById(R.id.car_image_view);
            carModel = (TextView) carLine.findViewById(R.id.car_model_txt_view);
            carPrice = (TextView) carLine.findViewById(R.id.car_price_txt_view);
            carImmatriculation = (TextView) carLine.findViewById(R.id.car_immatriculation_txt_view);
            carType = (TextView) carLine.findViewById(R.id.car_type_txt_view);
            carIsRestore = (Switch) carLine.findViewById(R.id.car_is_restore_switch);

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
            context.startActivity(intent);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Car car);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
