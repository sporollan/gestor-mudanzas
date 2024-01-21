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

    public Ciudad(Comparable codigo, String nombre, String provincia, InputReader inputReader)
    {
        this.codigo = codigo;
        this.nombre = nombre;
        this.provincia = provincia;
        this.solicitudes = new MapeoAMuchos();
        this.inputReader = inputReader;
    }

    public boolean modificarSolicitudes(Ciudad ciudadDestino)
    {
        boolean exito = false;
        Lista listaSolicitudes = solicitudes.obtenerValor(ciudadDestino.getCodigo());
        Solicitud s = (Solicitud)listaSolicitudes.recuperar(1);
        int i = 1;
        while(s != null)
        {
            System.out.println(s.getFecha());
            System.out.println(((Cliente)s.getCliente()).getNombres());
            if(inputReader.scanBool("Modificar? s/n"))
            {
                if(listaSolicitudes.eliminar(i))
                    System.out.println("Eliminado con exito");
            }
            else
            {
                i+=1;
            }
            s = (Solicitud)listaSolicitudes.recuperar(i);
        }
        return exito;
    }

    public boolean eliminarSolicitudes(Ciudad ciudadDestino)
    {
        boolean exito = false;
        Lista listaSolicitudes = solicitudes.obtenerValor(ciudadDestino.getCodigo());
        Solicitud s = (Solicitud)listaSolicitudes.recuperar(1);
        int i = 1;
        while(s != null)
        {
            System.out.println(s.getFecha());
            System.out.println(((Cliente)s.getCliente()).getNombres());
            if(inputReader.scanBool("Eliminar? s/n"))
            {
                if(listaSolicitudes.eliminar(i))
                    System.out.println("Eliminado con exito");
            }
            else
            {
                i+=1;
            }
            s = (Solicitud)listaSolicitudes.recuperar(i);
        }
        return exito;
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

    public Lista obtenerSolicitudes(Comparable codigoDestino){
        return this.solicitudes.obtenerValor(codigoDestino);
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
