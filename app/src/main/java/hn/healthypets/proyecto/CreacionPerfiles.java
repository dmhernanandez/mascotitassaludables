package hn.healthypets.proyecto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.Especie;
import hn.healthypets.proyecto.database.Entidades.Raza;
import hn.healthypets.proyecto.database.SingletonDB;
import hn.healthypets.proyecto.database.dao.EspecieDAO;
import hn.healthypets.proyecto.database.dao.RazaDAO;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreacionPerfiles extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ModalDialogoEspecie.ModalDialogoEspecieListener, ModalDialogoRaza.ModalDialogoRazaListener {

    ImageButton btnTomarFotos;
    ImageView imgFotoMascota;
    String rutaImagen;
    String especie;
    TextView textViewEspecie;
    ImageButton agregarEspecie;

    Spinner spiEspecie;
    Spinner spiRaza;

    TextView textViewRaza;
    ImageButton agregarRaza;
    DataBase instanciaDB;
    private ArrayList<String> arrayNombreRazas;
    private ArrayList<String> arrayNombreEspecies;
    private ArrayAdapter<String> adaptadorEspecie;
    private ArrayAdapter<String> adaptadorRaza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_perfiles);
        btnTomarFotos = findViewById(R.id.imgbtnTomarFotosCP);
        imgFotoMascota = findViewById(R.id.imgCreacionPerfiles);
        agregarEspecie = findViewById(R.id.imgbtnAgregarNuevaEspecie2);
        agregarRaza = findViewById(R.id.imgbtnAgregarNuevaRaza);
        spiEspecie = findViewById(R.id.spiEspecie);
        spiRaza = findViewById(R.id.spiRaza);

        especie = "";
         arrayNombreRazas = new ArrayList<>();
         arrayNombreEspecies = new ArrayList<>();

         arrayNombreRazas.add("Seleccione raza");
        disableSpinnerAndButton(spiRaza,agregarRaza);
    //    adaptadorRaza = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,nombreRazas);
       // spiRaza.setAdapter(adaptadorRaza);

        /** ----- Spinner de especie ----- **/
        spiEspecie.setOnItemSelectedListener(this);
        //Obtenemos una instancia de la base de datos
        instanciaDB = SingletonDB.getDatabase(this);

        /*Se obtiene una arreglo de todas las especies de la base de datos y se guarda el nombre en un arreglo*/
        EspecieDAO.NombreEspecie[] especieNombres = instanciaDB.getSpeciesDAO().getAllNameSpecies();


        //Cuando se agregan los nombres de las especies se empiezan agregar desde la posición 1 y que la  0 es ocupada por el valor default
        for (int i = 0; i < especieNombres.length; i++)
             arrayNombreEspecies.add(especieNombres[i].getNombreEspecie());

        //Iniciamos el adaptador y pasamos la lista de elementos
         startSpinnerValues(spiEspecie,arrayNombreEspecies,adaptadorEspecie);
         spiRaza.setOnItemSelectedListener(this);




        agregarEspecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogoEspecie();
            }
        });

        agregarRaza.setOnClickListener(v -> abrirDialogoRaza());

        btnTomarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });
    }

    //Abre el dialogo para recibir una nueva especie
    public void abrirDialogoEspecie() {
        ModalDialogoEspecie modalDialogoEspecie = new ModalDialogoEspecie();
        modalDialogoEspecie.show(getSupportFragmentManager(), "especie dialog");
    }

    @Override
    public void applyTextEspecie(String especieMascota) {
        //Se guarda la especie de la mascota que envio el usuario
        especie = especieMascota;
        instanciaDB.getSpeciesDAO().insertSpecies(new Especie(especieMascota));
        //textViewEspecie.setText(especieMascota);
    }

    public void abrirDialogoRaza() {
        ModalDialogoRaza modalDialogoRaza = new ModalDialogoRaza();
        modalDialogoRaza.show(getSupportFragmentManager(), "raza dialogo");
    }

    @Override
    public void applyTextRaza(String razaMascota) {
        //Se guarda la especie de la mascota que envio el usuario
        if (!especie.isEmpty())
            instanciaDB.getRazaDAO().insertBreed(new Raza(razaMascota,
                    instanciaDB.getSpeciesDAO().getIdSpeciesByName(especie)));
//        textViewRaza.setText(razaMascota);
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

    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }

    //Metodos para controlar eventos de los Spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     //   Toast.makeText(this, spiEspecie.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

         switch (parent.getId())
         {
             case R.id.spiEspecie:
                 //Vericamos si la posicion seleccionada es mayor que cero, ya que el numero 0 es un valor por default
                 //y no tiene ningun valor en la base de datos
                 if(position>0)
                 {

                     //Desbloqueamos la base el sepinner de razas
                     enableSpinnerAndButton(spiRaza,agregarRaza);
                     List<RazaDAO.NombreRaza> razasPorEspecie = instanciaDB.getRazaDAO().getAllBreedsFromSpecie(parent.getSelectedItem().toString());
                     String [] listaRazas = new String[razasPorEspecie.size()];

                     for (int i = 0; i < razasPorEspecie.size(); i++) {
                      listaRazas[i]=razasPorEspecie.get(i).toString();
                     // adaptadorRaza. add(listaRazas[i]=razasPorEspecie.get(i).toString());
                     }
                      adaptadorRaza.addAll(listaRazas);
                     adaptadorRaza.notifyDataSetChanged();

             //  startSpinnerValues(spiRaza,listaRazas,adaptadorRaza);

                 }
                 else
                     disableSpinnerAndButton(spiRaza,agregarRaza);
             break;
             case R.id.spiRaza:
                 Toast.makeText(this,parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
             break;
         }



    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    /**
     * Con este metodo llenamos los Spinners pasando la lista de valores que vamos agregar y el adaptador
     * */
    private void startSpinnerValues(Spinner spinner, ArrayList<String> valores, ArrayAdapter<String> adapter)
    {
        //Inicializamos el adaptador y lo agregamos al Spinner
        adapter=new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,valores);
        spinner.setAdapter(adapter);
    }

}