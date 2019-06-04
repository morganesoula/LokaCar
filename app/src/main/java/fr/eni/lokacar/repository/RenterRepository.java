package fr.eni.lokacar.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import fr.eni.lokacar.dao.Database;
import fr.eni.lokacar.dao.RenterDAO;
import fr.eni.lokacar.model.Renter;

public class RenterRepository {

    RenterDAO renterDAO;
    LiveData<List<Renter>> listRenters;


    public RenterRepository(Context context)
    {
        Database database = Database.getDatabase(context);
        renterDAO = database.renterDAO();
        listRenters = renterDAO.getAll();
    }


    public LiveData<List<Renter>> getAll() {
        return listRenters;
    }

    public Renter getRenter(final int id)
    {
        return renterDAO.getRenter(id);
    }

    public void insert(Renter renter)
    {
        RenterRepository.AsyncInsert asyncInsert = new RenterRepository.AsyncInsert();
        asyncInsert.execute(renter);
    }

    public void update(Renter renter)
    {
        RenterRepository.AsyncUpdate asyncUpdate = new RenterRepository.AsyncUpdate();
        asyncUpdate.execute(renter);
    }

    public void delete(Renter renter)
    {
        RenterRepository.AsyncDelete asyncDelete = new RenterRepository.AsyncDelete();
        asyncDelete.execute(renter);
    }


    /**
     * Asynchrone tasks
     */
    public class AsyncInsert extends AsyncTask<Renter, Void, Void>
    {
        @Override
        protected Void doInBackground(Renter... renters) {
            renterDAO.insert(renters[0]);
            return null;
        }
    }

    public class AsyncUpdate extends AsyncTask<Renter, Void, Void>
    {
        @Override
        protected Void doInBackground(Renter... renters) {
            renterDAO.update(renters[0]);
            return null;
        }
    }

    public class AsyncDelete extends AsyncTask<Renter, Void, Void>
    {
        @Override
        protected Void doInBackground(Renter... renters) {
            renterDAO.delete(renters[0]);
            return null;
        }
    }


}
