package fr.eni.lokacar.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import fr.eni.lokacar.model.Renter;
import fr.eni.lokacar.repository.RenterRepository;

public class RenterViewModel extends AndroidViewModel {

    private LiveData<List<Renter>> renters;

    RenterRepository repository;

    public RenterViewModel(@NonNull Application application)
    {
        super(application);
        repository = new RenterRepository(application);
        init();
    }

    private void init()
    {
        renters = repository.getAll();
    }

    public void insert(Renter renter)
    {
        repository.insert(renter);
    }

    public LiveData<List<Renter>> getAll()
    {
        return renters;
    }

    public Renter getRenter(int id)
    {
        return repository.getRenter(id);
    }

    public void update(Renter renter)
    {
        repository.update(renter);
    }

    public void delete(Renter renter)
    {
        repository.delete(renter);
    }
}