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

    @Query("SELECT * FROM location INNER JOIN car ON car.id = location.carId WHERE car.id = :id")
    /* @Query("SELECT l.id, l.dateStart, l.dateEnd, l.userId, l.carId, u.id, u.firstname, u.name, c.id " +
            "FROM location l, user u, car c " +
            "INNER JOIN car ON c.id = l.carId " +
            "INNER JOIN user ON u.id = l.userId " +
            "WHERE l.userId = u.id " +
            "AND l.carId = c.id " +
            "AND c.id = :id") */
    LiveData<List<Location>> getAllByCar(int id);

    @Insert
    void insert(Location location);

    @Update
    void update(Location location);

    @Delete
    void delete(Location location);
}
