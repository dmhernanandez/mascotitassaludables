package hn.healthypets.proyecto;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    MetodosImagenes metodosImagenes;
    private EditText edtFechaApliDesp;
    private EditText edtNombreDespa;
    private EditText edtPesoDespa;
    private EditText edtObserDespa;
    private Button btnGuardar;
    private DataBase instanciaDB;
    private DateTime fechaHora;
    private int dia, mes, anio;
    Intent intentValues;

    /**
     * Se utilizan para validar que tipo de accion se realizara en la actividad, estos datos se reciben del intent
     **/
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
                        edtFechaApliDesp.setText(fechaHora.formatoFecha(dayOfMonth, month, year)), anio, mes, dia);
                dialogoFecha.show();
            }
        });

        btnGuardar.setOnClickListener((v) -> {
            boolean fieldsFill = Validacion.fieldsAreNotEmpty(
                    edtFechaApliDesp.getText().toString(),
                    edtNombreDespa.getText().toString(),
                    edtPesoDespa.getText().toString());

            if (fieldsFill) {
                switch (accion) {
                    case Constantes.GUARDAR:
                        Medicamento desparasitante = new Medicamento(
                                0,
                                edtNombreDespa.getText().toString(),
                                edtFechaApliDesp.getText().toString(),
                                "",
                                Float.parseFloat(String.valueOf(edtPesoDespa.getText().toString())),
                                edtObserDespa.getText().toString(),
                                getIntent().getIntExtra(Constantes.TAG_ID, Constantes.DEFAULT),
                                instanciaDB.getCategoriaMedicamentoDAO().getIdMedicinesCategoriesByName("Desparasitante"),
                                0
                        );
                        instanciaDB.getMedicamentoDAO().insertdewormer(desparasitante);
                        Toast.makeText(Desparacitante.this, "Información guardada exitosamente ;)", Toast.LENGTH_LONG).show();
                        finish();
                        break;

                    case Constantes.ACTUALIZAR:
                        desparasitante = new Medicamento(
                                intentValues.getIntExtra(Constantes.TAG_ID, Constantes.DEFAULT),
                                edtNombreDespa.getText().toString(),
                                edtFechaApliDesp.getText().toString(),
                                "",
                                Float.parseFloat(String.valueOf(edtPesoDespa.getText().toString())),
                                edtObserDespa.getText().toString(),
                                getIntent().getIntExtra(Constantes.TAG_ID_MASCOTA, Constantes.DEFAULT),
                                instanciaDB.getCategoriaMedicamentoDAO().getIdMedicinesCategoriesByName("Desparasitante"),
                                0);

                        instanciaDB.getMedicamentoDAO().updateMedicine(desparasitante);
                        Toast.makeText(Desparacitante.this, "Informacion Actualizada con exitosamente ;)", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }
            } else {
                Toast.makeText(Desparacitante.this, "Campos OBLIGATORIOS(*) vacios", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void init() {
        edtFechaApliDesp = findViewById(R.id.edtFechaAplicacionDesparacitante);
        edtNombreDespa = findViewById(R.id.edtNombreDesparacitante);
        edtPesoDespa = findViewById(R.id.edtPesoDesparacitante);
        edtObserDespa = findViewById(R.id.edtObservacionesDesparacitante);
        btnGuardar = findViewById(R.id.btnListoDesparacitante);
        fechaHora = new DateTime();
//        metodosImagenes = new MetodosImagenes(this);

        /**Se obtiene una instancia de la base de datos*/
        instanciaDB = SingletonDB.getDatabase(this);

        /**Obtemos datos del Intent y determinamos si es una actualizacion o una insercion, estos valores se optienen con el */
        intentValues = getIntent();
        accion = intentValues.getIntExtra(Constantes.TAG_ACCION, Constantes.GUARDAR);
        if (accion == Constantes.GUARDAR) {
            /**Recuperamos el valor de la fecha por defecto que es la fecha actual*/
            dia = DateTime.diaDelMes;
            mes = DateTime.mes;
            anio = DateTime.anio;
        } else if (accion == Constantes.ACTUALIZAR) {
            /** Si es una actualización se debe parsear la fecha guadarda previamente para colocarla en variables de fecha
             * para asignarlo y luego asignarla al input*/

            String[] fecha = intentValues.getStringExtra(Constantes.TAG_FECHA_APLICACION).split("-");
//            //String fecha1 = "15-03-2021";
//            String[] fecha = fecha1.split("-");
            dia = Integer.parseInt(fecha[0]);
            mes = Integer.parseInt(fecha[1])-1;
            anio = Integer.parseInt(fecha[2]);
            edtFechaApliDesp.setText(intentValues.getStringExtra(Constantes.TAG_FECHA_APLICACION));
            edtNombreDespa.setText(intentValues.getStringExtra(Constantes.TAG_NOMBRE));
            edtObserDespa.setText(intentValues.getStringExtra(Constantes.TAG_OBSERVACION));
            edtPesoDespa.setText(intentValues.getStringExtra(Constantes.TAG_PESO));
        }
        edtFechaApliDesp.setText(fechaHora.formatoFecha(dia, mes, anio));
    }
}