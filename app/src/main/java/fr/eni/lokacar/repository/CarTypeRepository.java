package fr.eni.lokacar.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import fr.eni.lokacar.dao.CarTypeDAO;
import fr.eni.lokacar.dao.Database;
import fr.eni.lokacar.model.CarType;

public class CarTypeRepository {

    CarTypeDAO carTypeDAO;
    LiveData<List<CarType>> listCarTypes;

    public CarTypeRepository(Context context)
    {
        Database database = Database.getDatabase(context);
        carTypeDAO = database.carTypeDAO();

        listCarTypes = carTypeDAO.getAll();
    }

    public LiveData<List<CarType>> getAll() {
        return listCarTypes;
    }

    public void insert(CarType carType)
    {
        CarTypeRepository.AsyncInsert asyncInsert = new CarTypeRepository.AsyncInsert();
        asyncInsert.execute(carType);
    }

    public void update(CarType carType)
    {
        CarTypeRepository.AsyncUpdate asyncUpdate = new CarTypeRepository.AsyncUpdate();
        asyncUpdate.execute(carType);
    }

    public void delete(CarType carType)
    {
        CarTypeRepository.AsyncDelete asyncDelete = new CarTypeRepository.AsyncDelete();
        asyncDelete.execute(carType);
    }


    /**
     * Asynchrone tasks
     */
    public class AsyncInsert extends AsyncTask<CarType, Void, Void>
    {
        @Override
        protected Void doInBackground(CarType... carTypes) {
            carTypeDAO.insert(carTypes[0]);
            return null;
        }
    }

    public class AsyncUpdate extends AsyncTask<CarType, Void, Void>
    {
        @Override
        protected Void doInBackground(CarType... carTypes) {
            carTypeDAO.update(carTypes[0]);
            return null;
        }
    }

    public class AsyncDelete extends AsyncTask<CarType, Void, Void>
    {
        @Override
        protected Void doInBackground(CarType... carTypes) {
            carTypeDAO.delete(carTypes[0]);
            return null;
        }
    }
}
