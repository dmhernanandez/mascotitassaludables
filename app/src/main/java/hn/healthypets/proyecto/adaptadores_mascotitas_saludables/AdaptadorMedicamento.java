package hn.healthypets.proyecto.adaptadores_mascotitas_saludables;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import hn.healthypets.proyecto.R;
import hn.healthypets.proyecto.database.Entidades.AgendaMedicamento;

public class AdaptadorMedicamento extends RecyclerView.Adapter<AdaptadorMedicamento.ViewHolder>{

    private ArrayList<AgendaMedicamento> medicamentoArrayList;

    @Override
    public int getItemCount() {
        return this.medicamentoArrayList.size();
    }

    public AdaptadorMedicamento(ArrayList<AgendaMedicamento> medicamentoArrayList) {
        this.medicamentoArrayList = medicamentoArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvNombreMascotaMedicamento;
        TextView txvFechaAplicacionMedicamento;
        TextView txvRecetaMedicamento;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNombreMascotaMedicamento = itemView.findViewById(R.id.txvNombrePerfil);
            txvFechaAplicacionMedicamento = itemView.findViewById(R.id.txtFechaNacimiento);
            txvRecetaMedicamento = itemView.findViewById(R.id.txvRecetaMedicamento);
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
        AgendaMedicamento medicamento = medicamentoArrayList.get(position);
        String nombre = medicamento.getNombreMedicamento();
        String fecha=medicamento.getFechaHoraInicio();
        int dosis = medicamento.getDosisCantidad();
        int dosisid=medicamento.getDosisId();
        int intervalo=medicamento.getIntervaloTiempo();
        int dias=medicamento.getNumeroDosis();

        holder.txvNombreMascotaMedicamento.setText("Nombre: " + nombre);
        holder.txvFechaAplicacionMedicamento.setText("Fecha: " + fecha);
        holder.txvRecetaMedicamento.setText("Se dara "+dosis+ dosisid+" Cada cuanto" +intervalo+ " Horas"+dias + " Días");

    }
}
