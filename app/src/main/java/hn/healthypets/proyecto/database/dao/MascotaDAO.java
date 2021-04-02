package hn.healthypets.proyecto.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import hn.healthypets.proyecto.database.Entidades.Mascota;

// Objeto de acceso a datos(MascotaDAO)
@Dao
public interface MascotaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertarMascota(Mascota mascota);

    @Update
    public void actualizarMascota(Mascota mascota);

    //Selecciona a todas Las mascotas y retorna un arreglo
    @Query("SELECT * FROM Mascota")
    public List<Mascota> getAllPets();

}
