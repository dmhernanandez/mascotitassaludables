package hn.healthypets.proyecto.database.relacionesTablas;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import hn.healthypets.proyecto.database.Entidades.Mascota;
import hn.healthypets.proyecto.database.Entidades.Raza;

public class RazaConMascota {
    @Embedded public Raza raza;
    @Relation(
            parentColumn = "razaId",
            entityColumn = "razaMascotaId"

    )
    public List<Mascota> mascotaRaza;
}
