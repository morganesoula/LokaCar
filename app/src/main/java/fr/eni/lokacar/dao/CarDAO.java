package fr.eni.lokacar.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eni.lokacar.model.Car;

@Dao
public interface CarDAO {

    @Query("SELECT * FROM Car WHERE isRestore = 1")
    LiveData<List<Car>> getAllCarsAvailable();

    @Query("SELECT * FROM Car WHERE isRestore = 0")
    LiveData<List<Car>> getAllCarsRented();

    @Query("SELECT * FROM Car WHERE idCar = :id")
    Car getCar(int id);

    @Insert
    void insert(Car car);

    @Update
    void update(Car car);

    @Delete
    void delete(Car car);
}
