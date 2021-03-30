 package hn.healthypets.proyecto.database;

import java.sql.Time;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

 @Entity
public class AgendaMedicamento {
     //declaramos todos los campos de la tabla y establecemos como autoincrementable el campo id de Agenda medicamento
    @PrimaryKey(autoGenerate = true)
    int id;
    String nombreMedicamento;
    int dosisCantidad;
    int dosisId;
    int intervaloTiempo;
    int numeroDosis;
    String fechaHoraInicio;
    String horaFechaProximaDosis;
    boolean estado;
    

}
