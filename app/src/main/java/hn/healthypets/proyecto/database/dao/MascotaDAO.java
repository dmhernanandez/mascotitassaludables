package hn.healthypets.proyecto.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import hn.healthypets.proyecto.database.Entidades.Mascota;

// Objeto de acceso a datos(MascotaDAO)
@Dao
public interface MascotaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertNewPet(Mascota mascota);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public void updatePet(Mascota mascota);

    //Selecciona a todas Las mascotas y retorna un arreglo
    @Query("SELECT * FROM Mascota")
    public LiveData<List<Mascota>> getAllPets();


   @Query("SELECT count(*) FROM Mascota")
    public int getNumbersPets();

    //Esta clase se utiliza  para devolver la cantidad de filas de la tabla mascotas
    static class NumberPets {

        private int numberPets;
        public NumberPets(int numberPets) {
            this.numberPets = numberPets;
        }

        public int getNumberPets() {
            return numberPets;
        }

        public void setNumberPets(int numberPets) {
            this.numberPets = numberPets;
        }
    }

}
