package fr.eni.lokacar.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "agency_authentification")
public class AgencyAuthentification {

    @PrimaryKey(autoGenerate = true)
    public long agencyId;
    public String username;
    public String password;

    public AgencyAuthentification(long agencyId, String username, String password) {
        this.agencyId = agencyId;
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(long agencyId) {
        this.agencyId = agencyId;
    }
}
