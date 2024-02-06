package mudanzas;

import estructuras.grafo.Grafo;
import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.Diccionario;
import estructuras.propositoEspecifico.MapeoAMuchos;

public class ViajesManager {
    private InputReader inputReader;
    private Grafo rutas;
    private Diccionario ciudades;

    public ViajesManager(InputReader inputReader, Diccionario ciudades, Grafo rutas)
    {
        this.inputReader = inputReader;
        this.rutas = rutas;
        this.ciudades = ciudades;
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
            seleccion = inputReader.scanString("Seleccion");
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
        Lista camino = obtenerCaminoMenorDistancia();
        mostrarCamino(camino);
    }

    private void mostrarCaminoMenosCiudades()
    {
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = inputReader.scanCodigos(names);
        Lista camino = null;

        if(codigos[codigos.length-1]!=-1)
        {
            camino = this.rutas.obtenerCaminoPorCiudades(codigos[0], codigos[1]);
        }

        mostrarCamino(camino);
    }

    private void mostrarCaminoPasandoPorCiudad()
    {
        String[] names = {"Origen(cp)", "Destino(cp)", "Pasando por(cp)"};
        int[] codigos = inputReader.scanCodigos(names);

        if(codigos[names.length-1] != -1)
        {
            Lista caminos = this.rutas.obtenerCaminoPasandoPorCiudad(codigos[0], codigos[1], codigos[2]);
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
        float maximo = -1;
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = inputReader.scanCodigos(names);
        boolean continuar = codigos[codigos.length-1] != -1;
        Lista camino = null;

        if(continuar)
        {
            maximo = inputReader.scanFloat("Longitud Maxima (KM)");
            continuar = maximo != -1;
        }

        if(continuar)
        {
            camino = this.rutas.obtenerCaminoPorKMMaximos(codigos[0], codigos[1], maximo);
        }

        mostrarCamino(camino);
    }

    private void mostrarPedidosIntermedios()
    {
        int capacidad = -1;
        Lista camino = obtenerCaminoMenorDistancia();
        if(camino != null)
        {
            capacidad = inputReader.scanInt("Capacidad del Camion");
        }

        if(capacidad != -1)
        {
            Ciudad origen = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(1));
            Ciudad destino = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(camino.longitud()-1));
            Lista solicitudesIntermedias = new Lista();
            mostrarCamino(camino);

            int disponible = capacidad - obtenerEspacioPedidos(origen.obtenerSolicitudes(destino.getCodigo()));

            MapeoAMuchos pedidosEnCamion = new MapeoAMuchos();
            Lista descargas;
            Solicitud descarga;
            Ciudad ciudadOrigen;
            for(int indexOrigen = 1; indexOrigen < camino.longitud(); indexOrigen++)
            {
                // recorro partiendo de cada ciudad
                // compruebo si hay pedidos que se puedan descargar
                ciudadOrigen = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(indexOrigen));

                descargas = pedidosEnCamion.obtenerValor((Comparable)camino.recuperar(indexOrigen));
                if(descargas != null)
                {
                    disponible = disponible + obtenerEspacioPedidos(descargas);
                }

                Lista pedidosParaEnviar = null;
                for(int indexDestino = indexOrigen+1; indexDestino < camino.longitud(); indexDestino++)
                {
                    // recorro hacia cada destino
                    // compruebo si hay espacio disponible para enviar pedidos

                    // el envio desde origen a destino tiene prioridad
                    if(!(indexOrigen == 1 && indexDestino == camino.longitud()-1))
                    {
                        pedidosParaEnviar = ciudadOrigen.obtenerSolicitudes((Comparable)camino.recuperar(indexDestino));
                        if(pedidosParaEnviar!=null)
                        {
                            Solicitud pedidoParaEnviar = null;

                            for(int i = 1; i <= pedidosParaEnviar.longitud(); i++)
                            {
                                // recorro agregando todos los pedidos segun el espacio disponible
                                pedidoParaEnviar = (Solicitud)pedidosParaEnviar.recuperar(i);
                                if(disponible - pedidoParaEnviar.getMetrosCubicos() >= 0)
                                {
                                    Lista solicitudIntermedia = new Lista();
                                    solicitudIntermedia.insertar(ciudadOrigen.getCodigo(), 1);
                                    solicitudIntermedia.insertar(pedidoParaEnviar, 2);
                                    solicitudesIntermedias.insertar(solicitudIntermedia, solicitudesIntermedias.longitud()+1);

                                    disponible = disponible - pedidoParaEnviar.getMetrosCubicos();
                                    pedidosEnCamion.asociar((Comparable)camino.recuperar(indexDestino), pedidoParaEnviar);
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("Posibles solicitudes intermedias");
            Lista solicitudIntermedia;
            for(int i = 1; i < solicitudesIntermedias.longitud(); i++)
            {
                solicitudIntermedia = (Lista)solicitudesIntermedias.recuperar(i);
                System.out.println(solicitudIntermedia.toString());
            }
        }
    }

    private void verificarCaminoPerfecto()
    {
        // un camino perfecto es 8300 5620
        {
            int capacidad = -1;
            Lista camino = obtenerCaminoMenorDistancia();
            if(camino != null)
            {
                capacidad = inputReader.scanInt("Capacidad del Camion");
            }
    
            if(capacidad != -1)
            {
                Ciudad origen = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(1));
                Ciudad destino = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(camino.longitud()-1));
                Lista solicitudesIntermedias = new Lista();
                mostrarCamino(camino);
    
                // primero se cargan los pedidos de origen a fin
                int disponible = capacidad;
                if(origen.obtenerSolicitudes(destino.getCodigo())!=null)
                    disponible = disponible - obtenerEspacioPedidos(origen.obtenerSolicitudes(destino.getCodigo()));
    
                MapeoAMuchos pedidosEnCamion = new MapeoAMuchos();
                Lista descargas;
                Solicitud descarga;
                Ciudad ciudadOrigen;
                int indexOrigen = 1;
                boolean caminoPerfecto = true;
                while(indexOrigen < camino.longitud() && caminoPerfecto)
                {
                    // recorro partiendo de cada ciudad
                    // compruebo si hay pedidos que se puedan descargar
                    ciudadOrigen = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(indexOrigen));
    
                    descargas = pedidosEnCamion.obtenerValor((Comparable)camino.recuperar(indexOrigen));
                    if(descargas != null)
                    {
                        disponible = disponible + obtenerEspacioPedidos(descargas);
                    }
    
                    Lista pedidosParaEnviar = null;
                    boolean haySolicitudSaliente = false;
                    int indexDestino = indexOrigen+1;
                    while(indexDestino < camino.longitud())
                    {

                        // recorro hacia cada destino

                        // compruebo si hay espacio disponible para enviar pedidos
                        // el envio desde origen a destino tiene prioridad
                        if(!(indexOrigen == 1 && indexDestino == camino.longitud()-1))
                        {
                            pedidosParaEnviar = ciudadOrigen.obtenerSolicitudes((Comparable)camino.recuperar(indexDestino));
                            if(pedidosParaEnviar!=null)
                            {
                                // compruebo flag de camino perfecto
                                if(!haySolicitudSaliente)
                                {
                                    haySolicitudSaliente = true;
                                }

                                Solicitud pedidoParaEnviar = null;
    
                                for(int i = 1; i <= pedidosParaEnviar.longitud(); i++)
                                {
                                    // recorro agregando todos los pedidos segun el espacio disponible
                                    pedidoParaEnviar = (Solicitud)pedidosParaEnviar.recuperar(i);
                                    if(disponible - pedidoParaEnviar.getMetrosCubicos() >= 0)
                                    {
                                        Lista solicitudIntermedia = new Lista();
                                        solicitudIntermedia.insertar(ciudadOrigen.getCodigo(), 1);
                                        solicitudIntermedia.insertar(pedidoParaEnviar, 2);
                                        solicitudesIntermedias.insertar(solicitudIntermedia, solicitudesIntermedias.longitud()+1);
    
                                        disponible = disponible - pedidoParaEnviar.getMetrosCubicos();
                                        pedidosEnCamion.asociar((Comparable)camino.recuperar(indexDestino), pedidoParaEnviar);
                                    }
                                }
                            }
                        }
                        else
                        {
                            if(!haySolicitudSaliente)
                                haySolicitudSaliente = (ciudadOrigen.obtenerSolicitudes((Comparable)camino.recuperar(camino.longitud()-1))!=null);
                        }
                        indexDestino = indexDestino + 1;
                    }
                    if(indexOrigen != camino.longitud()-1)
                        caminoPerfecto = haySolicitudSaliente;
                    indexOrigen = indexOrigen + 1;
                }
    
                if(caminoPerfecto)
                {
                    System.out.println("Es un camino perfecto");
                    System.out.println("Posibles solicitudes intermedias");
                    Lista solicitudIntermedia;
                    for(int i = 1; i < solicitudesIntermedias.longitud(); i++)
                    {
                        solicitudIntermedia = (Lista)solicitudesIntermedias.recuperar(i);
                        System.out.println(solicitudIntermedia.toString());
                    }
                }
                else
                {
                    System.out.println("No es un camino perfecto");
                }
            }
    
        }
    }

    private void mostrarCamino(Lista camino)
    {
        if(camino != null)
        {
            for(int i = 1; i < camino.longitud(); i++)
            {
                System.out.println(this.ciudades.obtenerInformacion((Comparable)camino.recuperar(i)));
            }
            System.out.println("Longitud: " + camino.recuperar(camino.longitud()));
        }
    }
    private Lista obtenerCaminoMenorDistancia()
    {
        Lista camino=null;
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = inputReader.scanCodigos(names);
        if(codigos[codigos.length-1]!=-1)
            camino = this.rutas.obtenerCaminoPorDistancia(codigos[0], codigos[1]);
        return camino;
    }

    private int obtenerEspacioPedidos(Lista pedidos)
    {
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