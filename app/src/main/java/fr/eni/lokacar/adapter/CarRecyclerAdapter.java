package fr.eni.lokacar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.eni.lokacar.CarsAvailableFragment;
import fr.eni.lokacar.R;
import fr.eni.lokacar.model.Car;

public class CarRecyclerAdapter extends RecyclerView.Adapter<CarRecyclerAdapter.ViewHolder>{


    List<Car> listCars = new ArrayList<>();
    Context context;

    File imgFile;

    private OnItemClickListener listener;
    private OnItemLongClickListener listenerLongClick;

    @Override
    public CarRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View carLine = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_line, parent, false);
        return new ViewHolder(carLine);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Car car = listCars.get(position);

        if (car.getImagePath() != null) {
            imgFile = new  File(car.getImagePath());
        } else {
            imgFile = null;
        }

        if(imgFile != null){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.carImage.setImageBitmap(myBitmap);
        } else {
            holder.carImage.setImageResource(R.drawable.logo_car);
        }

        holder.carModel.setText(car.getModel());
        holder.carPrice.setText(String.valueOf(car.getPrice()) + "€");
        holder.carImmatriculation.setText(car.getImmatriculation());
        holder.carType.setText(car.getCarType().getLabel());

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
        private TextView carIsRestore;


        public ViewHolder(View carLine) {
            super(carLine);
            carImage = (ImageView) carLine.findViewById(R.id.car_image_view);
            carModel = (TextView) carLine.findViewById(R.id.car_model_txt_view);
            carPrice = (TextView) carLine.findViewById(R.id.car_price_txt_view);
            carImmatriculation = (TextView) carLine.findViewById(R.id.car_immatriculation_txt_view);
            carType = (TextView) carLine.findViewById(R.id.car_type_txt_view);
            carIsRestore = (TextView) carLine.findViewById(R.id.car_is_restore_txt_view);

            carLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(getCar(getAdapterPosition()));
                    }
                }
            });

            carLine.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listenerLongClick != null)
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
            Intent intent = new Intent(context, CarsAvailableFragment.class);

            Car car = listCars.get(getAdapterPosition());
            context.startActivity(intent);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Car car);
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
