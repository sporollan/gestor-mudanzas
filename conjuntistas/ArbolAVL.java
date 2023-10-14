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
    
    private boolean _insertarAux(Object elem, NodoAVL n, NodoAVL p, boolean i)
    {
        boolean exito = false;
        if(n != null)
        {
            if((int)n.getElem() > (int)elem)
            {
                exito = this._insertarAux(elem, n.getIzquierdo(), n, true);
            }
            else
            {
                exito = this._insertarAux(elem, n.getDerecho(), n, false);
            }
        }
        else
        {
            exito = true;
            if(i)
            {
                p.setIzquierdo(new NodoAVL(elem, null, null));
            }
            else
            {
                p.setDerecho(new NodoAVL(elem, null, null));
            }
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
            if((int)this.raiz.getElem() > (int)elem)
            {
                exito = this._insertarAux(elem, this.raiz.getIzquierdo(), this.raiz, true);
            }
            else
            {
                exito = this._insertarAux(elem, this.raiz.getDerecho(), this.raiz, false);
            }
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
        if(n != null)
        {
            s += "\n";
            s += n.getElem();
            s += ": ";
            s += addElem(n.getIzquierdo());
            s += ", ";
            s += addElem(n.getDerecho());

            s += this._toStringAux(n.getIzquierdo());
            s += this._toStringAux(n.getDerecho());
        }
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
        String s = "";
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
        return s;
    }

    // otras operaciones de ABB
}