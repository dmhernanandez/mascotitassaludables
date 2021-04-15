package hn.healthypets.proyecto;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
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
    private Button btnCancel;

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la hora hora
    final int horaActual = c.get(Calendar.HOUR_OF_DAY);
    final int minutoActual = c.get(Calendar.MINUTE);
    final int diaActual = c.get(Calendar.DAY_OF_MONTH);
    final int mesActual = c.get(Calendar.MONTH);
    final int anioActual = c.get(Calendar.YEAR);
    int dia,mes,anio,hora,minuto;
    Intent intentRecibido;
    /**Con esta variaable vamos a determinar que accion vamos a realizar si es guardar o una actualizacion*/
    int actionToPerform;

    DataBase instanciaDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        edtNombreActividad = findViewById(R.id.edtNombreActividad);
        spiTipAgenda = findViewById(R.id.spiTipoAgenda);
        edtFechaAgenda = findViewById(R.id.edtFechaAgenda1);
        edtHoraAgenda = findViewById(R.id.edtHoraAgenda);
        spiRecordame = findViewById(R.id.spiRecordarme);
        edtObservaionesAgenda = findViewById(R.id.edtObseracionesAgenda);
        btnListo = findViewById(R.id.btnListoAgenda);
        btnCancel = findViewById(R.id.btnCancelarAgenda);
         instanciaDB= SingletonDB.getDatabase(this);
        /** Obtengo los intent para validar los datos que se reciben*/
        intentRecibido= getIntent();
        /** Obtememos la acción*/
        actionToPerform=intentRecibido.getIntExtra(Constantes.TAG_ACCION,Constantes.GUARDAR);
        if(actionToPerform==Constantes.GUARDAR)
        {
            dia=DateTime.diaDelMes;
            mes=DateTime.mes;
            anio=DateTime.anio;
            hora=DateTime.hora;
            minuto=DateTime.minuto;
        }
        else if(actionToPerform==Constantes.ACTUALIZAR)
        {

        }

        edtFechaAgenda.setText(DateTime.formatoFecha(dia,mes,anio));
        edtHoraAgenda.setText(DateTime.formatoHora(hora,minuto));


        /**Validar Campos Vacios Con el Metodo */
        edtHoraAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog recogerHora = new TimePickerDialog(Agenda.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        /** Guardamos la fecha seleccinada por el usuario para cuando se vuelva a llamar al timepiker
                         * aparezca seleccinada la ultima fecha guarda*/
//                         hora=hourOfDay;
//                         minuto=minute;
                        edtHoraAgenda.setText(DateTime.formatoHora(hourOfDay,minute));
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
//                           dia=dayOfMonth;
//                           mes=month;
//                           anio=year;
                        edtFechaAgenda.setText(DateTime.formatoFecha(dayOfMonth, month, year));}, anioActual, mesActual, diaActual);
                dialogoFecha.show();
            }
        });
       btnCancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               cancelAlarm(0,Agenda.this);
           }
       });
        btnListo.setOnClickListener(v -> {
            boolean comprobar = Validacion.fieldsAreNotEmpty(edtNombreActividad.getText().toString(),
                    edtHoraAgenda.getText().toString(),
                    edtFechaAgenda.getText().toString()
            );

            if (comprobar && spiTipAgenda.getSelectedItemId()>0)
            {
                AgendaVisita task = new AgendaVisita(
                        0,
                 edtNombreActividad.getText().toString(),
                 dia, mes, anio,hora,minuto,
                0,
                 edtObservaionesAgenda.getText().toString(),
                 DateTime.generateNumber(),
                 intentRecibido.getIntExtra(Constantes.TAG_ID,Constantes.DEFAULT),
                 instanciaDB.getCategoriaMedicamentoDAO().getIdCategoryMedicineByName(spiTipAgenda.getSelectedItem().toString()),
                 Constantes.ACTIVO);
                instanciaDB.getAgendaVisitaDAO().inertNewTask(task);

                /** Cuando se guarda se envia a la actividad principal*/
                Toast.makeText(Agenda.this, "Información guardada exitosamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,MenuLateral.class);
                startActivity(intent);

//                Calendar today = Calendar.getInstance();
//                today.setTimeInMillis(System.currentTimeMillis());
//                today.set(Calendar.HOUR_OF_DAY, hora);
//                today.set(Calendar.MINUTE, minuto);
//                today.set(Calendar.SECOND, 0);
//                today.set(Calendar.DAY_OF_MONTH,dia);
//                today.set(Calendar.MONTH,mes);
//                today.set(Calendar.YEAR,anio);

                /**Establecemos la alarma para que se pueda utilizar */
             //   setAlarm(-25, today.getTimeInMillis(), Agenda.this,edtNombreActividad.getText().toString());
            }
            else
                {
                            Calendar today = Calendar.getInstance();
                            today.setTimeInMillis(System.currentTimeMillis());
                            today.set(Calendar.HOUR_OF_DAY, hora);
                            today.set(Calendar.MINUTE, minuto);
                            today.set(Calendar.SECOND, 0);
                            today.set(Calendar.DAY_OF_MONTH,dia);
                            today.set(Calendar.MONTH,mes);
                            today.set(Calendar.YEAR,anio);
                    Toast.makeText(Agenda.this, "Debe llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                    //                   // setAlarm(id, today.getTimeInMillis(), Agenda.this,edtNombreAxt().toString());
            }
        });


    }


    public static void setAlarm(int idAlarma, Long timestamp, Context context,String identificador)
    {
        /** Se crea una instancia del AlarmManager para crear nuestra Alarma*/
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("ID",identificador);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context, idAlarma, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
   //     alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }

    public static void  cancelAlarm(int idAlarma,Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context, idAlarma, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.cancel(pendingIntent);
    }
}