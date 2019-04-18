package fr.eni.lokacar.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import fr.eni.lokacar.dao.CarDAO;
import fr.eni.lokacar.dao.CarTypeDAO;
import fr.eni.lokacar.dao.Database;
import fr.eni.lokacar.model.Car;

public class CarRepository {

    CarDAO carDAO;
    LiveData<List<Car>> listCars;


    public CarRepository(Context context)
    {
        Database database = Database.getDatabase(context);
        carDAO = database.carDAO();

        listCars = carDAO.getAll();
    }


    public LiveData<List<Car>> getAll() {
        return listCars;
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
