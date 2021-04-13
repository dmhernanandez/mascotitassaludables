package hn.healthypets.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import hn.healthypets.proyecto.Utilidades.Validacion;

public class Agenda extends AppCompatActivity {

    private EditText edtNombreActividad;
    private Spinner spiTipAgenda;
    private EditText edtFechaAgenda;
    private EditText edtHoraAgenda;
    private Spinner spiRecordame;
    private EditText edtObservaionesAgenda;
    private Button btnListo;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        edtNombreActividad=findViewById(R.id.edtNombreActividad);
        spiTipAgenda=findViewById(R.id.spiTipoAgenda);
        edtFechaAgenda =findViewById(R.id.edtFechaAgenda);
        edtHoraAgenda=findViewById(R.id.edtHoraAgenda);
        spiRecordame=findViewById(R.id.spiRecordarme);
        edtObservaionesAgenda =findViewById(R.id.edtObseracionesAgenda);
        btnListo=findViewById(R.id.btnListoAgenda);
        btnCancel=findViewById(R.id.btnCancelarAgenda);

        /**
         Validar Campos Vacios Con el Metodo */

        btnListo.setOnClickListener(v -> {
            Validacion.fieldsAreNotEmpty();
            boolean comprobar=Validacion.fieldsAreNotEmpty(edtNombreActividad.getText().toString(),
                                                            edtHoraAgenda.getText().toString(),
                                                            edtFechaAgenda.getText().toString()
                                                           );

            if (comprobar){
                Toast.makeText(Agenda.this,"Guardo Medicamentos",Toast.LENGTH_LONG).show();
//                LLAMAR METODO DAO
            }else{
                Toast.makeText(Agenda.this,"Campo Obligatorio (*) esta Vacio",Toast.LENGTH_LONG).show();
            }
        });
    }

}