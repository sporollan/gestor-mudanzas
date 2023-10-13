package conjuntistas;

import lineales.dinamicas.Lista;


public class ArbolAVL {
    NodoAVL raiz;
    public ArbolAVL()
    {
        this.raiz = null;
    }
    public boolean pertenece(Object elem)
    {
        return false;
    }
    
    private boolean _insertarAux(Object elem, NodoAVL r)
    {
        boolean exito = false;
        if(r != null)
        {
            
        }
        return exito;
    }

    public boolean insertar(Object elem)
    {
        boolean exito = false;

        if(this.raiz == null)
        {
            this.raiz = new NodoAVL(elem, null, null);
            exito = true;
        }
        else
        {
            exito = this._insertarAux(elem, this.raiz);
        }

        return exito;
    }

    public boolean eliminar(Object elem)
    {
        return false;
    }

    public Lista listar()
    {
        return new Lista();
    }

    public Lista listarRango(Object elem1, Object elem2)
    {
        return new Lista();
    }

    public Object minimoElem()
    {
        return new Object();
    }

    public Object maximoElem()
    {
        return new Object();
    }

    public boolean vacio()
    {
        return false;
    }

    private String _toStringAux(NodoAVL n)
    {
        String s = "";
        return s;

    }

    private String addElem(NodoAVL n)
    {
        String s = "";
        if(n != null)
        {
            s += n.getElem();
        }
        else
            s += null;
        return s;
    }

    @Override
    public String toString()
    {
        String s = "[";
        NodoAVL n = this.raiz; 
        NodoAVL i;
        NodoAVL d;
        if(n!=null)
        {
            s += n.getElem() + ": ";
            i = this.raiz.getIzquierdo();
            s += addElem(i);

            s += ", ";

            d = this.raiz.getDerecho();
            s += addElem(d);

            s += this._toStringAux(n.getIzquierdo());
            s += this._toStringAux(n.getDerecho());
        }
        return s + "]";
    }

    // otras operaciones de ABB
}