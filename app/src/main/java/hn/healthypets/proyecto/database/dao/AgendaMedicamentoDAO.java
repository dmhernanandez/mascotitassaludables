package hn.healthypets.proyecto.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hn.healthypets.proyecto.database.Entidades.AgendaMedicamento;

@Dao
public interface AgendaMedicamentoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertMedicinesSchedule(AgendaMedicamento agendaMedicamento);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertMedicinesSchedules(AgendaMedicamento agendaMedicamento);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public void updateAgendaMedicamento(AgendaMedicamento agendaMedicamento);

    @Query("SELECT id FROM AgendaMedicamento WHERE nombreMedicamento = :nombreMedicamento")
    public int getIdMedicinesScheduleByName(String nombreMedicamento);

    //Selecciona todas las filas de Categoria Medicamento

    @Query("SELECT * FROM AgendaMedicamento")
    public LiveData<List<AgendaMedicamento>> getAllMedicinesSchedule();

}
