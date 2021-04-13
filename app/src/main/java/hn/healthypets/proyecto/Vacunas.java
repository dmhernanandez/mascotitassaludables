package hn.healthypets.proyecto;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Vacunas extends AppCompatActivity {

    MetodosImagenes metodosImagenes;

    private ImageView imgFotoVacuna;
    private ImageButton btnTomarFotos;
    private ImageButton buscarImagen;
    private EditText edtNombreVacuna;
    private EditText edtFechaVacuna;
    private EditText edtDescripVacuna;
    private Button ok;
    private Button btnCancel;
    private Button btnProxima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacunas);

        imgFotoVacuna = findViewById(R.id.imgComprobacionVacunas);
        btnTomarFotos = findViewById(R.id.imgbtnTomarFotosV);
        buscarImagen = findViewById(R.id.imgbtnBuscarFotosV);
        edtNombreVacuna = findViewById(R.id.edtNombreVacuna);
        edtFechaVacuna = findViewById(R.id.edtFechaAplicacionVacuna);
        edtDescripVacuna = findViewById(R.id.edtDescripcionVacuna);
        ok = findViewById(R.id.btnListoVacunas);
        btnCancel = findViewById(R.id.btnCancelarVacuna);
        metodosImagenes = new MetodosImagenes(this);
        buscarImagen.setOnClickListener((v) -> {
            /**Aquí obtenemos los permisos para entrar a la GALERIA*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { /**En esta línea se verifica el permiso para la versión de android en el dispositivo en tiempo de ejecución*/
                if (ActivityCompat.checkSelfPermission(Vacunas.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    metodosImagenes.openGallery(Vacunas.this);
                } else {
                    ActivityCompat.requestPermissions(Vacunas.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MetodosImagenes.REQUEST_PERMISSION_GALLERY);
                }
            } else {
                metodosImagenes.openGallery(Vacunas.this);
            }
        });

        btnTomarFotos.setOnClickListener((v) -> {
            /**Aquí obtenemos los permisos para USAR la cámara del dispositivo*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { /**En esta línea se verifica el permiso para la versión de android en el dispositivo en tiempo de ejecución*/
                if (ActivityCompat.checkSelfPermission(Vacunas.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    metodosImagenes.goToCamera(Vacunas.this);
                } else {
                    ActivityCompat.requestPermissions(Vacunas.this, new String[]{Manifest.permission.CAMERA}, MetodosImagenes.REQUEST_PERMISSION_CAMERA);
                }
            } else {
                metodosImagenes.goToCamera(Vacunas.this);
            }
        });

        ok.setOnClickListener((v) -> {
            /**Aquí usamos el método que creamos para obtener la imágen*/
            Bitmap imagen = ((BitmapDrawable) imgFotoVacuna.getDrawable()).getBitmap();
//
            Toast.makeText(getApplicationContext(), "Imagen obtenida con éxito :)", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /**Acá abrimos el cuadro de dialogo para poder habilitar los permisos,
         * Si el usuario acepta los permisos, habilitará la cámara o la galería*/
        if (requestCode == MetodosImagenes.REQUEST_PERMISSION_GALLERY) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                metodosImagenes.openGallery(Vacunas.this);
            } else {
                Toast.makeText(this, "Es necesario habilitar todos los permisos", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == MetodosImagenes.REQUEST_PERMISSION_CAMERA) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                metodosImagenes.goToCamera(Vacunas.this);
            } else {
                Toast.makeText(this, "Es necesario habilitar todos los permisos", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**Verificar si los permisos son correctos.
         * En esta parte lo que hacemos es crear la ruta
         * para guardar la imágen*/
        if (requestCode == MetodosImagenes.REQUEST_IMAGE_GALLERY) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                /**Obtenemos la ruta de la imagen*/
                Uri photo = data.getData();
                imgFotoVacuna.setImageURI(photo);
                Log.i("TAG", "Result: " + photo);
            } else {
                Toast.makeText(this, "No seleccionó ninguna foto", Toast.LENGTH_LONG).show();
            }
        } else {
            if (requestCode == MetodosImagenes.REQUEST_IMAGE_CAMERA) {
                if (resultCode == Activity.RESULT_OK) {
                    imgFotoVacuna.setImageURI(Uri.parse(metodosImagenes.getRootPath()));
                    Toast.makeText(getApplicationContext(), "Fotografía tomada con éxito :)", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}