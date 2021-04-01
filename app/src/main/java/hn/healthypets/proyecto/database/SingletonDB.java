package hn.healthypets.proyecto.database;

import android.content.Context;

import androidx.room.Room;

public class SingletonDB {
    private static String NOMBRE_DB="mascotasDB";
    private static DataBase database;

    //Se crea unicamente una instancia de la base de datos en caso de ser nula
    //de lo contrario solo se retorna la Actual
    public static synchronized DataBase getDatabase(Context contexto){
        if(database==null){
            database= Room.databaseBuilder(contexto, DataBase.class,NOMBRE_DB)
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

}
