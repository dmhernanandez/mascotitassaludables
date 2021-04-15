package hn.healthypets.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

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
            intent.putExtra(Constantes.TAG_ID_MASCOTA,getIntent().getIntExtra(Constantes.TAG_ID_MASCOTA,Constantes.DEFAULT));
            startActivity(intent);
        });

        desparacitante.setOnClickListener(v -> {
            Intent intent2 = new Intent(this,Desparacitante.class);
            intent2.putExtra(Constantes.TAG_ID_MASCOTA,getIntent().getIntExtra(Constantes.TAG_ID_MASCOTA,Constantes.DEFAULT));
            startActivity(intent2);
        });

        medicamento.setOnClickListener(v -> {
            Intent intent3 = new Intent(this, Medicamentos.class);
            intent3.putExtra(Constantes.TAG_ID_MASCOTA,getIntent().getIntExtra(Constantes.TAG_ID_MASCOTA,Constantes.DEFAULT));
            startActivity(intent3);
        });


        agenda.setOnClickListener(v -> {
            Intent intent5 = new Intent(this,Agenda.class);
            /**Vuelvo a enviar el ID del perro que lo recibi por medio de un intent*/
            intent5.putExtra(Constantes.TAG_ID,Constantes.DEFAULT);
            intent5.putExtra(Constantes.TAG_ACCION,Constantes.GUARDAR);
            startActivity(intent5);
        });
    }
}