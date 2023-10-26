package lib.conjuntistas;

public class TablaHash {
    final int TAMANIO = 100;
    
    Nodo[] tabla;
    int cant;

    public TablaHash() {
        this.tabla = new Nodo[TAMANIO];
        this.cant = 0;
    }
    public Nodo[] getTabla() {
        return tabla;
    }
    public void setTabla(Nodo[] tabla) {
        this.tabla = tabla;
    }
    public int getCant() {
        return cant;
    }
    public void setCant(int cant) {
        this.cant = cant;
    }

    public boolean pertenece(Object elem)
    {
        return false;
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

    
}
