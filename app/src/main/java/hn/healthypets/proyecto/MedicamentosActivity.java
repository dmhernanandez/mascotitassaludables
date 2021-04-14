package hn.healthypets.proyecto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import hn.healthypets.proyecto.Utilidades.DateTime;
import hn.healthypets.proyecto.Utilidades.Validacion;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class MedicamentosActivity extends AppCompatActivity {

    MetodosImagenes metodosImagenes;
    private EditText edtNombreMedicamento;
    private EditText edtNumeroDosis;
    private Spinner spiDosis;
    private EditText edtCadaDosis;
    private EditText edtPorDosis;
    private EditText edtFechaMedicamento;
    private EditText edtIndicacionesMedicamento;
    private Button btnListo;
    private Button btnCancel;
    private DataBase instanciaDB;
    private DateTime fechaHora;
    private int dia, mes, anio;

    /**
     * Se utilizan para validar que tipo de accion se realizara en la actividad, estos datos se reciben del intent
     **/
    private int accion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);
        Log.i("hola","onCreate");
        //Inicializamos todos los elementos
        init();

        Log.i("hora","nell");
        edtFechaMedicamento.setOnClickListener((v)->{
            Log.i("hora","entro");
            //Utilizamos este metodo par obtenener los datos
            DatePickerDialog dialogoFecha = new DatePickerDialog(MedicamentosActivity.this, (view, year, month, dayOfMonth) ->
                    edtFechaMedicamento.setText(fechaHora.formatoFecha(dayOfMonth, month, year)), anio, mes, dia);
            dialogoFecha.show();
        });

        btnListo.setOnClickListener((v) -> {
            boolean validacion = Validacion.fieldsAreNotEmpty(
                    edtNombreMedicamento.getText().toString(),
                    edtNumeroDosis.getText().toString(),
                    edtCadaDosis.getText().toString(),
                    edtPorDosis.getText().toString(),
                    edtFechaMedicamento.getText().toString());
            if (validacion) {
                metodosImagenes.checkPermissionStorage(MedicamentosActivity.this);
                Toast.makeText(MedicamentosActivity.this, "Información guardada exitosamente ;)", Toast.LENGTH_LONG).show();
            } else

                Toast.makeText(MedicamentosActivity.this, "Campos OBLIGATORIOS(*) vacios", Toast.LENGTH_LONG).show();
        });
    }
    private void init()
    {
        edtNombreMedicamento =findViewById(R.id.edtNombreMedicamento);
        edtNumeroDosis=findViewById(R.id.edtNumeroDosis);
        spiDosis=findViewById(R.id.spiDosis);
        edtCadaDosis=findViewById(R.id.edtCadaDosis);
        edtPorDosis=findViewById(R.id.edtPorDosis);
        edtFechaMedicamento=findViewById(R.id.edtFechaMedicamento);
        edtIndicacionesMedicamento=findViewById(R.id.edtIndicacionesMedicamento);
        btnListo=findViewById(R.id.btnListoMedicamento);
        btnCancel=findViewById(R.id.btnCancelarMedicamento);
        fechaHora = new DateTime();
        metodosImagenes = new MetodosImagenes(this);

        /** Se obtiene una instancia de la base de datos*/
        instanciaDB = SingletonDB.getDatabase(this);
        /**Obtemos datos del Intent y determinamos si es una actualizacion o una insercion, estos valores se optienen con el */
        Intent intentValues= getIntent();
        accion=intentValues.getIntExtra(Constantes.TAG_ACCION,Constantes.ACTUALIZAR);
        if(accion==Constantes.GUARDAR)
        {
            //Recuperamos el valor de la fecha por defecto que es la fecha actual
            dia=DateTime.diaDelMes;
            mes=DateTime.mes;
            anio=DateTime.anio;
        }
        else if(accion==Constantes.ACTUALIZAR)
        {
            String fecha1="15-03-2021";
            /** Si es una actualización se debe parsear la fecha guadarda previamente para colocarla en variables de fecha
             * para asignarlo y luego asignarla al input*/
            String [] fecha=fecha1.split("-");
            dia=Integer.parseInt(fecha[0]);
            mes=Integer.parseInt(fecha[1])-1;
            anio= Integer.parseInt(fecha[2]);
        }
        edtFechaMedicamento.setText(fechaHora.formatoFecha(dia,mes,anio));
        accion=Constantes.ACTUALIZAR;
    }
}
