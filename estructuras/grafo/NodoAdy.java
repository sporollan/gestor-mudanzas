package estructuras.grafo;

public class NodoAdy {
    NodoVert vertice;
    NodoAdy sigAdyacente;
    float etiqueta;

    public NodoAdy(NodoVert vertice, NodoAdy sigAdyacente, float etiqueta)
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
    public float getEtiqueta()
    {
        return this.etiqueta;
    }
    
}
