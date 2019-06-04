package fr.eni.lokacar.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eni.lokacar.model.Renter;

@Dao
public interface RenterDAO {


    @Query("SELECT * FROM Renter")
    LiveData<List<Renter>> getAll();

    @Query("SELECT * FROM Renter WHERE idRenter = :id")
    Renter getRenter(int id);

    @Insert
    void insert(Renter renter);

    @Update
    void update(Renter renter);

    @Delete
    void delete(Renter renter);
}
