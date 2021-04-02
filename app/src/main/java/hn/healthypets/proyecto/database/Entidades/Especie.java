package hn.healthypets.proyecto.database.Entidades;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Esta clase contiene campos para guardar datos de especie como ser gatos, perros, loros, etc
 * */
@Entity(indices = {@Index( value = "especieNombre",unique = true)})
public class Especie {
    @PrimaryKey(autoGenerate = true)
    private  int especieId;
    private String especieNombre;

    public Especie( String especieNombre) {

        this.especieNombre = especieNombre;
    }

    public int getEspecieId() {
        return especieId;
    }

    public void setEspecieId(int especieId) {
        this.especieId = especieId;
    }

    public String getEspecieNombre() {
        return especieNombre;
    }

    public void setEspecieNombre(String especieNombre) {
        this.especieNombre = especieNombre;
    }
}
