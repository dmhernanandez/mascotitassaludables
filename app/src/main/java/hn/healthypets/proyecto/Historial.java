package hn.healthypets.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class Historial extends AppCompatActivity {

    private Spinner spi;
    private ImageView imgMascota;
    private TextView txvNombreMascota;
    private TextView txvFechaAplic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        spi=findViewById(R.id.spinner);
        imgMascota=findViewById(R.id.imgvVacuna);
        txvNombreMascota=findViewById(R.id.txvNombreMascotaDesparasitante);
        txvFechaAplic=findViewById(R.id.txvFechaAplicacionDesparasitante);
    }
}