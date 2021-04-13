package hn.healthypets.proyecto.adaptadores_mascotitas_saludables;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hn.healthypets.proyecto.MenuRegistro;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.database.Entidades.Mascota;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class AdaptadorPerfil extends RecyclerView.Adapter<AdaptadorPerfil.ViewHolder> {

    private ArrayList<Mascota> mascotaArrayList;

    @Override
    public int getItemCount() {
        return this.mascotaArrayList.size();
    }

    public AdaptadorPerfil(ArrayList<Mascota> mascotaArrayList) {
        this.mascotaArrayList = mascotaArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvNombrePerfil;
        TextView txvNumeroChipPerfil;
        ImageView imgMascotaPerfil;
        Button btnControlPerfil;
        ImageButton btnActualizar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNombrePerfil = itemView.findViewById(R.id.txvNombreMascotaMedicamento);
            txvNumeroChipPerfil = itemView.findViewById(R.id.txvNumeroChipPerfil);
            imgMascotaPerfil = itemView.findViewById(R.id.imgvMascotaPerfil);
            btnControlPerfil = itemView.findViewById(R.id.btnControlPerfil);
            btnActualizar= itemView.findViewById(R.id.btnActualizarMascota);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //CREAMOS LA VISTA QUE CONTENDRA, EL DISENIO DE CADA ITEM

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perfil, parent, false);
        //RETORNAR INSTANCIA DE LA CLASE PERSONALIZADA PASANDO COMO ARGUMENTO LA VISTA CREADA
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Mascota mascota = mascotaArrayList.get(position);
        String nombre = mascota.getNombre();
        String fecha = mascota.getFechaNacimiento();


        holder.txvNombrePerfil.setText("Nombre: " + nombre);
        holder.txvNumeroChipPerfil.setText("Fecha: " + fecha);
        //Se valida que la ruta de la foto no se nula ni esta vacia, para cargarla
       if(mascota.getRutaFoto()!=null || !mascota.getRutaFoto().equals(""))
       {
           Glide.with(holder.itemView)
                   .load(mascota.getRutaFoto())
                   .transform(new CircleCrop())
                   .into(holder.imgMascotaPerfil);
       }
       else//Si la ruta de la foto esta vacia se coloca una por defecto
       {
           Glide.with(holder.itemView)
                   .load(R.drawable.default_credencial)
                   .transform(new CircleCrop())
                   .into(holder.imgMascotaPerfil);
       }


        holder.btnControlPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), MenuRegistro.class);
                //Envio el id de la mascota para usaro en cualquie
                intent.putExtra(Constantes.TAG_ID_MASCOTA,mascota.getMascotaId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), Mascota.class);
                //Envio el id de la mascota para usaro en cualquie
                intent.putExtra(Constantes.TAG_ACCION,Constantes.ACTUALIZAR);

                intent.putExtra(Constantes.TAG_ID_MASCOTA,mascota.getMascotaId());
                intent.putExtra(Constantes.TAG_NOMBRE,mascota.getNombre());
                intent.putExtra(Constantes.TAG_FECHA_NACIENTO,mascota.getFechaNacimiento());
                intent.putExtra(Constantes.TAG_NUMERO_CHIP,mascota.getNumeroChip());
                intent.putExtra(Constantes.TAG_GENERO,mascota.getGeneroMascotaId());
                intent.putExtra(Constantes.TAG_ESPECIE,mascota.getMascotaEspecieId());
                intent.putExtra(Constantes.TAG_RAZA,mascota.getRazaMascotaId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
}
