package hn.uth.examen202120010271.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "lugar_tabl")
public class Lugar implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "idlugar")
    private int idlugar;
    @NonNull
    @ColumnInfo(name = "lugar")
    private String lugar;

    @ColumnInfo(name = "longitud")
    private Double longitud;
    @NonNull
    @ColumnInfo(name = "latitud")
    private Double latitud;

    public Lugar(@NonNull String lugar, @NonNull Double longitud, @NonNull Double latitud) {
        this.lugar = lugar;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public int getIdlugar() {
        return idlugar;
    }

    public void setIdlugar(int idlugar) {
        this.idlugar = idlugar;
    }

    @NonNull
    public String getLugar() {
        return lugar;
    }

    public void setLugar(@NonNull String lugar) {
        this.lugar = lugar;
    }

    @NonNull
    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(@NonNull Double longitud) {
        this.longitud = longitud;
    }
    public String getLongitude() {
        return longitud+"";
    }

    @NonNull
    public Double getLatitud() {
        return latitud;
    }
    public String getLatitude() {
        return latitud+"";
    }

    public void setLatitud(@NonNull Double latitud) {
        this.latitud = latitud;
    }

    public String toText(){
        return this.latitud+","+this.longitud;
    }
}
