package estructuras.conjuntistas;

public class Nodo {
    Object elem;
    Nodo enlace;
    public Nodo(Object elem, Nodo enlace)
    {
        this.elem = elem;
        this.enlace = enlace;
    }
    public Object getElem() {
        return elem;
    }
    public void setElem(Object elem) {
        this.elem = elem;
    }
    public Nodo getEnlace() {
        return enlace;
    }
    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }
    @Override
    public String toString()
    {
        return ""+this.elem;
    }
}
