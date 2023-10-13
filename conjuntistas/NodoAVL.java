package conjuntistas;


public class NodoAVL
{
    Object elem;
    int altura;
    NodoAVL izquierdo;
    NodoAVL derecho;

    public NodoAVL(Object elem, NodoAVL izquierdo, NodoAVL derecho)
    {
        this.elem = elem;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }
    public Object getElem() {
        return elem;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public int getAltura() {
        return altura;
    }

    public void recalcularAltura() {
        this.altura = 0;
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