package mudanzas;

public class Cliente {
    final String tipo;
    final String num;
    String nombres;
    String apellidos;
    String telefono;
    String email;

    public Cliente(String tipo, String num, String nombres, String apellidos, String telefono, String email)
    {
        this.tipo = tipo;
        this.num = num;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }

    @Override
    public String toString()
    {
        return nombres + " " + apellidos;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNum() {
        return num;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

/*
    @Override
    public boolean equals(Object clave)
    {
        return this.clave.equals(clave);
    }
*/
}
