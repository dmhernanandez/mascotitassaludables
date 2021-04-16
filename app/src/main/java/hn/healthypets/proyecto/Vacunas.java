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

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import hn.healthypets.proyecto.Utilidades.DateTime;
import hn.healthypets.proyecto.Utilidades.Validacion;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.Medicamento;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class Vacunas extends AppCompatActivity {

    private MetodosImagenes metodosImagenes;

    private ImageView imgFotoVacuna;
    private ImageButton btnTomarFotos;
    private ImageButton btnSeleccionarFoto;
    private String rutaImagen;
    private EditText edtNombreVacuna;
    private EditText edtFechaVacuna;
    private EditText edtDescripVacuna;
    private EditText edtPesoDesparacitante;
    private Button btnGuardar;
    private SimpleDateFormat formatoFecha;
    private DataBase instanciaDB;
    private DateTime fechaHora;
    private int dia, mes, anio;

    /**
     * Se utilizan para validar que tipo de accion se realizara en la actividad, estos datos se reciben del intent
     **/
    private int accion;
    private Bitmap bitmapImage;

    /**
     * Esta variable se utiliza para almacenar el valor que se devuelve de la solicitud de los permisos, esto quiere decir
     * se coloca el nombre de permissionStorage ya que el unico momento en que se utilizara sera para validar si hay permisos de almacenamiento
     */
    private boolean permissionStorgare;
    /**
     * Creamos un intent para tener acceso a los datos cuando sea una actualizacion a travez de los getExtras
     */
    Intent intentValues;

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

        btnGuardar.setOnClickListener((v) -> {
            //Valida que los campos esten llenos
            boolean fieldsFill = Validacion.fieldsAreNotEmpty(
                    edtNombreVacuna.getText().toString(),
                    edtFechaVacuna.getText().toString());
            //Valida que por lo menos un radio button este seleccionado
            if (fieldsFill) {
                String nombreImagen = "";
                Medicamento medicamento;

                /** Para las versiones menores que 4.4 se solicta el permiso, en caso que nuestro telefono se mayor
                 * pasara de una solo vez almacenar*/
                if (metodosImagenes.checkPermissionStorage(Vacunas.this) || permissionStorgare) {
                    switch (accion) {
                        case Constantes.GUARDAR:
                            /** Validamos que no haya una imagen vacia si esta vacia entonces se
                             * la ruta se envia vacia, sino se guarda y se retorna el nombre de la imagen*/
                            if (bitmapImage != null) {
                                nombreImagen = metodosImagenes.savePhoto(Vacunas.this,
                                        bitmapImage,
                                        MetodosImagenes.VACUNA_FOLDER,
                                        metodosImagenes.generarNombre("img_pet_") + ".jpeg");
                            }
                            Medicamento vacuna = new Medicamento(
                                    0,
                                    edtNombreVacuna.getText().toString(),
                                    edtFechaVacuna.getText().toString(),
                                    nombreImagen,
                                    0,
                                    edtDescripVacuna.getText().toString(),
                                    getIntent().getIntExtra(Constantes.TAG_ID, Constantes.DEFAULT),
                                    instanciaDB.getCategoriaMedicamentoDAO().getIdMedicinesCategoriesByName("Vacuna"),
                                    0);

                            instanciaDB.getMedicamentoDAO().insertMedicine(vacuna);

                            Toast.makeText(Vacunas.this, "Información guardada exitosamente ;)", Toast.LENGTH_LONG).show();
                            finish();
                            break;
                        case Constantes.ACTUALIZAR:
                            /**
                             * Si la imagen del bitmap esta vacia en y se esta actualizando siginifica que no se selecciono
                             * una nueva imagen por lo tanto la ruta que se mandara sera la misma que tenia anteriormente,
                             * estos se logra atravez de el intent que se envio al iniciar la actividad
                             * */
                            nombreImagen = intentValues.getStringExtra(Constantes.TAG_IMG_PATH);
                            if (bitmapImage != null) {
                                /** Si se selecciono una foto nueva entonces mandamos a llamar al metodo savePhoto para que
                                 * guarde una nueva foto, pero si la ruta de la foto anterior esta vacia se genera un nuevo
                                 * nombre y si no se envia el nombre de la foto actual para sustituir el archivo pero que con
                                 * conserve el mismo nombre*/
                                if (nombreImagen.equals("")) {
                                    nombreImagen = metodosImagenes.savePhoto(Vacunas.this,
                                            bitmapImage,
                                            MetodosImagenes.VACUNA_FOLDER,
                                            metodosImagenes.generarNombre("img_vac_") + ".jpeg");
                                } else {
                                    nombreImagen = metodosImagenes.savePhoto(Vacunas.this,
                                            bitmapImage,
                                            MetodosImagenes.VACUNA_FOLDER,
                                            nombreImagen);
                                }
                            }
                            vacuna = new Medicamento(
                                    intentValues.getIntExtra(Constantes.TAG_ID, Constantes.DEFAULT),
                                    edtNombreVacuna.getText().toString(),
                                    edtFechaVacuna.getText().toString(),
                                    nombreImagen,
                                    0,
                                    edtDescripVacuna.getText().toString(),
                                    intentValues.getIntExtra(Constantes.TAG_ID, Constantes.DEFAULT),
                                    instanciaDB.getCategoriaMedicamentoDAO().getIdMedicinesCategoriesByName("Vacuna"),
                                    0);

                            instanciaDB.getMedicamentoDAO().updateMedicine(vacuna);

                            Toast.makeText(Vacunas.this, "Informacion Actualizada con exitosamente ;)", Toast.LENGTH_LONG).show();
                            finish();
                            break;
                    }
                } else {
                    Toast.makeText(Vacunas.this, "Permisos", Toast.LENGTH_SHORT).show();

                    metodosImagenes.requestPermissionFromUser(
                            Vacunas.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            MetodosImagenes.REQUEST_PERMISION_WRITE_STORAGE);
                }
            } else {
                Toast.makeText(Vacunas.this, "Debe llenar todos los campos obligatorios", Toast.LENGTH_LONG).show();
            }
        });

        btnSeleccionarFoto.setOnClickListener((v) ->
        {
            /** Se valida que tenga permisos para acceder a la galeria de ser asi lo envia a la galeria*/
            if (metodosImagenes.checkPermissionGallery(Vacunas.this)) {
                metodosImagenes.openGallery(Vacunas.this);
            }
            /**De lo contrario los solicita y luego se valida en el metodo on onRequestPermissonCode*/
            else {
                metodosImagenes.requestPermissionFromUser(
                        Vacunas.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        MetodosImagenes.REQUEST_PERMISSION_GALLERY);
            }
        });

        btnTomarFotos.setOnClickListener((v) ->
        {
            /** Se valida que tenga permisos para tomar fotos de ser asi lo envia a la camara*/
            if (metodosImagenes.checkPermissionCamera(Vacunas.this)) {
                metodosImagenes.goToCamera(Vacunas.this);
            }
            /**De lo contrario los solicita y luego se valida en el metodo on onRequestPermissonCode*/
            else {
                metodosImagenes.requestPermissionFromUser(
                        Vacunas.this,
                        Manifest.permission.CAMERA,
                        MetodosImagenes.REQUEST_PERMISSION_CAMERA);
            }
        });
    }

    /**
     * Este metodo recive los valores de respuesta al solicitar los permisos
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /** Se validan los permisos devueltos de la socilicitud y en caso de ser haber sido aceptados por el usuario
         *  el metodo validateRequestPermissionCode envia a la actividad solicitada, de acuerdo con el identificador*/
        metodosImagenes.validateRequestPermissionCode(requestCode, permissions, grantResults, Vacunas.this);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** Cuando se ejecuta el metodo startActivyForResult deposita los resultados en este metodo*/
        switch (requestCode) {
            case MetodosImagenes.REQUEST_IMAGE_CAMERA:
                /** Se obtiene le bitmap par mostrarlo pero sin guardarlo aun*/
                if (resultCode == RESULT_OK && data != null) {
                    bitmapImage = (Bitmap) data.getExtras().get("data");
                    imgFotoVacuna.setImageBitmap(bitmapImage);
                }
                break;
            case MetodosImagenes.REQUEST_IMAGE_GALLERY:

                /** Primero convertimos el recurso pasado obtennido por medio de un URI*/
                if (resultCode == RESULT_OK && data != null) {
                    bitmapImage = metodosImagenes.getBitmapFromUri(this, data.getData());
                    imgFotoVacuna.setImageBitmap(bitmapImage);
                }
                break;
        }
    }

    private void init() {
        imgFotoVacuna = findViewById(R.id.imgComprobacionVacunas);
        btnTomarFotos = findViewById(R.id.imgbtnTomarFotosV);
        btnSeleccionarFoto = findViewById(R.id.imgbtnBuscarFotosV);
        edtNombreVacuna = findViewById(R.id.edtNombreVacuna);
        edtFechaVacuna = findViewById(R.id.edtFechaAplicacionVacuna);
        edtDescripVacuna = findViewById(R.id.edtDescripcionVacuna);
        //edtPesoDesparacitante.findViewById(R.id.edtPesoDesparacitante);
        btnGuardar = findViewById(R.id.btnListoVacunas);
        fechaHora = new DateTime();
        metodosImagenes = new MetodosImagenes(this);

        /** Se obtiene una instancia de la base de datos*/
        instanciaDB = SingletonDB.getDatabase(this);

        /**Obtemos datos del Intent y determinamos si es una actualizacion o una insercion, estos valores se optienen con el */
        intentValues = getIntent();
        accion = intentValues.getIntExtra(Constantes.TAG_ACCION, Constantes.GUARDAR);
        if (accion == Constantes.GUARDAR) {
            /**Recuperamos el valor de la fecha por defecto que es la fecha actual*/
            dia = DateTime.diaDelMes;
            mes = DateTime.mes;
            anio = DateTime.anio;
            /**Asignamos una foto de perfil por defecto*/
            Glide.with(this)
                    .load(R.drawable.default_credencial)
                    .into(imgFotoVacuna);
        }
        /** Acciones a realizar si se la opcion es actualizar*/
        else if (accion == Constantes.ACTUALIZAR) {
            /** Si es una actualización se debe parsear la fecha guadarda previamente para colocarla en variables de fecha
             * para asignarlo y luego asignarla al input*/
            edtNombreVacuna.setText(intentValues.getStringExtra(Constantes.TAG_NOMBRE));
            edtDescripVacuna.setText(intentValues.getStringExtra(Constantes.TAG_OBSERVACION));
            String[] fecha = intentValues.getStringExtra(Constantes.TAG_FECHA_APLICACION).split("-");
            dia = Integer.parseInt(fecha[0]);
            mes = Integer.parseInt(fecha[1])-1;
            anio = Integer.parseInt(fecha[2]);
            /**Se valida si al actualizar tenia una foto, ser asi se muestra, de lo contrario se carga una imagen por defecto*/
            if (!intentValues.getStringExtra(Constantes.TAG_IMG_PATH).equals("")) {
                File filePhoto = new File(metodosImagenes.getRootPath() + "/"
                        + MetodosImagenes.VACUNA_FOLDER + "/" +
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
        edtFechaVacuna.setText(fechaHora.formatoFecha(dia,mes,anio));
    }
}
