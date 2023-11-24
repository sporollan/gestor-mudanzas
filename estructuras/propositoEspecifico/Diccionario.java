package estructuras.propositoEspecifico;

import estructuras.lineales.dinamicas.Lista;

public class Diccionario {
    NodoAVLDicc raiz;
    public Diccionario()
    {
        this.raiz = null;
    }
    private Object obtenerAux(NodoAVLDicc n, Comparable clave)
    {
        Object obtenido = null;
        if(n != null)
        {
            if(n.getClave().compareTo(clave) == 0)
                obtenido = n.getDato();
            else if (n.getClave().compareTo(clave) > 0)
            {
                obtenido = obtenerAux(n.getHijoIzquierdo(), clave);
            }
            else
            {
                obtenido = obtenerAux(n.getHijoDerecho(), clave);
            }
        }
        return obtenido;
    }
    public Object obtener(Comparable clave)
    {
        Object obtenido = null;
        if(this.raiz != null)
        {
            if(this.raiz.getClave().compareTo(clave) == 0)
            {
                obtenido = this.raiz.getDato();
            }
            else if(this.raiz.getClave().compareTo(clave) > 0)
            {
                obtenido = obtenerAux(this.raiz.getHijoIzquierdo(), clave);
            }
            else
            {
                obtenido = obtenerAux(this.raiz.getHijoDerecho(), clave);
            }
        }
        return obtenido;
    }
    public boolean pertenece(Object elem)
    {
        return false;
    }

    private void _rotarDerecha(NodoAVLDicc n)
    {
        NodoAVLDicc naux = n.getHijoIzquierdo();
        Object aux = n.getDato();
        Comparable claveAux = n.getClave();
        n.setDato(naux.getDato());
        n.setClave(naux.getClave());
        naux.setDato(aux);
        naux.setClave(claveAux);
        n.setHijoIzquierdo(n.getHijoIzquierdo().getHijoIzquierdo());
        naux.setHijoIzquierdo(naux.getHijoDerecho());
        naux.setHijoDerecho(n.getHijoDerecho());

        n.setHijoDerecho(naux);

    }

    private void _rotarIzquierda(NodoAVLDicc n)
    {
        NodoAVLDicc naux = n.getHijoDerecho();
        Object aux = n.getDato();
        Comparable claveAux = n.getClave();
        n.setDato(naux.getDato());
        n.setClave(naux.getClave());
        naux.setDato(aux);
        naux.setClave(claveAux);
        n.setHijoDerecho(n.getHijoDerecho().getHijoDerecho());
        naux.setHijoDerecho(naux.getHijoIzquierdo());
        naux.setHijoIzquierdo(n.getHijoIzquierdo());
        n.setHijoIzquierdo(naux);
    }

    
    private boolean _insertarAux(NodoAVLDicc n, Comparable clave, Object dato)
    {
        boolean exito = true;
        if (n.getClave().compareTo(clave) == 0)
        {
            exito = false;
        }
        else if (n.getClave().compareTo(clave) > 0)
        {
            // elem es menor que n.getDato()
            if(n.getHijoIzquierdo() != null)
            {
                exito = _insertarAux(n.getHijoIzquierdo(), clave, dato);
            }
            else
            {
                n.setHijoIzquierdo(new NodoAVLDicc(clave, dato, null, null));
                //this.raiz.recalcularAltura();
            }
        }
        else
        {
            // es mayor
            if(n.getHijoDerecho() != null)
            {
                exito = _insertarAux(n.getHijoDerecho(), clave, dato);
            } 
            else
            {
                n.setHijoDerecho(new NodoAVLDicc(clave, dato, null, null));
                //this.raiz.recalcularAltura();
            }
        }
        n.recalcularAltura();
        this._comprobarBalance(n);
        return exito;
    }

    private int _getBalance(NodoAVLDicc n)
    {
        int b = 0;
        int ai = -1;
        int ad = -1;

        if(n != null)
        {  
            if(n.getHijoIzquierdo()!=null)
                ai = n.getHijoIzquierdo().getAltura();
            if(n.getHijoDerecho()!=null)
                ad = n.getHijoDerecho().getAltura();
            b = ai - ad;
        }
        return b;
    }

