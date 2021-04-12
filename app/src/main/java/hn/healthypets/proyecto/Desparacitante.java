package hn.healthypets.proyecto;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Desparacitante extends AppCompatActivity {

    private EditText edtFechaApliDesp;
    private EditText edtNombreDespa;
    private EditText edtPesoDespa;
    private EditText edtObserDespa;
    private Button btnListo;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desparacitante);

        edtFechaApliDesp=findViewById(R.id.edtFechaAplicacionDesparacitante);
        edtNombreDespa=findViewById(R.id.edtNombreDesparacitante);
        edtPesoDespa=findViewById(R.id.edtPesoDesparacitante);
        edtObserDespa=findViewById(R.id.edtObservacionesDesparacitante);
        btnListo=findViewById(R.id.btnListoDesparacitante);
        btnCancel=findViewById(R.id.btnCancelarDesparacitante);
    }

    //Primero relacionar los elementos
    //On click en el boton guardar
    // Validar que los campos obligatorios no esten vacios
    // si estan vacios va mandar un mensaje

}