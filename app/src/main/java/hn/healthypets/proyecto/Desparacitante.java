package hn.healthypets.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Desparacitante extends AppCompatActivity {

    private EditText edtFechaApliDesp;
    private EditText edtNombreDespa;
    private EditText edtPesoDespa;
    private EditText edtObserDespa;
    private Button btnListo;
    private Button btnCancel;
    private Button btnProxima;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desparacitante);

        edtFechaApliDesp=findViewById(R.id.edtFechaAplicacionDesparacitante);
        edtNombreDespa=findViewById(R.id.edtNombreDesparasitante);
        edtPesoDespa=findViewById(R.id.edtPesoDesparasitante);
        edtObserDespa=findViewById(R.id.edtObservacionesDesparasitante);
        btnListo=findViewById(R.id.btnListoDesparacitante);
        btnCancel=findViewById(R.id.btnCancelarDesparacitante);
        btnProxima=findViewById(R.id.btnProximamenteDesparacitante);
    }
}