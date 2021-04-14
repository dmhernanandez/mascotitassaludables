package hn.healthypets.proyecto.Utilidades;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class RebootService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RebootService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String intentType = intent.getExtras().getString("caller");
        if (intentType == null) return;
        if (intentType.equals("RebootReceiver")) {
           // SharedPreferences settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
           // Utils.setAlarm(settings.getInt("alarmID", 0), settings.getLong("alarmTime", 0), this);
        }
    }
}
