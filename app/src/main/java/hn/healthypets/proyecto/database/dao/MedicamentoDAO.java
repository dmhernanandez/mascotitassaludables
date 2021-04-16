package hn.healthypets.proyecto.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import hn.healthypets.proyecto.database.Entidades.CategoriaMedicamento;
import hn.healthypets.proyecto.database.Entidades.Medicamento;

@Dao
public interface MedicamentoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertMedicine(Medicamento medicamento);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertdewormer(Medicamento medicamento);

//select Para la busqueda de CATEGORIA MEDICAMENTO VACUNA
    @Query("select nombre, descripcion,fechaAplicacion,rutaFotoComprobante  from Medicamento INNER JOIN Mascota ON mascotaMedicadaId=mascotaId WHERE mascotaMedicadaId=mascotaId & medicamentoCatMedicamentoId=1")
    public  LiveData<List<Vacunacard>> getMedicinesScheduleByVacuna();

    @Query("SELECT * FROM Medicamento WHERE medicamentoCatMedicamentoId = 2")
    public  LiveData<List<Medicamento>>getMedicinesScheduleByDesparasitante();

    //Selecciona todas las filas de Categoria Medicamento
    @Query("SELECT * FROM Medicamento ")
    public LiveData<List<Medicamento>> geAllMedicines();

    static class Vacunacard{
        private String nombre;
        private String descripcion;
        private String fechaAplicacion;

        private String rutaFotoComprobante;

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

}
