package hn.healthypets.proyecto.database.relacionesTablas;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import hn.healthypets.proyecto.database.Entidades.AgendaVisita;
import hn.healthypets.proyecto.database.Entidades.CategoriaMedicamento;

public class CategoriaMedAgendaVisita {
    @Embedded
    public CategoriaMedicamento categoriaMedicamento;
    @Relation(
            parentColumn = "categoriaMedicamentoId",
            entityColumn = "visitaCatMedicamentoId"
    )
    public List<AgendaVisita> agendaVisita;
}
