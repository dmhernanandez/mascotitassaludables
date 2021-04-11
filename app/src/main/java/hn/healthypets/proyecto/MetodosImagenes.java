package hn.healthypets.proyecto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MetodosImagenes {
    public static final int REQUEST_IMAGE_GALLERY = 101;
    public static final int REQUEST_IMAGE_CAMERA = 103;

    private String rutaImagen;

    /**
     * Metodo para poder accesar a la galeria del dispositivo
     */
    public void openGallery(Activity activity) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT); /**Con ACTION_GET_CONTENT, obtenemos la foto de la galeria*/
        galleryIntent.setType("image/*");
        activity.startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
    }

    /**
     * Metodo para poder accesar a la camara del dispositivo
     */
    public void goToCamera(Activity activity) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); /**ACTION_IMAGE_CAPTURE, es como una API que nos dibujara la interfaz, para acceder a la camara*/
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = crearImagen(activity);
            } catch (IOException ex) {
                Log.e("Error", ex.toString());
            }
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(
                        activity,
                        "hn.healthypets.proyecto.fileprovider",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                activity.startActivityForResult(cameraIntent, REQUEST_IMAGE_CAMERA);
            }
        }
    }

    /**
     * Metodo para crear el archivo temporal de la camara,
     * para luego ser guardada en el dispositivo
     */
    public File crearImagen(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss", Locale.getDefault()).format(new Date());
        String imgFileName = "IMG_" + timeStamp + "_"; //IMG_202104101111
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imgFileName,
                ".jpg",
                storageDir
        );
        rutaImagen = image.getAbsolutePath();
        return image;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }
}