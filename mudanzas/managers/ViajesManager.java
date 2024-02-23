package mudanzas.managers;

import estructuras.grafo.Grafo;
import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.Diccionario;
import estructuras.propositoEspecifico.MapeoAMuchos;
import mudanzas.datos.Ciudad;
import mudanzas.datos.Solicitud;
import mudanzas.librerias.InputReader;

public class ViajesManager {
    private Grafo rutas;
    private Diccionario ciudades;
    private MapeoAMuchos solicitudes;

    public ViajesManager(Diccionario ciudades, Grafo rutas, MapeoAMuchos solicitudes)
    {
        this.rutas = rutas;
        this.ciudades = ciudades;
        this.solicitudes = solicitudes;
    }

    private void mostrarMenu()
    {
            System.out.println("Gestionar Viajes");
            System.out.println("1. Camino por menor Distancia");
            System.out.println("2. Camino por menos Ciudades");
            System.out.println("3. Camino pasando por Ciudad");
            System.out.println("4. Camino por Distancia Maxima");
            System.out.println("5. Listar Posibles Pedidos Intermedios");
            System.out.println("6. Verificar Camino Perfecto");
    }
    
    public void gestionar()
    {
        String seleccion = "";
        while(!seleccion.equals("q"))
        {
            mostrarMenu();
            seleccion = InputReader.scanString("Seleccion");
            if(seleccion.equals("1"))
                mostrarCaminoMenorDistancia();
            else if(seleccion.equals("2"))
                mostrarCaminoMenosCiudades();
            else if(seleccion.equals("3"))
                mostrarCaminoPasandoPorCiudad();
            else if(seleccion.equals("4"))
                mostrarCaminoKMMaximos();
            else if(seleccion.equals("5"))
                mostrarPedidosIntermedios();
            else if(seleccion.equals("6"))
                verificarCaminoPerfecto();
        }  
    }

    private void mostrarCaminoMenorDistancia()
    {
        // obtengo y muestro el camino con distancia menor
        Lista camino = obtenerCaminoMenorDistancia();
        mostrarCamino(camino);
    }

    private void mostrarCaminoMenosCiudades()
    {
        // obtengo y muestro el camino con menor cantidad de ciudades
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = InputReader.scanCodigos(names);
        Lista camino = null;

        if(codigos[codigos.length-1]!=-1)
        {
            camino = this.rutas.obtenerCaminoPorNumeroDeNodos(codigos[0], codigos[1]);
        }

        mostrarCamino(camino);
    }

    private void mostrarCaminoPasandoPorCiudad()
    {
        // obtengo y muestro los caminos que pasan por una ciudad dada
        String[] names = {"Origen(cp)", "Destino(cp)", "Pasando por(cp)"};
        int[] codigos = InputReader.scanCodigos(names);

        if(codigos[names.length-1] != -1)
        {
            Lista caminos = this.rutas.obtenerCaminoPasandoPorNodo(codigos[0], codigos[1], codigos[2]);
            Lista camino;
            for(int i = 2; i < caminos.longitud()+1; i++)
            {
                camino = (Lista)caminos.recuperar(i);
                mostrarCamino(camino);
            }
            System.out.println("Longitud min " + caminos.recuperar(1));
        }
    }

    private void mostrarCaminoKMMaximos()
    {
        // ademas de origen y destino, ingreso una distancia maxima
        // obtengo el camino de menor distancia respetando el maximo dado.
        float maximo = -1;
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = InputReader.scanCodigos(names);
        boolean continuar = codigos[codigos.length-1] != -1;
        Lista camino = null;

        if(continuar)
        {
            maximo = InputReader.scanFloat("Longitud Maxima (KM)");
            continuar = maximo != -1;
        }

        if(continuar)
        {
            camino = this.rutas.obtenerCaminoPorPesoMaximo(codigos[0], codigos[1], maximo);
        }

        mostrarCamino(camino);
    }

