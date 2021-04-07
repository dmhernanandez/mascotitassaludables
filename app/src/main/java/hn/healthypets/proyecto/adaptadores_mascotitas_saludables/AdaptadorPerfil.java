package hn.healthypets.proyecto.adaptadores_mascotitas_saludables;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.ModeloPerfil;

public class AdaptadorPerfil extends RecyclerView.Adapter<AdaptadorPerfil.ViewHolder> {
    private final ArrayList<ModeloPerfil> perfilArrayList;

    @Override
    public int getItemCount() {
        return this.perfilArrayList.size();
    }

    public AdaptadorPerfil(ArrayList<ModeloPerfil> vacunaArrayList) {
        this.perfilArrayList = vacunaArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView NombrePerfil;
        TextView NumeroChipPerfil;
        ImageView MascotaPerfil;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NombrePerfil = itemView.findViewById(R.id.txvNombrePerfil);
            NumeroChipPerfil = itemView.findViewById(R.id.txvNumeroChipPerfil);
            MascotaPerfil = itemView.findViewById(R.id.imgvMascotaPerfil);
        }
    }

    @NonNull
    @Override
    public AdaptadorPerfil.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //CREAMOS LA VISTA QUE CONTENDRA, EL DISENIO DE CADA ITEM
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perfil, parent, false);
        //RETORNAR INSTANCIA DE LA CLASE PERSONALIZADA PASANDO COMO ARGUMENTO LA VISTA CREADA
        return new AdaptadorPerfil.ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPerfil.ViewHolder holder, int position) {
    }
}