package mudanzas.datos;


public class Ciudad {

    final Comparable codigo;
    String nombre;
    String provincia;

    public Ciudad(Comparable codigo, String nombre, String provincia)
    {
        this.codigo = codigo;
        this.nombre = nombre;
        this.provincia = provincia;
    }

    @Override public String toString()
    {
        return this.codigo + ", " + this.nombre + ", " + this.provincia;
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
