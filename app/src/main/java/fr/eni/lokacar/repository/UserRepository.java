package fr.eni.lokacar.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import fr.eni.lokacar.dao.Database;
import fr.eni.lokacar.dao.UserDAO;
import fr.eni.lokacar.model.User;

public class UserRepository {

    UserDAO userDAO;
    LiveData<List<User>> listUsers;


    public UserRepository(Context context)
    {
        Database database = Database.getDatabase(context);
        userDAO = database.userDAO();
        listUsers = userDAO.getAll();
    }


    public LiveData<List<User>> getAll() {
        return listUsers;
    }

    public void insert(User user)
    {
        UserRepository.AsyncInsert asyncInsert = new UserRepository.AsyncInsert();
        asyncInsert.execute(user);
    }

    public void update(User user)
    {
        UserRepository.AsyncUpdate asyncUpdate = new UserRepository.AsyncUpdate();
        asyncUpdate.execute(user);
    }

    public void delete(User user)
    {
        UserRepository.AsyncDelete asyncDelete = new UserRepository.AsyncDelete();
        asyncDelete.execute(user);
    }


    /**
     * Asynchrone tasks
     */
    public class AsyncInsert extends AsyncTask<User, Void, Void>
    {
        @Override
        protected Void doInBackground(User... users) {
            userDAO.insert(users[0]);
            return null;
        }
    }

    public class AsyncUpdate extends AsyncTask<User, Void, Void>
    {
        @Override
        protected Void doInBackground(User... users) {
            userDAO.update(users[0]);
            return null;
        }
    }

    public class AsyncDelete extends AsyncTask<User, Void, Void>
    {
        @Override
        protected Void doInBackground(User... users) {
            userDAO.delete(users[0]);
            return null;
        }
    }


}
