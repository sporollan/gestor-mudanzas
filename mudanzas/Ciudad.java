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

    public Lista listarSolicitudes()
    {
        return this.solicitudes.listarDatos();
    }

    public void mostrarEstructuraSolicitudes()
    {
        System.out.println(solicitudes.toString());
    }

    public Solicitud eliminarSolicitudes(Ciudad ciudadDestino)
    {
        Lista listaSolicitudes = solicitudes.obtenerValor(ciudadDestino.getCodigo());
        Solicitud s = (Solicitud)listaSolicitudes.recuperar(1);
        Solicitud retorno=null;
        int i = 1;
        while(s != null)
        {
            System.out.println(s.getFecha());
            System.out.println(((Cliente)s.getCliente()).getNombres());
            if(inputReader.scanBool("Eliminar?"))
            {
                if(solicitudes.desasociar(ciudadDestino.getCodigo(), s))
                {
                    System.out.println("Eliminado con exito");
                    retorno = s;
                }
            }
            else
            {
                i+=1;
            }
            s = (Solicitud)listaSolicitudes.recuperar(i);
        }
        return retorno;
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

    public boolean existeSolicitud(Solicitud s)
    {
        boolean existe = false;
        Lista solicitudesLista = solicitudes.obtenerValor(s.getDestino().getCodigo());
        if(solicitudesLista!=null)
        {
            existe = solicitudesLista.localizar(s)!=-1;
        }
        return existe;
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
