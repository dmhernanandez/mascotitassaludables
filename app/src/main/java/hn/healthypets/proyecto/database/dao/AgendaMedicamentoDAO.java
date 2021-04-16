package hn.healthypets.proyecto.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import hn.healthypets.proyecto.database.Entidades.AgendaMedicamento;


@Dao
public interface AgendaMedicamentoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertMedicinesSchedule(AgendaMedicamento agendaMedicamento);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertMedicinesSchedules(AgendaMedicamento agendaMedicamento);

    @Query("SELECT id FROM AgendaMedicamento WHERE nombreMedicamento = :nombreMedicamento")
    public int getIdMedicinesScheduleByName(String nombreMedicamento);

    //Selecciona todas las filas de Categoria Medicamento
      @Query("SELECT * FROM AgendaMedicamento")
    public LiveData<List<AgendaMedicamento>> getAllMedicinesSchedule();

}
