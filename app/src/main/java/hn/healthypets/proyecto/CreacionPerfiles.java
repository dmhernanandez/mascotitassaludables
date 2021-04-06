package hn.healthypets.proyecto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import hn.healthypets.proyecto.database.DataBase;
import hn.healthypets.proyecto.database.Entidades.Especie;
import hn.healthypets.proyecto.database.Entidades.Raza;
import hn.healthypets.proyecto.database.SingletonDB;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CreacionPerfiles extends AppCompatActivity implements ModalDialogoEspecie.ModalDialogoEspecieListener, ModalDialogoRaza.ModalDialogoRazaListener {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_perfiles);
        btnTomarFotos = findViewById(R.id.imgbtnTomarFotosCP);
        imgFotoMascota = findViewById(R.id.imgCreacionPerfiles);
        textViewEspecie = findViewById(R.id.txvNombreEspecie);
        agregarEspecie = findViewById(R.id.imgbtnAgregarNuevaEspecie2);
        textViewRaza = findViewById(R.id.txvNombreRaza);
        agregarRaza = findViewById(R.id.imgbtnAgregarNuevaRaza);
        spiEspecie = findViewById(R.id.spiEspecie);
        spiRaza = findViewById(R.id.spiRaza);

        especie="";

        //Obtenemos una instancia de la base de datos
        instanciaDB= SingletonDB.getDatabase(this);

        //Se obtiene una lista de todas las especies de la base de datos y se guarda el nombre en un arreglo
        List<Especie> especies= instanciaDB.getSpeciesDAO().getAllSpecies();
        String listaEspecies[] = new String[especies.size()];
        for(int i=0;i<especies.size();i++)
             listaEspecies[i]=especies.get(i).getEspecieNombre();

        //Agregamos al adaptador la lista de especies
        spiEspecie.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaEspecies));
        agregarEspecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogoEspecie();
            }
        });

        agregarRaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogoRaza();
            }
        });

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
        especie=especieMascota;
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
        if(!especie.isEmpty())
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
}