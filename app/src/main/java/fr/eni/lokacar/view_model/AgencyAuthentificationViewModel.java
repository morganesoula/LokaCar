package fr.eni.lokacar.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import fr.eni.lokacar.model.AgencyAuthentification;
import fr.eni.lokacar.repository.AgencyAuthentificationRepository;

public class AgencyAuthentificationViewModel extends AndroidViewModel {

    AgencyAuthentificationRepository agencyAuthentificationRepository;

    public AgencyAuthentificationViewModel(@NonNull Application application)
    {
        super(application);
        agencyAuthentificationRepository = new AgencyAuthentificationRepository(application);
    }

    public AgencyAuthentification getAgencyAuthentification(String username) throws ExecutionException, InterruptedException {
        Log.i("XXX", "Hop, un petit tour par ici (ViewModel)");
        return agencyAuthentificationRepository.getAgencyAuthentification(username);
    }

    public void insert(AgencyAuthentification agencyAuthentification)
    {
        agencyAuthentificationRepository.insert(agencyAuthentification);
    }
}
