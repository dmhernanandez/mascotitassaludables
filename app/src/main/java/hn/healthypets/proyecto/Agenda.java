package hn.healthypets.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Agenda extends AppCompatActivity {

    private EditText edtNombreActividad;
    private Spinner spiTipAgenda;
    private EditText edtFechaMedicamento;
    private EditText edtHoraMedicamento;
    private Spinner spiRecordame;
    private EditText edtIndicacionesMedicamento;
    private Button btnListo;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        edtNombreActividad=findViewById(R.id.edtNombreActividad);
        spiTipAgenda=findViewById(R.id.spiTipoAgenda);
        edtFechaMedicamento=findViewById(R.id.edtFechaAgenda);
        edtHoraMedicamento=findViewById(R.id.edtHoraMedicamento);
        spiRecordame=findViewById(R.id.spiRecordarme);
        edtIndicacionesMedicamento=findViewById(R.id.edtIndicacionesMedicamento2);
        btnListo=findViewById(R.id.btnListoDesparasitante);
        btnCancel=findViewById(R.id.btnCancelDesparasitante);
    }
}