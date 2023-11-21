package estructuras.propositoEspecifico;

public class MapeoAUno {
    private static int TAM = 20;
    private NodoHashMapeo[] tabla;
    private int cant;

    public MapeoAUno()
    {
        this.tabla = new NodoHashMapeo[TAM];
        this.cant = 0;
    }

    public boolean asociar(Object tipoDominio, Object tipoRango)
    {
        return false;
    }

    public Object obtenerValor(Object tipoDominio)
    {
        return new Object();
    }
}
