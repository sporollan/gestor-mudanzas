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
        // 8300 a 5620 es camino perfecto
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
            int aux = (int)solicitudesADestino.obtenerInformacion(j);
            solicitudesADestino.eliminar(j);
            solicitudesADestino.insertar(j, totalAgregado+aux);
        }
        return capacidad;
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

}
