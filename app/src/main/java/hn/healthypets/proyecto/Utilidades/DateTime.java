package hn.healthypets.proyecto.Utilidades;

import java.util.Calendar;

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


    public String getDate(){
        return "";
    }

    public static String formatoFecha(int dia, int mes, int year){
        mes = mes + 1;
        //Formateo el día obtenido: antepone el 0 si son menores de 10
        String diaFormateado = (dia < 10)? CERO + String.valueOf(dia):String.valueOf(dia);
        //Formateo el mes obtenido: antepone el 0 si son menores de 10
        String mesFormateado = (mes < 10)? CERO + String.valueOf(mes):String.valueOf(mes);
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
}


