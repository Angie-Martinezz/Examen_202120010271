package hn.uth.examen202120010271.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hn.uth.examen202120010271.database.Contactos;

@Dao
public interface ContactoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contactos contacto);


    @Update
    void update(Contactos contacto);
    @Delete
    void delete(Contactos contacto);

    @Query("DELETE FROM contactos_tabl")
    void deleteAll();

    @Query("SELECT * FROM contactos_tabl ORDER BY  nombre ASC")
    LiveData<List<Contactos>> getContacto();

}
