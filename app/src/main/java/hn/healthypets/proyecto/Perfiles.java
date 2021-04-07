package hn.healthypets.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

public class Perfiles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfiles);
        ImageView poster =findViewById(R.id.imgvMascotaPerfil);

        Glide.with(this)
                .load(R.drawable.golden2)
                .transform(new CircleCrop())
                .into(poster);
    }
}