package hn.healthypets.proyecto.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import hn.healthypets.proyecto.database.Entidades.Genero;

@Dao
public interface GeneroDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertGender(Genero genero);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertGenders(Genero ... genero);

    //Optiene todos los campos de la tabla especie
    @Query("SELECT * FROM genero")
    public List<Genero> getAllGenders();

    @Query("SELECT  genero as nombreGenero FROM genero WHERE generoId= :id")
    public NombreGenero getGenderById(int id);

    static class NombreGenero{
        private String nombreGenero;

        public String getNombreGenero() {
            return nombreGenero;
        }

        public void setNombreGenero(String nombreGenero) {
            this.nombreGenero = nombreGenero;
        }
    }
}
