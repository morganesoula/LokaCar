package fr.eni.lokacar.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fr.eni.lokacar.model.User;

@Dao
public interface UserDAO {


    @Query("SELECT * FROM user ORDER BY userId DESC")
    LiveData<List<User>> getAll();

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
