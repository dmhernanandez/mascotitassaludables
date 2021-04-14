package hn.healthypets.proyecto;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    int dia,mes,anio,hora,minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        anio=c.get(Calendar.YEAR);


        edtNombreActividad = findViewById(R.id.edtNombreActividad);
        spiTipAgenda = findViewById(R.id.spiTipoAgenda);
        edtFechaAgenda = findViewById(R.id.edtFechaAgenda1);
        edtHoraAgenda = findViewById(R.id.edtHoraAgenda);
        spiRecordame = findViewById(R.id.spiRecordarme);
        edtObservaionesAgenda = findViewById(R.id.edtObseracionesAgenda);
        btnListo = findViewById(R.id.btnListoAgenda);
        btnCancel = findViewById(R.id.btnCancelarAgenda);

        /**Validar Campos Vacios Con el Metodo */
        edtHoraAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog recogerHora = new TimePickerDialog(Agenda.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hora=hourOfDay;
                        minuto=minute;
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
                           dia=dayOfMonth;
                           mes=month;
                           anio=year;
                        edtFechaAgenda.setText(DateTime.formatoFecha(dayOfMonth, month, year));}, anio, mes, dia);
                dialogoFecha.show();
            }
        });

        btnListo.setOnClickListener(v -> {
            Validacion.fieldsAreNotEmpty();
            boolean comprobar = Validacion.fieldsAreNotEmpty(edtNombreActividad.getText().toString(),
                    edtHoraAgenda.getText().toString(),
                    edtFechaAgenda.getText().toString()
            );

            if (comprobar) {
                Toast.makeText(Agenda.this, "Información guardada exitosamente ;)", Toast.LENGTH_LONG).show();
//                LLAMAR METODO DAO
            } else
                {
                            Calendar today = Calendar.getInstance();

                            today.set(Calendar.HOUR_OF_DAY, hora);
                            today.set(Calendar.MINUTE, minuto);
                            today.set(Calendar.SECOND, 0);
                            today.set(Calendar.DAY_OF_MONTH,dia);
                            today.set(Calendar.MONTH,mes);
                            today.set(Calendar.YEAR,anio);



                            Toast.makeText(Agenda.this,"Alarma", Toast.LENGTH_LONG).show();

                            setAlarm(0, today.getTimeInMillis(), Agenda.this);
                Toast.makeText(Agenda.this, "Campos OBLIGATORIOS(*) vacios", Toast.LENGTH_LONG).show();
            }
        });
    }


    public static void setAlarm(int idAlarma, Long timestamp, Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context, idAlarma, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }
}