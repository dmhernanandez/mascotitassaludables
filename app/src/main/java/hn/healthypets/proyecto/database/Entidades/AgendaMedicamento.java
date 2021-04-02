 package hn.healthypets.proyecto.database.Entidades;

import java.sql.Time;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

 @Entity
public class AgendaMedicamento {
     //declaramos todos los campos de la tabla y establecemos como autoincrementable el campo id de Agenda medicamento
    @PrimaryKey(autoGenerate = true)
    int id;
    private String nombreMedicamento;
    private  int dosisCantidad;
    private int dosisId;
    private int intervaloTiempo;
    private int numeroDosis;
    private String fechaHoraInicio;
    private String horaFechaProximaDosis;
    private boolean estado;

    //Llave foranea de mascota en la Agenda de medicamento
    private int mascotaMedicamentoId;

    //Llave foranea para las unidades de medida
    private int unidadMedidaId;

    //LLave foranaea de la tabla tipo Dosis
     private int medicamentoDosisId;

     public AgendaMedicamento(int id, String nombreMedicamento, int dosisCantidad, int dosisId, int intervaloTiempo, int numeroDosis, String fechaHoraInicio, String horaFechaProximaDosis, boolean estado, int mascotaMedicamentoId, int unidadMedidaId, int medicamentoDosisId) {
         this.id = id;
         this.nombreMedicamento = nombreMedicamento;
         this.dosisCantidad = dosisCantidad;
         this.dosisId = dosisId;
         this.intervaloTiempo = intervaloTiempo;
         this.numeroDosis = numeroDosis;
         this.fechaHoraInicio = fechaHoraInicio;
         this.horaFechaProximaDosis = horaFechaProximaDosis;
         this.estado = estado;
         this.mascotaMedicamentoId = mascotaMedicamentoId;
         this.unidadMedidaId = unidadMedidaId;
         this.medicamentoDosisId = medicamentoDosisId;
     }

     public void setId(int id) {
         this.id = id;
     }

     public void setNombreMedicamento(String nombreMedicamento) {
         this.nombreMedicamento = nombreMedicamento;
     }

     public void setDosisCantidad(int dosisCantidad) {
         this.dosisCantidad = dosisCantidad;
     }

     public void setDosisId(int dosisId) {
         this.dosisId = dosisId;
     }

     public void setIntervaloTiempo(int intervaloTiempo) {
         this.intervaloTiempo = intervaloTiempo;
     }

     public void setNumeroDosis(int numeroDosis) {
         this.numeroDosis = numeroDosis;
     }

     public void setFechaHoraInicio(String fechaHoraInicio) {
         this.fechaHoraInicio = fechaHoraInicio;
     }

     public void setHoraFechaProximaDosis(String horaFechaProximaDosis) {
         this.horaFechaProximaDosis = horaFechaProximaDosis;
     }

     public void setEstado(boolean estado) {
         this.estado = estado;
     }

     public void setMascotaMedicamentoId(int mascotaMedicamentoId) {
         this.mascotaMedicamentoId = mascotaMedicamentoId;
     }

     public void setUnidadMedidaId(int unidadMedidaId) {
         this.unidadMedidaId = unidadMedidaId;
     }

     public void setMedicamentoDosisId(int medicamentoDosisId) {
         this.medicamentoDosisId = medicamentoDosisId;
     }

     public int getId() {
         return id;
     }

     public String getNombreMedicamento() {
         return nombreMedicamento;
     }

     public int getDosisCantidad() {
         return dosisCantidad;
     }

     public int getDosisId() {
         return dosisId;
     }

     public int getIntervaloTiempo() {
         return intervaloTiempo;
     }

     public int getNumeroDosis() {
         return numeroDosis;
     }

     public String getFechaHoraInicio() {
         return fechaHoraInicio;
     }

     public String getHoraFechaProximaDosis() {
         return horaFechaProximaDosis;
     }

     public boolean isEstado() {
         return estado;
     }

     public int getMascotaMedicamentoId() {
         return mascotaMedicamentoId;
     }

     public int getUnidadMedidaId() {
         return unidadMedidaId;
     }

     public int getMedicamentoDosisId() {
         return medicamentoDosisId;
     }
 }
