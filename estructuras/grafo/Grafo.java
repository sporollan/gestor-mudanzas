package estructuras.grafo;

import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.Diccionario;

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
        // busco origen
        NodoVert aux = inicio;
        while( aux != null && !aux.getElem().equals(origen) )
        { 
            // o destino
            aux = aux.getSigVertice();
        }
        if(aux != null)
        {
            NodoVert aux2=null;

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
                while(auxAdy != null && !auxAdy.getVertice().getElem().equals(destino))
                {
                    prevAdy = auxAdy;
                    auxAdy = auxAdy.getSigAdyacente();
                }
                if(auxAdy != null && auxAdy.getVertice().getElem().equals(destino))
                {
                    aux2 = auxAdy.getVertice();
                    if(aux2 != null)
                    {
                        prevAdy.setSigAdyacente(auxAdy.getSigAdyacente());
                        exito = true;
                    }
                }
            }

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
                while(auxAdy != null && !auxAdy.getVertice().getElem().equals(origen))
                {
                    prevAdy = auxAdy;
                    auxAdy = auxAdy.getSigAdyacente();
                }
                if(auxAdy != null && auxAdy.getVertice().getElem().equals(origen))
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

    public Lista obtenerCaminoPorPeso(Object origen, Object destino)
    {
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        Lista camino = new Lista();
//        camino.insertar(Float.MAX_VALUE, 1);

        while((auxO == null || auxD == null) && aux != null)
        {
            if (aux.getElem().equals(origen)) auxO=aux;
            if (aux.getElem().equals(destino)) auxD=aux;
            aux = aux.getSigVertice();
        }
        Diccionario visitados = new Diccionario();

        if(auxO != null && auxD != null)
        {
            Lista l = new Lista();
            l.insertar((float)0, 1);
            l.insertar(true, 2);
            visitados.insertar((Comparable)auxO.getElem(), l);
            obtenerCaminoPorPesoAux(auxO, auxD, visitados, camino, 0, null);
        }

        NodoAdy ady;
        NodoVert vis=null;
        float pesoMin=(float)-1;
        float pesoVert;
        float pesototal = (float)((Lista)visitados.obtenerInformacion((Comparable)auxD.getElem())).recuperar(1);
        camino.insertar(auxD.getElem(), 1);
        while(auxD != auxO)
        {
            ady = auxD.getPrimerAdy();
            auxD = ady.getVertice();
            Lista l =((Lista)visitados.obtenerInformacion((Comparable)ady.getVertice().getElem()));
            if(l != null)
                pesoMin = (float)(l.recuperar(1)) + (float)ady.getEtiqueta();
            while(ady != null)
            {
                vis = ady.getVertice();
                l = ((Lista)visitados.obtenerInformacion((Comparable)vis.getElem()));
                if(l != null)
                {
                    pesoVert = (float)(l.recuperar(1)) + (float)ady.getEtiqueta();
                    if(pesoMin > pesoVert)
                    {
                        pesoMin = pesoVert;
                        auxD = vis;
                    }
                }
                ady = ady.getSigAdyacente();
            }
            camino.insertar(auxD.getElem(), 1);
        }
        camino.insertar(pesototal, camino.longitud()+1);
        return camino;
    }

    public void obtenerCaminoPorPesoAux(NodoVert nodoActual, NodoVert nodoDestino, Diccionario visitados, Lista camino, float distancia, NodoVert prev)
    {
        Lista nodosAVisitar = new Lista();
        while(nodoActual != nodoDestino)
        {
            //System.out.println("Estoy en " + nodoActual.getElem());
            float pesoNodoActual = (float)(((Lista)visitados.obtenerInformacion((Comparable)nodoActual.getElem())).recuperar(1));
            //System.out.println(pesoNodoActual);
            NodoAdy caminoVisitante;
            NodoVert nodoAVisitar;
            float pesoRecorrido;
            NodoAdy ady = nodoActual.getPrimerAdy();

            while(ady != null)
            {

                // recorro nodosavisitar actualizando el peso de cada uno
                caminoVisitante = ady;
                nodoAVisitar = caminoVisitante.getVertice();
                pesoRecorrido = (float)caminoVisitante.getEtiqueta();
                boolean existeClave = visitados.existeClave((Comparable)nodoAVisitar.getElem());

                if(existeClave)
                {
                    if((float)(((Lista)visitados.obtenerInformacion((Comparable)nodoAVisitar.getElem())).recuperar(1)) > (pesoRecorrido + pesoNodoActual))
                    {
                        Lista l = (Lista)visitados.obtenerInformacion((Comparable)nodoAVisitar.getElem());
                        l.eliminar(1);
                        l.insertar(pesoRecorrido + pesoNodoActual, 1);       
                    }
                }
                else
                {
                    Lista l = new Lista();
                    l.insertar(pesoRecorrido + pesoNodoActual, 1);
                    l.insertar(true, 2);
                    visitados.insertar((Comparable)nodoAVisitar.getElem(), l);
                }
                if(prev != nodoAVisitar && (boolean)(((Lista)visitados.obtenerInformacion((Comparable)(nodoAVisitar.getElem()))).recuperar(2)))
                {
                    boolean noInsertado = true;
                    int i = 1;
                    while(noInsertado && i <= nodosAVisitar.longitud() && nodosAVisitar.localizar(ady) == -1)
                    {
                        // inserto en orden
                        if((float)(((Lista)visitados.obtenerInformacion((Comparable)(nodoAVisitar.getElem()))).recuperar(1)) < 
                           (float)(((Lista)visitados.obtenerInformacion((Comparable)((NodoVert)((NodoAdy)nodosAVisitar.recuperar(i)).getVertice()).getElem()))).recuperar(1))
                        {
                            nodosAVisitar.insertar(ady, i);
                            noInsertado = false;
                        }
                        i = i + 1;
                    }
                    if(noInsertado)
                    {
                        nodosAVisitar.insertar(ady, nodosAVisitar.longitud()+1);
                    }
                }
                ady = ady.getSigAdyacente();
            }
            Lista l = (Lista)visitados.obtenerInformacion((Comparable)nodoActual.getElem());
            l.eliminar(2);
            l.insertar(false, 2);
            while(ady != null)
            {
                // recorro los ady e inserto en nodosavisitar ordenados de menor a mayor
                boolean noInsertado = true;
                int i = 1;
                while(noInsertado && i <= nodosAVisitar.longitud())
                {
                    // inserto en orden
                    if((float)ady.getEtiqueta() < (float)((NodoAdy)nodosAVisitar.recuperar(i)).getEtiqueta())
                    {
                        nodosAVisitar.insertar(ady, i);
                        noInsertado = false;
                    }
                    i = i + 1;
                }
                if(noInsertado)
                {
                    nodosAVisitar.insertar(ady, nodosAVisitar.longitud()+1);
                }
                ady = ady.getSigAdyacente();
            }
            nodoActual = (NodoVert)(((NodoAdy)nodosAVisitar.recuperar(1)).getVertice());
            nodosAVisitar.eliminar(1);
        }
    }
    public Lista obtenerCaminoPorNumeroDeNodos(Object origen, Object destino)
    {
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        Lista camino = new Lista();
//        camino.insertar(Float.MAX_VALUE, 1);

        while((auxO == null || auxD == null) && aux != null)
        {
            if (aux.getElem().equals(origen)) auxO=aux;
            if (aux.getElem().equals(destino)) auxD=aux;
            aux = aux.getSigVertice();
        }
        Diccionario visitados = new Diccionario();

        if(auxO != null && auxD != null)
        {
            Lista l = new Lista();
            l.insertar((float)0, 1);
            l.insertar(true, 2);
            visitados.insertar((Comparable)auxO.getElem(), l);
            obtenerCaminoPorNumeroDeNodosAux(auxO, auxD, visitados, camino, 0, null);
        }

        NodoAdy ady;
        NodoVert vis=null;
        float pesoMin=(float)-1;
        float pesoVert;
        float pesototal = (float)(((Lista)visitados.obtenerInformacion((Comparable)auxD.getElem())).recuperar(1));
        camino.insertar(auxD.getElem(), 1);
        while(auxD != auxO)
        {
            pesoMin = Float.MAX_VALUE;
            ady = auxD.getPrimerAdy();
            auxD = ady.getVertice();
            Lista l =((Lista)visitados.obtenerInformacion((Comparable)ady.getVertice().getElem()));
            if(l != null)
                pesoMin = (float)(l.recuperar(1)) + (float)1;
            while(ady != null)
            {
                vis = ady.getVertice();
                l = ((Lista)visitados.obtenerInformacion((Comparable)vis.getElem()));
                if(l != null)
                {
                    pesoVert = (float)(l.recuperar(1)) + (float)1;
                    if(pesoMin > pesoVert)
                    {
                        pesoMin = pesoVert;
                        auxD = vis;
                    }
                }
                ady = ady.getSigAdyacente();
            }
            camino.insertar(auxD.getElem(), 1);
        }
        camino.insertar(pesototal, camino.longitud()+1);
        return camino;
    }

    public void obtenerCaminoPorNumeroDeNodosAux(NodoVert nodoActual, NodoVert nodoDestino, Diccionario visitados, Lista camino, float distancia, NodoVert prev)
    {
        Lista nodosAVisitar = new Lista();
        while(nodoActual != nodoDestino)
        {
            //System.out.println("Estoy en " + nodoActual.getElem());
            float pesoNodoActual = (float)(((Lista)visitados.obtenerInformacion((Comparable)nodoActual.getElem())).recuperar(1));
            //System.out.println(pesoNodoActual);
            NodoAdy caminoVisitante;
            NodoVert nodoAVisitar;
            float pesoRecorrido;
            NodoAdy ady = nodoActual.getPrimerAdy();

            while(ady != null)
            {

                // recorro nodosavisitar actualizando el peso de cada uno
                caminoVisitante = ady;
                nodoAVisitar = caminoVisitante.getVertice();
                pesoRecorrido = (float)1;
                boolean existeClave = visitados.existeClave((Comparable)nodoAVisitar.getElem());

                if(existeClave)
                {
                    if((float)(((Lista)visitados.obtenerInformacion((Comparable)nodoAVisitar.getElem())).recuperar(1)) > (pesoRecorrido + pesoNodoActual))
                    {
                        Lista l = (Lista)visitados.obtenerInformacion((Comparable)nodoAVisitar.getElem());
                        l.eliminar(1);
                        l.insertar(pesoRecorrido + pesoNodoActual, 1);       
                    }
                }
                else
                {
                    Lista l = new Lista();
                    l.insertar(pesoRecorrido + pesoNodoActual, 1);
                    l.insertar(true, 2);
                    visitados.insertar((Comparable)nodoAVisitar.getElem(), l);
                }
                if(prev != nodoAVisitar && (boolean)(((Lista)visitados.obtenerInformacion((Comparable)(nodoAVisitar.getElem()))).recuperar(2)))
                {
                    if(nodosAVisitar.localizar(ady) == -1)
                    {
                        // inserto
                        nodosAVisitar.insertar(ady, nodosAVisitar.longitud()+1);
                    }
                }
                ady = ady.getSigAdyacente();
            }
            Lista l = (Lista)visitados.obtenerInformacion((Comparable)nodoActual.getElem());
            l.eliminar(2);
            l.insertar(false, 2);
            while(ady != null)
            {
                // recorro los ady e inserto en nodosavisitar ordenados de menor a mayor
                boolean noInsertado = true;
                int i = 1;
                while(noInsertado && i <= nodosAVisitar.longitud())
                {
                    // inserto en orden
                    if((float)ady.getEtiqueta() < (float)((NodoAdy)nodosAVisitar.recuperar(i)).getEtiqueta())
                    {
                        nodosAVisitar.insertar(ady, i);
                        noInsertado = false;
                    }
                    i = i + 1;
                }
                if(noInsertado)
                {
                    nodosAVisitar.insertar(ady, nodosAVisitar.longitud()+1);
                }
                ady = ady.getSigAdyacente();
            }
            nodoActual = (NodoVert)(((NodoAdy)nodosAVisitar.recuperar(1)).getVertice());
            nodosAVisitar.eliminar(1);
        }
    }

    public Lista obtenerCaminoPasandoPorNodo(Object origen, Object destino, Object c)
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
            caminos = obtenerCaminoPasandoPorNodoAux(auxO, 0, auxD, auxC,  visitados, caminos, false);
        }
        return caminos;    
    }


    public Lista obtenerCaminoPasandoPorNodoAux(NodoVert n, float distancia, NodoVert dest,NodoVert c, 
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

                    // compruebo si vale la pena seguir recorriendo (no fue visitado ni es el destino)
                    // (!ady.getVertice().equals(dest) || (ady.getVertice().equals(dest) && pasoPorC))
                    if(vis.localizar(ady.getVertice().getElem()) < 0 )
                    {
                        // clono visitados, cada camino almacena sus propios visitados
                        Lista v = vis.clone();
                        
                        // recorro el camino hacia el proximo vertice
                        obtenerCaminoPasandoPorNodoAux(ady.getVertice(), distancia+(float)ady.getEtiqueta(), dest, c, v, caminos, pasoPorC);
                    }
                    // continuo recorriendo el proximo camino
                    ady = ady.getSigAdyacente();
                }
            }else if(pasoPorC)
            {
                // si el nodo actual es el destino
                // inserto el vertobtenerCaminoPorNumeroDeNodosice en visitados
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
            //caminos = obtenerCaminoPorPesoAux(auxO, 0, auxD, visitados, caminos);
        }
        Lista ret = null;
        if(caminos.longitud() > 1)
            ret = (Lista)caminos.recuperar(caminos.longitud());
        return ret;
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



}