    private void _comprobarBalance(NodoAVLDicc n)
    {
        int b, bh;
        NodoAVLDicc nh = null;
        int ai = -1;
        int ad = -1;

        if(n != null)
        {
            b = this._getBalance(n);
            if(n.getHijoIzquierdo() != null)
                ai = n.getHijoIzquierdo().getAltura();
            if(n.getHijoDerecho() != null)
                ad = n.getHijoDerecho().getAltura();
            if(ai > ad && ai > -1)
                nh = n.getHijoIzquierdo();
            else if (ad > ai && ad > -1)
                nh = n.getHijoDerecho();
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

    public boolean insertar(Comparable clave, Object dato)
    {
        boolean exito = false;
        if(this.raiz == null) 
        {
            this.raiz = new NodoAVLDicc(clave, dato, null, null);
            exito = true;
        }
        else
        {
            exito = _insertarAux(this.raiz, clave, dato);
        }
        if(exito)
        {
            this.raiz.recalcularAltura();
            this._comprobarBalance(this.raiz);
        }
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

    private String _toStringAux(NodoAVLDicc n)
    {
        String s = "";
        if(n != null)
        {
            s += "\n";
            s += n.getClave() + "(" + n.getAltura() + ")";
            s += ": ";
            s += _addElem(n.getHijoIzquierdo());
            s += ", ";
            s += _addElem(n.getHijoDerecho());

            s += this._toStringAux(n.getHijoIzquierdo());
            s += this._toStringAux(n.getHijoDerecho());
        }
        return s;

    }

    private String _addElem(NodoAVLDicc n)
    {
        String s = "";
        if(n != null)
        {
            s += n.getClave() + "(" + n.getAltura() + ")";
        }
        else
            s += null;
        return s;
    }

    @Override
    public String toString()
    {
        String s = "";
        NodoAVLDicc n = this.raiz; 
        NodoAVLDicc i;
        NodoAVLDicc d;
        if(n!=null)
        {
            s += n.getClave() + "(" + n.getAltura() + "): ";
            i = this.raiz.getHijoIzquierdo();
            s += _addElem(i);

            s += ", ";

            d = this.raiz.getHijoDerecho();
            s += _addElem(d);

            s += this._toStringAux(n.getHijoIzquierdo());
            s += this._toStringAux(n.getHijoDerecho());
        }
        return s;
    }

    public int getAltura()
    {
        if(this.raiz != null)
            return this.raiz.getAltura();
        return -1;
    }

    public boolean eliminar(Comparable elem) {
        this.raiz = eliminarAux(this.raiz, elem);
        return true;
    }
    
    private NodoAVLDicc eliminarAux(NodoAVLDicc raiz, Comparable elem) {
        if (raiz == null) {
            return null;
        }
    
        if (elem.compareTo(raiz.getClave()) < 0) {
            // buscar por izquierda
            raiz.setHijoIzquierdo(eliminarAux(raiz.getHijoIzquierdo(), elem));
        } else if (elem.compareTo(raiz.getClave()) > 0) {
            // buscar por derecha
            raiz.setHijoDerecho(eliminarAux(raiz.getHijoDerecho(), elem));
        } else {
            // encontrado
            if (raiz.getHijoIzquierdo() == null || raiz.getHijoDerecho() == null) {
                // nodo tiene maximo 1 hijo
                NodoAVLDicc temp = (raiz.getHijoIzquierdo() != null) ? raiz.getHijoIzquierdo() : raiz.getHijoDerecho();
                if (temp == null) {
                    // nodo no tiene hijos
                    raiz = null;
                } else {
                    // nodo tiene 1 hijo
                    raiz = temp;
                }
            } else {
                // nodo tiene 2 hijos
                NodoAVLDicc successor = getSuccessor(raiz.getHijoDerecho());
                raiz.setDato(successor.getDato());
                raiz.setClave(successor.getClave());
                raiz.setHijoDerecho(eliminarAux(raiz.getHijoDerecho(), successor.getClave()));
            }
        }
    
        if (raiz != null) {
            raiz.recalcularAltura();
            this._comprobarBalance(raiz);
        }
        return raiz;
    }
    
    private NodoAVLDicc getSuccessor(NodoAVLDicc nodo) {
        while (nodo.getHijoIzquierdo() != null) {
            nodo = nodo.getHijoIzquierdo();
        }
        return nodo;
    }
    
    
    // otras operaciones de ABB

}