
package hn.healthypets.proyecto;
/**
 * Con esta Clase lo que hacemos es establecer todas las funciones relacionadas
 * con CAMARA y GALERIA del dispositivo
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class MetodosImagenes {
    public static final int REQUEST_PERMISSION_GALLERY = 100;
    public static final int REQUEST_PERMISSION_CAMERA = 102;
    public static final int REQUEST_PERMISION_WRITE_STORAGE=104;
    public static final int REQUEST_IMAGE_GALLERY = 101;
    public static final int REQUEST_IMAGE_CAMERA = 103;

    private String rutaImagen;
    private Bitmap bitmap;

    /**
     * Método para poder accesar a la galería del dispositivo
     */
    public void openGallery(Activity activity) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK); /**Con ACTION_GET_CONTENT, obtenemos la foto de la galería*/
        galleryIntent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(galleryIntent,"Seleccione una imagen"), REQUEST_IMAGE_GALLERY);
    }

    /**
     * Método para poder accesar a la cámara del dispositivo
     */
    public void goToCamera(Activity activity) {
        /**ACTION_IMAGE_CAPTURE, es como una API que nos dibujará la interfaz, para acceder a la cámara*/
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, REQUEST_IMAGE_CAMERA);
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

    /** Con este metodo validamos si ya se otorgraron los permisos de la camara de ser así se retorna un valor booleano*/
    public boolean checkPermissionCamera(Activity activity)
    {
        boolean permisionCamara=false;
        /**Se valida que versión del SKD que sea igual o pesterior a la version Marshmello, de ser así, pedirá los permisos en tiempo de
         * ejecución*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /** Valida que si los permisos ya estan habilitados, si ya estan habilitados, devuelve un valor true
             * foto*/
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            {
                permisionCamara=true;
               //goToCamera(activity);
            }
            else
            {
                permisionCamara=false;
            }
        }
        else/** Si la version es inferior los permisos fueron aceptados al descargar la aplicación y el permiso es true*/
        {
            permisionCamara=true;
        }
        return permisionCamara;
    }

    public boolean checkPermissionStorage(Activity activity)
    {
        boolean permissionStorage=false;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                /** Valida que si los permisos ya estan habilitados, si ya estan habilitados entoces permissionStorage = true
                 * de lo contrario  el permissionStorage = false
                 * foto*/
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                   permissionStorage=true;
                }
                else
                {
                   permissionStorage=false;
                }
            }

        }
        else/** Si la version es inferior los permisos fueron aceptados al descargar la aplicación*/
        {
            permissionStorage=true;
        }
        return permissionStorage;
    }

    public boolean checkPermissionGallery(Activity activity)
    {
         boolean permissionGallery=false;
        /**En esta línea se verifica el permiso para la versión de android en el dispositivo en tiempo de ejecución
         * en caso de ser mayor que la version 10 o Nougat
         * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                permissionGallery=true;
            }
            else
            {
               permissionGallery=false;
            }
        }
        else
        {
            permissionGallery=true;
        }
        return  permissionGallery;
    }

    private void savePhoto(Activity activity,Bitmap bitmap) {

        /** Se obtiene la ruta principal del direcotorio de imagenes privado de la aplicación, de manera que no seran
         * visibles en la galería*/
        String path = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        OutputStream fOut = null;

        /** Con este archivo se crea el directoria ni no existe*/
        File fileFolder = new File(path, "Camera");
        /** Si el archivo no se creo, se intenta creearlo nuevamente*/
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }

        rutaImagen= "IMG_20210412_11-42-50_.jpeg";
        /** Se crea un archivo al cual se mandaran los bits por medio de FileOutputStream*/
        File filePhoto = new File(fileFolder,rutaImagen );

        try {
            /** Se crea una secuencia de salida de archivo para escribir en el archivo representado por el objeto especificado
             * en este caso filePhoto*/
            fOut = new FileOutputStream(filePhoto);
        } catch (FileNotFoundException e) {
            Log.i("error","Archivo no econtrado: "+e.getMessage());
        }

        /**
         * Una vez que tenemos lista la secuencia de salida procedemos a comprimir nuestro mapa de bits
         * estableciendo el formato de compresion, la calidad y la secuencia de salida de bytes, "fOut"
         * acepta bytes de salida enviados por el metodo "compress".
         *
         * se crea una variable booleana para para  determinar si se guardo correctamente
         * */
       boolean a=  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

        try {
            /** Vacía la secuencia de salida "fOut" y obliga a escribir los bytes de salida almacenados en búfer.*/
            fOut.flush();

            /** Cierra la secuencia de salida y libera los recursos del sistema asociados a la secuencia "fOut" **/
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Este metodo se utiliza se invoca en le mtedo onRequestPermisionCode*/
    public boolean validateRequestPermissionCode(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults,Activity activity)
    {
        boolean permissionRequest=false;
        /**
         * Se valida si el usuario dio los permisos, de ser asi lo envia la GALERIA o la CAMARA
         **/
        switch (requestCode)
        {
            case REQUEST_PERMISSION_GALLERY:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    openGallery(activity);
                }
                else
                {
                    Toast.makeText(activity, "Acceso a camara denegado", Toast.LENGTH_LONG).show();
                }
            break;
            case REQUEST_PERMISSION_CAMERA:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    goToCamera(activity);
                } else
                {
                    Toast.makeText(activity, "Acceso a galeria denegado", Toast.LENGTH_LONG).show();
                }
            break;
            case REQUEST_PERMISION_WRITE_STORAGE:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissionRequest=true;
                } else
                {
                    permissionRequest=false;
                }
            break;
        }
        /** Esta variable se usa solo para validar si se dio el permiso de almacenar en cambio de los otros metdos si los
         * permisos se otorgaron los lleva directamente a las acciones*/
       return permissionRequest;

    }

/** Este metodo se llama cuando se necesita pedir un permiso en caso que no este concedido*/
    public void requestPermissionFromUser(Activity activity, String typePermision, int identifier)
    {
        //Si los permisos no estan dados se solicitan al usuario
        ActivityCompat.requestPermissions(
                activity,
                new String[]{typePermision},
                identifier);
    }

    /** Con este metodo convertimos un recurso pasado por uri a un bitmap*/
    public Bitmap getBitmapFromUri (Activity activity, Uri uri )  {
        Bitmap image = null;
        try {
        ParcelFileDescriptor parcelFileDescriptor = activity.getContentResolver ().openFileDescriptor( uri ,"r");
        FileDescriptor fileDescriptor = parcelFileDescriptor . getFileDescriptor ();
        image = BitmapFactory. decodeFileDescriptor ( fileDescriptor );

            parcelFileDescriptor . close ();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image ;
    }
//    private void setPic(ImageView imageView) {
//        // Se optienen las dimensiones del imageview
//        int targetW = imageView.getWidth();
//        int targetH = imageView.getHeight();
//
//        //
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//
//        // Determine how much to scale down the image
//        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
//
//        // Decode the image file into a Bitmap sized to fill the View
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
//        imageView.setImageBitmap(bitmap);
//    }
}