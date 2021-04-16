package hn.healthypets.proyecto.database.relacionesTablas;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import hn.healthypets.proyecto.database.Entidades.AgendaMedicamento;
import hn.healthypets.proyecto.database.Entidades.TipoDosis;

//Union de la tabla Dosis con Agenda Medicamento.
public class DosisConAgendaMedicamento {

    @Embedded
    public TipoDosis dosis;
    @Relation(
            parentColumn = "tipoDosisId",
            entityColumn = "medicamentoDosisId"
    )
    public List<AgendaMedicamento> dosisAgendaMedicamento;
}
