package hn.healthypets.proyecto.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.adaptadores_mascotitas_saludables.AdaptadorPerfil;
import hn.healthypets.proyecto.database.Entidades.Mascota;

public class HistorialFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_historial, container, false);


        return vista;
    }
}