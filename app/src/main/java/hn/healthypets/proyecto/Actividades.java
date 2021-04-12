package hn.healthypets.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Actividades extends AppCompatActivity {

    private TextView txvNombreMascota;
    private TextView txvNombreActividad;
    private TextView txvFechaHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        txvNombreMascota=findViewById(R.id.txvNombreMascotaMedicamento);
        txvNombreActividad=findViewById(R.id.txvNombreActividad);
        txvFechaHora=findViewById(R.id.txvFechaHora);
    }
}