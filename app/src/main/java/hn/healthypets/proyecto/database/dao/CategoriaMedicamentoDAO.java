package hn.healthypets.proyecto.database.dao;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import hn.healthypets.proyecto.database.Entidades.CategoriaMedicamento;
@Dao
public interface CategoriaMedicamentoDAO {
   /**
    * Se declara como IGNORE para que cuando hayan datos repetidos no realice ninguna accion, simplemente
    * lo ignore, se dejo de esta manera porque estos datos ya estaran predeterminados
    * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertarCategoriaMedicamento(CategoriaMedicamento catMedicamento);

    //En este caso podemos mandar a guardar varios categorias de medicamentos como un arreglo
    @Insert
    public void insertMedicinesCategories(CategoriaMedicamento ... categoriaMedicamentos);
    @Update
    public void actualizarCategoriaMedicamento(CategoriaMedicamento categoriaMedicamento);

    //Selecciona todas las filas de Categoria Medicamento
    @Query("SELECT * FROM CategoriaMedicamento")
     public   List<CategoriaMedicamento> geAlltMedicineCategory();


}
