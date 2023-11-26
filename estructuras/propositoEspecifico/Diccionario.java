package estructuras.propositoEspecifico;

import estructuras.lineales.dinamicas.Lista;

public class Diccionario {
    NodoAVLDicc raiz;
    public Diccionario()
    {
        this.raiz = null;
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

    public boolean eliminar(Comparable clave)
    {
        boolean exito = this.raiz!=null;
        if(exito)
            exito = eliminarAux(this.raiz, clave, null, false);
        return exito;
    }

    public boolean eliminarAux(NodoAVLDicc n, Comparable clave, NodoAVLDicc p, boolean esIzquierdo)
    {
        boolean exito = false;
        if(n != null)
        {
            // buscar clave
            if(clave.compareTo(n.getClave())==0)
            {
                // encontrado
                NodoAVLDicc i, d, reemplazo;
                i = n.getHijoIzquierdo();
                d = n.getHijoDerecho();
                // evaluo cual es el reemplazo
                if (i == null && d == null)
                {
                    // caso 1, el nodo es una hoja
                    reemplazo = null;
                }
                else if(i == null || d == null)
                {
                    // caso 2, el nodo tiene un solo hijo
                    reemplazo = i == null ? d : i;
                }
                else
                {
                    // caso 3, el nodo tiene 2 hijos
                    reemplazo = obtenerCandidatoA(n);
                }

                // evaluo donde insertar el reemplazo
                NodoAVLDicc encontrado, derecho, izquierdo;
                if(p == null)
                {
                    // inserto en la raiz
                    if(this.raiz != null && reemplazo != null)
                    {
                        izquierdo = this.raiz.getHijoIzquierdo();
                        derecho = this.raiz.getHijoDerecho();
                        reemplazo.setHijoIzquierdo(izquierdo);
                        reemplazo.setHijoDerecho(derecho);
                    }
                    this.raiz = reemplazo;
                }
                else if(esIzquierdo)
                {
                    encontrado = p.getHijoIzquierdo();
                    derecho = null;

                    if(encontrado!=null)
                        derecho = encontrado.getHijoDerecho();

                    p.setHijoIzquierdo(reemplazo);
                    if(reemplazo != null)
                        p.getHijoIzquierdo().setHijoDerecho(derecho);

                }
                else
                {
                    encontrado = p.getHijoDerecho();
                    derecho = null;

                    if(encontrado!=null)
                        derecho = encontrado.getHijoDerecho();

                    p.setHijoDerecho(reemplazo);
                    if(reemplazo != null)
                        p.getHijoDerecho().setHijoDerecho(derecho);

                }

                if(this.raiz!=null)
                {
                    this.raiz.recalcularAltura();
                    //System.out.println(this.toString());
                    this._comprobarBalance(this.raiz);
                }
                exito = true;
            }
            else if(clave.compareTo(n.getClave()) < 0)
            {
                // buscar por izquierda
                exito = eliminarAux(n.getHijoIzquierdo(), clave, n, true);
            }
            else
            {
                // buscar por derecha
                exito = eliminarAux(n.getHijoDerecho(), clave, n, false);
            }
        }
        return exito;
    }

    private NodoAVLDicc obtenerCandidatoA(NodoAVLDicc n)
    {
        NodoAVLDicc i = n.getHijoIzquierdo();
        NodoAVLDicc candidatoA;
        if(i.getHijoDerecho() == null)
        {
            candidatoA = i;
        }
        else
        {
            candidatoA = obtenerCandidatoAAux(i);
        }
        return candidatoA;
    }

    private NodoAVLDicc obtenerCandidatoAAux(NodoAVLDicc n)
    {
        NodoAVLDicc candidatoA;
        NodoAVLDicc d = n.getHijoDerecho();
        if(d.getHijoDerecho() == null)
        {
            candidatoA = d;
            n.setHijoDerecho(null);
        }
        else
        {
            candidatoA = obtenerCandidatoAAux(d);
        }
        return candidatoA;
    }

    public boolean existeClave(Comparable clave)
    {
        return false;
    }

    public Object obtenerInformacion(Comparable clave)
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
                obtenido = _obtenerAux(this.raiz.getHijoIzquierdo(), clave);
            }
            else
            {
                obtenido = _obtenerAux(this.raiz.getHijoDerecho(), clave);
            }
        }
        return obtenido;
    }

    public Lista listarClaves()
    {
        return new Lista();
    }

    public Lista listarDatos()
    {
        return new Lista();
    }

    public boolean esVacio()
    {
        return false;
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

    private Object _obtenerAux(NodoAVLDicc n, Comparable clave)
    {
        Object obtenido = null;
        if(n != null)
        {
            if(n.getClave().compareTo(clave) == 0)
                obtenido = n.getDato();
            else if (n.getClave().compareTo(clave) > 0)
            {
                obtenido = _obtenerAux(n.getHijoIzquierdo(), clave);
            }
            else
            {
                obtenido = _obtenerAux(n.getHijoDerecho(), clave);
            }
        }
        return obtenido;
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
}