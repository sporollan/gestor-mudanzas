package src.mudanzas;

public class Solicitud implements Comparable{
    Comparable destino;
    String fecha;
    Object cliente;
    int metrosCubicos;
    int bultos;
    String domicilioRetiro;
    String domicilioEntrega;
    boolean estaPago;

    public Solicitud(Comparable destino, String fecha, Object cliente, int metrosCubicos, int bultos, String domicilioRetiro, String domicilioEntrega, boolean estaPago)
    {   
        this.destino = destino;
        this.fecha = fecha;
        this.cliente = cliente;
        this.metrosCubicos = metrosCubicos;
        this.bultos = bultos;
        this.domicilioEntrega = domicilioEntrega;
        this.domicilioRetiro = domicilioRetiro;
        this.estaPago = estaPago;
    }

    public Comparable getDestino()
    {
        return this.destino;
    }

    public int hashCode()
    {
        String c = "";
        int h;
        c += destino;
        h = c.hashCode();
        if(h < 0)
            h = -1 * h;
        return h;
    }

    public String toString()
    {
        return destino + ", " + fecha + ", " + cliente;
    }

    @Override
    public int compareTo(Object cp)
    {
        return this.destino.compareTo((Comparable)cp);
    }

}
