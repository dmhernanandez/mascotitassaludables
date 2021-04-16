package hn.healthypets.proyecto;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import hn.healthypets.proyecto.Utilidades.AlarmReceiver;
import hn.healthypets.proyecto.Utilidades.DateTime;
import hn.healthypets.proyecto.Utilidades.Validacion;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.AgendaVisita;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class Agenda extends AppCompatActivity {

    private EditText edtNombreActividad;
    private Spinner spiTipAgenda;
    private EditText edtFechaAgenda;
    private EditText edtHoraAgenda;
    private Spinner spiRecordame;
    private EditText edtObservaionesAgenda;
    private Button btnListo;

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la hora hora
    final int horaActual = c.get(Calendar.HOUR_OF_DAY);
    final int minutoActual = c.get(Calendar.MINUTE);
    final int diaActual = c.get(Calendar.DAY_OF_MONTH);
    final int mesActual = c.get(Calendar.MONTH);
    final int anioActual = c.get(Calendar.YEAR);
    int dia,mes,anio,hora,minuto;
    private Intent intentRecibido;
    
    /**Con esta variaable vamos a determinar que accion vamos a realizar si es guardar o una actualizacion*/
    private int actionToPerform;
    private int postionItemAgenda;
    private String nombreCategoria;
    private ArrayList<String> arrayNombreCategoriasMecicamento;
    private  ArrayAdapter<String> arrayAdapterCatMedicamento;
    DataBase instanciaDB;
    Calendar today;
    /** Esta veriable se utiliza para guardar el estado de en el que se encuentra una */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        edtNombreActividad = findViewById(R.id.edtNombreActividad);
        spiTipAgenda = findViewById(R.id.spiTipoAgenda);
        edtFechaAgenda = findViewById(R.id.edtFechaAgendaVisita);
        edtHoraAgenda = findViewById(R.id.edtHoraAgenda);
        spiRecordame = findViewById(R.id.spiRecordarme);
        edtObservaionesAgenda = findViewById(R.id.edtObseracionesAgenda);
        btnListo = findViewById(R.id.btnListoAgenda);
        instanciaDB= SingletonDB.getDatabase(this);
        arrayNombreCategoriasMecicamento = new ArrayList<>();
        postionItemAgenda=0;

         /** Inicializamos el spinner*/
         arrayNombreCategoriasMecicamento.add("Seleccione el tipo de actividad");
        startSpinnerValues(spiTipAgenda,arrayNombreCategoriasMecicamento,arrayAdapterCatMedicamento);

        /** Obtengo los intent para validar los datos que se reciben*/
        intentRecibido= getIntent();

        /** Obtememos la acción*/
        actionToPerform=intentRecibido.getIntExtra(Constantes.TAG_ACCION,Constantes.GUARDAR);
        if(actionToPerform==Constantes.GUARDAR)
        {
            dia=DateTime.diaDelMes;
            mes=DateTime.mes;
            anio=DateTime.anio;
            /**Definimos por defecto la hora a las 6 de la mañana*/
            hora=6;
            minuto=0;
        }
        else if(actionToPerform==Constantes.ACTUALIZAR)
        {
            Log.i("idRicibido",""+intentRecibido.getIntExtra(Constantes.TAG_ID,Constantes.DEFAULT));
            edtNombreActividad.setText(intentRecibido.getStringExtra(Constantes.TAG_NOMBRE_ACTIVIDAD));
            edtObservaionesAgenda.setText(intentRecibido.getStringExtra(Constantes.TAG_COMENTARIO));
            dia=intentRecibido.getIntExtra(Constantes.TAG_DIA,Constantes.DEFAULT);
            mes=intentRecibido.getIntExtra(Constantes.TAG_MES,Constantes.DEFAULT);

            anio=intentRecibido.getIntExtra(Constantes.TAG_ANIO,Constantes.DEFAULT);
            hora=intentRecibido.getIntExtra(Constantes.TAG_HORA,Constantes.DEFAULT);
            minuto=intentRecibido.getIntExtra(Constantes.TAG_MINUTO,Constantes.DEFAULT);
            /**Se obtiene el nombre de la categoria o tipo accion que va realizar para
             * seleccionarla en el spinner*/
            nombreCategoria= intentRecibido.getStringExtra(Constantes.TAG_ID_CAT_MEDICAMENTO);
        }
          
        edtFechaAgenda.setText(DateTime.formatoFecha(dia,mes,anio));
        edtHoraAgenda.setText(DateTime.formatoHora(hora,minuto,true));


        /**Validar Campos Vacios Con el Metodo */
        edtHoraAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog recogerHora = new TimePickerDialog(Agenda.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        /** Guardamos la fecha seleccinada por el usuario para cuando se vuelva a llamar al timepiker
                         * aparezca seleccinada la ultima fecha guarda*/
                         hora=hourOfDay;
                         minuto=minute;
                        edtHoraAgenda.setText(DateTime.formatoHora(hourOfDay,minute,true));
                    }
                    /**
                     * Estos valores deben ir en ese orden,colocar en false se muestra en formato 12 horas y true en formato
                      *24 horas Pero el sistema devuelve la hora en formato 24 horas
                      * */
                }, horaActual, minutoActual, false);
                recogerHora.show();
            }
        });
        edtFechaAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utilizamos este metodo par obtenener los datos
                DatePickerDialog dialogoFecha = new DatePickerDialog(Agenda.this, (view, year, month, dayOfMonth) ->{
                         /** Cada vez que seleccionamos un nueva fecha se actualizan las variables para que la proxima vez que
                          * se llame al calendario aparezaca seleccionada la ultima fech seleccionada*/
                           dia=dayOfMonth;
                           mes=month;
                           anio=year;
                         edtFechaAgenda.setText(DateTime.formatoFecha(dayOfMonth, month, year));}, anioActual, mesActual, diaActual);
                dialogoFecha.show();
            }
        });

        btnListo.setOnClickListener(v -> {
            boolean comprobar = Validacion.fieldsAreNotEmpty(edtNombreActividad.getText().toString(), edtFechaAgenda.getText().toString());
             /** En caso que el usuario no seleccione una hora para realizar la tarea por defecto se asigna las 6:00 a.m*/
          /** Se valida que los campos requeridos esten llenos**/
            if (comprobar && spiTipAgenda.getSelectedItemId()>0)
            {
                /** Valida que la fecha que se esta ingresando no sea anterior a la fecha actual
                 * [ mes + 1 ]  al mes se le suma una unidad ya que al tomarlo de DatePicker el valor del mes tiene una
                 * unidad menos ya que el mes de enero es 0 y diciembre 11
                 * */
//                if(DateTime.isFutureDate(dia+"/"+(mes + 1)+"/"+anio+" "+hora+":"+minuto,"dd/MM/yyyy HH:mm"))
//                {
                    today = Calendar.getInstance();
                    today.setTimeInMillis(System.currentTimeMillis());
                    today.set(Calendar.HOUR_OF_DAY, hora);
                    today.set(Calendar.MINUTE, minuto);
                    today.set(Calendar.SECOND, 0);
                    today.set(Calendar.DAY_OF_MONTH,dia);
                    today.set(Calendar.MONTH,mes);
                    today.set(Calendar.YEAR,anio);
                    Log.i("horaAlarma",""+today.getTimeInMillis());
                    int idAlarma =DateTime.generateNumber();
                    //Se crea un switch para determinar que tipo de accion es que se esta realizando
                    switch (actionToPerform)
                    {
                        case Constantes.GUARDAR:

                            AgendaVisita task = new AgendaVisita(
                                    0,
                                    edtNombreActividad.getText().toString(),
                                    dia, mes, anio,hora,minuto,
                                    0,
                                    edtObservaionesAgenda.getText().toString(), 
                                    idAlarma,
                                    intentRecibido.getIntExtra(Constantes.TAG_ID_MASCOTA,Constantes.DEFAULT),
                                    instanciaDB.getCategoriaMedicamentoDAO().getIdMedicinesCategoriesByName(spiTipAgenda.getSelectedItem().toString()),
                                    Constantes.ACTIVO);
                            //Inserta la nueva agenda
                            instanciaDB.getAgendaVisitaDAO().inertNewTask(task);

                            /** Establecemos la alarma para paa*/
                            setAlarm(idAlarma,today.getTimeInMillis(),this,
                                    edtNombreActividad.getText().toString(),
                                    instanciaDB.getMascotaDAO().getNamePetById(intentRecibido.getIntExtra(Constantes.TAG_ID_MASCOTA,Constantes.DEFAULT)),
                                    spiTipAgenda.getSelectedItem().toString(),
                                    edtObservaionesAgenda.getText().toString());

                            /** Cuando se guarda se envia a la actividad principal*/
                            Toast.makeText(Agenda.this, "Información guardada exitosamente", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(this,MenuLateral.class);
//                            startActivity(intent);
                                finish();
                            break;
                        case Constantes.ACTUALIZAR:
                            /**Creamos un ojeto de tipo ajenda y lo pasando nuestro id c*/
                            AgendaVisita updatedTask = new AgendaVisita(
                                    intentRecibido.getIntExtra(Constantes.TAG_ID,Constantes.DEFAULT),
                                    edtNombreActividad.getText().toString(),
                                    dia, mes, anio,hora,minuto,
                                    0,
                                    edtObservaionesAgenda.getText().toString(),
                                    intentRecibido.getIntExtra(Constantes.TAG_ID_ALARM,Constantes.DEFAULT),
                                    intentRecibido.getIntExtra(Constantes.TAG_ID_MASCOTA,Constantes.DEFAULT),
                                    instanciaDB.getCategoriaMedicamentoDAO().getIdMedicinesCategoriesByName(spiTipAgenda.getSelectedItem().toString()),
                                    Constantes.ACTIVO);
                            //Actualizamos la el valor la nueva agenda
                            instanciaDB.getAgendaVisitaDAO().updateTask(updatedTask);
                            setAlarm(idAlarma,today.getTimeInMillis(),this,
                                    edtNombreActividad.getText().toString(),
                                    instanciaDB.getMascotaDAO().getNamePetById(intentRecibido.getIntExtra(Constantes.TAG_ID_MASCOTA,Constantes.DEFAULT)),
                                    spiTipAgenda.getSelectedItem().toString(),
                                    edtObservaionesAgenda.getText().toString());
                            /** Cuando se guarda se envia a la actividad principal*/
                            Toast.makeText(Agenda.this, "Actidad actualizada exitosamente", Toast.LENGTH_LONG).show();
                            finish();

                        break;
                    }


               // }
                /** Si la fecha y la hora son menores que la actual envia una alerta notificando */
//                else
//                {
//                    Alertas.showSingleAlert(this,"Fecha y hora antigua", "La fecha u hora seleccionada esta en el pasado. \n\n" +
//                            "Por favor elija una fecha y hora que este despues de el actual");
//                }

            }
            else
                {

                    Toast.makeText(Agenda.this, "Debe llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                    //                   // setAlarm(id, today.getTimeInMillis(), Agenda.this,edtNombreAxt().toString());
            }
        });

        /** Creamos un obeservador que se encargara de llenar los medicamentos de la base de datos
         * en caso de actualizacion seleccionara el nombre del tipo de agenda que guardo el usuario*/
        instanciaDB.getCategoriaMedicamentoDAO().getAllNameCategoryMedicine().observe(Agenda.this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> nombresCatagrias) {
                //Cada vez que hay un cambio se limpia el array para poder llenarlo nuevamente
                arrayNombreCategoriasMecicamento.clear();
                switch (actionToPerform)
                {
                        case Constantes.GUARDAR:
                            for (int i = 0; i < nombresCatagrias.size(); i++) {
                                arrayNombreCategoriasMecicamento.add(i, nombresCatagrias.get(i));
                            }
                            break;

                            case Constantes.ACTUALIZAR:
                                /**En caso de ser una actualización se busca el valor que coincide con la lista para
                                 * seleccionarlo y asi asegurarnos que el usuario vea el valor que tenia guardado previamente
                                 * */
                                for (int i = 0; i < nombresCatagrias.size(); i++) {
                                    if (nombresCatagrias.get(i).equals(nombreCategoria)) {
                                        postionItemAgenda = i + 1; //Obtenemos el id del item
                                    }
                                    arrayNombreCategoriasMecicamento.add(i, nombresCatagrias.get(i));
                                }
                                break;
                        }

                        /**Independientemente si es una actualizacion o un se esta guardando una nueva mascota siempre se agrega
                         * un valor por defecto en la primera posicion del array. Adememas  positionItemEspecie  se utiliza para
                         * seleccionar un elemente en caso de que la accion se guardar el valor de este es de 0*/
                        arrayNombreCategoriasMecicamento.add(0, "Seleccione el tipo de actividad");

                        /**Al utilizar este metodo se ejecuta el llamado a onItemSelected utilizado par determinar, las razas de las
                         * especies
                         * */
                        spiTipAgenda.setSelection(postionItemAgenda);
            }//Fin de metodo onChanged

        });


    } //Fin del metodo on create
    /**
     * Con este metodo llenamos los Spinners pasando la lista de valores que vamos agregar y el adaptador que los
     * y el adaptador
     * */
    private void startSpinnerValues(Spinner spinner, ArrayList<String> valores, ArrayAdapter<String> adapter)
    {
        //Inicializamos el adaptador y lo agregamos al Spinner
        adapter=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,valores);
        spinner.setAdapter(adapter);
    }//Fin startSpinnerValues


    public static void setAlarm(int idAlarma, Long timestamp, Context context,String nombreActividad, String nombreMascota,String tipoActividad,String comentario)
    {
        /** Se crea una instancia del AlarmManager para crear nuestra Alarma*/
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra(Constantes.TAG_ID_ALARM,idAlarma);
        alarmIntent.putExtra(Constantes.TAG_NOMBRE_ACTIVIDAD,nombreActividad);
        alarmIntent.putExtra(Constantes.TAG_NOMBRE_TIPO_ACTIVIDAD,tipoActividad);
        alarmIntent.putExtra(Constantes.TAG_NOMBRE_MASCOTA,nombreMascota);
        alarmIntent.putExtra(Constantes.TAG_COMENTARIO,comentario);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context, idAlarma, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }

    public static void  cancelAlarm(int idAlarma,Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context, idAlarma, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

}