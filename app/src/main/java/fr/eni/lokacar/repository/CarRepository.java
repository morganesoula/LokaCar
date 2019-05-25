package fr.eni.lokacar.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import fr.eni.lokacar.dao.CarDAO;
import fr.eni.lokacar.dao.Database;
import fr.eni.lokacar.model.Car;

public class CarRepository {

    CarDAO carDAO;
    LiveData<List<Car>> listCarsAvailable;
    LiveData<List<Car>> listCarsRented;
    Car car;


    public CarRepository(Context context)
    {
        Database database = Database.getDatabase(context);
        carDAO = database.carDAO();

        listCarsAvailable = carDAO.getAllCarsAvailable();
        listCarsRented = carDAO.getAllCarsRented();
    }



    public LiveData<List<Car>> getAllCarsAvailable() {
        return listCarsAvailable;
    }

    public LiveData<List<Car>> getAllCarsRented() {
        return  listCarsRented;
    }

    public Car getCar(int id) {
        return carDAO.getCar(id);
    }

    public void insert(Car car)
    {
        AsyncInsert asyncInsert = new AsyncInsert();
        asyncInsert.execute(car);
    }

    public void update(Car car)
    {
        AsyncUpdate asyncUpdate = new AsyncUpdate();
        asyncUpdate.execute(car);
    }

    public void delete(Car car)
    {
        AsyncDelete asyncDelete = new AsyncDelete();
        asyncDelete.execute(car);
    }


    /**
     * Asynchrone tasks
     */

    public class AsyncGet extends AsyncTask<Integer, Void, Car>
    {
        @Override
        protected Car doInBackground(Integer... id) {
            car = carDAO.getCar(id[0]);
            return car;
        }

    }

    public class AsyncInsert extends AsyncTask<Car, Void, Void>
    {
        @Override
        protected Void doInBackground(Car... cars) {
            carDAO.insert(cars[0]);
            return null;
        }
    }

    public class AsyncUpdate extends AsyncTask<Car, Void, Void>
    {
        @Override
        protected Void doInBackground(Car... cars) {
            carDAO.update(cars[0]);
            return null;
        }
    }

    public class AsyncDelete extends AsyncTask<Car, Void, Void>
    {
        @Override
        protected Void doInBackground(Car... cars) {
            carDAO.delete(cars[0]);
            return null;
        }
    }
}
