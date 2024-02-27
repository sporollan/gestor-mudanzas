package estructuras.propositoEspecifico;

import estructuras.lineales.dinamicas.Lista;

// diccionario avl

public class Diccionario {
    NodoAVLDicc raiz;

    public Diccionario()
    {
        this.raiz = null;
    }

    public boolean esVacia()
    {
        return this.raiz == null;
    }

    public boolean insertar(Comparable clave, Object dato)
    {
        // recibe clave y dato
        // devuelve si el dato fue insertado
        boolean exito = false;
        if(this.raiz == null) 
        {
            // si no hay raiz se inserta en la raiz
            this.raiz = new NodoAVLDicc(clave, dato, null, null);
            exito = true;
        }
        else
        {
            // si la raiz existe se continua recorriendo
            exito = insertarAux(this.raiz, clave, dato);
        }

        return exito;
    }

    public boolean eliminar(Comparable clave)
    {
        // recibe clave, la busca en el arbol y devuelve si pudo elminarse
        boolean exito = this.raiz!=null;
        if(exito)
        {
            // si la raiz existe se continua recorriendo
            exito = eliminarAux(this.raiz, clave, null, false);
        }
        return exito;
    }

    private boolean eliminarAux(NodoAVLDicc n, Comparable clave, NodoAVLDicc p, boolean esIzquierdo)
    {
        // n es el nodo al comparar con la clave
        // p es su padre. esizquierdo es si es hijo izquierdo
        // esto es necesario realizar la eliminacion
        boolean exito = false;
        if(n != null)
        {
            // buscar clave
            if(clave.compareTo(n.getClave())==0)
            {
                // encontrado
                NodoAVLDicc i, d, reemplazo=null;
                Comparable areemplazar = n.getClave();
                boolean dobleHijo=false;
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

                    if(i != null)
                    {
                        reemplazo = i;
                        n.setHijoIzquierdo(null);
                    }
                    else if(d != null)
                    {
                        reemplazo = d;
                        n.setHijoDerecho(null);
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
                NodoAVLDicc encontrado, derecho, izquierdo;
                if(p == null)
                {


                    if(raiz.getClave() == areemplazar)
                    {
                        if(reemplazo != null)
                        {
                            izquierdo = n.getHijoIzquierdo();
                            if(reemplazo.getClave().compareTo(izquierdo.getClave()) == 0)
                            {
                                izquierdo = null;
                            }
                            derecho = n.getHijoDerecho();
                            reemplazo.setHijoIzquierdo(izquierdo);
                            reemplazo.setHijoDerecho(derecho);
                            raiz = reemplazo;
                            raiz.recalcularAltura();
                            comprobarBalance(raiz);
                        }
                        else
                        {
                            raiz = reemplazo;
                        }
                    }
                    else
                    {
                        boolean e = reemplazar(raiz.getHijoIzquierdo(), areemplazar, reemplazo);
                        if(!e)
                        {
                            reemplazar(raiz.getHijoDerecho(), areemplazar, reemplazo);
                        }
                    }

                }
                else if(esIzquierdo)
                {
                    // elimino izquierdo
                    encontrado = p.getHijoIzquierdo();
                    izquierdo = null;
                    derecho = null;

                    if(encontrado!=null)
                    {
                        izquierdo = encontrado.getHijoIzquierdo();
                        derecho = encontrado.getHijoDerecho();
                    }

                    p.setHijoIzquierdo(reemplazo);
                    if(reemplazo != null)
                    {
                        p.getHijoIzquierdo().setHijoIzquierdo(izquierdo);
                        if(dobleHijo)
                        {
                            p.getHijoIzquierdo().setHijoDerecho(derecho);
                        }
                        reemplazo.recalcularAltura();
                        comprobarBalance(reemplazo);
                    }
                    //p.recalcularAltura();
                }
                else
                {
                    // elimino derecho
                    encontrado = p.getHijoDerecho();
                    derecho = null;
                    izquierdo = null;

                    if(encontrado!=null)
                    {
                        derecho = encontrado.getHijoDerecho();
                        izquierdo = encontrado.getHijoIzquierdo();
                    }

                    p.setHijoDerecho(reemplazo);
                    if(reemplazo != null)
                    {
                        p.getHijoDerecho().setHijoDerecho(derecho);
                        if(dobleHijo)
                        {
                            p.getHijoDerecho().setHijoIzquierdo(izquierdo);
                        }
                        reemplazo.recalcularAltura();
                        comprobarBalance(reemplazo);
                    }
                    //p.recalcularAltura();
                }
                exito = true;
            }
            else if(clave.compareTo(n.getClave()) < 0)
            {
                // buscar por izquierda
                exito = eliminarAux(n.getHijoIzquierdo(), clave, n, true);
                if(exito)
                {
                    n.recalcularAltura();
                    comprobarBalance(n);
                }
            }
            else
            {
                // buscar por derecha
                exito = eliminarAux(n.getHijoDerecho(), clave, n, false);
                if(exito)
                {
                    n.recalcularAltura();
                    comprobarBalance(n);
                }
            }
        }
        return exito;
    }

    private boolean reemplazar(NodoAVLDicc n, Comparable areemplazar, NodoAVLDicc reemplazo)
    {
        boolean exito =false;
        if(n.getClave() == areemplazar)
        {
            n.setClave(reemplazo.getClave());
            n.setDato(reemplazo.getDato());
            exito = true;
        } 
        else
        {
            exito = reemplazar(n.getHijoIzquierdo(), areemplazar, reemplazo);
            if(!exito)
            {
                exito = reemplazar(n.getHijoDerecho(), areemplazar, reemplazo);
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
            n.setHijoIzquierdo(i.getHijoIzquierdo());
            n.recalcularAltura();
            comprobarBalance(n);
        }
        else
        {
            candidatoA = obtenerCandidatoAAux(i);
            i.recalcularAltura();
            comprobarBalance(i);
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
            n.setHijoDerecho(d.getHijoIzquierdo());
        }
        else
        {
            candidatoA = obtenerCandidatoAAux(d);
            // recalcular altura y verificar control de n
            d.recalcularAltura();
            comprobarBalance(d);
        }
        return candidatoA;
    }

    public boolean existeClave(Comparable clave)
    {
        return existeClaveAux(raiz, clave);
    }

    private boolean existeClaveAux(NodoAVLDicc aux, Comparable clave)
    {
        boolean existe = false;
        if(aux != null)
        {
            if(aux.getClave().compareTo(clave) == 0)
            {
                existe = true;
            }
            else
            {
                existe = existeClaveAux(aux.getHijoIzquierdo(), clave);
                if(!existe)
                {
                    existe = existeClaveAux(aux.getHijoDerecho(), clave);
                }
            }
        }
        return existe;
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
                obtenido = obtenerAux(this.raiz.getHijoIzquierdo(), clave);
            }
            else
            {
                obtenido = obtenerAux(this.raiz.getHijoDerecho(), clave);
            }
        }
        return obtenido;
    }

