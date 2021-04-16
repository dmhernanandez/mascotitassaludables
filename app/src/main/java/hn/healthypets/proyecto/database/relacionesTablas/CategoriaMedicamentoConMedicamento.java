package hn.healthypets.proyecto.database.relacionesTablas;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import hn.healthypets.proyecto.database.Entidades.CategoriaMedicamento;
import hn.healthypets.proyecto.database.Entidades.Medicamento;

public class CategoriaMedicamentoConMedicamento {
    //Por cada categoria de medicamento devuelve una lista con todos medicamentos aplicados a una mascota
    @Embedded
    public CategoriaMedicamento categoriaMedicamento;
    @Relation(
            parentColumn = "medicamentoId",
            entityColumn = "medicamentoCatMedicamentoId"
    )
    public List<Medicamento> medicamentos;
}
