package hn.healthypets.proyecto.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import hn.healthypets.proyecto.database.Entidades.Raza;

@Dao
public interface RazaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertBreed(Raza raza);

    /*Inserta una lista de*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertBreeds(Raza... raza);

    /* Obtiene todas razas de los perros*/
    @Query("Select * from Raza")
    public List<Raza> getAllBreeds();

    /* Obtiene todas razas de los perros*/
    @Query("Select nombreRaza from Raza")
    public NombreRaza[] getAllNamesBreeds();

    @Query("SELECT razaId FROM Raza WHERE nombreRaza = :nombreRaza")
    public int getIdRazaByName(String nombreRaza);

    @Query("SELECT nombreRaza FROM Raza WHERE razaId = :idRaza")
    public String getNameRazaById(int idRaza);

    @Transaction
    @Query("SELECT r.nombreRaza AS nombreRaza FROM Especie e JOIN Raza r on e.especieId= r.razaEspecieId WHERE e.especieNombre=:nombreRaza")
    public LiveData<List<NombreRaza>> getAllBreedsFromSpecie(String nombreRaza);

    //Clase POJO para devolver un arreglo de String con el nombre la raza de acuerdo al id de la especie o tambien se usa para devolver todas
    // los nombres de especies que existen`
    static class NombreRaza {
        private String nombreRaza;

        public void setNombreRaza(String nombreRaza) {
            this.nombreRaza = nombreRaza;
        }

        public String getNombreRaza() {
            return nombreRaza;
        }
    }
}
