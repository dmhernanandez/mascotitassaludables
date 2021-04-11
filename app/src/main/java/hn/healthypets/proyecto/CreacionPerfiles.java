:package hn.healthypets.proyecto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import hn.healthypets.proyecto.Utilidades.DateTime;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.Especie;
import hn.healthypets.proyecto.database.Entidades.Raza;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.database.dao.EspecieDAO;
import hn.healthypets.proyecto.database.dao.RazaDAO;
import hn.healthypets.proyecto.modelos_mascotitas_saludables.Constantes;

public class CreacionPerfiles extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ModalDialogoEspecie.ModalDialogoEspecieListener, ModalDialogoRaza.ModalDialogoRazaListener
{

    private ImageButton btnTomarFotos;
    private ImageView imgFotoMascota;
    private String rutaImagen;
    private TextView textViewEspecie;
    private ImageButton agregarEspecie;
    private Spinner spiEspecie;
    private Spinner spiRaza;
    private EditText edtFechaNaciento;
    private ImageButton agregarRaza;
    private DataBase instanciaDB;
    private DateTime fechaHora;
    private RadioButton rbtnMacho;
    private RadioButton rbtnHembra;
    private RadioGroup rgpGrupoGenero;
    private static ArrayList<String> arrayNombreRazas;
    private static ArrayList<String> arrayNombreEspecies;
    private ArrayAdapter<String> adaptadorEspecie;
    private ArrayAdapter<String> adaptadorRaza;
    private SimpleDateFormat  formatoFecha;
    /**Estas variables de utilizan para determinar la posicion en la que se encuentra un elemento cuando se esta actualizando
     * para asi seleccionarlo
     * */
    private int postionItemEspecie;
    private int postionItemRaza;

    /**Se utilizan para validar que tipo de accion se realizara en la actividad, estos datos se reciben del intent**/
    private  int accion;
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

                      switch (accion)
                      {
                          case Constantes.GUARDAR:

                              for(int i=0;i<nombreEspecies.size();i++)
                              {
                                  arrayNombreEspecies.add(i,nombreEspecies.get(i).getNombreEspecie());
                              }
                          break;
                          case Constantes.ACTUALIZAR:
                              /**En caso de ser una actualización se busca el valor que coincide con la lista para
                               * seleccionarlo y asi asegurarnos que el usuario vea el valor que tenia guardado previamente*/
                              for(int i=0;i<nombreEspecies.size();i++)
                              {
                                  if(nombreEspecies.get(i).getNombreEspecie().equals(especie))
                                  {
                                      postionItemEspecie =i+1;
                                  }
                                  arrayNombreEspecies.add(i,nombreEspecies.get(i).getNombreEspecie());
                              }
                              break;

                      }

                      /**Independientemente si es una actualizacion o un se esta guardando una nueva mascota siempre se agrega
                       * un valor por defecto en la primera posicion del array. Adememas  positionItemEspecie  se utiliza para
                       * seleccionar un elemente en caso de que la accion se guardar el valor de este es de 0*/
                        arrayNombreEspecies.add(0,"Seleccione especie");

                        spiEspecie.setSelection(postionItemEspecie);

                    }
                });

//        instanciaDB.getRazaDAO().getAllBreedsFromSpecie(especie).observe(CreacionPerfiles.this,
//                new Observer<List<RazaDAO.NombreRaza>>() {
//                    @Override
//                    public void onChanged(List<RazaDAO.NombreRaza> nombreRazas) {
//                        arrayNombreRazas.clear();
//                        for(RazaDAO.NombreRaza raza : nombreRazas)
//                        {
//                            arrayNombreRazas.add(raza.getNombreRaza());
//                        }
//                        arrayNombreRazas.add(0,"Seleccione raza");
//                    }
//                });



         //Hinabilitamos el Spinner de raza ya que esta depende de la especie y mientras no hay una seleccionada no tiene sentido estar habilidado
        // disableSpinnerAndButton(spiRaza,agregarRaza);



        /** EVENTOS DE LOS COMPONENTES  **/
        agregarEspecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 abrirDialogoEspecie();
            }
        });

        agregarRaza.setOnClickListener(v -> abrirDialogoRaza());

        btnTomarFotos.setOnClickListener(v -> abrirCamara());

        edtFechaNaciento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //Obtenemos la fecha de la del dia actual
                DatePickerDialog dialogoFecha= new DatePickerDialog(CreacionPerfiles.this, (view, year, month, dayOfMonth) ->
                        edtFechaNaciento.setText(fechaHora.formato(dayOfMonth,month,year)),DateTime.anio,DateTime.mes,DateTime.diaDelMes);
                dialogoFecha.show();
            }
        });




    }//Fin del metodo onCreate


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
//            Bundle extras = data.getExtras();
//            Bitmap imgBitmap = (Bitmap)extras.get("data")
            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            imgFotoMascota.setImageBitmap(imgBitmap);
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
        imgFotoMascota = findViewById(R.id.imgCreacionPerfiles);
        agregarEspecie = findViewById(R.id.imgbtnAgregarNuevaEspecie2);
        agregarRaza = findViewById(R.id.imgbtnAgregarNuevaRaza);
        spiEspecie = findViewById(R.id.spiEspecie);
        spiRaza = findViewById(R.id.spiRaza);
        edtFechaNaciento=findViewById(R.id.edtFechaNacimiento);
        rbtnHembra=findViewById(R.id.rbHembra);
        rbtnMacho = findViewById(R.id.rbMacho);
        rgpGrupoGenero=findViewById(R.id.rbgGrupoGenero);
        rbtnMacho.setSelected(true);
        //Creamos una instancia para obtener fechas y horas
        fechaHora = new DateTime();
        formatoFecha= new SimpleDateFormat("dd/MM/yyyy");
        edtFechaNaciento.setText(formatoFecha.format(new Date()));
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
        accion=intentValues.getIntExtra(Constantes.TAG_ACCION,Constantes.GUARDAR);
        if(accion==Constantes.GUARDAR){
            disableSpinnerAndButton(spiRaza,agregarRaza);
        }
        else if(accion==Constantes.ACTUALIZAR)
        {

        }
        accion=Constantes.ACTUALIZAR;
        especie="Perro";
        raza="Pitbull";
    }


    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
        rutaImagen = imagen.getAbsolutePath();

        Log.i("ruta",rutaImagen);
        Log.i("ruta",imagen.getCanonicalPath());
        return imagen;
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