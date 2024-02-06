package mudanzas;

public class Solicitud {
    final Ciudad destino;
    String fecha;
    Cliente cliente;
    int metrosCubicos;
    int bultos;
    String domicilioRetiro;
    String domicilioEntrega;
    boolean estaPago;

    public Solicitud(Ciudad destino, String fecha, Cliente cliente, int metrosCubicos, int bultos, String domicilioRetiro, String domicilioEntrega, boolean estaPago)
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

    public Ciudad getDestino()
    {
        return this.destino;
    }

    @Override
    public boolean equals(Object s)
    {
        Solicitud sol = (Solicitud) s;
        return (
            sol.destino.equals(destino) &&
            sol.domicilioEntrega.equals(domicilioEntrega) &&
            sol.domicilioRetiro.equals(domicilioRetiro) &&
            sol.getCliente().equals(cliente)
        );
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

    @Override
    public String toString()
    {
        return destino + ", " + fecha + ", " + cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
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
        return this.destino.compareTo((Comparable)cp);
    }
*/

}
