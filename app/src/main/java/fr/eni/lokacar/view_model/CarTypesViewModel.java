package fr.eni.lokacar.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.model.CarType;
import fr.eni.lokacar.repository.CarRepository;
import fr.eni.lokacar.repository.CarTypeRepository;

public class CarTypesViewModel extends AndroidViewModel {

    private LiveData<List<CarType>> carTypes;

    CarTypeRepository repository;


    public CarTypesViewModel(@NonNull Application application)
    {
        super(application);
        repository = new CarTypeRepository(application);
        init();
    }

    private void init()
    {
        carTypes = repository.getAll();
    }

    public void insert(CarType carType)
    {
        repository.insert(carType);
    }

    public LiveData<List<CarType>> getAll()
    {
        return carTypes;
    }


    public void update(CarType carType)
    {
        repository.update(carType);
    }

    public void delete(CarType carType)
    {
        repository.delete(carType);
    }
}
