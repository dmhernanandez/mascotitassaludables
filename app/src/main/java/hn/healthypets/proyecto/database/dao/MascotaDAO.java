package hn.healthypets.proyecto.database.dao;

import java.util.ArrayList;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import hn.healthypets.proyecto.database.Entidades.Mascota;

// Objeto de acceso a datos(MascotaDAO)
@Dao
public interface MascotaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertarMascota(Mascota mascota);

    @Update
    public void actualizarMascota(Mascota mascota);

    @Query("SELECT * FROM Mascota")
    ArrayList<Mascota> getAllPets();

}
