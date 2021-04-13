package hn.healthypets.proyecto.database.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/**
 * Contien campos para almacenar los datos de la mascota
 * */
@Entity
public class Mascota {
    @PrimaryKey(autoGenerate = true)
    private int mascotaId;
    @NonNull
    private String nombre;
    @NonNull
    private String fechaNacimiento;
    private String rutaFoto;
    private int numeroChip;
   //Llave foranea de la tabla genero
    @NonNull
    private int generoMascotaId;
    //llave foranea de la tabla raza, para determinar la raza de la mascota
    @NonNull
    private int razaMascotaId;
    //LLave foranea de la especie de la mascota, por ejemplo perro o gato
    private int mascotaEspecieId;



    //Constructor
    public Mascota(int mascotaId, @NonNull String nombre, @NonNull String fechaNacimiento, int generoMascotaId, int razaMascotaId, int mascotaEspecieId,String rutaFoto,int numeroChip) {
        this.mascotaId = mascotaId;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.generoMascotaId = generoMascotaId;
        this.razaMascotaId = razaMascotaId;
        this.mascotaEspecieId = mascotaEspecieId;
        this.rutaFoto=rutaFoto;
        this.numeroChip=numeroChip;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(int mascotaId) {
        this.mascotaId = mascotaId;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(@NonNull String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getGeneroMascotaId() {
        return generoMascotaId;
    }

    public void setGeneroMascotaId(int generoMascotaId) {
        this.generoMascotaId = generoMascotaId;
    }

    public int getRazaMascotaId() {
        return razaMascotaId;
    }

    public void setRazaMascotaId(int razaMascotaId) {
        this.razaMascotaId = razaMascotaId;
    }

    public int getMascotaEspecieId() {
        return mascotaEspecieId;
    }

    public void setMascotaEspecieId(int mascotaEspecieId) {
        this.mascotaEspecieId = mascotaEspecieId;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public int getNumeroChip() {
        return numeroChip;
    }

    public void setNumeroChip(int numeroChip) {
        this.numeroChip = numeroChip;
    }
}
