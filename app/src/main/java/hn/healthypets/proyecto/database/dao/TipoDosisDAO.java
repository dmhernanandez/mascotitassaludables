package hn.healthypets.proyecto.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

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

    @Query("SELECT dosisNombre AS dosisNombre FROM TipoDosis")
    public LiveData<List<NombreDosis>> getAllDoseTypes();

    /*Obtiene id del genero por el nombre*/
    @Query("SELECT tipoDosisId FROM TipoDosis WHERE dosisNombre = :dosisNombre")
    public int getIdDoseTypeByName(String dosisNombre);

    @Query("SELECT dosisNombre as nombre FROM TipoDosis WHERE tipoDosisId = :idTipoDosis")
    public String getNameDoseTypeById(int idTipoDosis);

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
