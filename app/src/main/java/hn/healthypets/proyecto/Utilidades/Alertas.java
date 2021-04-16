package hn.healthypets.proyecto.Utilidades;

import android.app.AlertDialog;
import android.content.Context;

public class Alertas {

    public static void showSingleAlert(Context context,String title, String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
