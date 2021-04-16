package hn.healthypets.proyecto.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.adaptadores_mascotitas_saludables.AdaptadorMedicamento;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.AgendaMedicamento;
import hn.healthypets.proyecto.database.SingletonDB;

public class HistorialMedicamentoFragment extends Fragment {

    private RecyclerView recyclerViewPerfiles;
    private LinearLayoutManager linearLayoutManager;
    private DataBase instanciaDB;

    ArrayList<AgendaMedicamento> arrayElementos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_medicamento_historial, container, false);
        RecyclerView recyclerView = vista.findViewById(R.id.rvHistorialMedicamento);

        //Obtenemos una instancia de la base de datos
        instanciaDB = SingletonDB.getDatabase(getContext());
        arrayElementos = new ArrayList<>();

        //Enlazamos los datos al Recicler View
        recyclerViewPerfiles = vista.findViewById(R.id.rvHistorialMedicamento);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPerfiles.setLayoutManager(linearLayoutManager);

        instanciaDB.getAgendaMedicamentoDAO().getAllMedicinesSchedule().observe(getActivity(), new Observer<List<AgendaMedicamento>>()
        {
            @Override
            public void onChanged(List<AgendaMedicamento> agendaMedicamentos)
            {
                /** Cada vez que agregamos una nueva mascota limpiamos el array*/
                arrayElementos.clear();
                for (AgendaMedicamento agendaMedicamento : agendaMedicamentos)
                {
                    //añadimos los datos al array lista
                    arrayElementos.add(agendaMedicamento);
                }

                //pasamos al adapator el array y este se encarga de manipulamos
                AdaptadorMedicamento adaptador = new AdaptadorMedicamento(arrayElementos);
                /*
                 *Por ultimo agregamos a nuestro ReciclerView el adaptador para que pueda mostarlas
                 */
                recyclerView.setAdapter(adaptador);
            }
        });
        return vista;
    }
}