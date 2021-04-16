package hn.healthypets.proyecto.adaptadores_mascotitas_saludables;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
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
import hn.healthypets.proyecto.Utilidades.DateTime;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.database.dao.AgendaVisitaDAO;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class AdaptadorAgenda extends RecyclerView.Adapter<AdaptadorAgenda.ViewHolder> {

    private ArrayList<AgendaVisitaDAO.AgendaNombre> agendaArrayList;

    @Override
    public int getItemCount() {
        return this.agendaArrayList.size();
    }

    public AdaptadorAgenda(ArrayList<AgendaVisitaDAO.AgendaNombre> mascotaArrayList) {
        this.agendaArrayList = mascotaArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvNombreActividad;
        TextView txvDescripcionAgenda;
        TextView txvNotas;
        TextView txvDescripcion;
        TextView txvTipoActividad;
        Button btnActualizarAgenda;
        Button btnEliminarAgenda;
        DataBase instanciaDB;
        static String tiempo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNombreActividad = itemView.findViewById(R.id.txvNombreAgendaActividad);
            txvDescripcionAgenda = itemView.findViewById(R.id.txvDescripcion);
            btnActualizarAgenda = itemView.findViewById(R.id.btnActualizarAgenda);
            btnEliminarAgenda = itemView.findViewById(R.id.btnElimnarAgenda);
            instanciaDB= SingletonDB.getDatabase(itemView.getContext());
            txvDescripcion=itemView.findViewById(R.id.txvDescripcion);
            txvNotas= itemView.findViewById(R.id.txvNota);
            txvTipoActividad=itemView.findViewById(R.id.txvTipoActividad);
            tiempo="";

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

        AgendaVisitaDAO.AgendaNombre agenda = agendaArrayList.get(position);

        holder.txvNombreActividad.setText(agenda.getNombreActividad());
        /** Todas las varibles de calendarios creadas se utilizan para dar un formato a la fecha*/
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,agenda.getDia());
        calendar.set(Calendar.MONTH,agenda.getMes());
        calendar.set(Calendar.YEAR,agenda.getAnio());
        calendar.set(Calendar.HOUR_OF_DAY,agenda.getHora());
        calendar.set(Calendar.MINUTE,agenda.getMinuto());
        /**Validamos si ya esta caducada la fecha de la actividad*/
        if(agenda.getEstado()==Constantes.ATRASADA)
        {
            holder.tiempo="tenía";
            holder.itemView.setBackgroundColor(Color.parseColor("#F1948A"));
        }
       else   if(DateTime.isFutureDate(agenda.getDia()+"/"+(agenda.getMes() + 1)+"/"+agenda.getAnio()+" "+agenda.getHora()+":"+agenda.getMinuto(),"dd/MM/yyyy HH:mm"))
        {
            holder.tiempo="tiene";
            holder.itemView.setBackgroundColor(Color.parseColor("#D1F2EB"));
        }

        else
        {
            holder.tiempo="tenía";
            holder.itemView.setBackgroundColor(Color.parseColor("#F1948A"));
            holder.instanciaDB.getAgendaVisitaDAO().updateState(agenda.getAgendaVisitaId(),Constantes.ATRASADA);
        }


        Date fechaHora = new Date(calendar.getTimeInMillis());
        SimpleDateFormat formato = new SimpleDateFormat("EEEE dd 'de' MMMM', 'yyyy ");
        /** Se crea la descricion*/
        holder.txvDescripcionAgenda.setText(agenda.getNombre() +" "+holder.tiempo+" una actividad programada el dia "
                +formato.format(fechaHora)+ " a las "+ DateTime.formatoHora(agenda.getHora(),agenda.getMinuto(),true));
          holder.txvTipoActividad.setText("Tipo actividad: "+agenda.getNombreCategoria());

        holder.txvNotas.setText("Nota: "+agenda.getObservación());

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
                        Agenda.cancelAlarm(agenda.getAlarmId(),holder.itemView.getContext());

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
                intent.putExtra(Constantes.TAG_ID_CAT_MEDICAMENTO,agenda.getNombreCategoria());
                intent.putExtra(Constantes.TAG_ID_ALARM,agenda.getAlarmId());
                intent.putExtra(Constantes.TAG_ID_MASCOTA,agenda.getMascotaAgendaVisitaId());
                intent.putExtra(Constantes.TAG_ESTADO,agenda.getEstado());
                Log.i("TAG",Constantes.TAG_ID_MASCOTA);
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }
}