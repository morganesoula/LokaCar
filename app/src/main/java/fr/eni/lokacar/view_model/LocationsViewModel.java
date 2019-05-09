package fr.eni.lokacar.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import fr.eni.lokacar.model.Location;
import fr.eni.lokacar.repository.LocationRepository;


public class LocationsViewModel extends AndroidViewModel {

    private LiveData<List<Location>> locations;

    LocationRepository repository;

    public LocationsViewModel(@NonNull Application application)
    {
        super(application);
        repository = new LocationRepository(application);
    }

    public void insert(Location location)
    {
       repository.insert(location);
    }

    public LiveData<List<Location>> getAll()
    {
        return locations;
    }

    public LiveData<List<Location>> getAllByCar(int idCar)
    {
        return repository.getAllByCar(idCar);
    }

    public void update(Location location)
    {
        repository.update(location);
    }

    public void delete(Location location)
    {
        repository.delete(location);
    }
}
