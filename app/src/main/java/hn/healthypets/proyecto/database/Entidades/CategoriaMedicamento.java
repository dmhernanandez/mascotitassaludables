package hn.healthypets.proyecto.database.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/**
 * Esta clase coresponde a los tipos de Categoria de Medicamentos tales como Vacuna, desparasitante, vitamina, etc.
 * */
@Entity
public class CategoriaMedicamento {
    @PrimaryKey(autoGenerate = true)
    private int categoriaMedicamentoId;



    @NonNull
    private String nombreCategoria;

    public CategoriaMedicamento(int categoriaMedicamentoId, @NonNull String nombreCategoria) {
        this.categoriaMedicamentoId = categoriaMedicamentoId;
        this.nombreCategoria = nombreCategoria;
    }

    public int getCategoriaMedicamentoId() {
        return categoriaMedicamentoId;
    }

    public void setCategoriaMedicamentoId(int categoriaMedicamentoId) {
        this.categoriaMedicamentoId = categoriaMedicamentoId;
    }

    @NonNull
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(@NonNull String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
