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

    public boolean asociar(Object dominio, Object rango)
    {
        int pos = dominio.hashCode() % TAM;
        NodoHashMapeo aux = this.tabla[pos];
        boolean encontrado = false;
        while(!encontrado && aux != null)
        {
            encontrado = aux.getDominio().equals(dominio);
            aux = aux.getEnlace();
        }

        if(!encontrado)
        {
            this.tabla[pos] = new NodoHashMapeo(dominio, rango, this.tabla[pos]);
            this.cant+=1;
        }
        return !encontrado;
    }

    public Object obtenerValor(Object dominio)
    {
        int pos = dominio.hashCode() % TAM;
        if(pos < 0)
            pos = pos *-1;
        NodoHashMapeo aux = this.tabla[pos];
        System.out.println("/////");
        System.out.println(aux.getDominio());
        System.out.println(dominio);
        while(!aux.equals(null) && !aux.getDominio().equals(dominio))
        {
            aux = aux.getEnlace();
        }
        return aux.equals(null) ? null : aux.getRango();
    }
}
