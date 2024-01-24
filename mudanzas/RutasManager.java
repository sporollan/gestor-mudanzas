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

        if(continuar)
        {
            maximo = inputReader.scanFloat("Longitud Maxima (KM)");
            continuar = maximo != -1;
        }

        if(continuar)
        {
            Lista rutas = this.rutas.obtenerCaminoPorKMMaximos(codigos[0], codigos[1], maximo);
            Lista ruta;
            for(int i = 2; i < rutas.longitud()+1; i++)
            {
                ruta = (Lista)rutas.recuperar(i);
                for(int j = 1; j < ruta.longitud(); j++)
                {
                    System.out.println(this.ciudades.obtenerInformacion((Comparable)ruta.recuperar(j)));
                }
                System.out.println("Longitud: " + ruta.recuperar(ruta.longitud()));
            }
            System.out.println("Longitud min " + rutas.recuperar(1));
        }
    }

    private void mostrarCaminoPasandoPorCiudad()
    {
        String[] names = {"Origen(cp)", "Destino(cp)", "Pasando por(cp)"};
        int[] codigos = inputReader.scanCodigos(names);

        if(codigos[names.length-1] != -1)
        {
            Lista rutas = this.rutas.obtenerCaminoPasandoPorCiudad(codigos[0], codigos[1], codigos[2]);
            Lista ruta;
            for(int i = 2; i < rutas.longitud()+1; i++)
            {
                ruta = (Lista)rutas.recuperar(i);
                for(int j = 1; j < ruta.longitud(); j++)
                {
                    System.out.println(this.ciudades.obtenerInformacion((Comparable)ruta.recuperar(j)));
                }
                System.out.println("Longitud: " + ruta.recuperar(ruta.longitud()));
            }
            System.out.println("Longitud min " + rutas.recuperar(1));
        }
    }

    private void mostrarCaminoMenosCiudades()
    {
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = inputReader.scanCodigos(names);

        if(codigos[codigos.length-1]!=-1)
        {
            Lista rutas = this.rutas.obtenerCaminoPorCiudades(codigos[0], codigos[1]);
            Lista ruta;
            for(int i = 2; i < rutas.longitud()+1; i++)
            {
                ruta = (Lista)rutas.recuperar(i);
                for(int j = 1; j < ruta.longitud(); j++)
                {
                    System.out.println(this.ciudades.obtenerInformacion((Comparable)ruta.recuperar(j)));
                }
                System.out.println("Longitud: " + ruta.recuperar(ruta.longitud()));
            }
            System.out.println("Longitud min " + rutas.recuperar(1));
        }
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

    private void eliminarVertice()
    {

        int ciudadCP = inputReader.scanCp("CP");
        if(rutas.eliminarVertice(ciudadCP))
            System.out.println("Eliminado con exito");
        else
            System.out.println("No se pudo eliminar");

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



    public void mostrarCaminoMenorDistancia()
    {

        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = inputReader.scanCodigos(names);

        if(codigos[codigos.length-1]!=-1)
        {
            Lista rutas = this.rutas.obtenerCaminoPorDistancia(codigos[0], codigos[1]);
            Lista ruta;
            for(int i = 2; i < rutas.longitud()+1; i++)
            {
                ruta = (Lista)rutas.recuperar(i);
                for(int j = 1; j < ruta.longitud(); j++)
                {
                    System.out.println(this.ciudades.obtenerInformacion((Comparable)ruta.recuperar(j)));
                }
                System.out.println("Longitud: " + ruta.recuperar(ruta.longitud()));
            }
            System.out.println("Longitud min " + rutas.recuperar(1));
        }
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
