package hn.healthypets.proyecto.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hn.healthypets.proyecto.database.Entidades.AgendaMedicamento;
import hn.healthypets.proyecto.database.Entidades.CategoriaMedicamento;
import hn.healthypets.proyecto.database.Entidades.Medicamento;
import hn.healthypets.proyecto.database.Entidades.Raza;


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


     @Query("select id, nombre, nombreMedicamento ,dosisCantidad,dosisId, dosisNombre,intervaloTiempo,numeroDosis,fechaHoraInicio,horaFechaProximaDosis  from AgendaMedicamento INNER JOIN Mascota ON mascotaMedicamentoId=Mascota.mascotaId  INNER JOIN TipoDosis On tipoDosisId=TipoDosis.tipoDosisId  WHERE dosisId=tipoDosisId")
    public LiveData<List<RecetaMascota>> getAllMedicinesSchedule();

     static class RecetaMascota{


         private int id;
         private String nombre;
         private String nombreMedicamento;
         private int dosisCantidad;
         private  String dosisNombre;
         private int intervaloTiempo;
         private int numeroDosis;
         private String fechaHoraInicio;
         private String horaFechaProximaDosis;

         public int getDosisId() {
             return dosisId;
         }

         public void setDosisId(int dosisId) {
             this.dosisId = dosisId;
         }

         private int dosisId;

         public int getId() {
             return id;
         }

         public void setId(int id) {
             this.id = id;
         }

         public String getNombre() {
             return nombre;
         }

         public void setNombre(String nombre) {
             this.nombre = nombre;
         }

         public String getNombreMedicamento() {
             return nombreMedicamento;
         }

         public void setNombreMedicamento(String nombreMedicamento) {
             this.nombreMedicamento = nombreMedicamento;
         }

         public int getDosisCantidad() {
             return dosisCantidad;
         }

         public void setDosisCantidad(int dosisCantidad) {
             this.dosisCantidad = dosisCantidad;
         }

         public String getDosisNombre() {
             return dosisNombre;
         }

         public void setDosisNombre(String dosisNombre) {
             this.dosisNombre = dosisNombre;
         }

         public int getIntervaloTiempo() {
             return intervaloTiempo;
         }

         public void setIntervaloTiempo(int intervaloTiempo) {
             this.intervaloTiempo = intervaloTiempo;
         }

         public int getNumeroDosis() {
             return numeroDosis;
         }

         public void setNumeroDosis(int numeroDosis) {
             this.numeroDosis = numeroDosis;
         }

         public String getFechaHoraInicio() {
             return fechaHoraInicio;
         }

         public void setFechaHoraInicio(String fechaHoraInicio) {
             this.fechaHoraInicio = fechaHoraInicio;
         }

         public String getHoraFechaProximaDosis() {
             return horaFechaProximaDosis;
         }

         public void setHoraFechaProximaDosis(String horaFechaProximaDosis) {
             this.horaFechaProximaDosis = horaFechaProximaDosis;
         }
     }

}
