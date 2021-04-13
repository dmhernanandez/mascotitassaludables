package hn.healthypets.proyecto.database.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Esta entidad contiene campos para guardar valores macho y hembra
 * */
@Entity(indices = {@Index(value = "genero",unique = true)})
public class Genero {
    @PrimaryKey(autoGenerate = true)
    private int generoId;
    @NonNull
    private String genero;
   //Constructor
    public Genero( @NonNull String genero) {
        this.genero = genero;
    }



    public int getGeneroId() {
        return generoId;
    }

    public void setGeneroId(int generoId) {
        this.generoId = generoId;
    }

    @NonNull
    public String getGenero() {
        return genero;
    }

    public void setGenero(@NonNull String genero) {
        this.genero = genero;
    }
}
