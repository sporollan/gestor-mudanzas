package lib.conjuntistas;

public class NodoAdy {
    NodoVert vertice;
    NodoAdy sigAdyacente;
    public NodoAdy()
    {
        vertice = null;
        sigAdyacente = null;
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
    
}
