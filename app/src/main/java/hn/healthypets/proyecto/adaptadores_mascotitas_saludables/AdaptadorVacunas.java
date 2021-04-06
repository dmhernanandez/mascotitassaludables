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

public class AdaptadorVacunas extends RecyclerView.Adapter<AdaptadorVacunas.ViewHolder> {
    private final ArrayList<Vacuna> vacunaArrayList;

    @Override
    public int getItemCount() {
        return this.vacunaArrayList.size();
    }

    public AdaptadorVacunas(ArrayList<Vacuna> vacunaArrayList) {
        this.vacunaArrayList = vacunaArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreVacuna;
        ImageView fotoVacuna;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreVacuna = itemView.findViewById(R.id.txvNombreVacuna);
            fotoVacuna = itemView.findViewById(R.id.imgVacuna);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //CREAMOS LA VISTA QUE CONTENDRA, EL DISENIO DE CADA ITEM
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vacunas, parent, false);
        //RETORNAR INSTANCIA DE LA CLASE PERSONALIZADA PASANDO COMO ARGUMENTO LA VISTA CREADA
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }
}