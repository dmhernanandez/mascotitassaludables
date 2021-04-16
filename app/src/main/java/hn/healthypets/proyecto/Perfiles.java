package hn.healthypets.proyecto;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

public class Perfiles extends AppCompatActivity {

    private ImageView imgFotoMascota;
    private ImageButton btnTomarFotos;
    private ImageButton imgbtnBuscarFotos;
    private EditText edtNombre;
    private EditText edtFechaNaciento;
    private EditText edtNumeroChip;
    private RadioButton rbtnMacho;
    private RadioButton rbtnHembra;
    private Spinner spiEspecie;
    private Spinner spiRaza;
    private ImageButton agregarEspecie;
    private ImageButton agregarRaza;
    private RadioGroup rgpGrupoGenero;
    private Button btnListo;
    private Button btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfiles);
        ImageView poster = findViewById(R.id.imgvVacuna);
        btnTomarFotos = findViewById(R.id.imgbtnTomarFotosCP);
        imgFotoMascota = findViewById(R.id.imgCreacionPerfiles);
        agregarEspecie = findViewById(R.id.imgbtnAgregarNuevaEspecie2);
        agregarRaza = findViewById(R.id.imgbtnAgregarNuevaRaza);
        spiEspecie = findViewById(R.id.spiEspecie);
        spiRaza = findViewById(R.id.spiRaza);
        edtFechaNaciento = findViewById(R.id.edtFechaNacimiento);
        rbtnHembra = findViewById(R.id.rbHembra);
        rbtnMacho = findViewById(R.id.rbMacho);
        btnListo = findViewById(R.id.btnGuardarCP);
        rgpGrupoGenero = findViewById(R.id.rbgGrupoGenero);

        Glide.with(this)
                .load(R.drawable.golden2)
                .transform(new CircleCrop())
                .into(poster);
    }
}