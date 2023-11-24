package estructuras.propositoEspecifico;


public class NodoAVLDicc {
    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVLDicc hijoIzquierdo;
    private NodoAVLDicc hijoDerecho;
    public NodoAVLDicc(Comparable clave, Object dato, NodoAVLDicc hijoIzquierdo, NodoAVLDicc hijoDerecho) {
        this.clave = clave;
        this.dato = dato;
        this.hijoIzquierdo = hijoIzquierdo;
        this.hijoDerecho = hijoDerecho;
        this.altura = 0;
    }

    private int _recalcularAlturaAux(NodoAVLDicc n, int a)
    {
        int ai;
        int ad;
        if( n != null )
        {
            ai = this._recalcularAlturaAux(n.hijoIzquierdo, a);
            ad = this._recalcularAlturaAux(n.hijoDerecho, a);
            a = ai > ad ? ai : ad;
            n.altura = a;
        }
        else
        {
            return 0;
        }
        return a + 1;

    }
    public void recalcularAltura() {
        int ai = 0;
        int ad = 0;
        ai = this._recalcularAlturaAux(this.hijoIzquierdo, 0);
        ad = this._recalcularAlturaAux(this.hijoDerecho, 0);
        this.altura = ai > ad ? ai : ad;
    }
    public Comparable getClave() {
        return clave;
    }
    public void setClave(Comparable clave) {
        this.clave = clave;
    }
    public Object getDato() {
        return dato;
    }
    public void setDato(Object dato) {
        this.dato = dato;
    }
    public int getAltura() {
        return altura;
    }
    public void setAltura(int altura) {
        this.altura = altura;
    }
    public NodoAVLDicc getHijoIzquierdo() {
        return hijoIzquierdo;
    }
    public void setHijoIzquierdo(NodoAVLDicc hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }
    public NodoAVLDicc getHijoDerecho() {
        return hijoDerecho;
    }
    public void setHijoDerecho(NodoAVLDicc hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }
}
