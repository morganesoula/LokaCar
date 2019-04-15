package fr.eni.lokacar.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import fr.eni.lokacar.dao.CarDAO;
import fr.eni.lokacar.model.Car;

public class CarRepository {

    CarDAO carDAO;
    LiveData<List<Car>> listCars;


}
