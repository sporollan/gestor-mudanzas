package lib.conjuntistas;

import lib.lineales.dinamicas.Lista;
import lib.lineales.dinamicas.Nodo;


public class ArbolAVL {
    NodoAVL raiz;
    public ArbolAVL()
    {
        this.raiz = null;
    }
    private Comparable obtenerAux(NodoAVL n, Comparable clave)
    {
        Comparable obtenido = null;
        if(n != null)
        {
            if(n.getElem().compareTo(clave) == 0)
                obtenido = n.getElem();
            else if (n.getElem().compareTo(clave) > 0)
            {
                obtenido = obtenerAux(n.getIzquierdo(), clave);
            }
            else
            {
                obtenido = obtenerAux(n.getDerecho(), clave);
            }
        }
        return obtenido;
    }
    public Comparable obtener(Comparable clave)
    {
        Comparable obtenido = null;
        if(this.raiz != null)
        {
            if(this.raiz.getElem().compareTo(clave) == 0)
            {
                obtenido = this.raiz.getElem();
            }
            else if(this.raiz.getElem().compareTo(clave) > 0)
            {
                obtenido = obtenerAux(this.raiz.getIzquierdo(), clave);
            }
            else
            {
                obtenido = obtenerAux(this.raiz.getDerecho(), clave);
            }
        }
        return obtenido;
    }
    public boolean pertenece(Object elem)
    {
        return false;
    }

    private void _rotarDerecha(NodoAVL n)
    {
        NodoAVL naux = n.getIzquierdo();
        Comparable aux = n.getElem();
        n.setElem(naux.getElem());
        naux.setElem(aux);
        n.setIzquierdo(n.getIzquierdo().getIzquierdo());
        naux.setIzquierdo(naux.getDerecho());
        naux.setDerecho(n.getDerecho());

        n.setDerecho(naux);

    }

    private void _rotarIzquierda(NodoAVL n)
    {
        NodoAVL naux = n.getDerecho();
        Comparable aux = n.getElem();
        n.setElem(naux.getElem());
        naux.setElem(aux);
        n.setDerecho(n.getDerecho().getDerecho());
        naux.setDerecho(naux.getIzquierdo());
        naux.setIzquierdo(n.getIzquierdo());
        n.setIzquierdo(naux);
    }

    
    private boolean _insertarAux(NodoAVL n, Comparable elem)
    {
        boolean exito = true;
        if (n.getElem().compareTo(elem) == 0)
        {
            exito = false;
        }
        else if (n.getElem().compareTo(elem) > 0)
        {
            // elem es menor que n.getElem()
            if(n.getIzquierdo() != null)
            {
                exito = _insertarAux(n.getIzquierdo(), elem);
            }
            else
            {
                n.setIzquierdo(new NodoAVL(elem, null, null));
                //this.raiz.recalcularAltura();
            }
        }
        else
        {
            // es mayor
            if(n.getDerecho() != null)
            {
                exito = _insertarAux(n.getDerecho(), elem);
            } 
            else
            {
                n.setDerecho(new NodoAVL(elem, null, null));
                //this.raiz.recalcularAltura();
            }
        }
        n.recalcularAltura();
        this._comprobarBalance(n);
        return exito;
    }

    private int _getBalance(NodoAVL n)
    {
        int b = 0;
        int ai = -1;
        int ad = -1;

        if(n != null)
        {  
            if(n.getIzquierdo()!=null)
                ai = n.getIzquierdo().getAltura();
            if(n.getDerecho()!=null)
                ad = n.getDerecho().getAltura();
            b = ai - ad;
        }
        return b;
    }

    private void _comprobarBalance(NodoAVL n)
    {
        int b, bh;
        NodoAVL nh = null;
        int ai = -1;
        int ad = -1;

        if(n != null)
        {
            b = this._getBalance(n);
            if(n.getIzquierdo() != null)
                ai = n.getIzquierdo().getAltura();
            if(n.getDerecho() != null)
                ad = n.getDerecho().getAltura();
            if(ai > ad && ai > -1)
                nh = n.getIzquierdo();
            else if (ad > ai && ad > -1)
                nh = n.getDerecho();
            if(nh != null)
            {
                bh = this._getBalance(nh);
                if(b == 2)
                {
                    if(bh == 1 || bh == 0)
                    {
                        // simple a derecha
                        this._rotarDerecha(n);
                        this.raiz.recalcularAltura();
                    }
                    else if(bh == -1)
                    {
                        // doble izquierda-derecha
                        this._rotarIzquierda(nh);
                        this._rotarDerecha(n);
                        this.raiz.recalcularAltura();
                    }
                }
                else if(b == -2)
                {
                    if(bh == 0 || bh == -1)
                    {
                        // simple a izquierda
                        this._rotarIzquierda(n);
                        this.raiz.recalcularAltura();
                    }
                    else if(bh == 1)
                    {
                        // doble derecha-izquierda
                        this._rotarDerecha(nh);
                        this._rotarIzquierda(n);
                        this.raiz.recalcularAltura();
                    }
                }
            }
        }
    }

    public boolean insertar(Comparable elem)
    {
        boolean exito = false;
        if(this.raiz == null) 
        {
            this.raiz = new NodoAVL(elem, null, null);
            exito = true;
        }
        else
        {
            exito = _insertarAux(this.raiz, elem);
        }
        if(exito)
        {
            this.raiz.recalcularAltura();
            this._comprobarBalance(this.raiz);
        }
        return exito;
    }

    public boolean eliminar(Comparable<Object> elem)
    {
        boolean exito = false;
        NodoAVL derecho, izquierdo;
        
        return exito;
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
            s += n.getElem() + "(" + n.getAltura() + ")";
            s += ": ";
            s += _addElem(n.getIzquierdo());
            s += ", ";
            s += _addElem(n.getDerecho());

            s += this._toStringAux(n.getIzquierdo());
            s += this._toStringAux(n.getDerecho());
        }
        return s;

    }

    private String _addElem(NodoAVL n)
    {
        String s = "";
        if(n != null)
        {
            s += n.getElem() + "(" + n.getAltura() + ")";
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
            s += n.getElem() + "(" + n.getAltura() + "): ";
            i = this.raiz.getIzquierdo();
            s += _addElem(i);

            s += ", ";

            d = this.raiz.getDerecho();
            s += _addElem(d);

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