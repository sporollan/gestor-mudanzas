package mudanzas;

import estructuras.conjuntistas.ArbolAVL;
import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.MapeoAMuchos;

public class Ciudad implements Comparable{

    final Comparable codigo;
    String nombre;
    String provincia;
    MapeoAMuchos solicitudes;
    InputReader inputReader;

    public Ciudad(Comparable codigo, String nombre, String provincia)
    {
        this.codigo = codigo;
        this.nombre = nombre;
        this.provincia = provincia;
        this.solicitudes = new MapeoAMuchos();
    }

    @Override public String toString()
    {
        return this.codigo + ", " + this.nombre + ", " + this.provincia;
    }

    @Override
    public int compareTo(Object n)
    {
        int comparacion;
        try{
            // caso n es int
            /*if(("" + n).length()==2)
            {
                comparacion = ((Comparable)(Integer.parseInt(codigo+"")/100)).compareTo(n);
            }
            else
            {*/
            comparacion = codigo.compareTo(n);
        } catch (Exception e)
        {
            // caso n es Ciudad
            comparacion = codigo.compareTo(((Ciudad)n).getCodigo());
        }
        return comparacion;
    }

    public boolean insertarSolicitud(Solicitud s)
    {
        Comparable codigoDestino = ((Ciudad)((Solicitud)s).getDestino()).getCodigo();
        return this.solicitudes.asociar(codigoDestino, s);
    }

    public Lista obtenerSolicitudes(Comparable codigo){
        return this.solicitudes.obtenerValor(codigo);
    }

    public Comparable getCodigo()
    {
        return this.codigo;
    }

}
