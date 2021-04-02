package hn.healthypets.proyecto.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import hn.healthypets.proyecto.database.Entidades.AgendaMedicamento;
import hn.healthypets.proyecto.database.Entidades.AgendaVisita;
import hn.healthypets.proyecto.database.Entidades.CategoriaMedicamento;
import hn.healthypets.proyecto.database.Entidades.Especie;
import hn.healthypets.proyecto.database.Entidades.Genero;
import hn.healthypets.proyecto.database.Entidades.Mascota;
import hn.healthypets.proyecto.database.Entidades.Medicamento;
import hn.healthypets.proyecto.database.Entidades.Raza;
import hn.healthypets.proyecto.database.Entidades.TipoDosis;
import hn.healthypets.proyecto.database.Entidades.UnidadesMedida;
import hn.healthypets.proyecto.database.dao.CategoriaMedicamentoDAO;
import hn.healthypets.proyecto.database.dao.EspecieDAO;
import hn.healthypets.proyecto.database.dao.MascotaDAO;
import hn.healthypets.proyecto.database.dao.RazaDAO;

//Escribimmos todas las entidades de la base de de datos
@Database(entities = {AgendaMedicamento.class,
        AgendaVisita.class,
        CategoriaMedicamento.class,
        Especie.class,
        Genero.class,
        Mascota.class,
        Medicamento.class,
        Raza.class,
        TipoDosis.class,
        UnidadesMedida.class
},version = 1)
//Creamos un patron singleton para nuestra base de datos
public abstract class DataBase extends RoomDatabase {

  public abstract MascotaDAO getMascotaDAO();

  public abstract CategoriaMedicamentoDAO getCategoriaMedicamentoDADO();
  public  abstract EspecieDAO getSpeciesDAO();
  public abstract RazaDAO getRazaDAO();
}
