package hn.healthypets.proyecto.Utilidades;

import java.util.Calendar;

public class DateTime {
    private static Calendar calendario = Calendar.getInstance();

    public static int anio = calendario.get(Calendar.YEAR);
    public static int mes = calendario.get(Calendar.MONTH);
    public static int diaDelMes = calendario.get(Calendar.DAY_OF_MONTH);
    private final String CERO = "0";
    private final String GUION = "-";

    public String getDate() {
        return "";
    }

    public String formato(int dia, int mes, int year) {
        mes = mes + 1;
        //Formateo el día obtenido: antepone el 0 si son menores de 10
        String diaFormateado = (dia < 10) ? CERO + String.valueOf(dia) : String.valueOf(dia);
        //Formateo el mes obtenido: antepone el 0 si son menores de 10
        String mesFormateado = (mes < 10) ? CERO + String.valueOf(mes) : String.valueOf(mes);
        return diaFormateado + GUION + mesFormateado + GUION + year;
    }

    /**
     * Este metedo solo se usa para crear una concatenacion de datos
     */
    public String formato(String dia, String mes, String year) {

        return dia + GUION + mes + GUION + anio;
    }
}


