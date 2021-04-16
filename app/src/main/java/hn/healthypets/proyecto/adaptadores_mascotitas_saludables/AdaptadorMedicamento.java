package hn.healthypets.proyecto.adaptadores_mascotitas_saludables;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hn.healthypets.proyecto.Medicamentos;
import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.database.Entidades.AgendaMedicamento;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class AdaptadorMedicamento extends RecyclerView.Adapter<AdaptadorMedicamento.ViewHolder> {
    private ArrayList<AgendaMedicamento> agendaMedicamentoArrayList;

    @Override
    public int getItemCount() {
        return this.agendaMedicamentoArrayList.size();
    }

    public AdaptadorMedicamento(ArrayList<AgendaMedicamento> agendaMedicamentoArrayList) {
        this.agendaMedicamentoArrayList = agendaMedicamentoArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvNombreMascotaMedicamento;
        TextView txvFechaAplicacionMedicamento;
        TextView txvRecetaMedicamento;
        ImageView btnActualizarMedicamento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNombreMascotaMedicamento = itemView.findViewById(R.id.txvNombreMascotaMedicamento);
            txvFechaAplicacionMedicamento = itemView.findViewById(R.id.txvFechaAplicacionMedicamento);
            txvRecetaMedicamento = itemView.findViewById(R.id.txvRecetaMedicamento);
            btnActualizarMedicamento = itemView.findViewById(R.id.btnActualizarMedicamento);
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
        AgendaMedicamento agendaMedicamento = agendaMedicamentoArrayList.get(position);

        int id = agendaMedicamento.getMascotaMedicamentoId();
        String nombre = agendaMedicamento.getNombreMedicamento();
        String fecha = agendaMedicamento.getFechaHoraInicio();
        int dosis = agendaMedicamento.getDosisCantidad();
        int dosisid = agendaMedicamento.getDosisId();
        int intervalo = agendaMedicamento.getIntervaloTiempo();
        int dias = agendaMedicamento.getNumeroDosis();

        holder.txvNombreMascotaMedicamento.setText("Nombre: " + id);
        holder.txvFechaAplicacionMedicamento.setText("Fecha: " + fecha);
        holder.txvRecetaMedicamento.setText("Se dará: " + nombre + "\n la cantidad de: " + dosis + "- " + dosisid + "\n Por: " + dias + " días,\n Cada: " + intervalo + " horas");

        holder.btnActualizarMedicamento.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), Medicamentos.class);
            //Envio el id de la mascota para usalro en cualquie
            intent.putExtra(Constantes.TAG_ACCION, Constantes.ACTUALIZAR);

            intent.putExtra(Constantes.TAG_ID, agendaMedicamento.getId());
            intent.putExtra(Constantes.TAG_NOMBRE, agendaMedicamento.getNombreMedicamento());
            intent.putExtra(Constantes.TAG_DOSIS, agendaMedicamento.getDosisId());
            intent.putExtra(Constantes.TAG_CADA, agendaMedicamento.getDosisCantidad());
            intent.putExtra(Constantes.TAG_POR, agendaMedicamento.getNumeroDosis());
            intent.putExtra(Constantes.TAG_FECHA_APLICACION, agendaMedicamento.getFechaHoraInicio());
            holder.itemView.getContext().startActivity(intent);
        });
    }
}
