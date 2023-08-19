package hn.uth.examen202120010271.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hn.uth.examen202120010271.Dao.ContactoDao;
import hn.uth.examen202120010271.Dao.LugarDao;

@Database(version = 1, exportSchema = false, entities = {Lugar.class, Contactos.class})
public abstract class DatabaseMarcadores extends RoomDatabase {

    public abstract LugarDao lugarDao();

    public abstract ContactoDao contactoDAO();

    private static volatile DatabaseMarcadores INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DatabaseMarcadores getDataBase(final Context context){
        if(INSTANCE == null){
            synchronized (DatabaseMarcadores.class){
                if (INSTANCE == null){
                    Callback callback = new Callback(){
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db){
                            super.onCreate(db);
                            databaseWriteExecutor.execute(() -> {
                                LugarDao testDao = INSTANCE.lugarDao();
                                testDao.deleteAll();

                            });
                        }
                    };
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DatabaseMarcadores.class,"db_recomendaciones").addCallback(callback).build();

                }
            }
        }
        return INSTANCE;
    }
}
