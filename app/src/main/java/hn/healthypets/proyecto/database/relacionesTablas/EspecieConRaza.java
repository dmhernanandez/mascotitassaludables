package hn.healthypets.proyecto.database.relacionesTablas;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import hn.healthypets.proyecto.database.Entidades.Especie;
import hn.healthypets.proyecto.database.Entidades.Raza;

public class EspecieConRaza {
    //Union de la tabla especie, con la tabla Raza, es decir que una especie puede tener varias razas
    @Embedded public Especie especie;
    @Relation(
        parentColumn = "especieId",
        entityColumn = "razaEspecieId"
    )
    public List<Raza> especieRaza;

}
