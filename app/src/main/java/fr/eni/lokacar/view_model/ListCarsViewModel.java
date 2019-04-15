package fr.eni.lokacar.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.repository.CarRepository;

public class ListCarsViewModel extends AndroidViewModel {

    private LiveData<List<Car>> cars;

    CarRepository repository;


    public ListCarsViewModel(@NonNull Application application)
    {
        super(application);
        repository = new CarRepository(application);
        init();
    }

    private void init()
    {
        cars = repository.getAll();
    }

    public void insert(Car car)
    {
        repository.insert(car);
    }

    public LiveData<List<Car>> get()
    {
        return cars;
    }


    public void update(Car car)
    {
        repository.update(car);
    }

    public void delete(Car car)
    {
        repository.delete(car);
    }
}
