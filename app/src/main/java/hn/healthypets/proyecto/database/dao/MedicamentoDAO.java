package hn.healthypets.proyecto.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hn.healthypets.proyecto.database.Entidades.Medicamento;

@Dao
public interface MedicamentoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertMedicine(Medicamento medicamento);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertdewormer(Medicamento medicamento);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public void updateMedicine(Medicamento medicamento);

    //select Para la busqueda de CATEGORIA MEDICAMENTO 1
    @Query("SELECT * FROM Medicamento WHERE medicamentoCatMedicamentoId = 1")
    public LiveData<List<Medicamento>> getMedicinesScheduleByVacuna();

    @Query("SELECT * FROM Medicamento WHERE medicamentoCatMedicamentoId = 2")
    public LiveData<List<Medicamento>> getMedicinesScheduleByDesparasitante();

    //Selecciona todas las filas de Categoria Medicamento
    @Query("SELECT * FROM Medicamento ")
    public LiveData<List<Medicamento>> geAllMedicines();
}
