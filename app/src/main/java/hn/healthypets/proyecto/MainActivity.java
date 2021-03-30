package hn.healthypets.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton = findViewById(R.id.button);
        Button nuevo = findViewById(R.id.nuevo);
        Button credencial = findViewById(R.id.btnCredencial);

        boton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreacionPerfiles.class);
            startActivity(intent);
        });

        nuevo.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, director.class);
            startActivity(intent2);
        });

        credencial.setOnClickListener(v -> {
            Intent intent3 = new Intent(this, perfiles.class);
            startActivity(intent3);
        });
    }
}