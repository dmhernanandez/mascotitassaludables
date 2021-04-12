package hn.healthypets.proyecto;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import hn.healthypets.proyecto.Utilidades.DateTime;
import hn.healthypets.proyecto.Utilidades.Validacion;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.Especie;
import hn.healthypets.proyecto.database.Entidades.Raza;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.database.dao.EspecieDAO;
import hn.healthypets.proyecto.database.dao.RazaDAO;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class CreacionPerfiles extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ModalDialogoEspecie.ModalDialogoEspecieListener, ModalDialogoRaza.ModalDialogoRazaListener {

    private ImageButton btnTomarFotos;
    private ImageView imgFotoMascota;
    private String rutaImagen;
    private TextView textViewEspecie;
    private ImageButton agregarEspecie;
    private ImageButton btnSeleccionarFoto;
    private Spinner spiEspecie;
    private Spinner spiRaza;
    private EditText edtFechaNaciento;
    private EditText edtNombreMascota;
    private ImageButton agregarRaza;
    private DataBase instanciaDB;
    private DateTime fechaHora;
    private RadioButton rbtnMacho;
    private RadioButton rbtnHembra;
    private RadioGroup rgpGrupoGenero;
    private Button btnGuardar;
    private MetodosImagenes metodosImagenes;
    private static ArrayList<String> arrayNombreRazas;
    private static ArrayList<String> arrayNombreEspecies;
    private ArrayAdapter<String> adaptadorEspecie;
    private ArrayAdapter<String> adaptadorRaza;
    private SimpleDateFormat formatoFecha;
    private int dia, mes, anio;
    /**
     * Estas variables de utilizan para determinar la posicion en la que se encuentra un elemento cuando se esta actualizando
     * para asi seleccionarlo
     */
    private int postionItemEspecie;
    private int postionItemRaza;

    /**
     * Se utilizan para validar que tipo de accion se realizara en la actividad, estos datos se reciben del intent
     **/
    private int accion;
    private String especie;
    private String raza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_perfiles);
        //Inicializamos todos los elementos
        init();

        /**
         * ESTE METODO SE EJECUTA DESPUES DE QUE LA APLICACION PASA AL ESTADO RESUMED Y QUE ES UNA CONSULTA ASINCRONA Y NO SE REALIZA
         * EN EL HILO PRINCIPAL O EN LA EJECUCI0N DEL onCreate
         * Se obtiene una Array de todas las especies de la base de datos y se guarda el nombre en un arreglo.
         * Ademas se agrega un Observer que notificara cuano hayan cambios para que la interfaz se recarga automaticamente cada vez que exista un cambio
         * */
        instanciaDB.getSpeciesDAO().getAllNameSpecies().observe(CreacionPerfiles.this,
                new Observer<List<EspecieDAO.NombreEspecie>>() {
                    @Override
                    public void onChanged(List<EspecieDAO.NombreEspecie> nombreEspecies) {

                        /** Evaluamos que tipo de accion se realiza para  seleccionar el dato que se esta actualizando o
                         * simplemente llenar el espiner en caso de querer guardar una nueva mascota*/
                        arrayNombreEspecies.clear();

                        switch (accion) {
                            case Constantes.GUARDAR:
                                for (int i = 0; i < nombreEspecies.size(); i++) {
                                    arrayNombreEspecies.add(i, nombreEspecies.get(i).getNombreEspecie());
                                }
                                break;

                            case Constantes.ACTUALIZAR:
                                /**En caso de ser una actualización se busca el valor que coincide con la lista para
                                 * seleccionarlo y asi asegurarnos que el usuario vea el valor que tenia guardado previamente
                                 * */
                                for (int i = 0; i < nombreEspecies.size(); i++) {
                                    if (nombreEspecies.get(i).getNombreEspecie().equals(especie)) {
                                        postionItemEspecie = i + 1; //Obtenemos el id del item
                                    }
                                    arrayNombreEspecies.add(i, nombreEspecies.get(i).getNombreEspecie());
                                }
                                break;
                        }

                        /**Independientemente si es una actualizacion o un se esta guardando una nueva mascota siempre se agrega
                         * un valor por defecto en la primera posicion del array. Adememas  positionItemEspecie  se utiliza para
                         * seleccionar un elemente en caso de que la accion se guardar el valor de este es de 0*/
                        arrayNombreEspecies.add(0, "Seleccione especie");

                        /**Al utilizar este metodo se ejecuta el llamado a onItemSelected utilizado par determinar, las razas de las
                         * especies
                         * */
                        spiEspecie.setSelection(postionItemEspecie);

                    }//Fin de metodo onChanged
                });



        /** EVENTOS DE LOS COMPONENTES  **/
        agregarEspecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogoEspecie();
            }
        });

        agregarRaza.setOnClickListener(v -> abrirDialogoRaza());

        btnTomarFotos.setOnClickListener(v -> metodosImagenes.goToCamera(CreacionPerfiles.this));

        edtFechaNaciento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtenemos la fecha de la del dia actual
                DatePickerDialog dialogoFecha = new DatePickerDialog(CreacionPerfiles.this, (view, year, month, dayOfMonth) ->
                        edtFechaNaciento.setText(fechaHora.formato(dayOfMonth, month, year)), anio, mes, dia);
                dialogoFecha.show();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /**Aquí usamos el método que creamos para obtener la imágen*/
                Bitmap imagen = ((BitmapDrawable) imgFotoMascota.getDrawable()).getBitmap();
                if(imagen!=null && rutaImagen.isEmpty())
                {
                    String ruta = metodosImagenes.guardarImagen(getApplicationContext(), imagen, imagen);
                }



                boolean validacion = Validacion.fieldsAreNotEmpty(edtNombreMascota.getText().toString(),
                        "perro", "");
                if (validacion) {
                    Toast.makeText(CreacionPerfiles.this, "ESTAN TODOS LOS CAMPOS LLENAMOS", Toast.LENGTH_SHORT).show();
                } else

                    Toast.makeText(CreacionPerfiles.this, "HAY campos vacios", Toast.LENGTH_SHORT).show();
            }
        });


        btnSeleccionarFoto.setOnClickListener((v) -> {
            /**Aquí obtenemos los permisos para entrar a la GALERIA*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) /**En esta línea se verifica el permiso para la versión de android en el dispositivo en tiempo de ejecución*/
            {
                if (ActivityCompat.checkSelfPermission(CreacionPerfiles.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    metodosImagenes.openGallery(CreacionPerfiles.this);
                }
                else
                 {
                    ActivityCompat.requestPermissions(CreacionPerfiles.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MetodosImagenes.REQUEST_PERMISSION_GALLERY);
                 }
            }
            else
            {
                metodosImagenes.openGallery(CreacionPerfiles.this);
            }
        });

        btnTomarFotos.setOnClickListener((v) -> {
//            /**Aquí obtenemos los permisos para USAR la cámara del dispositivo*/
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { /**En esta línea se verifica el permiso para la versión de android en el dispositivo en tiempo de ejecución*/
//                if (ActivityCompat.checkSelfPermission(CreacionPerfiles.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                    metodosImagenes.goToCamera(CreacionPerfiles.this);
//                } else {
//                    ActivityCompat.requestPermissions(CreacionPerfiles.this, new String[]{Manifest.permission.CAMERA}, MetodosImagenes.REQUEST_PERMISSION_CAMERA);
//                }
//            } else {
//                metodosImagenes.goToCamera(CreacionPerfiles.this);
//            }
          metodosImagenes.checkPermissionCamera(CreacionPerfiles.this);
        });
    }//Fin de onCreate

    /**Este metodo recive los valores de respuesta al solicitar los permisos*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /** Se validan los permisos devueltos de la socilicitud y en caso de ser haber sido aceptados por el usuario
         * se envia a la camara o la galeriaa */
        metodosImagenes.validateRequestPermissionCode(requestCode,permissions,grantResults,CreacionPerfiles.this);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**Verificar si los permisos son correctos.
         * En esta parte lo que hacemos es crear la ruta
         * para guardar la imágen*/
        Toast.makeText(this, "Entro "+String.valueOf(requestCode) , Toast.LENGTH_LONG).show();
        switch (requestCode)
        {

            case MetodosImagenes.REQUEST_IMAGE_CAMERA:

             Bitmap bitmap =(Bitmap) data.getExtras().get("data");
             imgFotoMascota.setImageBitmap(bitmap);

            break;
            case MetodosImagenes.REQUEST_IMAGE_GALLERY:

                Uri photo = data.getData();
                imgFotoMascota.setImageURI(photo);


                break;
        }
//        if (requestCode == MetodosImagenes.REQUEST_IMAGE_GALLERY) {
//            if (resultCode == Activity.RESULT_OK && data != null) {
//                /**Obtenemos la ruta de la imagen*/
//                Uri photo = data.getData();
//                imgFotoMascota.setImageURI(photo);
//                Log.i("TAG", "Result: " + photo);
//            } else {
//                Toast.makeText(this, "No seleccionó ninguna foto", Toast.LENGTH_LONG).show();
//            }
//        } else {
//            if (requestCode == MetodosImagenes.REQUEST_IMAGE_CAMERA && resultCode== RESULT_OK) {
//                //Bundle extras = data.getExtras();
////                Bitmap imageBitmap = (Bitmap) extras.get("data");
////                imgFotoMascota.setImageBitmap(imageBitmap);
//                   // imgFotoMascota.setImageURI(Uri.parse(metodosImagenes.getRutaImagen()));
//                    Toast.makeText(getApplicationContext(), metodosImagenes.getRutaImagen(), Toast.LENGTH_LONG).show();
//            }
//        }
    }




    //Abre el dialogo para recibir una nueva especie
    public void abrirDialogoEspecie() {
        ModalDialogoEspecie modalDialogoEspecie = new ModalDialogoEspecie();
        modalDialogoEspecie.show(getSupportFragmentManager(), "especie dialog");
    }

    @Override
    public void applyTextEspecie(String especieMascota) {
        /**
         * Se valida que no esta vacio el campo, en caso de tener valores, este se guarda en la base de datos
         * y tambien se guarda en la variable "especie" para que por medio de ella obtner el id  y mostrar o guardar razas
         * pertenecientes a esa raza
         * **/
        if(!especieMascota.isEmpty())
        {
            especie = especieMascota;
            //Guarda la especie
            instanciaDB.getSpeciesDAO().insertSpecies(new Especie(especieMascota));
        }
    }

    public void abrirDialogoRaza() {
        ModalDialogoRaza modalDialogoRaza = new ModalDialogoRaza();
        modalDialogoRaza.show(getSupportFragmentManager(), "Seleccione una raza");

    }

    @Override
    public void applyTextRaza(String razaMascota) {
      /** Se valida que no este vacio el nombre de raza, si tiene valores se obtiene el valor y se guarda en la base de datos.
       * Tambien por medio de la variabe "especie" se hace una consulta a la base de datos para obtener su id y asi
       * asociarlo la raza a una especie
       **/
        if (!razaMascota.isEmpty())
        {
            instanciaDB.getRazaDAO().insertBreed(new Raza(razaMascota,
            instanciaDB.getSpeciesDAO().getIdSpeciesByName(especie)));

        }

    }



    //Metodos para controlar eventos de los Spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

         switch (parent.getId())
         {
             case R.id.spiEspecie:
                 /**Vericamos si la posicion seleccionada es mayor que cero, ya que el numero 0 es un valor por default
                 y no tiene ningun valor en la base de datos*/
                 if(position>0)
                 {
                       //Desbloqueamos la base el sepinner de razas
                     enableSpinnerAndButton(spiRaza,agregarRaza);
                     /**Establecemos un observador para obtener las razas por especie seleccianada, en esta caso este se encarga de actualizar
                      * cada vez que agreguemos una nueva raza
                      * */
                     instanciaDB.getRazaDAO().getAllBreedsFromSpecie(parent.getSelectedItem().toString()).observe(CreacionPerfiles.this,
                             new Observer<List<RazaDAO.NombreRaza>>() {
                                 @Override
                                 public void onChanged(List<RazaDAO.NombreRaza> nombreRazas) {
                                     arrayNombreRazas.clear();
                                     /**Este metodo esta asociado a un Observer que decta cuando se han cambiado los datos  para actualizar
                                      * de forma asincrona, por tal razan validdamos cuando es guardar  y cuando es actualizar en caso de ser
                                      * "guardar" entonces solo actualiza el spinner raza cuando se selecciona una especie */
                                     if (accion == Constantes.GUARDAR) {
                                         for (RazaDAO.NombreRaza raza : nombreRazas) {
                                             arrayNombreRazas.add(raza.getNombreRaza());
                                         }
                                         arrayNombreRazas.add(0, "Seleccione raza");
                                     }
                                     else if (accion == Constantes.ACTUALIZAR) {
                                         for(int i=0;i<nombreRazas.size();i++)
                                         {
                                             if(nombreRazas.get(i).getNombreRaza().equals(raza))
                                             {
                                                 /**Se obtiene la posición y se le suma 1 porque el 0 es el valor por defecto*/
                                                 postionItemRaza =i+1;
                                             }
                                             arrayNombreRazas.add(i,nombreRazas.get(i).getNombreRaza());
                                         }
                                     }

                                     /** Independientemente si es actualizacion o se esta guardando la opcion por default siempre va e
                                      * existir por eso se coloca al final de las evaluaciones*/
                                     arrayNombreRazas.add(0,"Seleccione Raza");
                                     spiRaza.setSelection(postionItemRaza);
                                     /** Se resetea esta posición con el objetivo que la proxima vez que seleccione una nueva especie y
                                      * no coincida con la raza, el valor por defecto se seleccione*/
                                     postionItemRaza=0;
                                 }
                             });
                     /**
                      * Cada vez que se selecciona una especie se recarga las razas pertenecientes a esa especie, por tanto el primer item seleccionado sea el cero
                      * ya que es el valor por defecto "Seleccione raza", que le indica al usuario que hacer
                      * */

                 }
                 else
                 {
                     spiRaza.setSelection(0);
                     disableSpinnerAndButton(spiRaza,agregarRaza);

                 }



             break;
         }
    }// fin de onItemSelected

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * Con este metodo llenamos los Spinners pasando la lista de valores que vamos agregar y el adaptador
     * */
    private void startSpinnerValues(Spinner spinner, ArrayList<String> valores, ArrayAdapter<String> adapter)
    {
        //Inicializamos el adaptador y lo agregamos al Spinner
        adapter=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,valores);
        spinner.setAdapter(adapter);
    }

     /** Inicializa todos los objetos**/
    private void init()
    {
        btnTomarFotos = findViewById(R.id.imgbtnTomarFotosCP);
        btnSeleccionarFoto = findViewById(R.id.imgbtnBuscarFotosV);
        imgFotoMascota = findViewById(R.id.imgCreacionPerfiles);
        agregarEspecie = findViewById(R.id.imgbtnAgregarNuevaEspecie2);
        agregarRaza = findViewById(R.id.imgbtnAgregarNuevaRaza);
        spiEspecie = findViewById(R.id.spiEspecie);
        spiRaza = findViewById(R.id.spiRaza);
        edtFechaNaciento=findViewById(R.id.edtFechaNacimiento);
        rbtnHembra=findViewById(R.id.rbHembra);
        rbtnMacho = findViewById(R.id.rbMacho);
        rgpGrupoGenero=findViewById(R.id.rbgGrupoGenero);
        edtNombreMascota= findViewById(R.id.edtNombreMascota);
        rbtnMacho.setSelected(true);
        //Creamos una instancia para obtener fechas y horas
        fechaHora = new DateTime();
        formatoFecha= new SimpleDateFormat("dd/MM/yyyy");
        metodosImagenes =new MetodosImagenes();
        btnGuardar = findViewById(R.id.btnGuardarCP);
        especie = "";


        arrayNombreRazas = new ArrayList<>();
        arrayNombreEspecies = new ArrayList<>();

        /** Se obtiene una instancia de la base de datos*/
        instanciaDB = SingletonDB.getDatabase(this);


        /** Se asigna al Spinner de raza un evento de escucha, un array con la list**/
        spiRaza.setOnItemSelectedListener(this);
        arrayNombreRazas.add("Seleccione raza");
        startSpinnerValues(spiRaza,arrayNombreRazas,adaptadorRaza);

        /** Se asigna al Spinner de especie un evento de escucha, ademas se le agrega un adaptador con  **/
        spiEspecie.setOnItemSelectedListener(this);
        arrayNombreEspecies.add("Seleccione especie");
        startSpinnerValues(spiEspecie,arrayNombreEspecies,adaptadorEspecie);


        /**Obtemos datos del Intent y determinamos si es una actualizacion o una insercion, estos valores se optienen con el */
        Intent intentValues= getIntent();
        accion=intentValues.getIntExtra(Constantes.TAG_ACCION,Constantes.ACTUALIZAR);
        if(accion==Constantes.GUARDAR){
            disableSpinnerAndButton(spiRaza,agregarRaza);

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
            String [] fecha=fecha1.split("-");;
            dia=Integer.parseInt(fecha[0]);
            mes=Integer.parseInt(fecha[1])-1;
            anio= Integer.parseInt(fecha[2]);

        }
        edtFechaNaciento.setText(fechaHora.formato(dia,mes,anio));
        accion=Constantes.ACTUALIZAR;
        especie="Perro";
        raza="Pitbull";
    }




    /**
     * disableSpinnerAndButton() y enableSpinnerAndButton
     * Estos metodos tienen la función de deshabilitar y deshabilitar el spinner y el boton de agregar razas sino
     * se ha seleccionado una especie a la cual pertenezcan las razas
     * */
    private void disableSpinnerAndButton(Spinner spinner, ImageButton button)
    {
        button.setEnabled(false);
        spinner.setEnabled(false);
    }


    private void enableSpinnerAndButton(Spinner spinner, ImageButton button)
    {
        button.setEnabled(true);
        spinner.setEnabled(true);
    }

    /**Con estos metodos llamamo*/
}