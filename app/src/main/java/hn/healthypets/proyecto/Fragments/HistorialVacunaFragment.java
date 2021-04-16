package hn.healthypets.proyecto.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import hn.healthypets.proyecto.CreacionPerfiles;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.adaptadores_mascotitas_saludables.AdaptadorPerfil;
import hn.healthypets.proyecto.adaptadores_mascotitas_saludables.AdaptadorVacunas;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.Mascota;
import hn.healthypets.proyecto.database.Entidades.Medicamento;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.database.dao.MedicamentoDAO;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class HistorialVacunaFragment extends Fragment {

    private RecyclerView recyclerViewPerfiles;
    private LinearLayoutManager linearLayoutManager;
    private DataBase instanciaDB;

    //Creamos el Array o lista de lo que vamos a enviar
    ArrayList<MedicamentoDAO.Vacunacard> arrayElementos;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_historial_vacuna, container, false);
        RecyclerView  recyclerView=vista.findViewById(R.id.rvHistorialVacuna);

        //Obtenemos una instancia de la base de datos
        instanciaDB= SingletonDB.getDatabase(getContext());
        arrayElementos = new ArrayList<>();

        //Enlazamos los datos al Recicler View
        recyclerViewPerfiles=vista.findViewById(R.id.rvHistorialVacuna);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPerfiles.setLayoutManager(linearLayoutManager);


        instanciaDB.getMedicamentoDAO().getMedicinesScheduleByVacuna().observe(getActivity(), new Observer<List<MedicamentoDAO.Vacunacard>>() {
            @Override
            public void onChanged(List<MedicamentoDAO.Vacunacard> medicamentos) {
                /** Cada vez que agregamos una nueva mascota limpiamos el array*/
                arrayElementos.clear();
                for(MedicamentoDAO.Vacunacard medicamento: medicamentos)
                {
                    //añadimos los datos al array lista
                    arrayElementos.add(medicamento);
                }

                //pasamos al adapator el array y este se encarga de manipulamos
                AdaptadorVacunas adaptador = new AdaptadorVacunas(arrayElementos);
                /*
                 *Por ultimo agregamos a nuestro ReciclerView el adaptador para que pueda mostarlas
                 */
                recyclerView.setAdapter(adaptador);
            }
        });

        return vista;
    }

}