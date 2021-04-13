package hn.healthypets.proyecto.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import hn.healthypets.proyecto.database.Entidades.Raza;
import hn.healthypets.proyecto.database.Entidades.TipoDosis;

@Dao
public interface TipoDosisDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertDoseType(TipoDosis tipoDosis);

    /*Inserta una lista de*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertDoseTypes(TipoDosis... tipoDosis);

    /* Obtiene todas razas de los perros*/
    @Query("Select * from TipoDosis")
    public List<TipoDosis> getAllDoseTypes1();

    /**el LIVEDATA */
    @Query("SELECT dosisNombre AS dosisNombre FROM TipoDosis")
    public LiveData<List<NombreDosis>> getAllDoseTypes();


    public class NombreDosis {
        private String dosisNombre;

        public String getDosisNombre() {
            return dosisNombre;
        }

        public void setDosisNombre(String dosisNombre) {
            this.dosisNombre = dosisNombre;
        }
    }
}