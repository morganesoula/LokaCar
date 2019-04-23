package fr.eni.lokacar.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "agency_authentification")
public class AgencyAuthentification {

    @PrimaryKey(autoGenerate = true)
    public int agencyId;
    public String username;
    public String password;

    public AgencyAuthentification(int agencyId, String username, String password) {
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

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AgencyAuthentification{" +
                "agencyId=" + agencyId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
