package hn.healthypets.proyecto.Utilidades;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class AlarmReceiver extends BroadcastReceiver {
    DataBase instanciaDB;
    @Override
    public void onReceive(Context context, Intent intent) {
        instanciaDB= SingletonDB.getDatabase(context);
        Intent service1 = new Intent(context, NotificationService.class);
        service1.putExtra(Constantes.TAG_ID_ALARM,intent.getIntExtra(Constantes.TAG_ID_ALARM,Constantes.DEFAULT));
        service1.putExtra(Constantes.TAG_NOMBRE_ACTIVIDAD,intent.getStringExtra(Constantes.TAG_NOMBRE_ACTIVIDAD));
        service1.putExtra(Constantes.TAG_NOMBRE_MASCOTA,intent.getStringExtra(Constantes.TAG_NOMBRE_MASCOTA));
        service1.putExtra(Constantes.TAG_COMENTARIO,intent.getStringExtra(Constantes.TAG_COMENTARIO));
        service1.putExtra(Constantes.TAG_NOMBRE_TIPO_ACTIVIDAD,intent.getStringExtra(Constantes.TAG_NOMBRE_TIPO_ACTIVIDAD));
        instanciaDB.getAgendaVisitaDAO().updateStateByIdAlarm(intent.getIntExtra(Constantes.TAG_ID_ALARM,Constantes.DEFAULT),Constantes.ATRASADA);
        ContextCompat.startForegroundService(context, service1 );
    }
}
