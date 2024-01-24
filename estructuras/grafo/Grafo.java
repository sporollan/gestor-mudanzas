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

        boolean exito = true;
        if(inicio == null)
        {
            // caso Grafo Vacio
            System.out.println("caso 1");
            exito = false;
        }
        else if(inicio.getElem().equals(buscado))
        {
            // caso eliminar primer NodoVert
            System.out.println("caso 2");
            inicio = inicio.getSigVertice();
        }
        // caso eliminar nodovert no extremo
        {
            System.out.println("caso 3");
            NodoVert prev = inicio;
            NodoVert aux = inicio.getSigVertice();
            while( aux != null && !aux.getElem().equals(buscado) )
            {
                prev = aux;
                aux = aux.getSigVertice();
            }
            if(aux != null && aux.getElem().equals(buscado))
            {
                prev.setSigVertice(aux.getSigVertice());
            }
            else
            {
                exito = false;
            }
        }
        return exito;
    }

    public boolean eliminarArco(Object origen, Object destino)
    {
        boolean exito = false;
        // busco origen
        NodoVert aux = inicio;
        while( aux != null && !aux.getElem().equals(origen) )
        {
            aux = aux.getSigVertice();
        }
        if(aux != null)
        {
            // busco arco destino
            // caso, primerady es el destino
            System.out.println("buscando primerady");
            System.out.println(aux.getPrimerAdy().getVertice().getElem());
            if(aux.getPrimerAdy().getVertice().getElem().equals(destino))
            {
                exito = true;
                aux.setPrimerAdy(aux.getPrimerAdy().getSigAdyacente());
            } else {
                //caso, arco destino no es primerady
                NodoAdy prevAdy = aux.getPrimerAdy();
                NodoAdy auxAdy = prevAdy.getSigAdyacente();
                while(auxAdy != null && !auxAdy.getVertice().getElem().equals(destino))
                {
                    System.out.println("Buscando " + auxAdy.getVertice().getElem());
                    prevAdy = auxAdy;
                    auxAdy = auxAdy.getSigAdyacente();
                }
                if(auxAdy != null && auxAdy.getVertice().getElem().equals(destino))
                {
                    prevAdy.setSigAdyacente(auxAdy.getSigAdyacente());
                    exito = true;
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
        boolean exito;
        NodoVert aux = this.ubicarVertice(origen);
        NodoAdy auxAdy = aux.getPrimerAdy();

        exito = auxAdy != null && auxAdy.getVertice().getElem().equals(destino);
        while(auxAdy != null && !exito)
        {
            auxAdy = auxAdy.getSigAdyacente();
            exito = auxAdy != null && auxAdy.getVertice().getElem().equals(destino);
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
        caminos.insertar(Float.MAX_VALUE, 1);

        while((auxO == null || auxD == null) && aux != null)
        {
            if (aux.getElem().equals(origen)) auxO=aux;
            if (aux.getElem().equals(destino)) auxD=aux;
            aux = aux.getSigVertice();
        }

        if(auxO != null && auxD != null)
        {
            Lista visitados = new Lista();
            caminos = obtenerCaminoPorDistanciaAux(auxO, 0, auxD, visitados, caminos);
        }
        return (Lista)caminos.recuperar(caminos.longitud());
    }

    public Lista obtenerCaminoPorDistanciaAux(NodoVert n, float distancia, NodoVert dest, Lista vis, Lista caminos)
    {
        if(n != null)
        {
            if(!n.equals(dest))
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

    public Lista obtenerCaminoPorCiudades(Object origen, Object destino)
    {
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        Lista caminos = new Lista();
        caminos.insertar(Integer.MAX_VALUE, 1);

        // busco origen y destino
        while((auxO == null || auxD == null) && aux != null)
        {
            if (aux.getElem().equals(origen)) auxO=aux;
            if (aux.getElem().equals(destino)) auxD=aux;
            aux = aux.getSigVertice();
        }

        // busco el camino
        if(auxO != null && auxD != null)
        {
            Lista visitados = new Lista();
            caminos = obtenerCaminoPorCiudadesAux(auxO, 0, auxD, visitados, caminos);
        }
        return (Lista)caminos.recuperar(caminos.longitud());
    }


    public Lista obtenerCaminoPorCiudadesAux(NodoVert n, int cantCiudades, NodoVert dest, Lista vis, Lista caminos)
    {
        if(n != null)
        {

            if(!n.equals(dest))
            {
                // si el nodo actual no es el destino
                // inserto el nodo en los visitados
                vis.insertar(n.getElem(), vis.longitud() + 1);

                // recorro los caminos
                NodoAdy ady = n.getPrimerAdy();
                cantCiudades+=1;
                while (ady != null)
                {
                    // si el camino existe
                    // calculo la distancia total recorrida hasta el momento

                    // compruebo si vale la pena seguir recorriendo (no fue visitado ni supera la distancia maxima)
                    if(vis.localizar(ady.getVertice().getElem()) < 0 && cantCiudades < (int)caminos.recuperar(1))
                    { 
                        // clono visitados, cada camino almacena sus propios visitados
                        Lista v = vis.clone();

                        // recorro el camino hacia el proximo vertice
                        obtenerCaminoPorCiudadesAux(ady.getVertice(), cantCiudades, dest, v, caminos);
                    }
                    // continuo recorriendo el proximo camino
                    ady = ady.getSigAdyacente();
                }
            }else
            {
                // si el nodo actual es el destino
                // inserto el vertice en visitados
                vis.insertar(n.getElem(), vis.longitud()+1);
                vis.insertar(cantCiudades, vis.longitud()+1);
                
                // inserto visitados en caminos
                caminos.insertar(vis, caminos.longitud()+1);

                // calculo la nueva distancia maxima
                if(cantCiudades < (int)caminos.recuperar(1))
                {
                    caminos.eliminar(1);
                    caminos.insertar(cantCiudades, 1);
                }
            }
        }
        return caminos;
    }

    public Lista obtenerCaminoPasandoPorCiudad(Object origen, Object destino, Object c)
    {
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert auxC = null;
        NodoVert aux = this.inicio;
        Lista caminos = new Lista();
        caminos.insertar(Float.MAX_VALUE, 1);

        // busco origen, destino y c
        while((auxO == null || auxD == null || auxC == null) && aux != null)
        {
            if (aux.getElem().equals(origen)) auxO=aux;
            if (aux.getElem().equals(destino)) auxD=aux;
            if (aux.getElem().equals(c)) auxC=aux;
            aux = aux.getSigVertice();
        }

        // busco el camino
        if(auxO != null && auxD != null && auxC != null)
        {
            Lista visitados = new Lista();
            caminos = obtenerCaminoPasandoPorCiudadAux(auxO, 0, auxD, auxC,  visitados, caminos, false);
        }
        return caminos;    
    }


    public Lista obtenerCaminoPasandoPorCiudadAux(NodoVert n, float distancia, NodoVert dest,NodoVert c, 
                                                Lista vis, Lista caminos, boolean pasoPorC)
    {
        if(n != null)
        {   
            if(!pasoPorC)
                pasoPorC = n.equals(c);
            if(!n.equals(dest))
            {
                // si el nodo actual no es el destino
                // inserto el nodo en los visitados
                vis.insertar(n.getElem(), vis.longitud() + 1);

                // recorro los caminos
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null)
                {
                    // si el camino existe
                    // calculo la distancia total recorrida hasta el momento

                    // compruebo si vale la pena seguir recorriendo (no fue visitado ni supera la distancia maxima)
                    if(vis.localizar(ady.getVertice().getElem()) < 0)
                    { 
                        // clono visitados, cada camino almacena sus propios visitados
                        Lista v = vis.clone();

                        // recorro el camino hacia el proximo vertice
                        obtenerCaminoPasandoPorCiudadAux(ady.getVertice(), distancia+(float)ady.getEtiqueta(), dest, c, v, caminos, pasoPorC);
                    }
                    // continuo recorriendo el proximo camino
                    ady = ady.getSigAdyacente();
                }
            }else if(pasoPorC)
            {
                // si el nodo actual es el destino
                // inserto el vertobtenerCaminoPorCiudadesice en visitados
                vis.insertar(n.getElem(), vis.longitud()+1);
                vis.insertar(distancia, vis.longitud()+1);
                
                // inserto visitados en caminos
                caminos.insertar(vis, caminos.longitud()+1);

                // calculo la nueva distancia maxima
                if(distancia < (float)caminos.recuperar(1))
                {
                    caminos.eliminar(1);
                    caminos.insertar(distancia, 1);
                }
            }
        }
        return caminos;
    }

    public Lista obtenerCaminoPorKMMaximos(Object origen, Object destino, float maximo)
    {
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        Lista caminos = new Lista();
        caminos.insertar(maximo, 1);

        while((auxO == null || auxD == null) && aux != null)
        {
            if (aux.getElem().equals(origen)) auxO=aux;
            if (aux.getElem().equals(destino)) auxD=aux;
            aux = aux.getSigVertice();
        }

        if(auxO != null && auxD != null)
        {
            Lista visitados = new Lista();
            caminos = obtenerCaminoPorDistanciaAux(auxO, 0, auxD, visitados, caminos);
        }
        return (Lista)caminos.recuperar(caminos.longitud());
    }

    public Lista obtenerCaminoPorKMMaximos(NodoVert n, float distancia, NodoVert dest, Lista vis, Lista caminos)
    {
        if(n != null)
        {
            if(!n.equals(dest))
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




}
