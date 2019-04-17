package fr.eni.lokacar.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.model.CarType;

@Dao
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
public interface CarTypeDAO {

    @Query("SELECT * FROM car_type")
    LiveData<List<CarType>> getAll();

    @Insert
    void insert(CarType carType);

    @Update
    void update(CarType carType);

    @Delete
    void delete(CarType carType);
}
