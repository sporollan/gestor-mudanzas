package estructuras.grafo;

import estructuras.lineales.dinamicas.Lista;

public class Grafo {
    NodoVert inicio;

    private NodoVert ubicarVertice(Object buscado)
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

    public boolean eliminarVertice(Object buscado)
    {
        // se busca y elimina el nodovert buscado

        boolean exito = false;
        if(inicio == null)
        {
            // caso Grafo Vacio
        }
        else if(inicio.getElem().equals(buscado))
        {
            // caso eliminar primer NodoVert
            NodoAdy a = inicio.getPrimerAdy();
            while(a != null)
            {
                eliminarArcos(a.getVertice(), inicio);
                a = a.getSigAdyacente();
            }
            inicio = inicio.getSigVertice();
            exito = true;
        }
        // caso eliminar nodovert no extremo
        {
            NodoVert prev = inicio;
            NodoVert aux = inicio.getSigVertice();
            while( aux != null && !aux.getElem().equals(buscado) )
            {
                prev = aux;
                aux = aux.getSigVertice();
            }
            if(aux != null && aux.getElem().equals(buscado))
            {
                NodoAdy a = aux.getPrimerAdy();
                while(a != null)
                {
                    eliminarArcos(a.getVertice(), aux);
                    a = a.getSigAdyacente();
                }
                prev.setSigVertice(aux.getSigVertice());
                exito = true;
            }
        }
        return exito;
    }

    public void eliminarArcos(NodoVert n, NodoVert origen)
    {
        NodoAdy prev = n.getPrimerAdy();
        NodoAdy a = prev.getSigAdyacente();
        while(a != null)
        {
            if(a.getVertice().equals(origen))
            {
                prev.setSigAdyacente(a.getSigAdyacente());
            }
            prev = a;
            a = a.getSigAdyacente();
        }
    }

