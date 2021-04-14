package hn.healthypets.proyecto;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import hn.healthypets.proyecto.Utilidades.DateTime;
import hn.healthypets.proyecto.Utilidades.Validacion;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class Vacunas extends AppCompatActivity {

    MetodosImagenes metodosImagenes;

    private ImageView imgFotoVacuna;
    private ImageButton btnTomarFotos;
    private ImageButton btnSeleccionarFoto;
    private String rutaImagen;
    private EditText edtNombreVacuna;
    private EditText edtFechaVacuna;
    private EditText edtDescripVacuna;
    private Button btnGuardar;
    private Button btnCancel;
    private Button btnProxima;
    private DataBase instanciaDB;
    private DateTime fechaHora;
    private int dia, mes, anio;

    /**
     * Se utilizan para validar que tipo de accion se realizara en la actividad, estos datos se reciben del intent
     **/
    private int accion;
    private String especie = "";
    private String raza;
    private Bitmap bitmapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacunas);
        //Inicializamos todos los elementos
        init();

        edtFechaVacuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utilizamos este metodo par obtenener los datos
                DatePickerDialog dialogoFecha = new DatePickerDialog(Vacunas.this, (view, year, month, dayOfMonth) ->
                        edtFechaVacuna.setText(fechaHora.formatoFecha(dayOfMonth, month, year)), anio, mes, dia);
                dialogoFecha.show();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean validacion = Validacion.fieldsAreNotEmpty(
                        edtNombreVacuna.getText().toString(),
                        edtDescripVacuna.getText().toString(),
                        edtFechaVacuna.getText().toString());
                if (validacion) {
                    metodosImagenes.checkPermissionStorage(Vacunas.this);
                    Toast.makeText(Vacunas.this, "Información guardada exitosamente ;)", Toast.LENGTH_SHORT).show();
                } else

                    Toast.makeText(Vacunas.this, "Campos OBLIGATORIOS(*) vacios", Toast.LENGTH_SHORT).show();
            }
        });

        btnSeleccionarFoto.setOnClickListener((v) -> {
            /** Se valida que tenga permisos para acceder a la galeria de ser asi lo envia a la galeria*/
            if(metodosImagenes.checkPermissionGallery(Vacunas.this))
            {
                metodosImagenes.openGallery(Vacunas.this);
            }
            /**De lo contrario los solicita y luego se valida en el metodo on onRequestPermissonCode*/
            else
            {
                metodosImagenes.requestPermissionFromUser(
                        Vacunas.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        MetodosImagenes.REQUEST_PERMISSION_GALLERY);
            }
        });

        btnTomarFotos.setOnClickListener((v) -> {
            /** Se valida que tenga permisos para tomar fotos de ser asi lo envia a la camara*/
            if(metodosImagenes.checkPermissionCamera(Vacunas.this))
            {
                metodosImagenes.goToCamera(Vacunas.this);
            }
            /**De lo contrario los solicita y luego se valida en el metodo on onRequestPermissonCode*/
            else
            {
                metodosImagenes.requestPermissionFromUser(
                        Vacunas.this,
                        Manifest.permission.CAMERA,
                        MetodosImagenes.REQUEST_PERMISSION_CAMERA
                );
            }
        });
    }

    /**Este metodo recive los valores de respuesta al solicitar los permisos*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /** Se validan los permisos devueltos de la socilicitud y en caso de ser haber sido aceptados por el usuario
         *  el metodo validateRequestPermissionCode envia a la actividad solicitada, de acuerdo con el identificador*/
        metodosImagenes.validateRequestPermissionCode(requestCode,permissions,grantResults,Vacunas.this);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void init()
    {
        imgFotoVacuna = findViewById(R.id.imgComprobacionVacunas);
        btnTomarFotos = findViewById(R.id.imgbtnTomarFotosV);
        btnSeleccionarFoto = findViewById(R.id.imgbtnBuscarFotosV);
        edtNombreVacuna = findViewById(R.id.edtNombreVacuna);
        edtFechaVacuna = findViewById(R.id.edtFechaAplicacionVacuna);
        edtDescripVacuna = findViewById(R.id.edtDescripcionVacuna);
        btnGuardar = findViewById(R.id.btnListoVacunas);
        btnCancel = findViewById(R.id.btnCancelarVacuna);
        fechaHora = new DateTime();
        metodosImagenes = new MetodosImagenes(this);

        /** Se obtiene una instancia de la base de datos*/
        instanciaDB = SingletonDB.getDatabase(this);

        /**Obtemos datos del Intent y determinamos si es una actualizacion o una insercion, estos valores se optienen con el */
        Intent intentValues= getIntent();
        accion=intentValues.getIntExtra(Constantes.TAG_ACCION,Constantes.ACTUALIZAR);
        if(accion==Constantes.GUARDAR)
        {
            //Recuperamos el valor de la fecha por defecto que es la fecha actual
            dia=DateTime.diaDelMes;
            mes=DateTime.mes;
            anio=DateTime.anio;
        }
        else if(accion==Constantes.ACTUALIZAR)
        {
            String fecha1="15-03-2021";
            /** Si es una actualización se debe parsear la fecha guadarda previamente para colocarla en variables de fecha
             * para asignarlo y luego asignarla al input*/
            String [] fecha=fecha1.split("-");
            dia=Integer.parseInt(fecha[0]);
            mes=Integer.parseInt(fecha[1])-1;
            anio= Integer.parseInt(fecha[2]);
        }
        edtFechaVacuna.setText(fechaHora.formatoFecha(dia,mes,anio));
        accion=Constantes.ACTUALIZAR;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** Cuando se ejecuta el metodo startActivyForResult deposita los resultados en este metodo*/
        switch (requestCode)
        {
            case MetodosImagenes.REQUEST_IMAGE_CAMERA:
                /** Se obtiene le bitmap par mostrarlo pero sin guardarlo aun*/
                if (resultCode == RESULT_OK && data != null)
                {
                    bitmapImage =(Bitmap) data.getExtras().get("data");
                    imgFotoVacuna.setImageBitmap(bitmapImage);
                }
                break;
            case MetodosImagenes.REQUEST_IMAGE_GALLERY:

                /** Primero convertimos el recurso pasado obtennido por medio de un URI*/
                if(resultCode == RESULT_OK && data != null)
                {
                    bitmapImage= metodosImagenes.getBitmapFromUri(this,data.getData());
                    imgFotoVacuna.setImageBitmap(bitmapImage);
                }
                break;
        }
    }
}
