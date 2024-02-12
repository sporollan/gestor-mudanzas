package mudanzas;

public class Solicitud {
    final Comparable origenDestino;
    String fecha;
    String cliente;
    int metrosCubicos;
    int bultos;
    String domicilioRetiro;
    String domicilioEntrega;
    boolean estaPago;

    public Solicitud(Comparable origenDestino, String fecha, String cliente, int metrosCubicos, int bultos, String domicilioRetiro, String domicilioEntrega, boolean estaPago)
    {   
        this.origenDestino = origenDestino;
        this.fecha = fecha;
        this.cliente = cliente;
        this.metrosCubicos = metrosCubicos;
        this.bultos = bultos;
        this.domicilioEntrega = domicilioEntrega;
        this.domicilioRetiro = domicilioRetiro;
        this.estaPago = estaPago;
    }

    public Comparable getCodigo()
    {
        return this.origenDestino;
    }

    @Override
    public boolean equals(Object s)
    {
        Solicitud sol = (Solicitud) s;
        return (
            sol.origenDestino.equals(origenDestino) &&
            sol.domicilioEntrega.equals(domicilioEntrega) &&
            sol.domicilioRetiro.equals(domicilioRetiro) &&
            sol.getCliente().equals(cliente)
        );
    }

    public int hashCode()
    {
        String c = "";
        int h;
        c += origenDestino;
        h = c.hashCode();
        if(h < 0)
            h = -1 * h;
        return h;
    }

    @Override
    public String toString()
    {
        return "("+origenDestino + ", " + metrosCubicos + " m2, "+cliente+")";
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getMetrosCubicos() {
        return metrosCubicos;
    }

    public void setMetrosCubicos(int metrosCubicos) {
        this.metrosCubicos = metrosCubicos;
    }

    public int getBultos() {
        return bultos;
    }

    public void setBultos(int bultos) {
        this.bultos = bultos;
    }

    public String getDomicilioRetiro() {
        return domicilioRetiro;
    }

    public void setDomicilioRetiro(String domicilioRetiro) {
        this.domicilioRetiro = domicilioRetiro;
    }

    public String getDomicilioEntrega() {
        return domicilioEntrega;
    }

    public void setDomicilioEntrega(String domicilioEntrega) {
        this.domicilioEntrega = domicilioEntrega;
    }

    public boolean isEstaPago() {
        return estaPago;
    }

    public void setEstaPago(boolean estaPago) {
        this.estaPago = estaPago;
    }

/*
    @Override
    public int compareTo(Object cp)
    {
        return this.origenDestino.compareTo((Comparable)cp);
    }
*/

}
