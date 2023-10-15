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

    private int _getBalance(NodoAVL n)
    {
        int b = 0;
        int ai = 0;
        int ad = 0;
        if(n != null)
        {
            NodoAVL i = n.getIzquierdo();
            NodoAVL d = n.getDerecho();

            if(i!=null)
            {
                ai = i.getAltura();
            }
            if(d != null)
            {
                ai = d.getAltura();
            }

            b = ai - ad;
        }
        return b;
    }

    private void _comprobarBalance(NodoAVL p, NodoAVL pp)
    {
        int bp = this._getBalance(p);
        int bpp = this._getBalance(pp);
        if(bpp == 2)
        {
            if(bp == 1 || bp == 0)
            {
                // simple a derecha
            }
            else if(bp == -1)
            {
                // doble izquierda-derecha
            }
        }
        else if(bpp == -2)
        {
            if(bp == 0 || bp == -1)
            {
                // simple a izquierda
            }
            else if(bp == 1)
            {
                // doble derecha-izquierda
            }
        }
    }
    
    private boolean _insertarAux(Object elem, NodoAVL n, NodoAVL p, boolean i, NodoAVL pp)
    {
        boolean exito = false;
        if(n != null)
        {
            if((int)n.getElem() > (int)elem)
            {
                exito = this._insertarAux(elem, n.getIzquierdo(), n, true, p);
            }
            else
            {
                exito = this._insertarAux(elem, n.getDerecho(), n, false, p);
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
            this._comprobarBalance(p, pp);
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
                exito = this._insertarAux(elem, this.raiz.getIzquierdo(), this.raiz, true, null);
            }
            else
            {
                exito = this._insertarAux(elem, this.raiz.getDerecho(), this.raiz, false, null);
            }
        }
        if(exito)
        {
            this.raiz.recalcularAltura();
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

    public int getAltura()
    {
        if(this.raiz != null)
            return this.raiz.getAltura();
        return -1;
    }

    // otras operaciones de ABB
}