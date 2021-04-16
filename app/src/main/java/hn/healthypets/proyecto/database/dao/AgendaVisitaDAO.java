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

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateTask(AgendaVisita updatedTask);


     @Query("DELETE FROM  AgendaVisita  WHERE agendaVisitaId=:idTask ")
    public void deleteTask(int idTask);

     @Query("UPDATE  AgendaVisita SET estado= :estado WHERE agendaVisitaId=:idAgendaVisita")
     public void updateState(int idAgendaVisita,int estado);

    @Query("UPDATE  AgendaVisita SET estado= :estado WHERE alarmId=:idAlarm")
    public void updateStateByIdAlarm(int idAlarm,int estado);
     
    //Optiene todos los campos de la tabla especie
    @Query("SELECT * FROM AgendaVisita ")
    public LiveData<List<AgendaVisita>> getAllActivities();



    //Se obtiene el nombre de una especie de acuerdo a su id
    @Query("SELECT especieNombre as nombreEspecie FROM Especie WHERE especieId = :idEspecie")
    public String getNameSpecieById(int idEspecie);

    @Query("SELECT especieId FROM especie WHERE especieNombre = :nombreEspecie")
    public int getIdSpeciesByName(String nombreEspecie);

    @Query("SELECT agendaVisitaId, nombreActividad, dia,mes, anio, hora, minuto, " +
            "observación, estado, alarmId, m.nombre, ca.nombreCategoria, mascotaAgendaVisitaId " +
            "FROM agendavisita a JOIN  Mascota m ON  a.mascotaAgendaVisitaId = m.mascotaId " +
            "JOIN CategoriaMedicamento ca ON a.visitaCatMedicamentoId=categoriaMedicamentoId")
    public LiveData<List<AgendaNombre>> getAllAgendaDetails();

    @Query("SELECT agendaVisitaId, nombreActividad, dia,mes, anio, hora, minuto, " +
            "observación, estado, alarmId, m.nombre, ca.nombreCategoria, mascotaAgendaVisitaId " +
            "FROM agendavisita a JOIN  Mascota m ON  a.mascotaAgendaVisitaId = m.mascotaId " +
            "JOIN CategoriaMedicamento ca ON a.visitaCatMedicamentoId=categoriaMedicamentoId WHERE alarmId=:idAlarm")

    public AgendaNombre getAllAgendaDetailsByIdAlarm(int idAlarm);

     public class AgendaNombre{
         private  int agendaVisitaId;
         private String nombreActividad;
         private int dia;   //estos campos representan el dia y la hora  que se debe ejecutar la alarma
         private int mes;
         private int anio;
         private int hora;
         private int minuto;
         private  String observación;
         private  int estado;
         private int alarmId;
         private String nombre;
         private String nombreCategoria;
         private  int mascotaAgendaVisitaId;

         public int getMascotaAgendaVisitaId() {
             return mascotaAgendaVisitaId;
         }

         public void setMascotaAgendaVisitaId(int mascotaAgendaVisitaId) {
             this.mascotaAgendaVisitaId = mascotaAgendaVisitaId;
         }

         public int getAgendaVisitaId() {
             return agendaVisitaId;
         }

         public void setAgendaVisitaId(int agendaVisitaId) {
             this.agendaVisitaId = agendaVisitaId;
         }

         public String getNombreActividad() {
             return nombreActividad;
         }

         public void setNombreActividad(String nombreActividad) {
             this.nombreActividad = nombreActividad;
         }

         public int getDia() {
             return dia;
         }

         public void setDia(int dia) {
             this.dia = dia;
         }

         public int getMes() {
             return mes;
         }

         public void setMes(int mes) {
             this.mes = mes;
         }

         public int getAnio() {
             return anio;
         }

         public void setAnio(int anio) {
             this.anio = anio;
         }

         public int getHora() {
             return hora;
         }

         public void setHora(int hora) {
             this.hora = hora;
         }

         public int getMinuto() {
             return minuto;
         }

         public void setMinuto(int minuto) {
             this.minuto = minuto;
         }

         public String getObservación() {
             return observación;
         }

         public void setObservación(String observación) {
             this.observación = observación;
         }

         public int getEstado() {
             return estado;
         }

         public void setEstado(int estado) {
             this.estado = estado;
         }

         public int getAlarmId() {
             return alarmId;
         }

         public void setAlarmId(int alarmId) {
             this.alarmId = alarmId;
         }

         public String getNombre() {
             return nombre;
         }

         public void setNombre(String nombre) {
             this.nombre = nombre;
         }

         public String getNombreCategoria() {
             return nombreCategoria;
         }

         public void setNombreCategoria(String nombreCategoria) {
             this.nombreCategoria = nombreCategoria;
         }
     }
}
