package fr.eni.lokacar.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import fr.eni.lokacar.dao.AgencyAuthentificationDAO;
import fr.eni.lokacar.dao.Database;
import fr.eni.lokacar.model.AgencyAuthentification;

public class AgencyAuthentificationRepository {

    AgencyAuthentificationDAO agencyAuthentificationDAO;
    AgencyAuthentification agencyAuthentification;


    public AgencyAuthentificationRepository(Context context)
    {
        Database database = Database.getDatabase(context);
        agencyAuthentificationDAO = database.agencyAuthentificationDAO();
        agencyAuthentification = new AgencyAuthentification(0, null, null);
    }

    public AgencyAuthentification getAgencyAuthentification(final String username) {

//        Callable<AgencyAuthentification> callable = new Callable<AgencyAuthentification>() {
//            @Override
//            public AgencyAuthentification call() throws Exception {
//                return agencyAuthentificationDAO.getOneAgencyAuthentification(username);
//            }
//        };
//
//        Future<AgencyAuthentification> future = Executors.newSingleThreadExecutor().submit(callable);
//
//        return future.get();

        // Faster way to do what's above this
        // Don't forget to add .allowMainThreadQueries into Database
        return agencyAuthentificationDAO.getOneAgencyAuthentification(username);
    }

    public void insert(AgencyAuthentification agencyAuthentification)
    {
        AsyncInsert asyncInsert = new AsyncInsert();
        asyncInsert.execute(agencyAuthentification);
    }

    public void update(AgencyAuthentification agencyAuthentification)
    {
        AsyncUpdate asyncUpdate = new AsyncUpdate();
        asyncUpdate.execute(agencyAuthentification);
    }

    public void delete(AgencyAuthentification agencyAuthentification)
    {
        AsyncDelete asyncDelete = new AsyncDelete();
        asyncDelete.execute(agencyAuthentification);
    }

    /**
     * Asynchrone tasks
     */



    public class AsyncInsert extends AsyncTask<AgencyAuthentification, Void, Void>
    {
        @Override
        protected Void doInBackground(AgencyAuthentification... agencyAuthentifications) {
            agencyAuthentificationDAO.insert(agencyAuthentifications[0]);
            return null;
        }
    }

    public class AsyncUpdate extends AsyncTask<AgencyAuthentification, Void, Void>
    {
        @Override
        protected Void doInBackground(AgencyAuthentification... agencyAuthentifications) {
            agencyAuthentificationDAO.update(agencyAuthentifications[0]);
            return null;
        }
    }

    public class AsyncDelete extends AsyncTask<AgencyAuthentification, Void, Void>
    {
        @Override
        protected Void doInBackground(AgencyAuthentification... agencyAuthentifications) {
            agencyAuthentificationDAO.delete(agencyAuthentifications[0]);
            return null;
        }
    }
}
