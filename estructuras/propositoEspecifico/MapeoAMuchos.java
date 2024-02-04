package estructuras.propositoEspecifico;

import estructuras.lineales.dinamicas.Lista;

public class MapeoAMuchos {
    private NodoAVLMapeoM raiz;

    public MapeoAMuchos()
    {
        this.raiz = null;
    }

    public Lista listarDatos()
    {
        Lista datos = new Lista();
        listarDatosAux(raiz, datos);
        return datos;
    }
    private void listarDatosAux(NodoAVLMapeoM aux, Lista datos)
    {
        if(aux != null)
        {
            datos.insertar(aux.getRango(), datos.longitud()+1);
            listarDatosAux(aux.getIzquierdo(), datos);
            listarDatosAux(aux.getDerecho(), datos);
        }
    }

    public String toString()
    {
        return toStringAux(raiz, "");
    }
    private String toStringAux(NodoAVLMapeoM aux, String s)
    {
        if(aux != null)
        {
            s = s + aux.getDominio() + aux.getRango() + "\n";
            s = toStringAux(aux.getIzquierdo(), s);
            s = toStringAux(aux.getDerecho(), s);
        }
        return s;
    }

    private int _getBalance(NodoAVLMapeoM n)
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
    private void _comprobarBalance(NodoAVLMapeoM n)
    {
        int b, bh;
        NodoAVLMapeoM nh = null;
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
    private void _rotarDerecha(NodoAVLMapeoM n)
    {
        NodoAVLMapeoM naux = n.getIzquierdo();
        Comparable aux = n.getDominio();
        Lista auxRango = n.getRango();
        n.setDominio(naux.getDominio());
        n.setRango(naux.getRango());
        naux.setDominio(aux);
        naux.setRango(auxRango);
        n.setIzquierdo(n.getIzquierdo().getIzquierdo());
        naux.setIzquierdo(naux.getDerecho());
        naux.setDerecho(n.getDerecho());

        n.setDerecho(naux);

    }

    private void _rotarIzquierda(NodoAVLMapeoM n)
    {
        NodoAVLMapeoM naux = n.getDerecho();
        Comparable aux = n.getDominio();
        Lista auxRango = n.getRango();
        n.setDominio(naux.getDominio());
        n.setRango(naux.getRango());
        naux.setDominio(aux);
        naux.setRango(auxRango);
        n.setDerecho(n.getDerecho().getDerecho());
        naux.setDerecho(naux.getIzquierdo());
        naux.setIzquierdo(n.getIzquierdo());
        n.setIzquierdo(naux);
    }
    private boolean _asociarAux(NodoAVLMapeoM n, Comparable dominio, Object rango)
    {
        boolean exito = true;
        if (n.getDominio().compareTo(dominio) == 0)
        {
            Lista l = n.getRango();
            l.insertar(rango, l.longitud()+1);
        }
        else if (n.getDominio().compareTo(dominio) > 0)
        {
            // elem es menor que n.getElem()
            if(n.getIzquierdo() != null)
            {
                exito = _asociarAux(n.getIzquierdo(), dominio, rango);
            }
            else
            {
                Lista l = new Lista();
                l.insertar(rango, 1);
                n.setIzquierdo(new NodoAVLMapeoM(dominio, l, null, null));
                //this.raiz.recalcularAltura();
            }
        }
        else
        {
            // es mayor
            if(n.getDerecho() != null)
            {
                exito = _asociarAux(n.getDerecho(), dominio, rango);
            } 
            else
            {
                Lista l = new Lista();
                l.insertar(rango, 1);
                n.setDerecho(new NodoAVLMapeoM(dominio, l, null, null));
                //this.raiz.recalcularAltura();
            }
        }
        n.recalcularAltura();
        this._comprobarBalance(n);
        return exito;
    }
    public boolean asociar(Comparable dominio, Object rango)
    {
        boolean exito = false;
        if(this.raiz == null) 
        {
            Lista l = new Lista();
            l.insertar(rango, 1);
            this.raiz = new NodoAVLMapeoM(dominio, l, null, null);
            exito = true;
        }
        else
        {
            exito = _asociarAux(this.raiz, dominio, rango);
        }
        if(exito)
        {
            this.raiz.recalcularAltura();
            this._comprobarBalance(this.raiz);
        }
        return exito;
    }

    private Lista obtenerAux(NodoAVLMapeoM n, Comparable dominio)
    {
        Lista obtenido = null;
        if(n != null)
        {
            if(n.getDominio().compareTo(dominio) == 0)
                obtenido = n.getRango();
            else if (n.getDominio().compareTo(dominio) > 0)
            {
                obtenido = obtenerAux(n.getIzquierdo(), dominio);
            }
            else
            {
                obtenido = obtenerAux(n.getDerecho(), dominio);
            }
        }
        return obtenido;
    }
    public Lista obtenerValor(Comparable dominio)
    {
        Lista obtenido = null;
        if(this.raiz != null)
        {
            if(this.raiz.getDominio().compareTo(dominio) == 0)
            {
                obtenido = this.raiz.getRango();
            }
            else if(this.raiz.getDominio().compareTo(dominio) > 0)
            {
                obtenido = obtenerAux(this.raiz.getIzquierdo(), dominio);
            }
            else
            {
                obtenido = obtenerAux(this.raiz.getDerecho(), dominio);
            }
        }
        return obtenido;
    }
}
