package hn.uth.examen202120010271.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import hn.uth.examen202120010271.Dao.LugarRepository;
import hn.uth.examen202120010271.database.Lugar;

public class HomeViewModel extends AndroidViewModel {

    private LugarRepository repository;
    private final LiveData<List<Lugar>> dataset;


    public HomeViewModel(Application app) {
        super(app);
        this.repository=new LugarRepository(app);
        this.dataset=repository.getAllLugar();


    }

    public LiveData<List<Lugar>> getAllLugar(){
        return dataset;
    }
}