    private void mostrarPedidosIntermedios()
    {
        // obtengo el camino de menor distancia entre dos ciudades
        // luego ingreso la capacidad del camion
        // finalmente recorro el camino simulando cargas y descargas para hacer una lista 
        // de los pedidos intermedios que se podrian cargar
        int capacidad = -1;
        Lista camino = obtenerCaminoMenorDistancia();
        if(!camino.esVacia())
        {
            capacidad = InputReader.scanInt("Capacidad del Camion");
            if(capacidad != -1)
            {
                System.out.println(camino.recuperar(1));
                Ciudad origen = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(1));
                Ciudad destino = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(camino.longitud()-1));
                if(origen != null && destino != null)
                {
                    Lista solicitudesIntermedias = new Lista();
                    mostrarCamino(camino);

                    // calculo el disponible, simulo que cargo el pedido que va de origen a fin
                    int disponible = capacidad;
                    String codigoStr = origen.getCodigo() + "" + destino.getCodigo();
                    int codigo = Integer.parseInt(codigoStr);
                    if(solicitudes.obtenerValor((Comparable)codigo)!=null)
                    {
                        disponible = disponible - obtenerEspacioPedidos(solicitudes.obtenerValor((Comparable)codigo));
                    }
                    // pedidosEnCamion almacena como clave el destino de los pedidos para que sea facil descargarlos
                    MapeoAMuchos pedidosEnCamion = new MapeoAMuchos();
                    Lista descargas;
                    Ciudad ciudadOrigen;
                    for(int indexOrigen = 1; indexOrigen < camino.longitud(); indexOrigen++)
                    {
                        // recorro partiendo de cada ciudad
                        ciudadOrigen = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(indexOrigen));

                        // compruebo si hay pedidos que se puedan descargar
                        descargas = pedidosEnCamion.obtenerValor((Comparable)camino.recuperar(indexOrigen));
                        if(descargas != null)
                        {
                            disponible = disponible + obtenerEspacioPedidos(descargas);
                        }

                        Lista pedidosParaEnviar = null;
                        for(int indexDestino = indexOrigen+1; indexDestino < camino.longitud(); indexDestino++)
                        {
                            // recorro hacia cada destino
                            // el envio desde origen a destino tiene prioridad por lo que evito tenerlo en cuenta
                            if(!(indexOrigen == 1 && indexDestino == camino.longitud()-1))
                            {
                                // compruebo si hay pedidos para enviar
                                codigoStr = ciudadOrigen.getCodigo() + "" + camino.recuperar(indexDestino);
                                codigo = Integer.parseInt(codigoStr);
                                pedidosParaEnviar = solicitudes.obtenerValor((Comparable)codigo);
                                if(pedidosParaEnviar!=null)
                                {
                                    disponible = procesarCargaCamion(pedidosParaEnviar, solicitudesIntermedias, 
                                    pedidosEnCamion, camino, indexDestino, ciudadOrigen, disponible);
                                }
                            }
                        }
                    }
                    // se muestran las posibles solicitudes intermedias
                    System.out.println("Posibles solicitudes intermedias");
                    mostrarSolicitudes(solicitudesIntermedias);
                }
            }
        }
    }

    private int procesarCargaCamion(Lista pedidosParaEnviar, Lista solicitudesIntermedias, 
                        MapeoAMuchos pedidosEnCamion, Lista camino, int indexDestino,
                        Ciudad ciudadOrigen, int disponible)
    {
        Solicitud pedidoParaEnviar = null;

        for(int i = 1; i <= pedidosParaEnviar.longitud(); i++)
        {
            // recorro agregando todos los pedidos segun el espacio disponible
            pedidoParaEnviar = (Solicitud)pedidosParaEnviar.recuperar(i);
            if(disponible - pedidoParaEnviar.getMetrosCubicos() >= 0)
            {
                // si hay espacio se lo carga en solicitudes intermedias para mostrarlo despues
                Lista solicitudIntermedia = new Lista();
                solicitudIntermedia.insertar(ciudadOrigen.getCodigo(), 1);
                solicitudIntermedia.insertar(pedidoParaEnviar, 2);
                solicitudesIntermedias.insertar(solicitudIntermedia, solicitudesIntermedias.longitud()+1);
                // se simula que se lo carga en el camion
                disponible = disponible - pedidoParaEnviar.getMetrosCubicos();
                pedidosEnCamion.asociar((Comparable)camino.recuperar(indexDestino), pedidoParaEnviar);
            }
        }
        return disponible;
    }

    private void verificarCaminoPerfecto()
    {
        // se obtiene un camino
        // luego se comprueba que para cada ciudad haya al menos un pedido saliente
        // un camino perfecto es 8300 5620
        {
            int capacidad = -1;
            Lista camino = obtenerCaminoMenorDistancia();
            if(!camino.esVacia())
            {
                capacidad = InputReader.scanInt("Capacidad del Camion");
    
                if(capacidad != -1)
                {
                    Ciudad origen = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(1));
                    Ciudad destino = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(camino.longitud()-1));
                    if(origen != null && destino != null)
                    {
                        Lista solicitudesIntermedias = new Lista();
                        mostrarCamino(camino);
            
                        // primero se cargan los pedidos de origen a fin
                        // puede pasar que no hayan pedidos
                        // pero se considera que si existe tiene prioridad
                        int disponible = capacidad;
                        String codigoStr = origen.getCodigo() + "" + destino.getCodigo();
                        int codigo = Integer.parseInt(codigoStr);
                        Lista sol = solicitudes.obtenerValor((Comparable)codigo);
                        if(sol!=null)
                            disponible = disponible - obtenerEspacioPedidos(sol);
            
                        MapeoAMuchos pedidosEnCamion = new MapeoAMuchos();
                        Lista descargas;
                        Ciudad ciudadOrigen;
                        int indexOrigen = 1;
                        boolean caminoPerfecto = true;
                        while(indexOrigen < camino.longitud() && caminoPerfecto)
                        {
                            // recorro partiendo de cada ciudad siempre haya posibilidad de ser camino perfecto
                            ciudadOrigen = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(indexOrigen));
            
                            // compruebo si hay pedidos que se puedan descargar
                            descargas = pedidosEnCamion.obtenerValor((Comparable)camino.recuperar(indexOrigen));
                            if(descargas != null)
                            {
                                disponible = disponible + obtenerEspacioPedidos(descargas);
                            }
            
                            Lista pedidosParaEnviar = null;
                            boolean haySolicitudSaliente = false;
                            int nuevoDisponible;
                            int indexDestino = indexOrigen+1;
                            //System.out.println();
                            //System.out.println("origen" + ciudadOrigen);
                            while(indexDestino < camino.longitud())
                            {
                                // recorro hacia cada destino
                                // el envio desde origen a destino tiene prioridad
                                //System.out.println("pedidos a " + camino.recuperar(indexDestino));
                                if(!(indexOrigen == 1 && indexDestino == camino.longitud()-1))
                                {                        
                                    // compruebo si hay espacio disponible para enviar pedidos
                                    codigoStr = ciudadOrigen.getCodigo() + "" + camino.recuperar(indexDestino);
                                    codigo = Integer.parseInt(codigoStr);
                                    pedidosParaEnviar = solicitudes.obtenerValor((Comparable)codigo);
                                    //System.out.println(pedidosParaEnviar);
                                    if(pedidosParaEnviar!=null)
                                    {
                                        // compruebo flag de camino perfecto
                                        nuevoDisponible = procesarCargaCamion(pedidosParaEnviar, solicitudesIntermedias,
                                        pedidosEnCamion, camino, indexDestino, ciudadOrigen, disponible);

                                        //System.out.print(disponible);
                                        //System.out.print(" ");
                                        //System.out.print(nuevoDisponible);
                                        //System.out.println(solicitudesIntermedias);
                                        //System.out.println();

                                        if(nuevoDisponible != disponible)
                                        {
                                        if(!haySolicitudSaliente)
                                        {
                                            haySolicitudSaliente = true;
                                            disponible = nuevoDisponible;
                                        }
                                        }
                                    }
                                }
                                else
                                {
                                    // caso origen a fin
                                    if(!haySolicitudSaliente)
                                    {           
                                        codigoStr = ciudadOrigen.getCodigo() + "" + camino.recuperar(camino.longitud()-1);
                                        codigo = Integer.parseInt(codigoStr);
                                        haySolicitudSaliente = (solicitudes.obtenerValor(codigo)!=null);
                                    }
                                }
                                // actualizo valores del while para recorrer ciudades destino
                                indexDestino = indexDestino + 1;
                            }
                            // actualizo valores del while para recorrer ciudad origen, condicion de camino perfecto
                            if(indexOrigen != camino.longitud()-1)
                            {
                                caminoPerfecto = haySolicitudSaliente;
                            }
                            indexOrigen = indexOrigen + 1;
                        }
            
                        // muestro si es camino perfecto
                        if(caminoPerfecto)
                        {
                            System.out.println("Es un camino perfecto");
                            System.out.println("Posibles solicitudes intermedias");
                            mostrarSolicitudes(solicitudesIntermedias);
                        }
                        else
                        {
                            System.out.println("No es un camino perfecto");
                        }
                    }
                }
            }
        }
    }

    private void mostrarSolicitudes(Lista solicitudesIntermedias)
    {
        // se muestran los datos de la lista dada
        Lista solicitudIntermedia;
        for(int i = 1; i <= solicitudesIntermedias.longitud(); i++)
        {
            solicitudIntermedia = (Lista)solicitudesIntermedias.recuperar(i);
            System.out.println(solicitudIntermedia.toString());
        }
    }

    private void mostrarCamino(Lista camino)
    {
        // se recorre el camino mostrando cada ciudad
        // al final se muestra su longitud
        if(camino != null)
        {
            for(int i = 2; i <= camino.longitud(); i++)
            {
                System.out.println(this.ciudades.obtenerInformacion((Comparable)camino.recuperar(i)));
            }
            System.out.println("Longitud: " + camino.recuperar(1));
        }
    }

    private Lista obtenerCaminoMenorDistancia()
    {
        // se obtiene origen y destino
        // luego se devuelve el camino
        Lista camino=null;
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = InputReader.scanCodigos(names);
        if(codigos[codigos.length-1]!=-1)
        {
            try
            {
                camino = this.rutas.obtenerCaminoPorPeso(codigos[0], codigos[1]);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return camino;
    }

    private int obtenerEspacioPedidos(Lista pedidos)
    {
        // se obtiene una lista de pedidos
        // se devuelve el espacio total que ocupan en el camion
        int espacio = 0;
        Solicitud pedido;
        for(int i = 1; i <= pedidos.longitud(); i++)
        {
            // recorro descargando cada solicitud que llega al destino
            pedido = (Solicitud)pedidos.recuperar(i);
            espacio = espacio + pedido.getMetrosCubicos();
        }
        return espacio;
    }

}
