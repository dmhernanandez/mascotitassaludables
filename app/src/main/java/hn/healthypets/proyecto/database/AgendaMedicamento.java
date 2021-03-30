 package hn.healthypets.proyecto.database;

import java.sql.Time;
import java.util.Date;

import androidx.room.Entity;

@Entity
public class AgendaMedicamento {
    int id;
    String nombreMedicamento;
    int dosisCantidad;
    int dosisId;
    int intervaloTiempo;
    int numeroDosis;
    java.sql.Date fechaInicio;
    java.sql.Time horaInicio;
    java.sql.Time horaProximaDosis;
    boolean estado;
    

}
