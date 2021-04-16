package hn.healthypets.proyecto.adaptadores_mascotitas_saludables;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.database.Entidades.Medicamento;

public class AdaptadorDesparacitante extends RecyclerView.Adapter<AdaptadorDesparacitante.ViewHolder> {

    private ArrayList<Medicamento> desparasitanteArrayList;

    @Override
    public int getItemCount() {
        return this.desparasitanteArrayList.size();
    }

    public AdaptadorDesparacitante(ArrayList<Medicamento> desparacitanteArrayList) {
        this.desparasitanteArrayList = desparacitanteArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvNombreMascotaDesparasitante;
        TextView txvNombreDesparasitante;
        TextView txvPesoDesparasitante;
        TextView txvFechaAplicacionDesparasitante;
        
        /**
         * Este medoto se utiliza para obtener el la ruta del directorio raiz de la aplicion
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNombreMascotaDesparasitante = itemView.findViewById(R.id.txvNombreMascotaDesparasitante);
            txvNombreDesparasitante = itemView.findViewById(R.id.txvNombreDesparasitante);
            txvPesoDesparasitante = itemView.findViewById(R.id.txvPesoDesparasitante);
            txvFechaAplicacionDesparasitante = itemView.findViewById(R.id.txvFechaAplicacionDesparasitante);
           
        }

    }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //CREAMOS LA VISTA QUE CONTENDRA, EL DISENIO DE CADA ITEM

            View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_desparasitante, parent, false);
            //RETORNAR INSTANCIA DE LA CLASE PERSONALIZADA PASANDO COMO ARGUMENTO LA VISTA CREADA
            return new ViewHolder(vista);
        }
    
    @Override
    public void onBindViewHolder(@NonNull AdaptadorDesparacitante.ViewHolder holder, int position) {
        Medicamento vacuna = desparasitanteArrayList.get(position);
        holder.txvNombreMascotaDesparasitante.setText("Mascota: " + vacuna.getMascotaMedicadaId());
        holder.txvNombreDesparasitante.setText("Nombre Producto: " + vacuna.getDescripcion());
        holder.txvPesoDesparasitante.setText("Peso: " + vacuna.getPesoKilogramo());
        holder.txvFechaAplicacionDesparasitante.setText("Fecha: " + vacuna.getFechaAplicacion());
    }
}