    public Lista listarClaves()
    {
        Lista claves = new Lista();
        listarClavesAux(claves, raiz);
        return claves;
    }

    private void listarClavesAux(Lista claves, NodoAVLDicc aux)
    {
        if(aux != null)
        {
            claves.insertar(aux.getClave(), claves.longitud()+1);
            listarClavesAux(claves, aux.getHijoIzquierdo());
            listarClavesAux(claves, aux.getHijoDerecho());
        }
    }

    public Lista listarDatos()
    {
        Lista datos = new Lista();
        listarDatosAux(datos, raiz);
        return datos;
    }
    private void listarDatosAux(Lista datos, NodoAVLDicc aux)
    {
        if(aux != null)
        {
            datos.insertar(aux.getDato(), datos.longitud()+1);
            listarDatosAux(datos, aux.getHijoIzquierdo());
            listarDatosAux(datos, aux.getHijoDerecho());
        }
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
            s += addElem(i);

            s += ", ";

            d = this.raiz.getHijoDerecho();
            s += addElem(d);

            s += this.toStringAux(n.getHijoIzquierdo());
            s += this.toStringAux(n.getHijoDerecho());
        }
        return s;
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

    private void rotarDerecha(NodoAVLDicc n)
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
        naux.recalcularAltura();
        n.recalcularAltura();
    }

    private void rotarIzquierda(NodoAVLDicc n)
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
        naux.recalcularAltura();
        n.recalcularAltura();
    }
    
    private boolean insertarAux(NodoAVLDicc n, Comparable clave, Object dato)
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
                exito = insertarAux(n.getHijoIzquierdo(), clave, dato);
            }
            else
            {
                n.setHijoIzquierdo(new NodoAVLDicc(clave, dato, null, null));
            }
        }
        else
        {
            // es mayor
            if(n.getHijoDerecho() != null)
            {
                exito = insertarAux(n.getHijoDerecho(), clave, dato);
            } 
            else
            {
                n.setHijoDerecho(new NodoAVLDicc(clave, dato, null, null));
            }
        }
        if(exito)
        {
            n.recalcularAltura();
            comprobarBalance(n);
        }
        return exito;
    }

    private int getBalance(NodoAVLDicc n)
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

    private void comprobarBalance(NodoAVLDicc n)
    {
        int b, bh;
        NodoAVLDicc nh = null;
        int ai = -1;
        int ad = -1;
        if(n != null)
        {
            b = this.getBalance(n);
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
                bh = this.getBalance(nh);
                if(b == 2)
                {
                    if(bh == 1 || bh == 0)
                    {
                        // simple a derecha
                        this.rotarDerecha(n);
                    }
                    else if(bh == -1)
                    {
                        // doble izquierda-derecha
                        this.rotarIzquierda(nh);
                        this.rotarDerecha(n);
                    }
                }
                else if(b == -2)
                {
                    if(bh == 0 || bh == -1)
                    {
                        // simple a izquierda
                        this.rotarIzquierda(n);
                    }
                    else if(bh == 1)
                    {
                        // doble derecha-izquierda
                        this.rotarDerecha(nh);
                        this.rotarIzquierda(n);
                    }
                }
            }
        }
    }

    private String toStringAux(NodoAVLDicc n)
    {
        String s = "";
        if(n != null)
        {
            s += "\n";
            s += n.getClave() + "(" + n.getAltura() + ")";
            s += ": ";
            s += addElem(n.getHijoIzquierdo());
            s += ", ";
            s += addElem(n.getHijoDerecho());

            s += this.toStringAux(n.getHijoIzquierdo());
            s += this.toStringAux(n.getHijoDerecho());
        }
        return s;

    }

    private String addElem(NodoAVLDicc n)
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

    public Lista listarPrefijo(Comparable pf)
    {
        // se recorre el arbol tratando de mantenerse dentro del rango
        // pf es un prefijo. ej 83
        Lista ciudades = new Lista();
        Comparable min = (int)pf*100;
        Comparable max = (int)min + 100;
        listarPrefijoAux(ciudades, min, max, raiz);
        return ciudades;
    }

    public void listarPrefijoAux(Lista ciudades, Comparable min, Comparable max, NodoAVLDicc n)
    {
        if(n != null)
        {
            if(n.getClave().compareTo(min) >= 0 && n.getClave().compareTo(max) < 0)
            {
                ciudades.insertar(n.getDato(), ciudades.longitud()+1);
                listarPrefijoAux(ciudades, min, max, n.getHijoIzquierdo());
                listarPrefijoAux(ciudades, min, max, n.getHijoDerecho());
            }
            else if(n.getClave().compareTo(min) < 0)
            {
                listarPrefijoAux(ciudades, min, max, n.getHijoDerecho());
            }
            else if(n.getClave().compareTo(max) >= 0)
            {
                listarPrefijoAux(ciudades, min, max, n.getHijoIzquierdo());
            }
        }
    }
}