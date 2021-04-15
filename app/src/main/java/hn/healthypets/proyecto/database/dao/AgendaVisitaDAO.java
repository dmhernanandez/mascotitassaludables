package hn.healthypets.proyecto.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import hn.healthypets.proyecto.database.Entidades.AgendaVisita;

@Dao
public interface AgendaVisitaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void inertNewTask(AgendaVisita newTask);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public void udateTak(AgendaVisita updatedTask);


     @Query("DELETE FROM  AgendaVisita  WHERE agendaVisitaId=:idTask ")
    public void deleteTask(int idTask);

    //Optiene todos los campos de la tabla especie
    @Query("SELECT * FROM AgendaVisita ")
    public LiveData<List<AgendaVisita>> getAllActivities();



    //Se obtiene el nombre de una especie de acuerdo a su id
    @Query("SELECT especieNombre as nombreEspecie FROM Especie WHERE especieId = :idEspecie")
    public String getNameSpecieById(int idEspecie);

    @Query("SELECT especieId FROM especie WHERE especieNombre = :nombreEspecie")
    public int getIdSpeciesByName(String nombreEspecie);
}
