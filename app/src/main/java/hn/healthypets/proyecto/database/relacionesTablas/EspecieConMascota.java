package hn.healthypets.proyecto.database.relacionesTablas;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import hn.healthypets.proyecto.database.Entidades.Especie;
import hn.healthypets.proyecto.database.Entidades.Mascota;

//Relacion 1:M de la tabla Especie a Mascotas
public class EspecieConMascota {
    @Embedded
    public Especie mascotaPorEspecie;
    @Relation(
            parentColumn = "especieId",
            entityColumn = "mascotaEspecieId"
    )
    public List<Mascota> mascotaEspecie;
}
