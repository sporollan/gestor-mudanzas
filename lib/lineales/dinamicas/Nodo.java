package lib.lineales.dinamicas;

public class Nodo {
    Nodo enlace;
    Object elem;
    public Nodo(Object elem, Nodo enlace)
    {
        this.elem = elem;
        this.enlace = enlace;
    }
    public Nodo getEnlace() {
        return enlace;
    }
    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }
    public Object getElem() {
        return elem;
    }
    public void setElem(Object elem) {
        this.elem = elem;
    }
}
