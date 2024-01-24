package mudanzas;

import estructuras.grafo.Grafo;
import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.Diccionario;

public class RutasManager {
    private InputReader inputReader;
    private Grafo rutas;
    private Diccionario ciudades;

    public RutasManager(InputReader inputReader, Diccionario ciudades, Grafo rutas)
    {
        this.inputReader = inputReader;
        this.rutas = rutas;
        this.ciudades = ciudades;
    }
    private void mostrarMenu()
    {
            System.out.println("Gestionar Rutas");
            System.out.println("1. Cargar");
            System.out.println("2. Eliminar");
            System.out.println("3. Modificar");
            System.out.println("4. Camino por menor Distancia");
            System.out.println("5. Camino por menos Ciudades");
            System.out.println("6. Camino pasando por Ciudad");
            System.out.println("7. Camino por Distancia Maxima");
    }
    public void gestionar()
    {
        String seleccion = "";
        while(!seleccion.equals("q"))
        {
            mostrarMenu();
            seleccion = inputReader.scanString("Seleccion");
            if(seleccion.equals("1"))
                cargarDatos();
            else if(seleccion.equals("2"))
                eliminarArco();
            else if(seleccion.equals("3"))
                modificar();
            else if(seleccion.equals("4"))
                mostrarCaminoMenorDistancia();
            else if(seleccion.equals("5"))
                mostrarCaminoMenosCiudades();
            else if(seleccion.equals("6"))
                mostrarCaminoPasandoPorCiudad();
            else if(seleccion.equals("7"))
                mostrarCaminoKMMaximos();
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

    private void modificar()
    {
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = inputReader.scanCodigos(names);
        if(rutas.existeArco(codigos[0], codigos[1]))
        {
            float nuevaDistancia = inputReader.scanFloat("Nueva distancia");
            if(rutas.eliminarArco(codigos[0], codigos[1]))
                if(rutas.insertarArco(codigos[0], codigos[1], nuevaDistancia))
                    System.out.println("Modificado con exito");

        }
    }

    private void eliminarArco()
    {
        int origenCP = inputReader.scanCp("CP origen");
        int destinoCP = inputReader.scanCp("CP destino");

        if(rutas.eliminarArco(origenCP, destinoCP))
            System.out.println("Eliminado con exito");
        else
            System.out.println("No se pudo eliminar");
    }

    public Lista obtenerCaminoMenorDistancia()
    {
        Lista camino=null;
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = inputReader.scanCodigos(names);
        if(codigos[codigos.length-1]!=-1)
            camino = this.rutas.obtenerCaminoPorDistancia(codigos[0], codigos[1]);
        return camino;
    }

    public void mostrarCamino(Lista camino)
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

    public void mostrarCaminoMenorDistancia()
    {
        Lista camino = obtenerCaminoMenorDistancia();
        mostrarCamino(camino);
    }

    public void consultarRuta()
    {
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = inputReader.scanCodigos(names);

        if(codigos[codigos.length-1] != -1)
        {
            if(rutas.existeCamino(codigos[0], codigos[1]))
            {
                System.out.println("La ruta existe");
                System.out.println("Desde " + this.ciudades.obtenerInformacion(codigos[0]));
                System.out.println("Hasta " + this.ciudades.obtenerInformacion(codigos[1]));
                System.out.println("Distancia " + rutas.obtener(codigos[0], codigos[1]));
            }
            else
                System.out.println("La ruta no existe");
        }
    }

    public void cargarDatos()
    {
        float distancia = -1;
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = inputReader.scanCodigos(names);
        boolean continuar = codigos[codigos.length-1]!=-1;

        if(continuar)
        {
            distancia = inputReader.scanFloat("Distancia");
            continuar = distancia != -1;
        }

        if(continuar)
        {
            insertar(codigos[0], codigos[1], distancia);
        }
    }

    public void insertar(int cpo, int cpd, float distancia)
    {
        if(rutas.insertarArco(cpo, cpd, distancia))
            System.out.println("Ruta insertada con exito");
    }

}
