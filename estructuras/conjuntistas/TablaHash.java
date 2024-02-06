package estructuras.conjuntistas;

public class TablaHash {
    final int TAMANIO = 100;
    
    Nodo[] tabla;
    int cant;

    public TablaHash() {
        this.tabla = new Nodo[TAMANIO];
        this.cant = 0;
    }

    public int getCant() {
        return cant;
    }

    public boolean pertenece(Object elem)
    {
        int pos = elem.hashCode() % this.TAMANIO;
        if(pos < 0)
            pos = -1 * pos;
        return this.tabla[pos] != null;
    }

    public boolean insertar(Object nuevoElem)
    {
        int pos = nuevoElem.hashCode() % this.TAMANIO;
        Nodo aux = this.tabla[pos];
        boolean encontrado = false;
        while(!encontrado && aux != null)
        {
            encontrado = aux.getElem().equals(nuevoElem);
            aux = aux.getEnlace();
        }

        if(!encontrado)
        {
            this.tabla[pos] = new Nodo(nuevoElem, this.tabla[pos]);
            this.cant++;
        }
        return !encontrado;
    }

    public boolean eliminar(Object elem)
    {
        return false;
    }

    public String toString()
    {
        String s = "";
        for(int i = 0; i < this.TAMANIO; i++)
        {
            if(this.tabla[i] != null)
                s += this.tabla[i] + " ";
        }
        return s;
    }

    public String toString(String clave)
    {
        String s = "";
        return s;
    }

    public Object obtener(String clave)
    {
        int pos = clave.hashCode() % this.TAMANIO;
        if(pos < 0)
            pos = pos *-1;
        Nodo aux = this.tabla[pos];

        while(aux != null && !aux.getElem().equals(clave))
        {
            aux = aux.getEnlace();
        }
        return aux == null ? null : aux.getElem();
    }

    
}
