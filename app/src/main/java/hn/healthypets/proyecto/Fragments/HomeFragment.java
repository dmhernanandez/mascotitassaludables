package hn.healthypets.proyecto.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import hn.healthypets.proyecto.CreacionPerfiles;
import hn.healthypets.proyecto.R;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_home, container, false);

        Button aniadir=vista.findViewById(R.id.btnAniadirMascota);


        aniadir.setOnClickListener(new View.OnClickListener() {
            //        PARA ABRIR DE UN FRAGMENTE A UNA ACTIVITY
            @Override
            public void onClick(View v) {
               // NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.nav_perfiles);
                Intent intent = new Intent(getContext(), CreacionPerfiles.class);
                startActivity(intent);
            }

        });

        return vista;
    }
}