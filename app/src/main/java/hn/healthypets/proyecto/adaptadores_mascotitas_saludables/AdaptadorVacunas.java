package hn.healthypets.proyecto.adaptadores_mascotitas_saludables;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hn.healthypets.proyecto.MetodosImagenes;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.database.dao.MedicamentoDAO;

public class AdaptadorVacunas extends RecyclerView.Adapter<AdaptadorVacunas.ViewHolder>   {
   private ArrayList<MedicamentoDAO.Vacunacard> vacunaArrayList;

    @Override
    public int getItemCount() {
        return this.vacunaArrayList.size();
    }

    public AdaptadorVacunas(ArrayList<MedicamentoDAO.Vacunacard> vacunaArrayList) {
        this.vacunaArrayList = vacunaArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvNombreMascota;
        TextView txvNombreVacuna;
        TextView txvFechaAplicacionVacuna;
        ImageView imgvVacuna;
        /**Este medoto se utiliza para obtener el la ruta del directorio raiz de la aplicion
         */
        MetodosImagenes metodosImagenes;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            metodosImagenes = new MetodosImagenes(itemView.getContext());
            txvNombreMascota = itemView.findViewById(R.id.txvNombreMascota);
            txvNombreVacuna = itemView.findViewById(R.id.txvNombreVacuna);
            txvFechaAplicacionVacuna = itemView.findViewById(R.id.txvFechaAplicacionVacuna);
            imgvVacuna = itemView.findViewById(R.id.imgvVacuna);

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

        MedicamentoDAO.Vacunacard vacuna = vacunaArrayList.get(position);
        holder.txvNombreMascota.setText("Mascota: " + vacuna.getNombre());
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
    }


}