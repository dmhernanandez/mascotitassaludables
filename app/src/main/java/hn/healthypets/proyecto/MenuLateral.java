package hn.healthypets.proyecto;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.CategoriaMedicamento;
import hn.healthypets.proyecto.database.Entidades.Genero;
import hn.healthypets.proyecto.database.SingletonDB;

public class MenuLateral extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DataBase instanciaDB;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lateral);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        /*** Aqui se crean todos los valores valores por defecto de la base de datos*/
        instanciaDB = SingletonDB.getDatabase(MenuLateral.this);
        instanciaDB.getGeneroDAO().insertGenders(new Genero("Hembra"), new Genero("Macho"));
        instanciaDB.getCategoriaMedicamentoDAO().insertMedicinesCategories(
                new CategoriaMedicamento(0, "Vacuna"),
                new CategoriaMedicamento(0, "Desparasitante"),
                new CategoriaMedicamento(0, "Medicamento")
        );

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_historialVacuna,
                R.id.nav_historialDesparacitante,
                R.id.nav_historialMedicamento,
                R.id.nav_actividades,
                R.id.nav_informacion,
                R.id.nav_credencial)
                .setDrawerLayout(drawer)
                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if(instanciaDB.getMascotaDAO().getNumbersPets()<=0)
        {
            navController.navigate(R.id.nav_home);
        }

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);


     //   instanciaDB.getGeneroDAO().insertGender(new Genero("Hembra"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_lateral, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("entro","entro");
        if(instanciaDB.getMascotaDAO().getNumbersPets()<=0)
        {
            navController.navigate(R.id.nav_home);
        }
        else
            navController.navigate(R.id.nav_credencial);


    }
}