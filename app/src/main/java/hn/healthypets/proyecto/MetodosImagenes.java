package hn.healthypets.proyecto;
/**
 * Con esta Clase lo que hacemos es establecer todas las funciones relacionadas
 * con CAMARA y GALERIA del dispositivo
 */

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.core.content.FileProvider;

public class MetodosImagenes {
    public static final int REQUEST_PERMISSION_CODE = 100;
    public static final int REQUEST_IMAGE_GALLERY = 101;
    public static final int REQUEST_PERMISSION_CAMERA = 102;
    public static final int REQUEST_IMAGE_CAMERA = 103;

    private String rutaImagen;

    /**
     * Método para poder accesar a la galería del dispositivo
     */
    public void openGallery(Activity activity) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT); /**Con ACTION_GET_CONTENT, obtenemos la foto de la galería*/
        galleryIntent.setType("image/*");
        activity.startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
    }

    /**
     * Método para poder accesar a la cámara del dispositivo
     */
    public void goToCamera(Activity activity) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); /**ACTION_IMAGE_CAPTURE, es como una API que nos dibujará la interfaz, para acceder a la cámara*/
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = crearImagen(activity);
            } catch (IOException ex) {
                Log.e("Error", ex.toString());
            }
            /**Directorio donde se guardará la imágen*/
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
     * Función para generar los nombres de las imágenes obtenidas
     * por medio de la cámara y la galería del dispositivo
     */
    public String generarNombre(String prefijo) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss", Locale.getDefault()).format(new Date());
        return prefijo + timeStamp + "_"; //IMG_202104101111
    }

    /**
     * Método para crear el archivo temporal de la cámara,
     * para luego ser guardada en el dispositivo
     */
    public File crearImagen(Context context) throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss", Locale.getDefault()).format(new Date());
        String imgFileName = generarNombre("IMG_CAMERA_");
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

    /**
     * Método para guardar la imágen obtenida de la galería
     * en el dispositivo
     */
    public String guardarImagen(Context context, Bitmap nombre, Bitmap imagen) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        /**Directorio donde se guardará la imágen*/
        File dirImages = contextWrapper.getDir("Imagenes", Context.MODE_PRIVATE);
        File myPath = new File(
                dirImages,
                generarNombre("IMG_GALLERY_")
                        + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }
}