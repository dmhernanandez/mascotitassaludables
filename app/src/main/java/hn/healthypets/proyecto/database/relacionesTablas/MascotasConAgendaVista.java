package hn.healthypets.proyecto.database.relacionesTablas;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import hn.healthypets.proyecto.database.Entidades.AgendaVisita;
import hn.healthypets.proyecto.database.Entidades.Mascota;

public class MascotasConAgendaVista {
    //Crea una llave foranea de la tabla Mascota en la tabla AgendaVisita
    @Embedded public Mascota mascota;
    @Relation(
            parentColumn = "mascotaId",
            entityColumn = "mascotaAgendaVisitaId"
    )
    public List<AgendaVisita> mascataAgendaVisita;
}
