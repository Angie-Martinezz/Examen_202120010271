package hn.uth.examen202120010271.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hn.uth.examen202120010271.database.Lugar;

@Dao
public interface LugarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Lugar lugar);


    @Update
    void update(Lugar lugar);
    @Delete
    void delete(Lugar lugar);

    @Query("DELETE FROM lugar_tabl")
    void deleteAll();

    @Query("SELECT * FROM lugar_tabl ORDER BY  lugar ASC")
    LiveData<List<Lugar>> getLugar();
}
