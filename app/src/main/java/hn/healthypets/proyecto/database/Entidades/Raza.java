package hn.healthypets.proyecto.database.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Esta clase contiene atributos para almacenar razas de una especie.
 * */
@Entity(indices = {@Index( value = "nombreRaza",unique = true)})
public class Raza {
    @PrimaryKey(autoGenerate = true)
    private int razaId;
    @NonNull
    private String nombreRaza;
    //LLave foranea de la tabla
    private int razaEspecieId;

    public Raza( @NonNull String nombreRaza, int razaEspecieId) {
        this.nombreRaza = nombreRaza;
        this.razaEspecieId = razaEspecieId;
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

    public int getRazaEspecieId() {
        return razaEspecieId;
    }

    public void setRazaEspecieId(int razaEspecieId) {
        this.razaEspecieId = razaEspecieId;
    }
}
