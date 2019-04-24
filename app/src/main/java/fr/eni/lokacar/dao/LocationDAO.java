package fr.eni.lokacar.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eni.lokacar.model.Location;

@Dao
public interface LocationDAO {

    @Query("SELECT * FROM location")
    LiveData<List<Location>> getAll();

    @Query("SELECT * FROM location INNER JOIN car ON car.idCar = location.carId WHERE idCar = :id")
    LiveData<List<Location>> getAllByCar(int id);

    @Insert
    void insert(Location location);

    @Update
    void update(Location location);

    @Delete
    void delete(Location location);
}
