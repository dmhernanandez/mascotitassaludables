package hn.healthypets.proyecto.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hn.healthypets.proyecto.CreacionPerfiles;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.adaptadores_mascotitas_saludables.AdaptadorPerfil;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.Mascota;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class CredencialesFragment extends Fragment {
    private Button btnNewPet;
    private RecyclerView recyclerViewPerfiles;
    private LinearLayoutManager linearLayoutManager;
    private DataBase instanciaDB;

    //Creamos el Array o lista de lo que vamos a enviar
    ArrayList<Mascota> arrayElementos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_credenciales, container, false);
        RecyclerView recyclerView = vista.findViewById(R.id.rvCredencial);

        ImageButton nuevo = vista.findViewById(R.id.btnAniadirCredencial);
        //Obtenemos una instancia de la base de datos
        instanciaDB = SingletonDB.getDatabase(getContext());
        arrayElementos = new ArrayList<>();

        //        PARA ABRIR DE UN FRAGMENTE A UNA ACTIVITY
        nuevo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreacionPerfiles.class);
            intent.putExtra(Constantes.TAG_ACCION, Constantes.GUARDAR);
            startActivity(intent);
        });

        //Enlazamos los datos al Recicler View
        recyclerViewPerfiles = vista.findViewById(R.id.rvCredencial);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPerfiles.setLayoutManager(linearLayoutManager);

        //datos que vamos a enviar
        Mascota mascota = new Mascota(0, "rocky",
                "Rocky Prueba",
                1,
                1,
                1,
                "1", "");

        instanciaDB.getMascotaDAO().getAllPets().observe(getActivity(), new Observer<List<Mascota>>()
        {
            @Override
            public void onChanged(List<Mascota> mascotas)
            {
                /** Cada vez que agregamos una nueva mascota limpiamos el array*/
                arrayElementos.clear();
                for (Mascota mascota : mascotas)
                {
                    //añadimos los datos al array lista
                    arrayElementos.add(mascota);
                }
                //pasamos al adapator el array y este se encarga de manipulamos
                AdaptadorPerfil adaptador = new AdaptadorPerfil(arrayElementos);
                /*
                 *Por ultimo agregamos a nuestro ReciclerView el adaptador para que pueda mostarlas
                 */
                recyclerView.setAdapter(adaptador);
            }
        });
        return vista;
    }
}