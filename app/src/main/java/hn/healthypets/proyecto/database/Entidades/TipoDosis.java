package hn.healthypets.proyecto.database.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Esta contiene campos con respecto a las diferentes presentaciones y medidas por ejemplo ml, tableta, capsula
 * */
@Entity(indices = {@Index( value = "dosisNombre",unique = true)})
public class TipoDosis {
    @PrimaryKey(autoGenerate = true)
    private int tipoDosisId;

    @NonNull
    private String dosisNombre;

    public TipoDosis( @NonNull String dosisNombre) {
        this.dosisNombre = dosisNombre;
    }

    public int getTipoDosisId() {
        return tipoDosisId;
    }

    public void setTipoDosisId(int tipoDosisId) {
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
