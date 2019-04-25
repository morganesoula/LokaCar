package fr.eni.lokacar.dao;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import fr.eni.lokacar.model.AgencyAuthentification;
import fr.eni.lokacar.model.Car;
import fr.eni.lokacar.model.CarType;
import fr.eni.lokacar.model.Location;
import fr.eni.lokacar.model.StatusReport;
import fr.eni.lokacar.model.User;
import fr.eni.lokacar.model.type_converter.DateConverter;
import fr.eni.lokacar.model.type_converter.ListConverter;

@android.arch.persistence.room.Database(entities = {Car.class, CarType.class, User.class, AgencyAuthentification.class, Location.class, StatusReport.class}, version = 14, exportSchema = false)
@TypeConverters({DateConverter.class, ListConverter.class})
public abstract class Database extends RoomDatabase {

    public abstract CarDAO carDAO();
    public abstract CarTypeDAO carTypeDAO();
    public abstract UserDAO userDAO();
    public abstract AgencyAuthentificationDAO agencyAuthentificationDAO();
    public abstract LocationDAO locationDAO();
    private static Database database;

    public static synchronized Database getDatabase(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context, Database.class, "random")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    // ONLY if you do small requests (simple insert or get)
                    .allowMainThreadQueries()
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
