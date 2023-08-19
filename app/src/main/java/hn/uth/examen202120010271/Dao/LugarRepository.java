package hn.uth.examen202120010271.Dao;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import hn.uth.examen202120010271.database.DatabaseMarcadores;
import hn.uth.examen202120010271.database.Lugar;

public class LugarRepository {
    private LugarDao lugarDao;
    private LiveData<List<Lugar>> dataset;

    public LugarRepository(Application app ) {
        DatabaseMarcadores db = DatabaseMarcadores.getDataBase(app);
        this.lugarDao = db.lugarDao();
        this.dataset = lugarDao.getLugar();

    }
    public LugarRepository(LugarDao lugarDao) {
        this.lugarDao = lugarDao;
    }

    public LiveData<List<Lugar>> getAllLugar()
    {
        return lugarDao.getLugar();
    }

    public void insertL(Lugar lugar) {
        DatabaseMarcadores.databaseWriteExecutor.execute(() -> {
            lugarDao.insert(lugar);
        });
    }

    public void updateL(Lugar lugar) {
        DatabaseMarcadores.databaseWriteExecutor.execute(() -> {
            lugarDao.update(lugar);
        });
    }

    public void deleteL(Lugar lugar) {
        DatabaseMarcadores.databaseWriteExecutor.execute(() -> {
            lugarDao.delete(lugar);
        });
    }
}
