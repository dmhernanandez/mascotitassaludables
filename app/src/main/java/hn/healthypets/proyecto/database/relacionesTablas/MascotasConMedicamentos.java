package hn.healthypets.proyecto.database.relacionesTablas;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import hn.healthypets.proyecto.database.Entidades.Mascota;
import hn.healthypets.proyecto.database.Entidades.Medicamento;

//Esta clase crea la relación de una mascota a varias vacunas
public class MascotasConMedicamentos {

    /**
     * En esta relación establecemos que por cada mascota se generará una lista con todas las vacunas aplicadas
     * */
  @Embedded public Mascota mascota;
    @Relation(
            parentColumn = "mascotaId",
            entityColumn = "mascotaMedicadaId"
    )
    public List<Medicamento> mascotaMedicada;

}
