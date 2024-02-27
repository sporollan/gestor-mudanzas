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
            Lista caminos = this.rutas.obtenerCaminosPasandoPorNodo(codigos[0], codigos[1], codigos[2]);
            Lista camino;
            for(int i = 1; i <= caminos.longitud(); i++)
            {
                System.out.println();
                camino = (Lista)caminos.recuperar(i);
                mostrarCamino(camino);
            }
            System.out.println();
            System.out.println("Cantidad de caminos encontrados: " + caminos.longitud());
            System.out.println();
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
        int capacidad = -1;
        Lista camino = obtenerCaminoMenorDistancia();
        if(!camino.esVacia())
        {
            capacidad = InputReader.scanInt("Capacidad del Camion");
        }
        if(capacidad != -1)
        {
            mostrarCamino(camino);
            mostrarSolicitudes(obtenerSolicitudesIntermedias(camino, capacidad, false));
        }
    }
    private void verificarCaminoPerfecto()
    {
        int capacidad = -1;
        Lista camino = obtenerCaminoMenorDistancia();
        if(!camino.esVacia())
        {
            capacidad = InputReader.scanInt("Capacidad del Camion");
        }
        if(capacidad != -1)
        {
            mostrarCamino(camino);
            Lista caminoPerfecto = obtenerSolicitudesIntermedias(camino, capacidad, true);
            if(caminoPerfecto != null)
            {
                System.out.println("Es camino perfecto");
                mostrarSolicitudes(caminoPerfecto);
            }
            else
            {
                System.out.println("No es camino perfecto");
            }
        }
    }
    private Lista obtenerSolicitudesIntermedias(Lista camino, int capacidad, boolean flagPerfecto)
    {
        Lista solicitudesIntermedias = new Lista();
        Lista s;
        Diccionario solicitudesADestino = new Diccionario();
        // inicializo solicitudesADestino
        for(int i = 2; i <= camino.longitud(); i++)
        {
            solicitudesADestino.insertar(i, 0);
        }
        capacidad = procesarSolicitudes(solicitudesIntermedias, solicitudesADestino, 
                                        solicitudes.obtenerValor((Comparable)(Integer.parseInt(
                                                        camino.recuperar(2)+""+
                                                        camino.recuperar(camino.longitud())))), 
                                                        capacidad, camino.longitud());
        System.out.println(solicitudesADestino.listarClaves());
        boolean esPerfecto = true;
        int i = 2;
        int j;
        while(esPerfecto && i < camino.longitud())
        {
            // descargar en i
            capacidad = capacidad + (int)solicitudesADestino.obtenerInformacion(i);
            // cargar de i a j
            j = i+1;
            if(flagPerfecto)
            {
                esPerfecto = false;
            }
            while(j <= camino.longitud())
            {
                if(i == 2 && j == camino.longitud())
                {
                    // origen a destino, ya estan cargadas porque se priorizan, calculo camino perfecto
                    if(flagPerfecto)
                    {
                        if(!esPerfecto)
                        {
                            esPerfecto = (int)solicitudesADestino.obtenerInformacion(j)!=0;
                        }
                    }
                }
                else
                {
                    // obtengo lista solicitudes de origen i a destino j
                    s = solicitudes.obtenerValor((Comparable)(Integer.parseInt(camino.recuperar(i)+""+camino.recuperar(j))));
                    if(s != null)
                    {
                        int nuevaCapacidad = procesarSolicitudes(solicitudesIntermedias, solicitudesADestino, s, capacidad, j);
                        if(flagPerfecto)
                        {
                            if(!esPerfecto)
                            {
                                esPerfecto = nuevaCapacidad != capacidad;
                            }
                        }
                        capacidad = nuevaCapacidad;
                    }
                }
                j = j + 1;
            }
            i = i + 1;
        }
        if(!esPerfecto)
        {
            solicitudesIntermedias = null;
        }
        return solicitudesIntermedias;
    }

    private int procesarSolicitudes(Lista solicitudesIntermedias, Diccionario solicitudesADestino, Lista s, int capacidad, int j)
    {
        if(s != null)
        {
            int m2Solicitud;
            int k=1;
            int totalAgregado=0;
            while(capacidad >= 0 && k <= s.longitud())
            {
                Solicitud sol = (Solicitud)s.recuperar(k);
                m2Solicitud = sol.getMetrosCubicos();
                if(capacidad-m2Solicitud >= 0)
                {
                    capacidad = capacidad - m2Solicitud;
                    totalAgregado = totalAgregado + m2Solicitud;
                    solicitudesIntermedias.insertar(sol, 1);
                }
                k = k + 1;
            }
            System.out.println("##############");
            System.out.println(j);
            System.out.println(solicitudesADestino.listarClaves());
            System.out.println(solicitudesADestino.listarDatos());
            System.out.println(solicitudesADestino);
            int aux = (int)solicitudesADestino.obtenerInformacion(j);
            solicitudesADestino.eliminar(j);
            solicitudesADestino.insertar(j, totalAgregado+aux);
            System.out.println(j);
            System.out.println(solicitudesADestino.listarClaves());
            System.out.println(solicitudesADestino.listarDatos());
            System.out.println(solicitudesADestino);
        }
        return capacidad;
    }

    private void mostrarPedidosIntermedios1()
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
                Ciudad origen = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(camino.longitud()));
                Ciudad destino = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(2));
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
                    for(int indexOrigen = camino.longitud(); indexOrigen > 2; indexOrigen--)
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
                        for(int indexDestino = indexOrigen-1; indexDestino > 1; indexDestino--)
                        {
                            // recorro hacia cada destino
                            // el envio desde origen a destino tiene prioridad por lo que evito tenerlo en cuenta
                            if(!(indexOrigen == camino.longitud() && indexDestino == 2))
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

    private void verificarCaminoPerfecto1()
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
                    Ciudad origen = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(camino.longitud()));
                    Ciudad destino = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(2));
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
                        int indexOrigen = camino.longitud();
                        boolean caminoPerfecto = true;
                        while(indexOrigen > 2 && caminoPerfecto)
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
                            int indexDestino = indexOrigen-1;
                            //System.out.println();
                            //System.out.println("origen" + ciudadOrigen);
                            while(indexDestino > 1)
                            {
                                // recorro hacia cada destino
                                // el envio desde origen a destino tiene prioridad
                                if(!(indexOrigen == camino.longitud() && indexDestino == 2))
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
                                indexDestino = indexDestino - 1;
                            }
                            // actualizo valores del while para recorrer ciudad origen, condicion de camino perfecto
                            if(indexOrigen != 2)
                            {
                                caminoPerfecto = haySolicitudSaliente;
                            }
                            indexOrigen = indexOrigen - 1;
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
        for(int i = 1; i <= solicitudesIntermedias.longitud(); i++)
        {
            System.out.println(solicitudesIntermedias.recuperar(i));
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
