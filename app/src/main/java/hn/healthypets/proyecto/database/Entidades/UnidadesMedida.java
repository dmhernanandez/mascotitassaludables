package hn.healthypets.proyecto.database.Entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*Esta clase contiene campos que contendra valores para */
@Entity
public class UnidadesMedida {
    @PrimaryKey(autoGenerate = true)
    private int medidaId;
    private String nombreMedidal;

    public UnidadesMedida(int medidaId, String nombreMedidal) {
        this.medidaId = medidaId;
        this.nombreMedidal = nombreMedidal;
    }

    public int getMedidaId() {
        return medidaId;
    }

    public void setMedidaId(int medidaId) {
        this.medidaId = medidaId;
    }

    public String getNombreMedidal() {
        return nombreMedidal;
    }

    public void setNombreMedidal(String nombreMedidal) {
        this.nombreMedidal = nombreMedidal;
    }
}
