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
import hn.healthypets.proyecto.adaptadores_mascotitas_saludables.AdaptadorDesparacitante;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.Medicamento;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.database.dao.MedicamentoDAO;

public class HistorialDesparasitanteFragment extends Fragment {

    private RecyclerView recyclerViewPerfiles;
    private LinearLayoutManager linearLayoutManager;
    private DataBase instanciaDB;

    //Creamos el Array o lista de lo que vamos a enviar
    ArrayList<MedicamentoDAO.Desparacitantecard> arrayElementos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_historial_desparasitante, container, false);
        RecyclerView recyclerView = vista.findViewById(R.id.rvHistorialDesparasitantes);

        //Obtenemos una instancia de la base de datos
        instanciaDB = SingletonDB.getDatabase(getContext());
        arrayElementos = new ArrayList<>();

        //Enlazamos los datos al Recicler View
        recyclerViewPerfiles = vista.findViewById(R.id.rvHistorialDesparasitantes);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPerfiles.setLayoutManager(linearLayoutManager);

        instanciaDB.getMedicamentoDAO().getMedicinesScheduleByDesparasitante().observe(getActivity(), new Observer<List<MedicamentoDAO.Desparacitantecard>>()
        {
            @Override
            public void onChanged(List<MedicamentoDAO.Desparacitantecard> medicamentos)
            {
                /** Cada vez que agregamos una nueva mascota limpiamos el array*/
                arrayElementos.clear();
                for (MedicamentoDAO.Desparacitantecard medicamento : medicamentos)
                {
                    //añadimos los datos al array lista
                    arrayElementos.add(medicamento);
                }
                //pasamos al adapator el array y este se encarga de manipulamos
                AdaptadorDesparacitante adaptador = new AdaptadorDesparacitante(arrayElementos);
                /**
                 *Por ultimo agregamos a nuestro ReciclerView el adaptador para que pueda mostarlas
                 */
                recyclerView.setAdapter(adaptador);
            }
        });
        return vista;
    }
}