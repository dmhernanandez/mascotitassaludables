package hn.healthypets.proyecto.ui.home;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import hn.healthypets.proyecto.CreacionPerfiles;
import hn.healthypets.proyecto.R;

import android.app.FragmentTransaction;
import hn.healthypets.proyecto.ui.gallery.PerfilesFragment;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_home, container, false);

        Button aniadir=vista.findViewById(R.id.btnAniadirMascota);


        aniadir.setOnClickListener(new View.OnClickListener() {
                          //        PARA ABRIR DE UN FRAGMENTE A UNA ACTIVITY
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CreacionPerfiles.class);
                startActivity(intent);
            }

        });


        return vista;
    }
}