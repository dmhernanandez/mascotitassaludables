package hn.healthypets.proyecto.adaptadores_mascotitas_saludables;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.ArrayList;

import hn.healthypets.proyecto.CreacionPerfiles;
import hn.healthypets.proyecto.MainActivity;
import hn.healthypets.proyecto.MenuRegistro;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.database.Entidades.Mascota;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNombrePerfil = itemView.findViewById(R.id.txvNombrePerfil);
            txvNumeroChipPerfil = itemView.findViewById(R.id.txvNumeroChipPerfil);
            imgMascotaPerfil = itemView.findViewById(R.id.imgvMascotaPerfil);
            btnControlPerfil = itemView.findViewById(R.id.btnControlPerfil);
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

        Glide.with(holder.itemView)
                .load(R.drawable.golden3)
                .transform(new CircleCrop())
                .into(holder.imgMascotaPerfil);

        holder.btnControlPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), MenuRegistro.class);

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
}
