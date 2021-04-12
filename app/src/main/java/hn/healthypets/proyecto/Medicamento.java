package hn.healthypets.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.Spinner;

public class Medicamento extends AppCompatActivity {
    private EditText edtNombreDesparacitante2;
    private EditText edtNumeroDosis;
    private Spinner spiDosis;
    private EditText edtCadaDosis;
    private EditText edtPorDosis;
    private EditText edtFechaMedicamento;
    private EditText edtHoraMedicamento;
    private EditText edtIndicacionesMedicamento;
    private Button btnListo;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        edtNombreDesparacitante2=findViewById(R.id.edtNombreDesparacitante2);
        edtNumeroDosis=findViewById(R.id.edtNumeroDosis);
        spiDosis=findViewById(R.id.spiDosis);
        edtCadaDosis=findViewById(R.id.edtCadaDosis);
        edtPorDosis=findViewById(R.id.edtPorDosis);
        edtFechaMedicamento=findViewById(R.id.edtFechaMedicamento);
        edtHoraMedicamento=findViewById(R.id.edtHoraMedicamento);
        edtIndicacionesMedicamento=findViewById(R.id.edtIndicacionesMedicamento);
        btnListo=findViewById(R.id.btnListoDesparacitante2);
        btnCancel=findViewById(R.id.btnCancelarDesparacitante2);
    }
}