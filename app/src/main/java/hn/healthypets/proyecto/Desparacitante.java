package hn.healthypets.proyecto;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hn.healthypets.proyecto.Utilidades.DateTime;
import hn.healthypets.proyecto.Utilidades.Validacion;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.Medicamento;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class Desparacitante extends AppCompatActivity {

    MetodosImagenes metodosImagenes = new MetodosImagenes();
    private EditText edtFechaApliDesp;
    private EditText edtNombreDespa;
    private EditText edtPesoDespa;
    private EditText edtObserDespa;
    private Button btnListo;
    private Button btnCancel;
    private DataBase instanciaDB;
    private DateTime fechaHora;
    private int dia, mes, anio;

    // Se utilizan para validar que tipo de accion se realizara en la actividad, estos datos se reciben del intent
    private int accion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desparacitante);
        //Inicializamos todos los elementos
        init();

        edtFechaApliDesp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utilizamos este metodo par obtenener los datos
                DatePickerDialog dialogoFecha = new DatePickerDialog(Desparacitante.this, (view, year, month, dayOfMonth) ->
                        edtFechaApliDesp.setText(fechaHora.formato(dayOfMonth, month, year)), anio, mes, dia);
                dialogoFecha.show();
            }
        });

        btnListo.setOnClickListener((v) -> {
            boolean validacion = Validacion.fieldsAreNotEmpty(
                    edtFechaApliDesp.getText().toString(),
                    edtNombreDespa.getText().toString(),
                    edtPesoDespa.getText().toString());
            if (validacion) {

//                LLAMAR METODO DAO
                Medicamento desparasitante =new Medicamento(
                        edtNombreDespa.getText().toString(),
                        edtFechaApliDesp.getText().toString(),
                        "",
                        Float.parseFloat(String.valueOf(edtPesoDespa.getText().toString())),
                        edtObserDespa.getText().toString(),
                        1,
                        1
                );
                Toast.makeText(Desparacitante.this,"Información guardada exitosamente ;)",Toast.LENGTH_LONG).show();
                instanciaDB.getMedicamentoDAO().insertdewormer(desparasitante);

                } else
                Toast.makeText(Desparacitante.this,"Campos OBLIGATORIOS(*) vacios",Toast.LENGTH_LONG).show();
        });


    }

    private void init() {
        edtFechaApliDesp = findViewById(R.id.edtFechaAplicacionDesparacitante);
        edtNombreDespa = findViewById(R.id.edtNombreDesparacitante);
        edtPesoDespa = findViewById(R.id.edtPesoDesparacitante);
        edtObserDespa = findViewById(R.id.edtObservacionesDesparacitante);
        btnListo = findViewById(R.id.btnListoDesparacitante);
        btnCancel = findViewById(R.id.btnCancelarDesparacitante);
        fechaHora = new DateTime();
//Se obtiene una instancia de la base de datos
        instanciaDB = SingletonDB.getDatabase(this);

        /**Obtemos datos del Intent y determinamos si es una actualizacion o una insercion, estos valores se optienen con el */
        Intent intentValues = getIntent();
        accion = intentValues.getIntExtra(Constantes.TAG_ACCION, Constantes.ACTUALIZAR);
        if (accion == Constantes.GUARDAR) {


            //Recuperamos el valor de la fecha por defecto que es la fecha actual
            dia = DateTime.diaDelMes;
            mes = DateTime.mes;
            anio = DateTime.anio;

        } else if (accion == Constantes.ACTUALIZAR) {
            String fecha1 = "15-03-2021";
            //Si es una actualización se debe parsear la fecha guadarda previamente para colocarla en variables de fecha para asignarlo y luego asignarla al input*/
            String[] fecha = fecha1.split("-");
            dia = Integer.parseInt(fecha[0]);
            mes = Integer.parseInt(fecha[1]) - 1;
            anio = Integer.parseInt(fecha[2]);

        }
        edtFechaApliDesp.setText(fechaHora.formato(dia, mes, anio));
        accion = Constantes.ACTUALIZAR;
    }
}