package hn.healthypets.proyecto.adaptadores_mascotitas_saludables;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hn.healthypets.proyecto.Desparacitante;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.database.Entidades.Medicamento;
import hn.healthypets.proyecto.database.dao.MedicamentoDAO;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class AdaptadorDesparacitante extends RecyclerView.Adapter<AdaptadorDesparacitante.ViewHolder> {

    private ArrayList<MedicamentoDAO.Desparacitantecard> desparasitanteArrayList;

    @Override
    public int getItemCount() {
        return this.desparasitanteArrayList.size();
    }

    public AdaptadorDesparacitante(ArrayList<MedicamentoDAO.Desparacitantecard> desparacitanteArrayList) {
        this.desparasitanteArrayList = desparacitanteArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvNombreMascotaDesparasitante;
        TextView txvNombreDesparasitante;
        TextView txvPesoDesparasitante;
        TextView txvFechaAplicacionDesparasitante;
        ImageView btnActualizarDesparasitante;

        /**
         * Este medoto se utiliza para obtener el la ruta del directorio raiz de la aplicion
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNombreMascotaDesparasitante = itemView.findViewById(R.id.txvNombreMascotaDesparasitante);
            txvNombreDesparasitante = itemView.findViewById(R.id.txvNombreDesparasitante);
            txvPesoDesparasitante = itemView.findViewById(R.id.txvPesoDesparasitante);
            txvFechaAplicacionDesparasitante = itemView.findViewById(R.id.txvFechaAplicacionDesparasitante);
            btnActualizarDesparasitante = itemView.findViewById(R.id.btnActualizarDesparasitante);

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
        MedicamentoDAO.Desparacitantecard desparasitante = desparasitanteArrayList.get(position);
        holder.txvNombreMascotaDesparasitante.setText("Mascota: " + desparasitante.getNombre());
        holder.txvNombreDesparasitante.setText("Nombre Producto: " + desparasitante.getDescripcion());
        holder.txvPesoDesparasitante.setText("Peso: " + desparasitante.getPesoKilogramo());
        holder.txvFechaAplicacionDesparasitante.setText("Fecha: " + desparasitante.getFechaAplicacion());

        holder.btnActualizarDesparasitante.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), Desparacitante.class);
            //Envio el id de la mascota para usaro en cualquie
            intent.putExtra(Constantes.TAG_ACCION, Constantes.ACTUALIZAR);
            intent.putExtra(Constantes.TAG_ID, desparasitante.getMedicamentoId());
            intent.putExtra(Constantes.TAG_ID_MASCOTA, desparasitante.getMascotaMedicadaId());

            intent.putExtra(Constantes.TAG_FECHA_APLICACION, desparasitante.getFechaAplicacion());
            intent.putExtra(Constantes.TAG_NOMBRE, desparasitante.getDescripcion());
            intent.putExtra(Constantes.TAG_PESO, desparasitante.getPesoKilogramo());
            intent.putExtra(Constantes.TAG_OBSERVACION, desparasitante.getObservacion());
            holder.itemView.getContext().startActivity(intent);
        });
    }
}

