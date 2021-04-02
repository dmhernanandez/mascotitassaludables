package hn.healthypets.proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.CategoriaMedicamento;
import hn.healthypets.proyecto.database.Entidades.Especie;
import hn.healthypets.proyecto.database.Entidades.Raza;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.database.dao.EspecieDAO;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton = findViewById(R.id.button);
        Button nuevo = findViewById(R.id.nuevo);
        Button credencial = findViewById(R.id.btnCredencial);

        //Creo la base de datos
       DataBase  instanciaDB =   SingletonDB.getDatabase(this);


        boton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreacionPerfiles.class);
            startActivity(intent);
        });

        nuevo.setOnClickListener(v -> {
            instanciaDB.getSpeciesDAO().insertSpecies(new Especie("Perro"));
            instanciaDB.getSpeciesDAO().insertSpecies(new Especie("Gato"));

            List<Especie> traer = instanciaDB.getSpeciesDAO().getAllSpecies();
            for(int i=0;i<traer.size();i++)
            {
                //Log.i("especie1",String.valueOf(traer.get(i).getEspecieId()));
            }

            instanciaDB.getRazaDAO().insertRaza(new Raza("Siames",instanciaDB.getSpeciesDAO().getIdSpeciesByName("Gato"))
            ,new Raza("Angora",instanciaDB.getSpeciesDAO().getIdSpeciesByName("Gato")),
             new Raza("Sphynx",instanciaDB.getSpeciesDAO().getIdSpeciesByName("Gato"))
            );

            List<EspecieDAO.prueba> razasPorEspecie = instanciaDB.getSpeciesDAO().getAllRazaFromSpecie("Perro");
            for(int i=0;i<razasPorEspecie.size();i++)
            {
                Log.i("especie",String.valueOf(razasPorEspecie.get(i).getNombre()));
            }
//            Intent intent2 = new Intent(this, director.class);
//            startActivity(intent2);

        });

        credencial.setOnClickListener(v -> {
            Intent intent3 = new Intent(this, perfiles.class);
            startActivity(intent3);
        });
    }
}