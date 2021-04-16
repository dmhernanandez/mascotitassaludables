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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertMedicine(Medicamento medicamento);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertdewormer(Medicamento medicamento);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public void updateMedicine(Medicamento medicamento);

    //select Para la busqueda de CATEGORIA MEDICAMENTO VACUNA
    @Query("select medicamentoId,nombre, descripcion,fechaAplicacion,rutaFotoComprobante,observacion  from Medicamento INNER JOIN Mascota ON mascotaMedicadaId=mascotaId WHERE  medicamentoCatMedicamentoId=1")
    public  LiveData<List<Vacunacard>> getMedicinesScheduleByVacuna();

    @Query("select medicamentoId,mascotaMedicadaId,nombre,descripcion, fechaAplicacion,pesoKilogramo,observacion  from Medicamento INNER JOIN Mascota ON mascotaId= mascotaMedicadaId WHERE  medicamentoCatMedicamentoId=2")
    public  LiveData<List<Desparacitantecard>>getMedicinesScheduleByDesparasitante();

    //Selecciona todas las filas de Categoria Medicamento
    @Query("SELECT * FROM Medicamento ")
    public LiveData<List<Medicamento>> geAllMedicines();

    static class Vacunacard{

        private int medicamentoId;
        private String nombre;
        private String descripcion;
        private String fechaAplicacion;
        private String observacion;
        private String rutaFotoComprobante;

        public int getMedicamentoId() {
            return medicamentoId;
        }

        public void setMedicamentoId(int medicamentoId) {
            this.medicamentoId = medicamentoId;
        }

        public String getObservacion() {
            return observacion;
        }

        public void setObservacion(String observacion) {
            this.observacion = observacion;
        }


        public String getRutaFotoComprobante() {
            return rutaFotoComprobante;
        }

        public void setRutaFotoComprobante(String rutaFotoComprobante) {
            this.rutaFotoComprobante = rutaFotoComprobante;
        }


        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getFechaAplicacion() {
            return fechaAplicacion;
        }

        public void setFechaAplicacion(String fechaAplicacion) {
            this.fechaAplicacion = fechaAplicacion;
        }
    }

    static class Desparacitantecard{

        private int medicamentoId;
        private int mascotaMedicadaId;
        private String nombre;
        private String descripcion;
        private String fechaAplicacion;
        private String observacion;
        private String pesoKilogramo;

        public int getMascotaMedicadaId() {
            return mascotaMedicadaId;
        }

        public void setMascotaMedicadaId(int mascotaId) {
            this.mascotaMedicadaId = mascotaId;
        }

        public int getMedicamentoId() {
            return medicamentoId;
        }

        public void setMedicamentoId(int medicamentoId) {
            this.medicamentoId = medicamentoId;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getFechaAplicacion() {
            return fechaAplicacion;
        }

        public void setFechaAplicacion(String fechaAplicacion) {
            this.fechaAplicacion = fechaAplicacion;
        }

        public String getObservacion() {
            return observacion;
        }

        public void setObservacion(String observacion) {
            this.observacion = observacion;
        }

        public String getPesoKilogramo() {
            return pesoKilogramo;
        }

        public void setPesoKilogramo(String pesoKilogramo) {
            this.pesoKilogramo = pesoKilogramo;
        }
    }

}
