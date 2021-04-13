package hn.healthypets.proyecto.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hn.healthypets.proyecto.CreacionPerfiles;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.adaptadores_mascotitas_saludables.AdaptadorPerfil;
import hn.healthypets.proyecto.database.Entidades.Mascota;

public class CredencialesFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_credenciales, container, false);
        RecyclerView  recyclerView=vista.findViewById(R.id.rvCredencial);
        ImageButton nuevo=vista.findViewById(R.id.btnAniadirCredencial);


        nuevo.setOnClickListener(new View.OnClickListener() {
            //        PARA ABRIR DE UN FRAGMENTE A UNA ACTIVITY
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreacionPerfiles.class);
                startActivity(intent);
            }

        });

        //Enlazamos los datos al Recicler View
        RecyclerView recyclerViewPerfiles=vista.findViewById(R.id.rvCredencial);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPerfiles.setLayoutManager(linearLayoutManager);

        //datos que vamos a enviar
        Mascota mascota= new Mascota(1,
                "Rocky Prueba",
                "15/Junio/2020",
                1,
                1,
                1,
                 "",
                  122);


        //Creamos el Array o lista de lo que vamos a enviar
        ArrayList<Mascota> arrayElementos = new ArrayList<>();

        //añadimos los datos al array lista
        arrayElementos.add(mascota);
        //pasamos al adapator el array y este se encarga de manipulamos
        AdaptadorPerfil adaptador = new AdaptadorPerfil(arrayElementos);

        /*
         *Por ultimo agregamos a nuestro ReciclerView el adaptador para que pueda mostarlas
         */
        recyclerView.setAdapter(adaptador);
        return vista;
    }
}