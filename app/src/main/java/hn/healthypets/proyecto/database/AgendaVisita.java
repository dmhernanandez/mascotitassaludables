package hn.healthypets.proyecto.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AgendaVisita {
    @PrimaryKey(autoGenerate = true)
    int id;
    String nombreActividad;
    String fechaVencimiento;
    String horaVencimiento;
    int tiempoAnticipacion;
    String observación;
    
}
