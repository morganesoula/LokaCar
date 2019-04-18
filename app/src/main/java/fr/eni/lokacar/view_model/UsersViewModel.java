package fr.eni.lokacar.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import fr.eni.lokacar.model.User;
import fr.eni.lokacar.repository.UserRepository;

public class UsersViewModel extends AndroidViewModel {

    private LiveData<List<User>> users;

    UserRepository repository;

    public UsersViewModel(@NonNull Application application)
    {
        super(application);
        repository = new UserRepository(application);
        init();
    }

    private void init()
    {
        users = repository.getAll();
    }

    public void insert(User user)
    {
        repository.insert(user);
    }

    public LiveData<List<User>> getAll()
    {
        return users;
    }


    public void update(User user)
    {
        repository.update(user);
    }

    public void delete(User user)
    {
        repository.delete(user);
    }
}