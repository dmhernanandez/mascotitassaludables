package hn.healthypets.proyecto.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import hn.healthypets.proyecto.database.Entidades.Medicamento;

@Dao
public interface MedicamentoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertMedicine(Medicamento medicamento);

}