    public boolean eliminarArco(Object origen, Object destino)
    {
        boolean exito = false;
        // busco origen o destino
        NodoVert aux = inicio;
        while(aux != null && (!aux.getElem().equals(origen) && !aux.getElem().equals(destino)) )
        { 
            aux = aux.getSigVertice();
        }
        if(aux != null)
        {
            NodoVert aux2=null;
            if(aux.getElem().equals(destino))
            {
                destino = origen;
                origen = aux.getElem();
            }
            // busco para la ruta de ida, de paso busco aux2 el vertice destino
            if(aux.getPrimerAdy().getVertice().getElem().equals(destino))
            {
                // busco arco destino
                // caso, primerady es el destino
                aux2 = aux.getPrimerAdy().getVertice();
                exito = true;
                aux.setPrimerAdy(aux.getPrimerAdy().getSigAdyacente());
            } else {
                //caso, arco destino no es primerady
                NodoAdy prevAdy = aux.getPrimerAdy();
                NodoAdy auxAdy = prevAdy.getSigAdyacente();
                while(auxAdy != null && (
                    !auxAdy.getVertice().getElem().equals(destino)))
                {
                    prevAdy = auxAdy;
                    auxAdy = auxAdy.getSigAdyacente();
                }
                if(auxAdy != null && (auxAdy.getVertice().getElem().equals(destino)))
                {
                    aux2 = auxAdy.getVertice();
                    if(aux2 != null)
                    {
                        prevAdy.setSigAdyacente(auxAdy.getSigAdyacente());
                        exito = true;
                    }
                }
            }
            if(aux2 != null)
            {
                // si aux2 existe, el arco existe
                // repito para la ruta de vuelta
                if(aux2.getPrimerAdy().getVertice().getElem().equals(origen))
                {
                    // busco arco destino
                    // caso, primerady es el destino
                    exito = true;
                    aux2.setPrimerAdy(aux2.getPrimerAdy().getSigAdyacente());
                } else {
                    //caso, arco destino no es primerady
                    NodoAdy prevAdy = aux2.getPrimerAdy();
                    NodoAdy auxAdy = prevAdy.getSigAdyacente();
                    while(auxAdy != null && 
                        (!auxAdy.getVertice().getElem().equals(origen)))
                    {
                        prevAdy = auxAdy;
                        auxAdy = auxAdy.getSigAdyacente();
                    }
                    if(auxAdy != null && 
                        (auxAdy.getVertice().getElem().equals(origen)))
                    {
                        prevAdy.setSigAdyacente(auxAdy.getSigAdyacente());
                        exito = true;
                    }
                }
            }
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

    public boolean existeArco(Object origen, Object destino)
    {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(origen);
        NodoAdy auxAdy = aux.getPrimerAdy();

        while(auxAdy != null && !exito)
        {
            exito = auxAdy.getVertice().getElem().equals(destino);
            auxAdy = auxAdy.getSigAdyacente();
        }
        return exito;
    }

    private void insertarNodoAdy(NodoVert nodoOrigen, NodoVert nodoDestino, float e)
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

    public boolean insertarArco(Object o, Object d, float e)
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
            }
        }
        return etiqueta;
    }

    public String toString()
    {
        String s = "";
        NodoVert vert = inicio;
        NodoAdy ady;
        while(vert != null)
        {
            s = s + "\n" + "(" + vert.getElem() + ")";
            ady = vert.getPrimerAdy();
            while(ady != null)
            {
                s = s + ", " + ady.getVertice().getElem() + ": " + ady.getEtiqueta();
                ady = ady.getSigAdyacente();
            }
            vert = vert.getSigVertice();
        }
        return s;
    }

    public Lista listarDatos()
    {
        Lista s = new Lista();
        Lista arco;
        NodoVert vert = inicio;
        NodoAdy ady;
        while(vert != null)
        {
            arco = new Lista();
            arco.insertar(vert.getElem(), 1);
            ady = vert.getPrimerAdy();
            while(ady != null)
            {
                arco.insertar(ady.getVertice().getElem(), 2);
                arco.insertar(ady.getEtiqueta(), 3);
                s.insertar(arco.clone(), s.longitud()+1);
                ady = ady.getSigAdyacente();
            }
            vert = vert.getSigVertice();
        }
        return s;
    }

    public Lista listarArcos(Object origen)
    {
        Lista destinos = new Lista();
        NodoVert aux = this.ubicarVertice(origen);
        if(aux != null)
        {
            NodoAdy ady = aux.getPrimerAdy();
            while(ady != null)
            {
                destinos.insertar(ady.getVertice().getElem(), destinos.longitud()+1);
                ady = ady.getSigAdyacente();
            }
        }
        return destinos;
    }

    public Lista obtenerCaminoPorPeso(Object origen, Object destino)
    {
        // formato del camino: ["peso", "destino", "nodo", ..., "origen"]
        Lista camino = new Lista();
        camino.insertar((float)0, 1);

        return obtenerCaminoPorPesoAux(ubicarVertice(origen), destino, camino, (float)0, true, Float.MAX_VALUE);
    }

    public Lista obtenerCaminoPorNumeroDeNodos(Object origen, Object destino)
    {
        // formato del camino: ["peso", "destino", "nodo", ..., "origen"]
        Lista camino = new Lista();
        camino.insertar((float)0, 1);

        return obtenerCaminoPorPesoAux(ubicarVertice(origen), destino, camino, (float)0, false, Float.MAX_VALUE);
    }

    public Lista obtenerCaminoPorPesoMaximo(Object origen, Object destino, float pesoMaximo)
    {
        Lista camino = new Lista();
        camino.insertar((float)0, 1);

        return obtenerCaminoPorPesoAux(ubicarVertice(origen), destino, camino, (float)0, true, pesoMaximo);
    }

    private Lista obtenerCaminoPorPesoAux(NodoVert n, Object destino, Lista visitados, float nuevoPeso, boolean usarPesoDeEtiqueta, float maximo)
    {
        Lista caminoMasCorto = null;
        // evito seguir recorriendo si ya fue visitado o si es mayor al maximo
        if(n != null && visitados.localizar(n.getElem())==-1 && nuevoPeso <= maximo)
        {
            // actualizo el camino
            agregarNodoAlCamino(visitados, n.getElem(), nuevoPeso);

            // evaluo si llegue al destino o si continuo buscando
            if(n.getElem().equals(destino))
            {
                // termino de recorrer
                caminoMasCorto = visitados;
            }
            else
            {
                // continuo recorriendo
                NodoAdy ady = n.getPrimerAdy();
                Lista caminoAux;
                float sumaPeso;
                while(ady != null)
                {
                    // sumo el peso del recorrido hasta ahora con el peso del ady, segun si se tiene en cuenta la etiqueta
                    sumaPeso = usarPesoDeEtiqueta ? ady.getEtiqueta() + nuevoPeso : (float)1 + nuevoPeso;
                    // obtengo el camino
                    caminoAux = obtenerCaminoPorPesoAux(ady.getVertice(), destino, visitados.clone(), sumaPeso, usarPesoDeEtiqueta, maximo);
                    if(caminoAux != null)
                    {                    
                        // si caminoAux != null entonces encontro un nuevo caminoMasCorto
                        // actualizo el camino mas corto
                        caminoMasCorto = caminoAux;
                        maximo = (float)caminoMasCorto.recuperar(1);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return caminoMasCorto;
    }

    private void agregarNodoAlCamino(Lista camino, Object elem, Object nuevoPeso)
    {
        // formato: ["peso", "destino", "nodo", ..., "origen"]
        camino.eliminar(1);
        camino.insertar(elem, 1);
        camino.insertar(nuevoPeso, 1);
    }

    public Lista obtenerCaminosPasandoPorNodo(Object origen, Object destino, Object C)
    {
        Lista caminos = new Lista();
        Lista visitados = new Lista();
        visitados.insertar((float)0, 1);
        obtenerCaminosPasandoPorNodoAux(caminos, ubicarVertice(origen), destino, C, false, visitados, (float)0);
        return caminos;
    }

    public void obtenerCaminosPasandoPorNodoAux(Lista caminos, NodoVert n, Object destino, Object C, boolean pasoPorC, Lista visitados, float nuevoPeso)
    {
        // visito el nodo si fue visitado,
        // y segun si paso por c y es el destino
        if(n != null && 
          (pasoPorC || (!pasoPorC && !n.getElem().equals(destino))) && 
          visitados.localizar(n.getElem())==-1)
        {
            // actualizo visitados
            agregarNodoAlCamino(visitados, n.getElem(), nuevoPeso);
            pasoPorC = (pasoPorC || n.getElem().equals(C));
            // compruebo si continuar recorriendo
            if(n.getElem().equals(destino))
            {
                // dejo de recorrer, guardo el camino
                caminos.insertar(visitados, 1);
            }
            else
            {
                // continuo recorriendo
                NodoAdy ady = n.getPrimerAdy();
                while(ady != null)
                {
                    obtenerCaminosPasandoPorNodoAux(caminos, ady.getVertice(), destino, C, pasoPorC, visitados.clone(), nuevoPeso + ady.getEtiqueta());
                    ady = ady.getSigAdyacente();
                }
            }
        }
    }
}
