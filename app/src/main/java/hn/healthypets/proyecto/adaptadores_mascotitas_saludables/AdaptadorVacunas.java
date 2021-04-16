package hn.healthypets.proyecto.adaptadores_mascotitas_saludables;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.io.File;
import java.util.ArrayList;

import hn.healthypets.proyecto.CreacionPerfiles;
import hn.healthypets.proyecto.MetodosImagenes;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.Vacunas;
import hn.healthypets.proyecto.database.Entidades.Medicamento;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class AdaptadorVacunas extends RecyclerView.Adapter<AdaptadorVacunas.ViewHolder> {
   private ArrayList<Medicamento> vacunaArrayList;

    @Override
    public int getItemCount() {
        return this.vacunaArrayList.size();
    }

    public AdaptadorVacunas(ArrayList<Medicamento> vacunaArrayList) {
        this.vacunaArrayList = vacunaArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvNombreMascota;
        TextView txvNombreVacuna;
        TextView txvFechaAplicacionVacuna;
        ImageView imgvVacuna;
        ImageView btnActualizarVacuna;
        /**
         * Este medoto se utiliza para obtener el la ruta del directorio raiz de la aplicion
         */
        MetodosImagenes metodosImagenes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            metodosImagenes = new MetodosImagenes(itemView.getContext());
            txvNombreMascota = itemView.findViewById(R.id.txvNombreMascota);
            txvNombreVacuna = itemView.findViewById(R.id.txvNombreVacuna);
            txvFechaAplicacionVacuna = itemView.findViewById(R.id.txvFechaAplicacionVacuna);
            imgvVacuna = itemView.findViewById(R.id.imgvVacuna);
            btnActualizarVacuna = itemView.findViewById(R.id.btnActualizarVacuna);
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

        Medicamento vacuna = vacunaArrayList.get(position);
        holder.txvNombreMascota.setText("Mascota: " + vacuna.getMascotaMedicadaId());
        holder.txvNombreVacuna.setText("Vacuna: " +     vacuna.getDescripcion());
        holder.txvFechaAplicacionVacuna.setText("Fecha: " +     vacuna.getFechaAplicacion());

        //Se valida que la ruta de la foto no se nula ni esta vacia, para cargarla
        if(!vacuna.getRutaFotoComprobante().equals(""))
        {
            File filePhoto = new File(holder.metodosImagenes.getRootPath()+"/"+MetodosImagenes.VACUNA_FOLDER+"/"+vacuna.getRutaFotoComprobante());
            Glide.with(holder.itemView)
                    .load(filePhoto)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transform(new CircleCrop())
                    .into(holder.imgvVacuna);
        }
        else//Si la ruta de la foto esta vacia se coloca una por defecto
        {
            Glide.with(holder.itemView)
                    .load(R.drawable.default_credencial)
                    .transform(new CircleCrop())
                    .into(holder.imgvVacuna);
        }
        holder.btnActualizarVacuna.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), Vacunas.class);
            //Envio el id de la mascota para usaro en cualquie
            intent.putExtra(Constantes.TAG_ACCION,Constantes.ACTUALIZAR);

            intent.putExtra(Constantes.TAG_ID,vacuna.getMedicamentoId());
            intent.putExtra(Constantes.TAG_NOMBRE,vacuna.getDescripcion());
            intent.putExtra(Constantes.TAG_FECHA_APLICACION,vacuna.getFechaAplicacion());
            intent.putExtra(Constantes.TAG_OBSERVACION,vacuna.getObservacion());
            intent.putExtra(Constantes.TAG_IMG_PATH,vacuna.getRutaFotoComprobante());
            holder.itemView.getContext().startActivity(intent);
        });
    }
}