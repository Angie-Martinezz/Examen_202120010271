package hn.uth.examen202120010271.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import hn.uth.examen202120010271.Dao.LugarRepository;
import hn.uth.examen202120010271.database.Lugar;

public class DashboardViewModel extends AndroidViewModel {

    private static LugarRepository repository;
    private final LiveData<List<Lugar>> dataset;

    public DashboardViewModel(@NonNull Application app) {
        super(app);
        this.repository=new LugarRepository(app);
        this.dataset=repository.getAllLugar();
    }

    public LiveData<List<Lugar>> getAllLugares(){
        return dataset;
    }

    public static void insert(Lugar lugar){repository.insertL(lugar);}

    public static void update(Lugar lugar){repository.updateL(lugar);}
    public static void delete(Lugar lugar){repository.deleteL(lugar);}
}