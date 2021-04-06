package hn.healthypets.proyecto.database.dao;

import android.widget.RatingBar;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import hn.healthypets.proyecto.database.Entidades.Raza;

@Dao
 public  interface   RazaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertBreed(Raza raza);

    /*Inserta una lista de*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertBreeds(Raza ... raza);

    /* Obtiene todas razas de los perros*/
    @Query("Select * from Raza")
    public List<Raza> getAllBreeds();
}
