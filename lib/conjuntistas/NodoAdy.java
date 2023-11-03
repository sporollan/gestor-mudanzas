package lib.conjuntistas;

public class NodoAdy {
    NodoVert vertice;
    NodoAdy sigAdyacente;
    Object etiqueta;

    public NodoAdy(NodoVert vertice, NodoAdy sigAdyacente, Object etiqueta)
    {
        this.vertice = vertice;
        this.sigAdyacente = sigAdyacente;
        this.etiqueta = etiqueta;
    }
    public NodoVert getVertice() {
        return vertice;
    }
    public void setVertice(NodoVert vertice) {
        this.vertice = vertice;
    }
    public NodoAdy getSigAdyacente() {
        return sigAdyacente;
    }
    public void setSigAdyacente(NodoAdy sigAdyacente) {
        this.sigAdyacente = sigAdyacente;
    }
    public Object getEtiqueta()
    {
        return this.etiqueta;
    }
    
}
