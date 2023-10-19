package lib.conjuntistas;


public class NodoAVL
{
    Comparable<Object> elem;
    int altura;
    NodoAVL izquierdo;
    NodoAVL derecho;

    public NodoAVL(Comparable<Object> elem, NodoAVL izquierdo, NodoAVL derecho)
    {
        this.elem = elem;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = 0;
    }
    public Comparable<Object> getElem() {
        return elem;
    }

    public void setElem(Comparable<Object> elem) {
        this.elem = elem;
    }

    public int getAltura() {
        return altura;
    }

    private int _recalcularAlturaAux(NodoAVL n, int a)
    {
        int ai;
        int ad;
        if( n != null )
        {
            ai = this._recalcularAlturaAux(n.izquierdo, a);
            ad = this._recalcularAlturaAux(n.derecho, a);
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
        ai = this._recalcularAlturaAux(this.izquierdo, 0);
        ad = this._recalcularAlturaAux(this.derecho, 0);
        this.altura = ai > ad ? ai : ad;
    }

    public NodoAVL getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoAVL izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoAVL getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAVL derecho) {
        this.derecho = derecho;
    }


    
}