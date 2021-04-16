package hn.healthypets.proyecto.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hn.healthypets.proyecto.database.Entidades.CategoriaMedicamento;

@Dao
public interface CategoriaMedicamentoDAO {
    /**
     * Se declara como IGNORE para que cuando hayan datos repetidos no realice ninguna accion, simplemente
     * lo ignore, se dejo de esta manera porque estos datos ya estaran predeterminados
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertMedicinesCategorie(CategoriaMedicamento catMedicamento);

    //En este caso podemos mandar a guardar varios categorias de medicamentos como un arreglo
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertMedicinesCategories(CategoriaMedicamento... categoriaMedicamentos);

    @Update
    public void actualizarCategoriaMedicamento(CategoriaMedicamento categoriaMedicamento);

    @Query("SELECT categoriaMedicamentoId FROM categoriamedicamento WHERE nombreCategoria = :nombreCategoria")
    public int getIdMedicinesCategoriesByName(String nombreCategoria);

    //Selecciona todas las filas de Categoria Medicamento
    @Query("SELECT * FROM CategoriaMedicamento")
    public List<CategoriaMedicamento> geAlltMedicineCategory();
}
