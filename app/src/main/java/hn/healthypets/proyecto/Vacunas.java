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
import java.util.IllegalFormatCodePointException;

public class Vacunas extends AppCompatActivity {
    ImageButton buscarImagen;
    ImageButton btnTomarFotos;
    ImageView imgFotoVacuna;
    String rutaImagen;

    private static final int REQUEST_PERMISSION_CODE = 100;
    private static final int REQUEST_IMAGE_GALLERY = 101;
    private static final int REQUEST_PERMISSION_CAMERA = 102;
    private static final int REQUEST_IMAGE_CAMERA = 103;


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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (ActivityCompat.checkSelfPermission(Vacunas.this, Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                        goToCamera();
                    }else {
                        ActivityCompat.requestPermissions(Vacunas.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                    }
                }else {
                    goToCamera();
                }
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
    }

    private void goToCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File imagenArchivo = null;
            try {
                imagenArchivo = crearImagen();
            } catch (IOException ex) {
                Log.e("Error", ex.toString());
            }
            if (imagenArchivo != null) {
                Uri fotoUri = FileProvider.getUriForFile(this, "hn.healthypets.proyecto.fileprovider", imagenArchivo);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAMERA);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GALLERY) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri photo = data.getData();
                photo.getPath();
                Log.i("foto", photo.getPath());
                imgFotoVacuna.setImageURI(photo);
            } else {
                Log.i("TAG", "Result: " + resultCode);
                Toast.makeText(this, "No selecciono ninguna foto", Toast.LENGTH_LONG).show();
            }
        } else {
            if (requestCode == REQUEST_IMAGE_CAMERA && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imgBitmap = (Bitmap)extras.get("data")
                Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
                imgFotoVacuna.setImageBitmap(imgBitmap);
            } //else {
//                if (resultCode == RESULT_OK) {
//                    Uri path = data.getData();
//                    imgFotoVacuna.setImageURI(path);
//                }
//            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Necesita todos los permisos", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode==REQUEST_PERMISSION_CAMERA){
            if (permissions.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
               goToCamera();
            }else {
                Toast.makeText(this, "Necesita habilitar los permisos", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private File crearImagen() throws IOException {
        String nombreImagen = "fotoVacunaCamara_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }
}