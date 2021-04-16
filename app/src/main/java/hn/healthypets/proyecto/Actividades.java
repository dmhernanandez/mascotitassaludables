package hn.healthypets.proyecto;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Actividades extends AppCompatActivity {

    private TextView txvNombreMascota;
    private TextView txvNombreActividad;
    private TextView txvFechaHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        txvNombreMascota=findViewById(R.id.txvDescripcion);
        txvNombreActividad=findViewById(R.id.txvNombreActividad);
        txvFechaHora=findViewById(R.id.txvFechaHora);
    }
}