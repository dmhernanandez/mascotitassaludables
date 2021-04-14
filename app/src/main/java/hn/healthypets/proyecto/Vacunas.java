package hn.healthypets.proyecto;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import java.io.File;

import hn.healthypets.proyecto.Utilidades.DateTime;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class Vacunas extends AppCompatActivity {

    MetodosImagenes metodosImagenes;

    private ImageView imgFotoVacuna;
    private ImageButton btnTomarFotos;
    private ImageButton buscarImagen;
    private EditText edtNombreVacuna;
    private EditText edtFechaVacuna;
    private EditText edtDescripVacuna;
    private Button btnListo;
    private DataBase instanciaDB;
    private DateTime fechaHora;
    private int dia, mes, anio;
    private int accion;
    Intent intentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacunas);

        init();

        edtFechaVacuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utilizamos este metodo par obtenener los datos
                DatePickerDialog dialogoFecha = new DatePickerDialog(Vacunas.this, (view, year, month, dayOfMonth) ->
                        edtFechaVacuna.setText(fechaHora.formato(dayOfMonth, month, year)), anio, mes, dia);
                dialogoFecha.show();
            }
        });

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

        btnListo.setOnClickListener((v) -> {
            /**Aquí usamos el método que creamos para obtener la imágen*/
            Bitmap imagen = ((BitmapDrawable) imgFotoVacuna.getDrawable()).getBitmap();
//
            Toast.makeText(getApplicationContext(), "Imagen obtenida con éxito :)", Toast.LENGTH_LONG).show();
        });
    }

    private void init() {
        imgFotoVacuna = findViewById(R.id.imgComprobacionVacunas);
        btnTomarFotos = findViewById(R.id.imgbtnTomarFotosV);
        buscarImagen = findViewById(R.id.imgbtnBuscarFotosV);
        edtNombreVacuna = findViewById(R.id.edtNombreVacuna);
        edtFechaVacuna = findViewById(R.id.edtFechaAplicacionVacuna);
        edtDescripVacuna = findViewById(R.id.edtDescripcionVacuna);
        btnListo = findViewById(R.id.btnListoVacunas);
        metodosImagenes = new MetodosImagenes(this);
        fechaHora = new DateTime();


        /**Obtemos datos del Intent y determinamos si es una actualizacion o una insercion, estos valores se optienen con el */
        intentValues = getIntent();
        accion = intentValues.getIntExtra(Constantes.TAG_ACCION, Constantes.GUARDAR);
        if (accion == Constantes.GUARDAR) {
            //Se carga la fecha por defecto que es la fecha actual
            dia = DateTime.diaDelMes;
            mes = DateTime.mes;
            anio = DateTime.anio;
            /**Asignamos una foto de perfil por defecto*/
            Glide.with(this)
                    .load(R.drawable.default_imagen)
                    .into(imgFotoVacuna);
        }

        /** Acciones a realizar si se la opcion es actualizar*/
        else if (accion == Constantes.ACTUALIZAR) {

            /** Si es una actualización se debe parsear la fecha guadarda previamente para colocarla en variables de fecha
             * para dar formato y tomarla como fecha de refeencia*/
            String[] fecha = intentValues.getStringExtra(Constantes.TAG_FECHA_NACIENTO).split("-");
            dia = Integer.parseInt(fecha[0]);
            mes = Integer.parseInt(fecha[1]);
            anio = Integer.parseInt(fecha[2]);
            /**Se valida si al actualizar tenia una foto, ser asi se muestra, de lo contrario se carga una imagen por defecto*/
            if (!intentValues.getStringExtra(Constantes.TAG_IMG_PATH).equals("")) {
                File filePhoto = new File(metodosImagenes.getRootPath() + "/"
                        + MetodosImagenes.PET_FOLDER + "/" +
                        intentValues.getStringExtra(Constantes.TAG_IMG_PATH));
                Glide.with(this)
                        .load(filePhoto)
                        .into(imgFotoVacuna);
            } else {
                /**Asignamos una foto de perfil por defecto*/
                Glide.with(this)
                        .load(R.drawable.default_credencial)
                        .into(imgFotoVacuna);
            }
        }
        edtFechaVacuna.setText(fechaHora.formato(dia, mes, anio));

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