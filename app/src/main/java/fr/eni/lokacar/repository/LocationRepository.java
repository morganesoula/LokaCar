package fr.eni.lokacar.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import fr.eni.lokacar.dao.CarDAO;
import fr.eni.lokacar.dao.Database;
import fr.eni.lokacar.dao.LocationDAO;
import fr.eni.lokacar.dao.RenterDAO;
import fr.eni.lokacar.model.Location;

public class LocationRepository {
    LocationDAO locationDAO;
    CarDAO carDAO;
    RenterDAO renterDAO;
    LiveData<List<Location>> listLocations;


    public LocationRepository(Context context)
    {
        Database database = Database.getDatabase(context);
        locationDAO = database.locationDAO();

        carDAO = database.carDAO();
        renterDAO = database.renterDAO();

        listLocations = locationDAO.getAll();
    }

    public LiveData<List<Location>> getAll() {
        return listLocations;
    }

    public LiveData<List<Location>> getAllByCar(int idCar) {
        return locationDAO.getAllByCar(idCar);
    }

    public Location getOne(int id) {
        return locationDAO.getOne(id);
    }


    public void insert(Location location)
    {
        LocationRepository.AsyncInsert asyncInsert = new LocationRepository.AsyncInsert();
        asyncInsert.execute(location);
    }

    public void update(Location location)
    {
        LocationRepository.AsyncUpdate asyncUpdate = new LocationRepository.AsyncUpdate();
        asyncUpdate.execute(location);
    }

    public void delete(Location location)
    {
        AsyncDelete asyncDelete = new AsyncDelete();
        asyncDelete.execute(location);
    }


    /**
     * Asynchrone tasks
     */
    public class AsyncInsert extends AsyncTask<Location, Void, Void>
    {
        @Override
        protected Void doInBackground(Location... locations) {

            locationDAO.insert(locations[0]);
            return null;
        }
    }

    public class AsyncUpdate extends AsyncTask<Location, Void, Void>
    {
        @Override
        protected Void doInBackground(Location... locations) {
            locationDAO.update(locations[0]);
            return null;
        }
    }

    public class AsyncDelete extends AsyncTask<Location, Void, Void>
    {
        @Override
        protected Void doInBackground(Location... locations) {
            locationDAO.delete(locations[0]);
            return null;
        }
    }


}


