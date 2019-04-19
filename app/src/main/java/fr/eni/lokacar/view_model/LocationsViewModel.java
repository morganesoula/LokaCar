package fr.eni.lokacar.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import fr.eni.lokacar.model.Location;
import fr.eni.lokacar.repository.LocationRepository;


public class LocationsViewModel extends AndroidViewModel {

    private LiveData<List<Location>> locations;
    private Location location;

    LocationRepository repository;

    public LocationsViewModel(@NonNull Application application)
    {
        super(application);
        repository = new LocationRepository(application);
        init();
    }

    private void init()
    {
        locations = repository.getAll();
    }

    public void insert(Location car)
    {
        repository.insert(location);
    }


    public LiveData<List<Location>> getAll()
    {
        return locations;
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
