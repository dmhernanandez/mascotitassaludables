package hn.healthypets.proyecto.database.relacionesTablas;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import hn.healthypets.proyecto.database.Entidades.Genero;
import hn.healthypets.proyecto.database.Entidades.Mascota;

public class GeneroConMascota {
    //Por cada genero devuelve una lista de mascotas pertecientes a ese genero.
    @Embedded public Genero genero;
        @Relation(
                parentColumn = "generoId",
                entityColumn = "generoMascotaId"
        )
    public List<Mascota> mascotaGenero;
}
