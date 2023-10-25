package src.mudanzas;

public class Ciudad implements Comparable{

    final Comparable codigo;
    String nombre;

    public Ciudad(Comparable codigo, String nombre)
    {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    @Override public String toString()
    {
        return this.nombre;
    }

    @Override
    public int compareTo(Object n)
    {

        return codigo.compareTo(((Ciudad)n).codigo);
    }

}
