package fr.eni.lokacar.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.repository.CarRepository;

public class CarsViewModel extends AndroidViewModel {

    private LiveData<List<Car>> carsAvailable;
    private LiveData<List<Car>> carsRented;
    private Car car;

    CarRepository repository;

    public CarsViewModel(@NonNull Application application)
    {
        super(application);
        repository = new CarRepository(application);
    }

    public void insert(Car car)
    {
        repository.insert(car);
    }

    public Car getCar(int id)
    {
        car = repository.getCar(id);
        return car;
    }

    public LiveData<List<Car>> getAllCarsAvailable()
    {
        return repository.getAllCarsAvailable();
    }

    public LiveData<List<Car>> getAllCarsRented()
    {
        return repository.getAllCarsRented();
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
