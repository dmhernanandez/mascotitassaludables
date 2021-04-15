package hn.healthypets.proyecto.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.adaptadores_mascotitas_saludables.AdaptadorAgenda;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.AgendaVisita;
import hn.healthypets.proyecto.database.SingletonDB;

public class ActividadesFragment extends Fragment {
    DataBase instanciaDB;
    private  RecyclerView recyclerViewAgenda;
    private LinearLayoutManager linearLayoutManager;
    //Creamos el Array o lista de lo que vamos a enviar
    ArrayList<AgendaVisita> arrayElementos ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_actividades, container, false);

        //Obtenemos una instancia de la base de datos
        instanciaDB= SingletonDB.getDatabase(getContext());
        arrayElementos = new ArrayList<>();



        //Enlazamos los datos al Recicler View
        recyclerViewAgenda =vista.findViewById(R.id.rcvAgenda);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAgenda.setLayoutManager(linearLayoutManager);

        instanciaDB.getAgendaVisitaDAO().getAllActivities().observe(getActivity(), new Observer<List<AgendaVisita>>() {
            @Override
            public void onChanged(List<AgendaVisita> mascotas) {
                /** Cada vez que agregamos una nueva mascota limpiamos el array*/
                arrayElementos.clear();
                for(AgendaVisita tarea: mascotas)
                {
                    //añadimos los datos al array lista
                    arrayElementos.add(tarea);
                }
                //pasamos al adapator el array y este se encarga de manipulamos
                AdaptadorAgenda adaptador = new AdaptadorAgenda(arrayElementos);
                /*
                 *Por ultimo agregamos a nuestro ReciclerView el adaptador para que pueda mostarlas
                 */
                recyclerViewAgenda.setAdapter(adaptador);
            }
        });
        return vista;
    }
}