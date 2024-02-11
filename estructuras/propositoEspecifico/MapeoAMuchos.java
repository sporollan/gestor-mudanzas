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

    public String toString1()
    {
        return toStringAux1(raiz, "");
    }
    private String toStringAux1(NodoAVLMapeoM aux, String s)
    {
        if(aux != null)
        {
            s = s + aux.getDominio() + aux.getRango() + "\n";
            s = toStringAux1(aux.getIzquierdo(), s);
            s = toStringAux1(aux.getDerecho(), s);
        }
        return s;
    }

    private void rotarDerecha(NodoAVLMapeoM n)
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

    private void rotarIzquierda(NodoAVLMapeoM n)
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
            if(l.localizar(rango) == -1)
            {
                l.insertar(rango, l.longitud()+1);
            }
            else
            {
                exito = false;
            }
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
        this.comprobarBalance(n);
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
            this.comprobarBalance(this.raiz);
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
    public boolean desasociar(Comparable clave, Object rango)
    {
        // recibe clave, la busca en el arbol y devuelve si pudo elminarse
        boolean exito = this.raiz!=null;
        if(exito)
        {
            // si la raiz existe se continua recorriendo
            exito = eliminarAux(this.raiz, clave, null, false, rango);
        }
        return exito;
    }

    private boolean eliminarAux(NodoAVLMapeoM n, Comparable clave, NodoAVLMapeoM p, boolean esIzquierdo, Object rango)
    {
        // n es el nodo al comparar con la clave
        // p es su padre. esizquierdo es si es hijo izquierdo
        // esto es necesario realizar la eliminacion
        boolean exito = false;
        if(n != null)
        {
            // buscar clave
            if(clave.compareTo(n.getDominio())==0)
            {
                Lista rangoN = n.getRango();
                int indexRango = rangoN.localizar(rango);
                if(indexRango != -1)
                {
                    // desasociar
                    rangoN.eliminar(indexRango);
                }
                if(rangoN.esVacia())
                {
                    // elimino el nodo si el rango es vacio
                    NodoAVLMapeoM i, d, reemplazo=null;
                    boolean dobleHijo=false;
                    i = n.getIzquierdo();
                    d = n.getDerecho();
                    // evaluo cual es el reemplazo
                    if (i == null && d == null)
                    {
                        // caso 1, el nodo es una hoja
                        reemplazo = null;
                    }
                    else if(i == null || d == null)
                    {
                        // caso 2, el nodo tiene un solo hijo

                        if(i != null)
                        {
                            reemplazo = i;
                            n.setIzquierdo(null);
                        }
                        else if(d != null)
                        {
                            reemplazo = d;
                            n.setDerecho(null);
                        }
                    }
                    else
                    {
                        // caso 3, el nodo tiene 2 hijos
                        // utilizo el metodo de obtener candidato A
                        reemplazo = obtenerCandidatoA(n);
                        dobleHijo = true;

                    }
                    // evaluo donde insertar el reemplazo
                    NodoAVLMapeoM encontrado, derecho, izquierdo;
                    if(p == null)
                    {
                        // inserto en la raiz
                        if(this.raiz != null && reemplazo != null)
                        {
                            izquierdo = this.raiz.getIzquierdo();
                            derecho = this.raiz.getDerecho();
                            reemplazo.setIzquierdo(izquierdo);
                            reemplazo.setDerecho(derecho);
                            this.raiz.recalcularAltura();

                        }
                        this.raiz = reemplazo;
                    }
                    else if(esIzquierdo)
                    {
                        // elimino izquierdo
                        encontrado = p.getIzquierdo();
                        izquierdo = null;
                        derecho = null;

                        if(encontrado!=null)
                        {
                            izquierdo = encontrado.getIzquierdo();
                            derecho = encontrado.getDerecho();
                        }

                        p.setIzquierdo(reemplazo);
                        if(reemplazo != null)
                        {
                            p.getIzquierdo().setIzquierdo(izquierdo);
                            if(dobleHijo)
                                p.getIzquierdo().setDerecho(derecho);
                        }
                        this.raiz.recalcularAltura();
                        if(reemplazo != null)
                            comprobarBalance(reemplazo);
                        else
                            comprobarBalance(p);
                    }
                    else
                    {
                        // elimino derecho
                        encontrado = p.getDerecho();
                        derecho = null;
                        izquierdo = null;

                        if(encontrado!=null)
                        {
                            derecho = encontrado.getDerecho();
                            izquierdo = encontrado.getIzquierdo();
                        }

                        p.setDerecho(reemplazo);
                        if(reemplazo != null)
                        {
                            p.getDerecho().setDerecho(derecho);
                            if(dobleHijo)
                                p.getDerecho().setIzquierdo(izquierdo);
                        }
                        this.raiz.recalcularAltura();
                        if(reemplazo != null)
                            comprobarBalance(reemplazo);
                        else
                            comprobarBalance(p);
                    }
                }
                exito = true;
            }
            else if(clave.compareTo(n.getDominio()) < 0)
            {
                // buscar por izquierda
                exito = eliminarAux(n.getIzquierdo(), clave, n, true, rango);
            }
            else
            {
                // buscar por derecha
                exito = eliminarAux(n.getDerecho(), clave, n, false, rango);
            }
        }
        if(this.raiz != null)
            this.raiz.recalcularAltura();
        return exito;
    }
    private NodoAVLMapeoM obtenerCandidatoA(NodoAVLMapeoM n)
    {
        NodoAVLMapeoM i = n.getIzquierdo();
        NodoAVLMapeoM candidatoA;
        if(i.getDerecho() == null)
        {
            candidatoA = i;
            n.setIzquierdo(null);
        }
        else
        {
            candidatoA = obtenerCandidatoAAux(i);
        }
        return candidatoA;
    }

    private NodoAVLMapeoM obtenerCandidatoAAux(NodoAVLMapeoM n)
    {
        NodoAVLMapeoM candidatoA;
        NodoAVLMapeoM d = n.getDerecho();
        if(d.getDerecho() == null)
        {
            candidatoA = d;
            n.setDerecho(null);
        }
        else
        {
            candidatoA = obtenerCandidatoAAux(d);
        }
        return candidatoA;
    }
    private void comprobarBalance(NodoAVLMapeoM n)
    {
        int b, bh;
        NodoAVLMapeoM nh = null;
        int ai = -1;
        int ad = -1;
        if(n != null)
        {
            b = this.getBalance(n);
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
                bh = this.getBalance(nh);
                if(b == 2)
                {
                    if(bh == 1 || bh == 0)
                    {
                        // simple a derecha
                        this.rotarDerecha(n);
                        this.raiz.recalcularAltura();
                    }
                    else if(bh == -1)
                    {
                        // doble izquierda-derecha
                        this.rotarIzquierda(nh);
                        this.rotarDerecha(n);
                        this.raiz.recalcularAltura();
                    }
                }
                else if(b == -2)
                {
                    if(bh == 0 || bh == -1)
                    {
                        // simple a izquierda
                        this.rotarIzquierda(n);
                        this.raiz.recalcularAltura();

                    }
                    else if(bh == 1)
                    {
                        // doble derecha-izquierda
                        this.rotarDerecha(nh);
                        this.rotarIzquierda(n);
                        this.raiz.recalcularAltura();
                    }
                }
            }
            buscarYBalancearPadre(this.raiz, n);
        }
    }

    private boolean buscarYBalancearPadre(NodoAVLMapeoM p, NodoAVLMapeoM n)
    {
        boolean exito = false;
        if(p!=null)
        {
            if(p.getDerecho() == n || p.getIzquierdo() == n)
            {
                comprobarBalance(p);
                exito = true;
            }
            else
            {
                exito = buscarYBalancearPadre(p.getIzquierdo(), n);
                if(!exito)
                {
                    exito = buscarYBalancearPadre(p.getDerecho(), n);
                }
            }
        }
        return exito;
    }
    private int getBalance(NodoAVLMapeoM n)
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

    @Override
    public String toString()
    {
        String s = "";
        NodoAVLMapeoM n = this.raiz; 
        NodoAVLMapeoM i;
        NodoAVLMapeoM d;
        if(n!=null)
        {
            s += n.getDominio() + "(" + n.getAltura() + "): ";
            i = this.raiz.getIzquierdo();
            s += addElem(i);

            s += ", ";

            d = this.raiz.getDerecho();
            s += addElem(d);

            s += this.toStringAux(n.getIzquierdo());
            s += this.toStringAux(n.getDerecho());
        }
        return s;
    }

    private String toStringAux(NodoAVLMapeoM n)
    {
        String s = "";
        if(n != null)
        {
            s += "\n";
            s += n.getDominio() + "(" + n.getAltura() + ")";
            s += ": ";
            s += addElem(n.getIzquierdo());
            s += ", ";
            s += addElem(n.getDerecho());

            s += this.toStringAux(n.getIzquierdo());
            s += this.toStringAux(n.getDerecho());
        }
        return s;
    }

    private String addElem(NodoAVLMapeoM n)
    {
        String s = "";
        if(n != null)
        {
            s += n.getDominio() + "(" + n.getAltura() + ")";
        }
        else
            s += null;
        return s;
    }
}
