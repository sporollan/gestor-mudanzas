package lib.conjuntistas;

import lib.lineales.dinamicas.Lista;

public class Grafo {
    NodoVert inicio;
    public NodoVert ubicarVertice(Object buscado)
    {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado))
        {
            aux = aux.getSigVertice();
        }
        return aux;
    }
    public boolean insertarVertice(Object nuevoVertice)
    {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if(aux == null)
        {
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
        }
        return exito;
    }
    public Lista listarEnProfundidad()
    {
        Lista visitados = new Lista();

        NodoVert aux = this.inicio;
        while( aux != null)
        {
            if(visitados.localizar(aux.getElem()) < 0)
            {
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }
    private void listarEnProfundidadAux(NodoVert n, Lista vis)
    {
        if(n != null)
        {
            vis.insertar(n.getElem(), vis.longitud() + 1);
            NodoAdy ady = n.getPrimerAdy();
            while(ady != null)
            {
                if(vis.localizar(ady.getVertice().getElem()) < 0)
                {
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }
    public boolean existeCamino(Object origen, Object destino)
    {
        boolean exito = false;
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;

        while((auxO == null || auxD == null) && aux != null)
        {
            if (aux.getElem().equals(origen)) auxO=aux;
            if (aux.getElem().equals(destino)) auxD=aux;
            aux = aux.getSigVertice();
        }

        if(auxO != null && auxD != null)
        {
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);
        }
        return exito;
    }

    private boolean existeCaminoAux (NodoVert n, Object dest, Lista vis)
    {
        boolean exito = false;
        if( n != null)
        {
            if(n.getElem().equals(dest))
            {
                exito = true;
            }
            else
            {
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null)
                {
                    if(vis.localizar(ady.getVertice().getElem()) < 0)
                    {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    private void insertarNodoAdy(NodoVert nodoOrigen, NodoVert nodoDestino, Object e)
    {
        NodoAdy adyO;
        adyO = nodoOrigen.getPrimerAdy();
        if(adyO == null)
        {
            nodoOrigen.setPrimerAdy(new NodoAdy(nodoDestino, null, e));
        }
        else
        {
            while(adyO.getSigAdyacente() != null)
                adyO = adyO.getSigAdyacente();
            adyO.setSigAdyacente(new NodoAdy(nodoDestino, null, e));
        }
    }

    public boolean insertarArco(Object o, Object d, Object e)
    {
        boolean exito = false;
        NodoVert nodoOrigen = this.ubicarVertice(o);
        NodoVert nodoDestino = this.ubicarVertice(d);

        if(nodoDestino != null && nodoOrigen != null)
        {
            exito = true;
            insertarNodoAdy(nodoOrigen, nodoDestino, e);
            insertarNodoAdy(nodoDestino, nodoOrigen, e);
        }
        return exito;
    }
    public Object obtener(Object origen, Object destino)
    {
        Object etiqueta = null;
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;

        while((auxO == null || auxD == null) && aux != null)
        {
            if (aux.getElem().equals(origen)) auxO=aux;
            if (aux.getElem().equals(destino)) auxD=aux;
            aux = aux.getSigVertice();
        }

        if(auxO != null && auxD != null)
        {
            Lista visitados = new Lista();
            etiqueta = obtenerAux(auxO, null, destino, visitados);
        }
        return etiqueta;
    }

    private Object obtenerAux(NodoVert n, Object etiqueta, Object dest, Lista vis)
    {
        Object ret = null;
        if(n != null)
        {
            if(!n.getElem().equals(dest))
            {
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (etiqueta == null && ady != null)
                {
                    if(vis.localizar(ady.getVertice().getElem()) < 0)
                    {
                        etiqueta = obtenerAux(ady.getVertice(), ady.getEtiqueta(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }else
            {
                ret = etiqueta;
            }
        }
        return etiqueta;
    }

    public Lista obtenerCaminoPorDistancia(Object origen, Object destino)
    {
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        Lista caminos = new Lista();
        caminos.insertar((float)999999, 1);

        while((auxO == null || auxD == null) && aux != null)
        {
            if (aux.getElem().equals(origen)) auxO=aux;
            if (aux.getElem().equals(destino)) auxD=aux;
            aux = aux.getSigVertice();
        }

        if(auxO != null && auxD != null)
        {
            Lista visitados = new Lista();
            caminos = obtenerCaminoPorDistanciaAux(auxO, 0, destino, visitados, caminos);
        }
        return caminos;
    }

    public Lista obtenerCaminoPorDistanciaAux(NodoVert n, float distancia, Object dest, Lista vis, Lista caminos)
    {
        if(n != null)
        {
            if(!n.getElem().equals(dest))
            {
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                float newDistancia;
                while (ady != null)
                {
                    newDistancia = (float)ady.getEtiqueta() + distancia;
                    if(vis.localizar(ady.getVertice().getElem()) < 0 && newDistancia < (float)caminos.recuperar(1))
                    { 
                        Lista v = vis.clone();
                        System.out.println(n.getElem() + " " + ady.getVertice().getElem() + " " + ady.getEtiqueta());
                        obtenerCaminoPorDistanciaAux(ady.getVertice(), distancia + (float)ady.getEtiqueta(), dest, v, caminos);
                    }
                    ady = ady.getSigAdyacente();
                }
            }else
            {
                vis.insertar(n.getElem(), vis.longitud()+1);
                vis.insertar(distancia, vis.longitud()+1);
                caminos.insertar(vis, caminos.longitud()+1);
                if(distancia < (float)caminos.recuperar(1))
                {
                    caminos.eliminar(1);
                    caminos.insertar(distancia, 1);
                }
            }
        }
        return caminos;
    }

    public Lista obtenerCaminoPorNodo()
    {
        return new Lista();
    }
}
