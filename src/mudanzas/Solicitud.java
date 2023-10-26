package src.mudanzas;

public class Solicitud {
    Comparable origen;
    Comparable destino;
    String nombre;

    public Solicitud(Comparable origen, Comparable destino, String nombre)
    {   
        this.origen = origen;
        this.destino = destino;
        this.nombre = nombre;

    }

    public int hashCode()
    {
        String c = "";
        int h;
        c += origen;
        c += destino;
        h = c.hashCode();
        if(h < 0)
            h = -1 * h;
        return h;
    }

    public String toString()
    {
        return this.nombre;
    }
}
