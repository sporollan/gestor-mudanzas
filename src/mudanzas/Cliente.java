package src.mudanzas;

public class Cliente {
    final Comparable clave;
    String nombres;
    String apellidos;
    String telefono;
    String email;

    public Cliente(Comparable clave, String nombres, String apellidos, String telefono, String email)
    {
        this.clave = clave;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hc = this.clave.hashCode();
        hc = hc < 0 ? -1 * hc : hc;
        return hc;
    }

    @Override
    public String toString()
    {
        return nombres + " " + apellidos;
    }

    @Override
    public boolean equals(Object clave)
    {
        return this.clave.equals(clave);
    }
}
