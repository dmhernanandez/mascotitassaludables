package hn.healthypets.proyecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreacionPerfiles extends AppCompatActivity {
    ImageView foto;
    Button btnTomarFotos;

    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final int REQUEST_IMAGE_CAMERA = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_perfiles);

        foto = findViewById(R.id.fotoMascota);
        btnTomarFotos = findViewById(R.id.btnTomarFotos);

        btnTomarFotos.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(CreacionPerfiles.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreacionPerfiles.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    irCamara();
                } else {
                    ActivityCompat.requestPermissions(CreacionPerfiles.this,
                            new String[]{Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CAMERA);
                }
            } else {
                irCamara();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                irCamara();
            } else {
                Toast.makeText(this, "Se nececitan todos los permisos", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAMERA) {
//            if (resultCode == Activity.RESULT_OK) {
//                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                foto.setImageBitmap(bitmap);
//                Log.i("TAG", "Result =>" + bitmap);
//
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                foto.setImageBitmap(bitmap);
                Log.i("TAG", "Result=>" + bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void irCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAMERA);
        }
    }
// INICIO DE PROCESO DE ALMACENAMIENTO EN DISPOSITIVO

    String currentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Backup_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.i("ruta", currentPhotoPath);
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

//    public void irCamara() {
//        Log.i("prueba", "entro a tomar foto");
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                Log.i("error", ex.getMessage());
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Log.i("error", "entro al if");
//                Uri photoURI = FileProvider.getUriForFile(CreacionPerfiles.this,
//                        "hn.healthypets.proyecto/files/Pictures",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                Log.i("prueba", "salio de tomar foto");
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAMERA);
//            }
//        }
//    }

//    static final int REQUEST_IMAGE_CAPTURE = 1;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            foto.setImageBitmap(imageBitmap);
//        }
//    }
}