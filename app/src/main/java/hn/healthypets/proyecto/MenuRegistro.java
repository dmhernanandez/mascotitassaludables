package hn.healthypets.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

public class MenuRegistro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_registro);

        Button vacuna = findViewById(R.id.btnVacunasMenu);

        vacuna.setOnClickListener(v -> {
            vacuna.setBackgroundColor(Color.RED);
            Intent intent4 = new Intent(this, Vacunas.class);
            startActivity(intent4);
        });
    }
}