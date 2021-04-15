package hn.healthypets.proyecto.adaptadores_mascotitas_saludables;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hn.healthypets.proyecto.Agenda;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.AgendaVisita;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class AdaptadorAgenda extends RecyclerView.Adapter<AdaptadorAgenda.ViewHolder> {

    private ArrayList<AgendaVisita> agendaArrayList;

    @Override
    public int getItemCount() {
        return this.agendaArrayList.size();
    }

    public AdaptadorAgenda(ArrayList<AgendaVisita> mascotaArrayList) {
        this.agendaArrayList = mascotaArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvNombreActividad;
        TextView txvNombreMascota;
        TextView txvFechaHoraAgenda;
        Button btnActualizarAgenda;
        Button btnEliminarAgenda;
        DataBase instanciaDB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNombreActividad = itemView.findViewById(R.id.txtFechaNacimiento);
            txvNombreMascota = itemView.findViewById(R.id.txvNombrePerfil);
            txvFechaHoraAgenda= itemView.findViewById(R.id.txvFechaHoraAgenda);
            btnActualizarAgenda = itemView.findViewById(R.id.btnActualizarAgenda);
            btnEliminarAgenda = itemView.findViewById(R.id.btnElimnarAgenda);
            instanciaDB= SingletonDB.getDatabase(itemView.getContext());

        }
    }

    @NonNull
    @Override
    public AdaptadorAgenda.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //CREAMOS LA VISTA QUE CONTENDRA, EL DISENIO DE CADA ITEM

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividad, parent, false);
        //RETORNAR INSTANCIA DE LA CLASE PERSONALIZADA PASANDO COMO ARGUMENTO LA VISTA CREADA
        return new AdaptadorAgenda.ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorAgenda.ViewHolder holder, int position) {

        AgendaVisita agenda = agendaArrayList.get(position);

        holder.txvNombreActividad.setText("Nombre: " + agenda.getNombreActividad());
        /** Todas las varibles de calendarios creadas se utilizan para dar un formato a la fecha*/
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,agenda.getDia());
        calendar.set(Calendar.MONTH,agenda.getMes());
        calendar.set(Calendar.YEAR,agenda.getAnio());
        calendar.set(Calendar.HOUR_OF_DAY,agenda.getHora());
        calendar.set(Calendar.MINUTE,agenda.getMinuto());
        Date fechaHora = new Date(calendar.getTimeInMillis());
        SimpleDateFormat formato = new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm ");
        holder.txvNombreMascota.setText(formato.format(fechaHora));



//        Abrir El menu desde el boton registro
        holder.btnEliminarAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Eliminar actividad");
                builder.setMessage("¿Estas seguro de eliminar esta activad?");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.instanciaDB.getAgendaVisitaDAO().deleteTask(agenda.getAgendaVisitaId());
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        /**Evento del boton actualizar*/
        holder.btnActualizarAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), Agenda.class);
                //Envio el id de la mascota para usaro en cualquie
                intent.putExtra(Constantes.TAG_ACCION,Constantes.ACTUALIZAR);
                intent.putExtra(Constantes.TAG_ID,agenda.getAgendaVisitaId());
                intent.putExtra(Constantes.TAG_NOMBRE_ACTIVIDAD,agenda.getNombreActividad());
                intent.putExtra(Constantes.TAG_DIA,agenda.getDia());
                intent.putExtra(Constantes.TAG_MES,agenda.getMes());
                intent.putExtra(Constantes.TAG_ANIO,agenda.getAnio());
                intent.putExtra(Constantes.TAG_HORA,agenda.getHora());
                intent.putExtra(Constantes.TAG_MINUTO,agenda.getMinuto());
                intent.putExtra(Constantes.TAG_COMENTARIO,agenda.getObservación());
                intent.putExtra(Constantes.TAG_HORA,agenda.getHora());
                intent.putExtra(Constantes.TAG_MINUTO,agenda.getMinuto());
                intent.putExtra(Constantes.TAG_COMENTARIO,agenda.getObservación());
                intent.putExtra(Constantes.TAG_ID_CAT_MEDICAMENTO,agenda.getVisitaCatMedicamentoId());
                intent.putExtra(Constantes.TAG_ESTADO,agenda.getEstado());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
}