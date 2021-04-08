package hn.healthypets.proyecto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class Vacunas extends AppCompatActivity {
    ImageButton buscarImagen;
    ImageButton btnTomarFotos;
    ImageView imgFotoVacuna;
    String rutaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacunas);

        btnTomarFotos = findViewById(R.id.imgbtnTomarFotosV);
        imgFotoVacuna = findViewById(R.id.imgComprobacionVacunas);

        imgFotoVacuna = findViewById(R.id.imgComprobacionVacunas);
        buscarImagen = findViewById(R.id.imgbtnBuscarFotosV);
        buscarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });

        btnTomarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicacion"), 10);
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File imagenArchivo = null;
            try {
                imagenArchivo = crearImagen();
            } catch (IOException ex) {
                Log.e("Error", ex.toString());
            }
            if (imagenArchivo != null) {
                Uri fotoUri = FileProvider.getUriForFile(this, "hn.healthypets.proyecto.fileprovider", imagenArchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intent, 1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Log.i("error", "entro");
//            Bundle extras = data.getExtras();
//            Bitmap imgBitmap = (Bitmap)extras.get("data")
            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            imgFotoVacuna.setImageBitmap(imgBitmap);
        } else {
            if (resultCode == RESULT_OK) {
                Uri path = data.getData();
                imgFotoVacuna.setImageURI(path);
            }
        }
    }

    private File crearImagen() throws IOException {
        String nombreImagen = "fotoVacuna_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }
}