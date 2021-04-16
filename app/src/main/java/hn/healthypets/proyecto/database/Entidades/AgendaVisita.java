package hn.healthypets.proyecto.database.Entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AgendaVisita {
    @PrimaryKey(autoGenerate = true)
    private int agendaVisitaId;
    private String nombreActividad;
    private String fechaVencimiento;
    private String horaVencimiento;
    private int tiempoAnticipacion;
    private String observación;

    public AgendaVisita(String nombreActividad, String fechaVencimiento, String horaVencimiento, int tiempoAnticipacion, String observación, int mascotaAgendaVisitaId, int visitaCatMedicamentoId) {
        this.nombreActividad = nombreActividad;
        this.fechaVencimiento = fechaVencimiento;
        this.horaVencimiento = horaVencimiento;
        this.tiempoAnticipacion = tiempoAnticipacion;
        this.observación = observación;
        this.mascotaAgendaVisitaId = mascotaAgendaVisitaId;
        this.visitaCatMedicamentoId = visitaCatMedicamentoId;
    }

    public void setAgendaVisitaId(int agendaVisitaId) {
        this.agendaVisitaId = agendaVisitaId;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setHoraVencimiento(String horaVencimiento) {
        this.horaVencimiento = horaVencimiento;
    }

    public void setTiempoAnticipacion(int tiempoAnticipacion) {
        this.tiempoAnticipacion = tiempoAnticipacion;
    }

    public void setObservación(String observación) {
        this.observación = observación;
    }

    public void setMascotaAgendaVisitaId(int mascotaAgendaVisitaId) {
        this.mascotaAgendaVisitaId = mascotaAgendaVisitaId;
    }

    public void setVisitaCatMedicamentoId(int visitaCatMedicamentoId) {
        this.visitaCatMedicamentoId = visitaCatMedicamentoId;
    }

    public int getAgendaVisitaId() {
        return agendaVisitaId;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getHoraVencimiento() {
        return horaVencimiento;
    }

    public int getTiempoAnticipacion() {
        return tiempoAnticipacion;
    }

    public String getObservación() {
        return observación;
    }

    public int getMascotaAgendaVisitaId() {
        return mascotaAgendaVisitaId;
    }

    public int getVisitaCatMedicamentoId() {
        return visitaCatMedicamentoId;
    }

    //llave foranea de la tabla mascota
    private int mascotaAgendaVisitaId;
    //Llave foranea de la tabla Categoria
    private int visitaCatMedicamentoId;
}
