package hn.healthypets.proyecto.database.relacionesTablas;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import hn.healthypets.proyecto.database.Entidades.AgendaMedicamento;
import hn.healthypets.proyecto.database.Entidades.TipoDosis;
//Union de la tabla Dosis con Agenda Medicamentos.
public class DosisConAgendaMedicamento {

    @Embedded public TipoDosis dosis;
    @Relation(
            parentColumn = "tipoDosisId",
            entityColumn = "medicamentoDosisId"
    )
    public List<AgendaMedicamento> dosisAgendaMedicamento;
}
