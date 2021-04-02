package hn.healthypets.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton = findViewById(R.id.button);
        Button nuevo = findViewById(R.id.nuevo);
        Button credencial = findViewById(R.id.btnCredencial);
        Button vacuna= findViewById(R.id.btnCredencial2);

        boton.setOnClickListener(v -> {

            Intent intent =new Intent(this,CreacionPerfiles.class);

            startActivity(intent);

        });

        nuevo.setOnClickListener(v -> {

            Intent intent2 =new Intent(this, Director.class);

            startActivity(intent2);


        });

        credencial.setOnClickListener(v -> {

            Intent intent3 =new Intent(this, Perfiles.class);

            startActivity(intent3);


        });

        vacuna.setOnClickListener(v -> {

            Intent intent4 =new Intent(this, Vacunas.class);

            startActivity(intent4);

        });
    }
}