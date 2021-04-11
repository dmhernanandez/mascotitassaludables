package hn.healthypets.proyecto.Utilidades;

public class Validacion {

    /**Este metodo se utiliza para validar si todos los campos contienen valores
     *NOTA: los nombres se utilizan en ingles ya que son mas cortos que en español
     * */
    public boolean fieldsAreNotEmpty(String ... fields)
    {
        boolean request=true;
        for (String field: fields) {
            /** En caso de haber un campo vacio la respuesta se vuelve falsa y se detiene el ciclo*/
            if(field.isEmpty())
            {
                request=false;
                break;
            }
        }
        return request;
    }
}
