package hn.healthypets.proyecto.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import hn.healthypets.proyecto.database.Entidades.Especie;
import hn.healthypets.proyecto.database.relacionesTablas.EspecieConRaza;

@Dao
public interface EspecieDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertSpecies(Especie especie);

   @Transaction
    @Query("SELECT r.nombreRaza FROM Especie e JOIN Raza r on e.especieId= r.razaEspecieId WHERE e.especieNombre=:nombreRaza")
    public List<EspecieConRaza> getAllRazaFromSpecie(String nombreRaza);

    class prueba{
        private  String nombre;

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }
    }
}