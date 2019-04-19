package fr.eni.lokacar.dao;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import fr.eni.lokacar.model.AgencyAuthentification;
import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.model.CarType;
import fr.eni.lokacar.model.User;

@android.arch.persistence.room.Database(entities = {Car.class, CarType.class, User.class, AgencyAuthentification.class}, version = 5, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract CarDAO carDAO();
    public abstract CarTypeDAO carTypeDAO();
    public abstract UserDAO userDAO();
    public abstract AgencyAuthentificationDAO agencyAuthentificationDAO();
    private static Database database;

    public static synchronized Database getDatabase(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context, Database.class, "random")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return database;
    }

    private static Callback roomCallBack = new Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
