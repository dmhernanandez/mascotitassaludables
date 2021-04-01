package hn.healthypets.proyecto.database.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Esta clase contiene atributos para almacenar razas de una especie.
 * */
@Entity
public class Raza {
    @PrimaryKey(autoGenerate = true)
    private int razaId;
    @NonNull
    private String nombreRaza;
    //LLave foranea de la tabla
    private int generoRazaId;

    public Raza(int razaId, @NonNull String nombreRaza, int generoRazaId) {
        this.razaId = razaId;
        this.nombreRaza = nombreRaza;
        this.generoRazaId = generoRazaId;
    }

    public int getRazaId() {
        return razaId;
    }

    public void setRazaId(int razaId) {
        this.razaId = razaId;
    }

    @NonNull
    public String getNombreRaza() {
        return nombreRaza;
    }

    public void setNombreRaza(@NonNull String nombreRaza) {
        this.nombreRaza = nombreRaza;
    }

    public int getGeneroRazaId() {
        return generoRazaId;
    }

    public void setGeneroRazaId(int generoRazaId) {
        this.generoRazaId = generoRazaId;
    }
}
