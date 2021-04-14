package hn.healthypets.proyecto.database.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Contiene los campos que tienen los valores de
 * */
@Entity(indices = {@Index( value = "descripcion",unique = true)})
public class Medicamento {
    @PrimaryKey(autoGenerate = true)
    private int medicamentoId;
    @NonNull
    private String descripcion;
    @NonNull
    private String fechaAplicacion;
    private String rutaFotoComprobante;
    private float pesoKilogramo;
    private String observacion;

    //Esta es la llave foranea de la mascota a la que se asigno la medicina
    private int mascotaMedicadaId;

    //Llave foranea de categoria del medicamento
    private int medicamentoCatMedicamentoId;

    public Medicamento( int medicamentoId, @NonNull String descripcion, @NonNull String fechaAplicacion, String rutaFotoComprobante, float pesoKilogramo, String observacion, int mascotaMedicadaId, int medicamentoCatMedicamentoId) {
        this.medicamentoId = medicamentoId;
        this.descripcion = descripcion;
        this.fechaAplicacion = fechaAplicacion;
        this.rutaFotoComprobante = rutaFotoComprobante;
        this.pesoKilogramo = pesoKilogramo;
        this.observacion = observacion;
        this.mascotaMedicadaId = mascotaMedicadaId;
        this.medicamentoCatMedicamentoId = medicamentoCatMedicamentoId;
    }

    public int getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(int medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    @NonNull
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NonNull String descripcion) {
        this.descripcion = descripcion;
    }

    @NonNull
    public String getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(@NonNull String fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public String getRutaFotoComprobante() {
        return rutaFotoComprobante;
    }

    public void setRutaFotoComprobante(String rutaFotoComprobante) {
        this.rutaFotoComprobante = rutaFotoComprobante;
    }

    public float getPesoKilogramo() {
        return pesoKilogramo;
    }

    public void setPesoKilogramo(float pesoKilogramo) {
        this.pesoKilogramo = pesoKilogramo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getMascotaMedicadaId() {
        return mascotaMedicadaId;
    }

    public void setMascotaMedicadaId(int mascotaMedicadaId) {
        this.mascotaMedicadaId = mascotaMedicadaId;
    }

    public int getMedicamentoCatMedicamentoId() {
        return medicamentoCatMedicamentoId;
    }

    public void setMedicamentoCatMedicamentoId(int medicamentoCatMedicamentoId) {
        this.medicamentoCatMedicamentoId = medicamentoCatMedicamentoId;
    }
}
