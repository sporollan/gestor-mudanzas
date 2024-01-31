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

    private void verificarCaminoPerfecto()
    {
        Lista camino = obtenerCaminoMenorDistancia();
        mostrarCamino(camino);

    }
    private void mostrarCaminoMenorDistancia()
    {
        Lista camino = obtenerCaminoMenorDistancia();
        mostrarCamino(camino);
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
            mostrarCamino(camino);
            mostrarPesoTotalPedidos(origen.obtenerSolicitudes(destino.getCodigo()));

            MapeoAMuchos pedidosEnCamion = new MapeoAMuchos();
            Lista descargas;
            Solicitud descarga;
            int disponible = capacidad;
            Ciudad ciudadOrigen;
            for(int indexOrigen = 1; indexOrigen < camino.longitud(); indexOrigen++)
            {
                // recorro partiendo de cada ciudad
                // compruebo si hay pedidos que se puedan descargar
                ciudadOrigen = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(indexOrigen));

                descargas = pedidosEnCamion.obtenerValor((Comparable)camino.recuperar(indexOrigen));
                if(descargas != null)
                {
                    for(int i = 1; i <= descargas.longitud(); i++)
                    {
                        // recorro descargando cada solicitud que llega al destino
                        descarga = (Solicitud)descargas.recuperar(i);
                        System.out.println("Descargando " + descarga.getMetrosCubicos());
                        disponible = disponible + descarga.getMetrosCubicos();
                    }
                    System.out.println("Nuevo Disponible " + disponible);
                }

                Lista pedidosParaEnviar = null;
                for(int indexDestino = indexOrigen+1; indexDestino < camino.longitud(); indexDestino++)
                {
                    // recorro hacia cada destino
                    // compruebo si hay espacio disponible para enviar pedidos
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
                                disponible = disponible - pedidoParaEnviar.getMetrosCubicos();
                                pedidosEnCamion.asociar((Comparable)camino.recuperar(indexDestino), pedidoParaEnviar);
                            }
                        }
                    }
                }
            }
            System.out.println(disponible);
        }

    }

    private void mostrarPesoTotalPedidos(Lista pedidos)
    {
        int pesoPedidos=0;
        Solicitud pedido; 
        for(int i = 1; i < pedidos.longitud()+1; i++)
        {
            pedido = (Solicitud)pedidos.recuperar(i);
            pesoPedidos = pesoPedidos + pedido.getMetrosCubicos();
        }
        System.out.println("Pedidos " + pesoPedidos + " metros cubicos");
    }

    private void mostrarPedidosIntermedios1()
    {
        int capacidad=-1;
        Lista camino = obtenerCaminoMenorDistancia();
        if(camino != null)
            capacidad = inputReader.scanInt("Capacidad del Camion");

        Ciudad c;
        Ciudad origen;
        origen = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(1));
        Ciudad destino = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(camino.longitud()-1));

        mostrarCamino(camino);

        Lista pedidos = origen.obtenerSolicitudes(destino.getCodigo());

        int pesoPedidos=0;
        Solicitud pedido; 
        for(int i = 1; i < pedidos.longitud()+1; i++)
        {
            pedido = (Solicitud)pedidos.recuperar(i);
            pesoPedidos = pesoPedidos + pedido.getMetrosCubicos();
        }
        System.out.println("Pedidos " + pesoPedidos + " metros cubicos");

        Ciudad o,d;
        Lista pedidosLista;
        Lista sol = new Lista();
        int disp = capacidad - pesoPedidos;
        Solicitud s;
        System.out.println("Disponible " + disp);

        o = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(1));
        MapeoAMuchos destinos = new MapeoAMuchos();
        Lista descarga;
        for(int j = 2; j < camino.longitud()-1;j++)
        {
            Comparable cpDestino = (Comparable) camino.recuperar(j);
            pedidosLista = o.obtenerSolicitudes(cpDestino);
            descarga = destinos.obtenerValor((Comparable)camino.recuperar(j));
            if(descarga != null)
            {
                System.out.println("Descargando");
                int aCargar = 0;
                for(int w = 1; w <= descarga.longitud(); w++)
                {

                    aCargar = aCargar + ((Solicitud)descarga.recuperar(w)).getMetrosCubicos();
                }
                System.out.println("Disponible " + disp);
                System.out.println("Descargando " + aCargar);
                disp = disp + aCargar;
                System.out.println("Nuevo Disponible " + disp);
            }
            if(pedidosLista != null)
            {
                for(int k = 1; k < pedidosLista.longitud()+1;k++)
                {
                    s = (Solicitud)pedidosLista.recuperar(k);

                    
                    System.out.println(o.getCodigo() +" a "+ s.getDestino());
                    System.out.println("Requiere " + s.getMetrosCubicos());
                    System.out.println(disp + " disponible");
                    if(disp - s.getMetrosCubicos() >= 0)
                    {
                        System.out.println("Insertando");
                        disp = disp - s.getMetrosCubicos();
                        sol.insertar(s, sol.longitud()+1);
                        System.out.println("Asociando " + s.getDestino().getCodigo());
                        destinos.asociar(s.getDestino().getCodigo(), s);
                    }
                    else
                        System.out.println("No insertando");
                }
            }
        }
        for(int i = 2; i < camino.longitud(); i++)
        {
            o = (Ciudad)ciudades.obtenerInformacion((Comparable)camino.recuperar(i));
            for(int j = i+1; j < camino.longitud();j++)
            {
                pedidosLista = o.obtenerSolicitudes((Comparable)camino.recuperar(j));
                descarga = destinos.obtenerValor((Comparable)camino.recuperar(j));
                if(descarga != null)
                {
                    System.out.println("Descargando");
                    int aCargar = 0;
                    for(int w = 1; w <= descarga.longitud(); w++)
                    {

                        aCargar = aCargar + ((Solicitud)descarga.recuperar(w)).getMetrosCubicos();
                    }
                    System.out.println("Disponible " + disp);
                    System.out.println("Descargando " + aCargar);
                    disp = disp + aCargar;
                    System.out.println("Nuevo Disponible " + disp);
                }
                if(pedidosLista != null)
                {
                    for(int k = 1; k < pedidosLista.longitud()+1;k++)
                    {
                        s = (Solicitud)pedidosLista.recuperar(k);



                        System.out.println(o.getCodigo() +" a "+ s.getDestino());
                        System.out.println("Requiere " + s.getMetrosCubicos());
                        System.out.println(disp + " disponible");
                        if(disp - s.getMetrosCubicos() >= 0)
                        {
                            disp = disp - s.getMetrosCubicos();
                            sol.insertar(s, sol.longitud()+1);
                            destinos.asociar(s.getDestino().getCodigo(), s);
                        }
                        else
                            System.out.println("No insertando");
                    }
                }
                else
                {
                }
            }
        }
        System.out.println("Solicitudes que podrian incluirse");
        Solicitud s1;
        for(int i = 1; i < sol.longitud()+1; i++)
        {
            s1 = (Solicitud)sol.recuperar(i);
            System.out.println(s1.getDestino() +" "+ s1.getCliente().getNombres() +" "+
                                s1.getFecha() +" "+ s1.getBultos() +" "+ s1.getMetrosCubicos());
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
}
