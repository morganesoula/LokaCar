package fr.eni.lokacar.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

    public AgencyAuthentification getAgencyAuthentification(String username)
    {
        AsyncGet asyncGet = new AsyncGet(this);
        asyncGet.execute(username);

        return agencyAuthentification;
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

    public class AsyncGet extends AsyncTask<String, Void, AgencyAuthentification>
    {
        private AgencyAuthentificationRepository referrer;

        public AsyncGet(AgencyAuthentificationRepository referrer) {
            this.referrer = referrer;
        }

        @Override
        protected AgencyAuthentification doInBackground(String... username)
        {
            return agencyAuthentificationDAO.getOneAgencyAuthentification(username[0]);
        }

        @Override
        protected void onPostExecute(AgencyAuthentification agencyAuthentification) {
            super.onPostExecute(agencyAuthentification);
            referrer.agencyAuthentification = agencyAuthentification;
        }
    }

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
