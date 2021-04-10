package hn.healthypets.proyecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatCodePointException;
import java.util.Locale;

public class Vacunas extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 100;
    private static final int REQUEST_IMAGE_GALLERY = 101;
    private static final int REQUEST_PERMISSION_CAMERA = 102;
    private static final int REQUEST_IMAGE_CAMERA = 103;

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
                /**Aqui obtenemos los permisos para entrar a la GALERIA*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { /**En esta linea se verifica el permiso para la version de android en el dispositivo en tiempo de ejecucion*/
                    if (ActivityCompat.checkSelfPermission(Vacunas.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        openGallery();
                    } else {
                        ActivityCompat.requestPermissions(Vacunas.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
                    }
                } else {
                    openGallery();
                }
            }
        });

        btnTomarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**Aqui obtenemos los permisos para USAR la camara del dispositivo*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { /**En esta linea se verifica el permiso para la version de android en el dispositivo en tiempo de ejecucion*/
                    if (ActivityCompat.checkSelfPermission(Vacunas.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        goToCamera();
                    } else {
                        ActivityCompat.requestPermissions(Vacunas.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                    }
                } else {
                    goToCamera();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /**Aca abrimos el cuadro de dialogo para poder habilitar los permisos,
         * Si el usuario acepta los permisos, habilitara la camara o la galeria*/
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Necesita todos los permisos", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goToCamera();
            } else {
                Toast.makeText(this, "Necesita habilitar los permisos", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**Verificar si los permisos son correctos.
         * En esta parte lo que hacemos es guardar
         * la imagen*/
        if (requestCode == REQUEST_IMAGE_GALLERY) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                /**Obtenemos la ruta de la imagen*/
                Uri photo = data.getData();
                imgFotoVacuna.setImageURI(photo);
                Log.i("TAG", "Result: " + photo);
            } else {
                Toast.makeText(this, "No selecciono ninguna foto", Toast.LENGTH_LONG).show();
            }
        } else {
            if (requestCode == REQUEST_IMAGE_CAMERA) {
                if (resultCode == Activity.RESULT_OK) {
                    imgFotoVacuna.setImageURI(Uri.parse(rutaImagen));
                }
            }
        }
    }

    /**
     * Metodo para poder accesar a la galeria del dispositivo
     */
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT); /**Con ACTION_GET_CONTENT, obtenemos la foto de la galeria*/
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
    }

    /**
     * Metodo para poder accesar a la camara del dispositivo
     */
    private void goToCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); /**ACTION_IMAGE_CAPTURE, es como una API que nos dibujara la interfaz, para acceder a la camara*/
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = crearImagen();
            } catch (IOException ex) {
                Log.e("Error", ex.toString());
            }
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(
                        this,
                        "hn.healthypets.proyecto.fileprovider",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAMERA);
            }
        }
    }

    /**
     * Metodo para crear el archivo temporal de la camara,
     * para luego ser guardada en el dispositivo
     */
    private File crearImagen() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss", Locale.getDefault()).format(new Date());
        String imgFileName = "IMG_" + timeStamp + "_"; //IMG_202104101111
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imgFileName,
                ".jpg",
                storageDir
        );
        rutaImagen = image.getAbsolutePath();
        return image;
    }
}