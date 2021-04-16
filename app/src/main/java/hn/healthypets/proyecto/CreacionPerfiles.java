package hn.healthypets.proyecto;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import hn.healthypets.proyecto.Utilidades.DateTime;
import hn.healthypets.proyecto.Utilidades.Validacion;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.Especie;
import hn.healthypets.proyecto.database.Entidades.Mascota;
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
    private RadioGroup rbgGrupoGenero;
    private Button btnGuardar;
    private MetodosImagenes metodosImagenes;
    private static ArrayList<String> arrayNombreRazas;
    private static ArrayList<String> arrayNombreEspecies;
    private ArrayAdapter<String> adaptadorEspecie;
    private ArrayAdapter<String> adaptadorRaza;
    private SimpleDateFormat formatoFecha;
    private EditText edtNumeroChip;
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
    private String especie = "";
    private String raza;
    private Bitmap bitmapImage;
 /**Esta variable se utiliza para almacenar el valor que se devuelve de la solicitud de los permisos, esto quiere decir
  * se coloca el nombre de permissionStorage ya que el unico momento en que se utilizara sera para validar si hay permisos de almacenamiento
  *
  * */
    private boolean permissionStorgare;
    /*Creamos un intent para tener acceso a los datos cuando sea una actualizacion a travez de los getExtras*/
    Intent intentValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_perfiles);
        //Inicializamos todos los elementos
        init();

        instanciaDB.getSpeciesDAO().insertSpecies(new Especie("Perro"));
        instanciaDB.getSpeciesDAO().insertSpecies(new Especie("Gato"));

        instanciaDB.getRazaDAO().insertBreeds(
                new Raza("Desconocida", instanciaDB.getSpeciesDAO().getIdSpeciesByName("Gato"))
                , new Raza("Angora", instanciaDB.getSpeciesDAO().getIdSpeciesByName("Gato")),
                new Raza("Sphynx", instanciaDB.getSpeciesDAO().getIdSpeciesByName("Gato"))
        );

        instanciaDB.getRazaDAO().insertBreeds(
                new Raza("Mestizo", instanciaDB.getSpeciesDAO().getIdSpeciesByName("Perro"))
                , new Raza("Pitbull", instanciaDB.getSpeciesDAO().getIdSpeciesByName("Perro")),
                new Raza("Boxer", instanciaDB.getSpeciesDAO().getIdSpeciesByName("Perro"))
        );

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

        edtFechaNaciento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utilizamos este metodo par obtenener los datos
                DatePickerDialog dialogoFecha = new DatePickerDialog(CreacionPerfiles.this, (view, year, month, dayOfMonth) ->
                        edtFechaNaciento.setText(fechaHora.formatoFecha(dayOfMonth, month, year)), anio, mes, dia);
                dialogoFecha.show();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Valida que los campos esten llenos
             boolean fieldsFill = Validacion.fieldsAreNotEmpty(
                     edtNombreMascota.getText().toString(),
                      edtFechaNaciento.getText().toString());
             //Valida que por lo menos un radio button este seleccionado
              boolean radioButtonIsSelected=rbtnHembra.isSelected() || rbtnMacho.isSelected()?true:false;
                if (fieldsFill && spiEspecie.getSelectedItemId()>0 && radioButtonIsSelected)
                {
                    String nombreImagen="";
                    String genero = rbtnHembra.isChecked()?"Hembra":"Macho";
                    Mascota mascota;

                    /** Para las versiones menores que 4.4 se solicta el permiso, en caso que nuestro telefono se mayor
                     * pasara de una solo vez almacenar*/
                   if( metodosImagenes.checkPermissionStorage(CreacionPerfiles.this) || permissionStorgare)
                   {

                       switch (accion)
                       {
                           case Constantes.GUARDAR:
                               /** Validamos que no haya una imagen vacia si esta vacia entonces se
                                * la ruta se envia vacia, sino se guarda y se retorna el nombre de la imagen*/
                               if(bitmapImage!=null)
                               {
                                   nombreImagen=metodosImagenes.savePhoto(CreacionPerfiles.this,
                                           bitmapImage,
                                           MetodosImagenes.PET_FOLDER,
                                           metodosImagenes.generarNombre("img_pet_")+".jpeg");
                               }
                               mascota=new Mascota(
                                       0,
                                       edtNombreMascota.getText().toString(),
                                       edtFechaNaciento.getText().toString(),
                                       instanciaDB.getGeneroDAO().getIdGenderByName(genero),
                                       instanciaDB.getRazaDAO().getIdRazaByName(spiRaza.getSelectedItem().toString()),
                                       instanciaDB.getSpeciesDAO().getIdSpeciesByName(spiEspecie.getSelectedItem().toString()),
                                               nombreImagen,
                                               edtNumeroChip.getText().toString()
                               );
                             instanciaDB.getMascotaDAO().insertNewPet(mascota);

                              Toast.makeText(CreacionPerfiles.this,"Información guardada exitosamente ;)",Toast.LENGTH_LONG).show();
                               finish();
                              break;
                           case Constantes.ACTUALIZAR:
                               /**
                                * Si la imagen del bitmap esta vacia en y se esta actualizando siginifica que no se selecciono
                                * una nueva imagen por lo tanto la ruta que se mandara sera la misma que tenia anteriormente,
                                * estos se logra atravez de el intent que se envio al iniciar la actividad
                                * */
                               nombreImagen=intentValues.getStringExtra(Constantes.TAG_IMG_PATH);
                               if(bitmapImage!=null)
                               {
                                   /** Si se selecciono una foto nueva entonces mandamos a llamar al metodo savePhoto para que
                                    * guarde una nueva foto, pero si la ruta de la foto anterior esta vacia se genera un nuevo
                                    * nombre y si no se envia el nombre de la foto actual para sustituir el archivo pero que con
                                    * conserve el mismo nombre*/
                                   if(nombreImagen.equals(""))
                                   {
                                       nombreImagen=metodosImagenes.savePhoto(CreacionPerfiles.this,
                                               bitmapImage,
                                               MetodosImagenes.PET_FOLDER,
                                               metodosImagenes.generarNombre("img_pet_")+".jpeg");
                                   }
                                   else
                                   {
                                       nombreImagen=metodosImagenes.savePhoto(CreacionPerfiles.this,
                                               bitmapImage,
                                               MetodosImagenes.PET_FOLDER,
                                               nombreImagen);
                                   }

                               }
                               mascota=new Mascota(
                                       intentValues.getIntExtra(Constantes.TAG_ID,Constantes.DEFAULT),
                                       edtNombreMascota.getText().toString(),
                                       edtFechaNaciento.getText().toString(),
                                       instanciaDB.getGeneroDAO().getIdGenderByName(genero),
                                       instanciaDB.getRazaDAO().getIdRazaByName(spiRaza.getSelectedItem().toString()),
                                       instanciaDB.getSpeciesDAO().getIdSpeciesByName(spiEspecie.getSelectedItem().toString()),
                                       nombreImagen,
                                       edtNumeroChip.getText().toString()
                               );
                               instanciaDB.getMascotaDAO().updatePet(mascota);

                               Toast.makeText(CreacionPerfiles.this,"Información actualizada exitosamente ;)",Toast.LENGTH_LONG).show();
                               finish();
                               break;
                       }
                   }
                   else
                   {
                       Toast.makeText(CreacionPerfiles.this, "Permisos", Toast.LENGTH_SHORT).show();

                       metodosImagenes.requestPermissionFromUser(
                               CreacionPerfiles.this,
                                  Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                  MetodosImagenes.REQUEST_PERMISION_WRITE_STORAGE);
                   }

                }
                else
                {
                    Snackbar.make(v,
                            "Debe llenar todos los campos obligatorios",
                            Snackbar.LENGTH_LONG).show();
                }

            }
        });


        btnSeleccionarFoto.setOnClickListener((v) -> {
            /** Se valida que tenga permisos para acceder a la galeria de ser asi lo envia a la galeria*/
            if(metodosImagenes.checkPermissionGallery(CreacionPerfiles.this))
            {
                metodosImagenes.openGallery(CreacionPerfiles.this);
            }
            /**De lo contrario los solicita y luego se valida en el metodo on onRequestPermissonCode*/
            else
            {
                metodosImagenes.requestPermissionFromUser(
                        CreacionPerfiles.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        MetodosImagenes.REQUEST_PERMISSION_GALLERY
                );
            }

        });

        btnTomarFotos.setOnClickListener((v) -> {
            /** Se valida que tenga permisos para tomar fotos de ser asi lo envia a la camara*/
         if(metodosImagenes.checkPermissionCamera(CreacionPerfiles.this))
         {
             metodosImagenes.goToCamera(CreacionPerfiles.this);
         }
         /**De lo contrario los solicita y luego se valida en el metodo on onRequestPermissonCode*/
         else
         {
             metodosImagenes.requestPermissionFromUser(
                     CreacionPerfiles.this,
                     Manifest.permission.CAMERA,
                     MetodosImagenes.REQUEST_PERMISSION_CAMERA
             );
         }

        });
    }//Fin de onCreate

    /**Este metodo recive los valores de respuesta al solicitar los permisos*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /** Se validan los permisos devueltos de la socilicitud y en caso de ser haber sido aceptados por el usuario
         *  el metodo validateRequestPermissionCode envia a la actividad solicitada, de acuerdo con el identificador
         *  tambie se alamcena el valor devuelto ya que se utilizara para determinar si hay permisos de almacenamiento,
         *  los permisos de Camara y Galeria no hacen uso de esta variable unicamente*/
       permissionStorgare=  metodosImagenes.validateRequestPermissionCode(requestCode,permissions,grantResults,CreacionPerfiles.this);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                    imgFotoMascota.setImageBitmap(bitmapImage);
                }


            break;
            case MetodosImagenes.REQUEST_IMAGE_GALLERY:

                /** Primero convertimos el recurso pasado obtennido por medio de un URI*/
                if(resultCode == RESULT_OK && data != null)
                {
                    bitmapImage= metodosImagenes.getBitmapFromUri(this,data.getData());
                    imgFotoMascota.setImageBitmap(bitmapImage);

                }


                break;
        }


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
            /*Obtenemos el nombre se la especie que esta seleccionada en este momento para optener su id*/
            instanciaDB.getRazaDAO().insertBreed(new Raza(razaMascota,
            instanciaDB.getSpeciesDAO().getIdSpeciesByName(spiEspecie.getSelectedItem().toString())));

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
                                      * no tenga razas relacionadas en la base de datos, el valor por defecto se seleccione*/
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
                     /**Se selecciona el valor po defecto*/
                     spiRaza.setSelection(postionItemRaza);
                     disableSpinnerAndButton(spiRaza,agregarRaza);

                 }



             break;
         }
    }// fin de onItemSelected

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * Con este metodo llenamos los Spinners pasando la lista de valores que vamos agregar y el adaptador que los
     * y el adaptador
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
        rbgGrupoGenero =findViewById(R.id.rbgGrupoGenero);
        edtNombreMascota= findViewById(R.id.edtNombreMascota);
        rbtnMacho.setSelected(true);
        metodosImagenes =new MetodosImagenes(this);
        btnGuardar = findViewById(R.id.btnGuardarCP);
        edtNumeroChip =findViewById(R.id.edtNumeroChip);
        especie = "";
        fechaHora = new DateTime();

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
        intentValues= getIntent();
        accion=intentValues.getIntExtra(Constantes.TAG_ACCION,Constantes.GUARDAR);
        if(accion==Constantes.GUARDAR){
            disableSpinnerAndButton(spiRaza,agregarRaza);
             //Se carga la fecha por defecto que es la fecha actual
            dia=DateTime.diaDelMes;
            mes=DateTime.mes;
            anio=DateTime.anio;
            /**Asignamos una foto de perfil por defecto*/
            Glide.with(this)
                    .load(R.drawable.default_credencial)
                    .into(imgFotoMascota);
        }

        /** Acciones a realizar si se la opcion es actualizar*/
        else if(accion==Constantes.ACTUALIZAR)
        {

            /** Si es una actualización se debe parsear la fecha guadarda previamente para colocarla en variables de fecha
             * para dar formato y tomarla como fecha de refeencia*/
            String [] fecha=intentValues.getStringExtra(Constantes.TAG_FECHA_NACIENTO).split("-");
            dia=Integer.parseInt(fecha[0]);
            mes= Integer.parseInt(fecha[1]);
            anio=Integer.parseInt(fecha[2]);
            /**Se valida si al actualizar tenia una foto, ser asi se muestra, de lo contrario se carga una imagen por defecto*/
            if(!intentValues.getStringExtra(Constantes.TAG_IMG_PATH).equals(""))
            {
                File filePhoto = new File(metodosImagenes.getRootPath()+"/"
                        +MetodosImagenes.PET_FOLDER+"/"+
                        intentValues.getStringExtra(Constantes.TAG_IMG_PATH));
                Glide.with(this)
                        .load(filePhoto)
                        .into(imgFotoMascota);
            }
            else
            {
                /**Asignamos una foto de perfil por defecto*/
                Glide.with(this)
                        .load(R.drawable.default_credencial)
                        .into(imgFotoMascota);
            }

            /** Se optiene el nombre del genero y se valida para seleccionar el radio button Correcto
             * */
            String genero = instanciaDB.getGeneroDAO().
                    getGenderById(intentValues.getIntExtra(Constantes.TAG_GENERO,Constantes.DEFAULT));
            if (genero.equals("Hembra")) {
                rbgGrupoGenero.check(R.id.rbHembra);
            } else {
                rbgGrupoGenero.check(R.id.rbMacho);
            }

            edtNombreMascota.setText(intentValues.getStringExtra(Constantes.TAG_NOMBRE));
            edtNumeroChip.setText(intentValues.getStringExtra(Constantes.TAG_NUMERO_CHIP));

            /** Se optiene en nombre de la especie para seleccionarlo en el Spinner de especie, ya que este es
             * el valor guardado por el usuario*/
            raza= instanciaDB.getRazaDAO()
                    .getNameRazaById(intentValues.getIntExtra(Constantes.TAG_RAZA,Constantes.DEFAULT));

            /** Se obtiene el nombre de la raza para seleccionarlo en el Spinner*/
            especie = instanciaDB.getSpeciesDAO()
                    .getNameSpecieById(intentValues.getIntExtra(Constantes.TAG_ESPECIE,Constantes.DEFAULT));

        }
        edtFechaNaciento.setText(fechaHora.formatoFecha(dia,mes,anio));

    }

    /**
     * disableSpinnerAndButton() y enableSpinnerAndButton()
     * Estos metodos tienen la función de deshabilitar y habilitar el spinner y el boton de "agregar razas" sino
     * se ha seleccionado una especie a la cual pertenezcan las razas.
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

    /** intent */
    void goHome()
    {
//        Intent intent = new Intent
    }
}