package hn.healthypets.proyecto.database;

import androidx.room.Database;
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
import hn.healthypets.proyecto.database.dao.AgendaMedicamentoDAO;
import hn.healthypets.proyecto.database.dao.AgendaVisitaDAO;
import hn.healthypets.proyecto.database.dao.CategoriaMedicamentoDAO;
import hn.healthypets.proyecto.database.dao.EspecieDAO;
import hn.healthypets.proyecto.database.dao.GeneroDAO;
import hn.healthypets.proyecto.database.dao.MascotaDAO;
import hn.healthypets.proyecto.database.dao.MedicamentoDAO;
import hn.healthypets.proyecto.database.dao.RazaDAO;
import hn.healthypets.proyecto.database.dao.TipoDosisDAO;

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
},version = 1,exportSchema = false)
//Creamos un patron singleton para nuestra base de datos
public abstract class DataBase extends RoomDatabase {

  public abstract MascotaDAO getMascotaDAO();
  public abstract CategoriaMedicamentoDAO getCategoriaMedicamentoDAO();
  public  abstract EspecieDAO getSpeciesDAO();
  public abstract RazaDAO getRazaDAO();
  public abstract TipoDosisDAO getTipoDosisDAO();
  public abstract MedicamentoDAO getMedicamentoDAO();
  public abstract GeneroDAO getGeneroDAO();
  public abstract AgendaMedicamentoDAO getAgendaMedicamentoDAO();
  public abstract AgendaVisitaDAO getAgendaVisitaDAO();

}
