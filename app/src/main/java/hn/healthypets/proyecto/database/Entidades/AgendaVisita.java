package hn.healthypets.proyecto.database.Entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AgendaVisita {
    @PrimaryKey(autoGenerate = true)
   private  int agendaVisitaId;
   private String nombreActividad;
   private int dia;   //estos campos representan el dia y la hora  que se debe ejecutar la alarma
   private int mes;
   private int anio;
   private int hora;
   private int minuto;
   private  int tiempoAnticipacion;
   private  String observación;
   private  int estado;
   private int alarmId;
    //llave foranea de la tabla mascota
    private  int mascotaAgendaVisitaId;
    //Llave foranea de la tabla Categoria
    private int visitaCatMedicamentoId;

    public AgendaVisita(int agendaVisitaId, String nombreActividad, int dia,
                        int mes, int anio, int hora, int minuto, int tiempoAnticipacion,
                        String observación, int alarmId, int mascotaAgendaVisitaId, int visitaCatMedicamentoId, int estado) {
        this.agendaVisitaId = agendaVisitaId;
        this.nombreActividad = nombreActividad;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minuto = minuto;
        this.tiempoAnticipacion = tiempoAnticipacion;
        this.observación = observación;
        this.alarmId = alarmId;
        this.mascotaAgendaVisitaId = mascotaAgendaVisitaId;
        this.visitaCatMedicamentoId = visitaCatMedicamentoId;
        this.estado=estado;
    }

    public void setAgendaVisitaId(int agendaVisitaId) {
        this.agendaVisitaId = agendaVisitaId;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
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

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
