package fr.eni.lokacar.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import fr.eni.lokacar.model.AgencyAuthentification;

@Dao
public interface AgencyAuthentificationDAO {

    @Query("SELECT * FROM agency_authentification WHERE username = :username")
    AgencyAuthentification getOneAgencyAuthentification(String username);

    @Insert
    void insert(AgencyAuthentification agencyAuthentification);

    @Update
    void update(AgencyAuthentification agencyAuthentification);

    @Delete
    void delete(AgencyAuthentification agencyAuthentification);

}
