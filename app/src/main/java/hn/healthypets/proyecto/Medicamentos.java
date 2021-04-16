package hn.healthypets.proyecto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import hn.healthypets.proyecto.Utilidades.DateTime;
import hn.healthypets.proyecto.Utilidades.Validacion;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.AgendaMedicamento;
import hn.healthypets.proyecto.database.Entidades.TipoDosis;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.database.dao.TipoDosisDAO;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class Medicamentos extends AppCompatActivity {
    private EditText edtNombreMedicamento;
    private EditText edtNumeroDosis;
    private Spinner spiDosis;
    private EditText edtCadaDosis;
    private EditText edtPorDosis;
    private EditText edtFechaMedicamento;
    private EditText edtIndicacionesMedicamento;
    private Button btnGuardar;
    private DataBase instanciaDB;
    private Integer accion;
    private DateTime fechaHora;
    private int dia, mes, anio;
    private ArrayAdapter<String> adaptadorTipoDosis;
    private Integer positionItemMedicamento;
    private String nombreMedicamento;
    private Intent intentValues;
    private static ArrayList<String> arrayNombreTipoDosis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);
        init();

        edtFechaMedicamento.setOnClickListener((v) -> {
            //Utilizamos este metodo par obtenener los datos
            DatePickerDialog dialogoFecha = new DatePickerDialog(Medicamentos.this, (view, year, month, dayOfMonth) ->
                    edtFechaMedicamento.setText(fechaHora.formatoFecha(dayOfMonth, month, year)), anio, mes, dia);
            dialogoFecha.show();
        });
        arrayNombreTipoDosis = new ArrayList<>();

        //se le agrega el adaptador al spinner
        arrayNombreTipoDosis.add("Seleccione Dosis");
        startSpinnerValues(spiDosis, arrayNombreTipoDosis, adaptadorTipoDosis);
        positionItemMedicamento = 0;

        instanciaDB.getTipoDosisDAO().insertDoseTypes(new TipoDosis("Media Pastilla"),
                new TipoDosis("Tableta"),
                new TipoDosis("Gotero"),
                new TipoDosis("ml"),
                new TipoDosis("Gotas"));

        instanciaDB.getTipoDosisDAO().getAllDoseTypes().observe(Medicamentos.this,
                new Observer<List<TipoDosisDAO.NombreDosis>>() {
                    @Override
                    public void onChanged(List<TipoDosisDAO.NombreDosis> nombreDosis) {
                        /** Evaluamos que tipo de accion se realiza para  seleccionar el dato que se esta actualizando o
                         * simplemente llenar el espiner en caso de querer guardar una nueva mascota*/
                        arrayNombreTipoDosis.clear();

                        switch (accion) {
                            case Constantes.GUARDAR:
                                for (int i = 0; i < nombreDosis.size(); i++) {
                                    arrayNombreTipoDosis.add(i, nombreDosis.get(i).getDosisNombre());
                                }
                                break;
                            case Constantes.ACTUALIZAR:
                                /**En caso de ser una actualización se busca el valor que coincide con la lista para
                                 * seleccionarlo y asi asegurarnos que el usuario vea el valor que tenia guardado previamente
                                 * */
                                for (int i = 0; i < nombreDosis.size(); i++) {
                                    if (nombreDosis.get(i).getDosisNombre().equals(nombreMedicamento)) {
                                        positionItemMedicamento = i + 1; //Obtenemos el id del item
                                    }
                                    arrayNombreTipoDosis.add(i, nombreDosis.get(i).getDosisNombre());
                                }
                                break;
                        }

                        /**Independientemente si es una actualizacion o un se esta guardando una nueva mascota siempre se agrega
                         * un valor por defecto en la primera posicion del array. Adememas  positionItemEspecie  se utiliza para
                         * seleccionar un elemente en caso de que la accion se guardar el valor de este es de 0*/
                        arrayNombreTipoDosis.add(0, "Seleccione");

                        /**Al utilizar este metodo se ejecuta el llamado a onItemSelected utilizado par determinar, las razas de las
                         * especies
                         * */
                        spiDosis.setSelection(positionItemMedicamento);

                    }//Fin de metodo onChanged
                });

        btnGuardar.setOnClickListener(v -> {
            Validacion.fieldsAreNotEmpty();

            boolean fieldsFill = Validacion.fieldsAreNotEmpty(
                    edtNombreMedicamento.getText().toString(),
                    edtNumeroDosis.getText().toString(),
                    edtCadaDosis.getText().toString(),
                    edtPorDosis.getText().toString(),
                    edtFechaMedicamento.getText().toString());

            if (fieldsFill && spiDosis.getSelectedItemPosition() > 0) {
                switch (accion) {
                    case Constantes.GUARDAR:
                        AgendaMedicamento agendaMedicamento = new AgendaMedicamento(
                                0,
                                edtNombreMedicamento.getText().toString(),
                                Integer.parseInt(edtNumeroDosis.getText().toString()),
                                instanciaDB.getTipoDosisDAO().getIdDoseTypeByName(spiDosis.getSelectedItem().toString()),
                                Integer.parseInt(edtCadaDosis.getText().toString()),
                                Integer.parseInt(edtPorDosis.getText().toString()),
                                edtFechaMedicamento.getText().toString(),
                                edtIndicacionesMedicamento.getText().toString(),
                                true,
                                getIntent().getIntExtra(Constantes.TAG_ID, Constantes.DEFAULT),
                                0,
                                0
                        );
                        instanciaDB.getAgendaMedicamentoDAO().insertMedicinesSchedule(agendaMedicamento);
                        Toast.makeText(Medicamentos.this, "Información guardada exitosamente ;)", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case Constantes.ACTUALIZAR:
                        Log.i("otro", "entro");
                        agendaMedicamento = new AgendaMedicamento(
                                intentValues.getIntExtra(Constantes.TAG_ID, Constantes.DEFAULT),
                                edtNombreMedicamento.getText().toString(),
                                Integer.parseInt(edtNumeroDosis.getText().toString()),
                                instanciaDB.getTipoDosisDAO().getIdDoseTypeByName(spiDosis.getSelectedItem().toString()),
                                Integer.parseInt(edtCadaDosis.getText().toString()),
                                Integer.parseInt(edtPorDosis.getText().toString()),
                                edtFechaMedicamento.getText().toString(),
                                edtIndicacionesMedicamento.getText().toString(),
                                true,
                                getIntent().getIntExtra(Constantes.TAG_ID, Constantes.DEFAULT),
                                0,
                                0);
                        instanciaDB.getAgendaMedicamentoDAO().updateAgendaMedicamento(agendaMedicamento);
                        Toast.makeText(Medicamentos.this, "Información Actualizada exitosamente ;)", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                }
            } else {
                Toast.makeText(Medicamentos.this, "Campos OBLIGATORIOS(*) vacios", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void init() {
        edtNombreMedicamento = findViewById(R.id.edtNombreMedicamento);
        edtNumeroDosis = findViewById(R.id.edtNumeroDosis);
        spiDosis = findViewById(R.id.spiDosis);
        edtCadaDosis = findViewById(R.id.edtCadaDosis);
        edtPorDosis = findViewById(R.id.edtPorDosis);
        edtFechaMedicamento = findViewById(R.id.edtFechaMedicamento);
        edtIndicacionesMedicamento = findViewById(R.id.edtIndicacionesMedicamento);
        btnGuardar = findViewById(R.id.btnListoMedicamento);
        fechaHora = new DateTime();
        positionItemMedicamento = 0;

        //Obtenemos una instancia de la base de datos
        instanciaDB = SingletonDB.getDatabase(this);

        //FECHA
        /**Obtemos datos del Intent y determinamos si es una actualizacion o una insercion, estos valores se optienen con el */
        intentValues = getIntent();
        accion = intentValues.getIntExtra(Constantes.TAG_ACCION, Constantes.GUARDAR);
        Log.i("accion", "" + accion);
        if (accion == Constantes.GUARDAR) {
            /**Recuperamos el valor de la fecha por defecto que es la fecha actual*/
            dia = DateTime.diaDelMes;
            mes = DateTime.mes;
            anio = DateTime.anio;
        } else if (accion == Constantes.ACTUALIZAR) {
            /** Si es una actualización se debe parsear la fecha guadarda previamente para colocarla en variables de fecha
             * para asignarlo y luego asignarla al input*/
            String[] fecha = intentValues.getStringExtra(Constantes.TAG_FECHA_APLICACION).split("-");
            dia = Integer.parseInt(fecha[0]);
            mes = Integer.parseInt(fecha[1]);
            anio = Integer.parseInt(fecha[2]);
            edtNombreMedicamento.setText(intentValues.getStringExtra(Constantes.TAG_NOMBRE_MEDICAMENTO));
            edtNumeroDosis.setText(String.valueOf( intentValues.getIntExtra(Constantes.TAG_CADA,Constantes.DEFAULT)));
            nombreMedicamento = instanciaDB.getTipoDosisDAO().getNameDoseTypeById(intentValues.getIntExtra(Constantes.TAG_DOSIS, Constantes.DEFAULT));
            Log.i("prueba", "" + intentValues.getIntExtra(Constantes.TAG_DOSIS, Constantes.DEFAULT));

            edtCadaDosis.setText(String.valueOf(intentValues.getIntExtra(Constantes.TAG_INTERVALO_TIEMPO,Constantes.DEFAULT)));

            edtPorDosis.setText(String.valueOf(intentValues.getIntExtra(Constantes.TAG_POR,Constantes.DEFAULT)));
            edtIndicacionesMedicamento.setText(intentValues.getStringExtra(Constantes.TAG_OBSERVACION));
        }
        edtFechaMedicamento.setText(fechaHora.formatoFecha(dia, mes, anio));
    }

    private void startSpinnerValues(Spinner spinner, ArrayList<String> valores, ArrayAdapter<String> adapter) {
        /**Inicializamos el adaptador y lo agregamos al Spinner*/
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, valores);
        spinner.setAdapter(adapter);
    }
}