package hn.healthypets.proyecto.database.Entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Esta clase contiene campos para guardar datos de especie como ser gatos, perros, loros, etc
 * */
@Entity
public class Especie {
    @PrimaryKey(autoGenerate = true)
    private  long especieId;
    private String especieNombre;

    public Especie(long especieId, String especieNombre) {
        this.especieId = especieId;
        this.especieNombre = especieNombre;
    }

    public long getEspecieId() {
        return especieId;
    }

    public void setEspecieId(long especieId) {
        this.especieId = especieId;
    }

    public String getEspecieNombre() {
        return especieNombre;
    }

    public void setEspecieNombre(String especieNombre) {
        this.especieNombre = especieNombre;
    }
}
