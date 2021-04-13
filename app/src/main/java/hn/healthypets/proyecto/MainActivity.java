package hn.healthypets.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.Especie;
import hn.healthypets.proyecto.database.Entidades.Raza;
import hn.healthypets.proyecto.database.SingletonDB;

public class MainActivity extends AppCompatActivity {
    DataBase instanciaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton = findViewById(R.id.button);
        Button nuevo = findViewById(R.id.nuevo);
        Button credencial = findViewById(R.id.btnCredencial);
        Button vacuna = findViewById(R.id.btnVacunas);
        Button desparacitante = findViewById(R.id.btnDesparacitanteMenu);
        Button historial = findViewById(R.id.btnHistorial);
        Button medicamento = findViewById(R.id.btnMedicamentoMenu);
        Button agenda = findViewById(R.id.btnAgenda);
        Button actividades = findViewById(R.id.btnActividades);
        Button menu=findViewById(R.id.btnMenuLateral);

        //Obtenemos una instancia de la base de datos
        instanciaDB = SingletonDB.getDatabase(this);

        boton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreacionPerfiles.class);
            startActivity(intent);
        });

        nuevo.setOnClickListener(v -> {
            instanciaDB.getSpeciesDAO().insertSpecies(new Especie("Perro"));
            instanciaDB.getSpeciesDAO().insertSpecies(new Especie("Gato"));

            List<Especie> traer = instanciaDB.getSpeciesDAO().getAllSpecies();
            for (int i = 0; i < traer.size(); i++) {
                //Log.i("especie1",String.valueOf(traer.get(i).getEspecieId()));
            }

            instanciaDB.getRazaDAO().insertBreeds(
                    new Raza("Siames", instanciaDB.getSpeciesDAO().getIdSpeciesByName("Gato"))
                    , new Raza("Angora", instanciaDB.getSpeciesDAO().getIdSpeciesByName("Gato")),
                    new Raza("Sphynx", instanciaDB.getSpeciesDAO().getIdSpeciesByName("Gato"))
            );




            Intent intent2 = new Intent(this, Director.class);
            startActivity(intent2);
        });

        credencial.setOnClickListener(v -> {
            Intent intent3 = new Intent(this, Perfiles.class);
            startActivity(intent3);
        });

        vacuna.setOnClickListener(v -> {
            Intent intent4 = new Intent(this, Vacunas.class);
            startActivity(intent4);
        });

        desparacitante.setOnClickListener(v -> {
            Intent intent5 = new Intent(this, Desparacitante.class);
            startActivity(intent5);
        });
        historial.setOnClickListener(v -> {
            Intent intent6 = new Intent(this, Historial.class);
            startActivity(intent6);
        });

        medicamento.setOnClickListener(v -> {
            Intent intent7 = new Intent(this, Medicamento.class);
            startActivity(intent7);
        });
        agenda.setOnClickListener(v -> {
            Intent intent8 = new Intent(this, Agenda.class);
            startActivity(intent8);
        });
        actividades.setOnClickListener(v -> {
            Intent intent9 = new Intent(this, Actividades.class);
            startActivity(intent9);
        });
        menu.setOnClickListener(v -> {
            Intent intent10 = new Intent(this, MenuLateral.class);
            startActivity(intent10);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }
}
