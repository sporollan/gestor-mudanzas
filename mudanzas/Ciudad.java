package mudanzas;

import estructuras.conjuntistas.ArbolAVL;
import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.MapeoAMuchos;

public class Ciudad {

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

}
