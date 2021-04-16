package hn.healthypets.proyecto.database.relacionesTablas;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import hn.healthypets.proyecto.database.Entidades.AgendaMedicamento;
import hn.healthypets.proyecto.database.Entidades.Mascota;

//Union de la tabla Mascota con Agenda Medicamento, para agendar
public class MascotaConAgendMedicamento {
    @Embedded
    public Mascota mascota;
    @Relation(parentColumn = "mascotaId",
            entityColumn = "mascotaMedicamentoId")
    private List<AgendaMedicamento> mascataAgendaMedicamento;
}
