package hn.healthypets.proyecto.adaptadores_mascotitas_saludables;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.database.Entidades.AgendaMedicamento;
import hn.healthypets.proyecto.database.dao.AgendaMedicamentoDAO;

public class AdaptadorMedicamento extends RecyclerView.Adapter<AdaptadorMedicamento.ViewHolder> {

    private ArrayList<AgendaMedicamentoDAO.RecetaMascota> agendaMedicamentoArrayList;

    @Override
    public int getItemCount() {

        return this.agendaMedicamentoArrayList.size();
    }

    public AdaptadorMedicamento(ArrayList<AgendaMedicamentoDAO.RecetaMascota> agendaMedicamentoArrayList) {
        this.agendaMedicamentoArrayList = agendaMedicamentoArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvNombreMascotaMedicamento;
        TextView txvFechaAplicacionMedicamento;
        TextView txvRecetaMedicamento;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNombreMascotaMedicamento = itemView.findViewById(R.id.txvNombreMascotaMedicamento);
            txvFechaAplicacionMedicamento = itemView.findViewById(R.id.txvFechaAplicacionDesparasitante);
            txvRecetaMedicamento = itemView.findViewById(R.id.txvNombreDesparasitante);
             }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //CREAMOS LA VISTA QUE CONTENDRA, EL DISENIO DE CADA ITEM
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento, parent, false);
        //RETORNAR INSTANCIA DE LA CLASE PERSONALIZADA PASANDO COMO ARGUMENTO LA VISTA CREADA
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMedicamento.ViewHolder holder, int position) {
        AgendaMedicamentoDAO.RecetaMascota agendaMedicamento = agendaMedicamentoArrayList.get(position);

        String nombre = agendaMedicamento.getNombre();
        String nombremedicamento=agendaMedicamento.getNombreMedicamento();
        String fecha=agendaMedicamento.getFechaHoraInicio();
        int dosis = agendaMedicamento.getDosisCantidad();
        String dosisid=agendaMedicamento.getDosisNombre();
        int intervalo=agendaMedicamento.getIntervaloTiempo();
        int dias=agendaMedicamento.getNumeroDosis();
        String horaFechaInicioProxima=agendaMedicamento.getHoraFechaProximaDosis();

        holder.txvNombreMascotaMedicamento.setText("Mascota: " + nombre);
        holder.txvFechaAplicacionMedicamento.setText("Fecha: " + fecha);
        holder.txvRecetaMedicamento.setText("Se dará "+nombremedicamento+"\n la Cantidad de "+dosis+"- "+dosisid+ "\n Por " +dias+ " Dias,\n Cada " +intervalo+ " Horas");

    }
}
