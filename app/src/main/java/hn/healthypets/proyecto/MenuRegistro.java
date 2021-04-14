package hn.healthypets.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuRegistro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_registro);

        Button vacuna = findViewById(R.id.btnVacunasMenu);
        Button desparacitante=findViewById(R.id.btnDesparacitanteMenu);
        Button medicamento=findViewById(R.id.btnMedicamentoMenu);
        Button agenda=findViewById(R.id.btnAgendaMenu);

        vacuna.setOnClickListener(v -> {
            Intent intent = new Intent(this, Vacunas.class);
            startActivity(intent);
        });

        desparacitante.setOnClickListener(v -> {
            Intent intent2 = new Intent(this,Desparacitante.class);
            startActivity(intent2);
        });

        medicamento.setOnClickListener(v -> {
            Intent intent3 = new Intent(this, MedicamentosActivity.class);
            startActivity(intent3);
        });


        agenda.setOnClickListener(v -> {
            Intent intent5 = new Intent(this,Agenda.class);
            startActivity(intent5);
        });
    }
}