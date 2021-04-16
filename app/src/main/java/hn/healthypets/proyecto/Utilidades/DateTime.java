package hn.healthypets.proyecto.Utilidades;

import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime  {
    private static Calendar calendario= Calendar.getInstance();


    public static int anio = calendario.get(Calendar.YEAR);
    public static  int mes=calendario.get(Calendar.MONTH);
    public static int diaDelMes=calendario.get(Calendar.DAY_OF_MONTH);
    public static int  hora=calendario.get(Calendar.HOUR_OF_DAY);
    public static int minuto= calendario.get(Calendar.MINUTE);
    private static final String CERO="0";
    private static final String GUION ="-";
    private static final String DOS_PUNTOS = ":";
    private static Calendar calendarioAux = Calendar.getInstance();

    public String getDate(){
        return "";
    }

    public static String formatoFecha(int dia, int mes, int year){
        mes = mes ;
        //Formateo el día obtenido: antepone el 0 si son menores de 10
        String diaFormateado = (dia < 10) ? CERO + String.valueOf(dia) : String.valueOf(dia);
        //Formateo el mes obtenido: antepone el 0 si son menores de 10
        String mesFormateado = (mes < 10) ? CERO + String.valueOf(mes) : String.valueOf(mes);
        return diaFormateado + GUION + mesFormateado + GUION + year;
    }

    public static String formatoHora(int hourOfDay, int minuto){
        //Formateo de la hora obtenida: antepone el 0 si son menores de 10
        String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
        String minutoFormateado = (minuto < 10)? String.valueOf(CERO + minuto):String.valueOf(minuto);
        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
        String AM_PM;
        if(hourOfDay < 12) {
            AM_PM = "a.m.";
        } else {
            AM_PM = "p.m.";
        }
        //Retorna la hora con el formato deseado
       return horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM;
    }
   /** Este metedo solo se usa para crear una concatenacion de datos*/
    public String formatoFecha(String dia, String mes, String year){

        return dia + GUION + mes + GUION + anio;
    }

    /** Se genera un numero aleatorio*/
    public static int generateNumber() {
        int numero = 0;
        SecureRandom secureRandom = null;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
            numero = secureRandom.nextInt();

        } catch (NoSuchAlgorithmException e) {
            Log.i("semilla", e.getMessage());
            numero = 0;
            e.printStackTrace();
        }
        return numero;
    }

    public static boolean isFutureDate( String dateStart, String pattern)
    {
        Log.i("fecha","Ingreso fecha "+dateStart);
        /*** Se crea una una instancia de la de formato para convertir la fecha a tipo Date*/
        SimpleDateFormat date = new SimpleDateFormat(pattern);
        Date fechaInicioDate = null;  //Primero se busca hacer un parseo de fechas por tal razon se encierra en un TryCatch
        try {
            fechaInicioDate = date.parse(dateStart); //Se parsea la echa de String a tipo dete
            Log.i("fecha","Ingreso fecha "+dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Comprueba si la fecha seleccionado esta despues de actual.
        if(fechaInicioDate.after(new Date(System.currentTimeMillis()))){

            return true;
        }else{

            return false;
        }
    }

    public static String formatoHora(int hourOfDay, int minuto, boolean formato12){

        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
        String AM_PM;
        if(hourOfDay < 12) {
            AM_PM = "a.m.";
        } else {
            AM_PM = "p.m.";
        }
        /**
         * 8Si la hora que se seleciono es mayor  que 12 entonces se selecciona un valor se le resta 12
         **/
        hourOfDay = (hourOfDay>12)?hourOfDay-12:hourOfDay;


        //Formateo de la hora obtenida: antepone el 0 si son menores de 10
        String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
        String minutoFormateado = (minuto < 10)? String.valueOf(CERO + minuto):String.valueOf(minuto);

        //Retorna la hora con el formato deseado
        return horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM;
    }
}


