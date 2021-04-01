package hn.healthypets.proyecto.database.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Esta contiene campos con respecto a las diferentes presentaciones y medidas por ejemplo ml, tableta, capsula
 * */
@Entity
public class TipoDosis {
    @PrimaryKey(autoGenerate = true)
    private long tipoDosisId;

    @NonNull
    private String dosisNombre;

    public TipoDosis(long tipoDosisId, @NonNull String dosisNombre) {
        this.tipoDosisId = tipoDosisId;
        this.dosisNombre = dosisNombre;
    }

    public long getTipoDosisId() {
        return tipoDosisId;
    }

    public void setTipoDosisId(long tipoDosisId) {
        this.tipoDosisId = tipoDosisId;
    }

    @NonNull
    public String getDosisNombre() {
        return dosisNombre;
    }

    public void setDosisNombre(@NonNull String dosisNombre) {
        this.dosisNombre = dosisNombre;
    }
}
