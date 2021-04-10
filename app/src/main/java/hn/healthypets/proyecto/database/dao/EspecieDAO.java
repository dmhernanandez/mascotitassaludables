package hn.healthypets.proyecto.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import hn.healthypets.proyecto.database.Entidades.Especie;

@Dao
public interface EspecieDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertSpecies(Especie especie);

    //Optiene todos los campos de la tabla especie
   @Query("SELECT * FROM Especie")
    public List<Especie> getAllSpecies();

   //Obtiene solo el nombre del de la especie
    @Query("SELECT especieNombre AS nombreEspecie from Especie")
    public LiveData<List<NombreEspecie>> getAllNameSpecies();


   @Query("SELECT especieId FROM especie WHERE especieNombre = :nombreEspecie")
   public int getIdSpeciesByName(String nombreEspecie);


    //Clase POJO para devolver un arreglo de String con el nombre la especie unicamente
    static class NombreEspecie{
        private String nombreEspecie;

        public String getNombreEspecie() {
            return nombreEspecie;
        }

        public void setNombreEspecie(String nombreEspecie) {
            this.nombreEspecie = nombreEspecie;
        }
    }

}
