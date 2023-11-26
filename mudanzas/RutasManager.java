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
    public void gestionar()
    {
        String seleccion = "";
        while(!seleccion.equals("q"))
        {
            mostrarMenu();
            seleccion = inputReader.scanString("Seleccion:");
            if(seleccion.equals("1"))
                System.out.println(rutas.listarEnProfundidad());
            else if(seleccion.equals("2"))
                cargarDatos();
            else if(seleccion.equals("3"))
                consultarRuta();
            else if(seleccion.equals("4"))
                consultarRutaPorDistancia();
        }  
    }

    private void mostrarMenu()
    {
            System.out.println("Gestionar Rutas");
            System.out.println("1. Mostrar");
            System.out.println("2. Insertar");
            System.out.println("3. Consultar Ruta");
            System.out.println("4. Encontrar camino por distancia");
    }

    public void consultarRutaPorDistancia()
    {
        int cpo = -1;
        int cpd = -1;
        boolean continuar = true;

        cpo = inputReader.scanCp("Origen(cp)");
        continuar = cpo != -1;

        if(continuar)
        {
            cpd = inputReader.scanCp("Destino(cp)");
            continuar = cpd != -1;
        }

        if(continuar)
        {
            Lista rutas = this.rutas.obtenerCaminoPorDistancia(cpo, cpd);
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
        int cpo = -1;
        int cpd = -1;
        boolean continuar = true;

        cpo = inputReader.scanCp("Origen(cp)");
        continuar = cpo != -1;

        if(continuar)
        {
            cpd = inputReader.scanCp("Destino(cp)");
            continuar = cpd != -1;
        }

        if(continuar)
        {
            if(rutas.existeCamino(cpo, cpd))
            {
                System.out.println("La ruta existe");
                System.out.println("Desde " + this.ciudades.obtenerInformacion(cpo));
                System.out.println("Hasta " + this.ciudades.obtenerInformacion(cpd));
                System.out.println("Distancia " + rutas.obtener(cpo, cpd));
            }
            else
                System.out.println("La ruta no existe");
        }
    }

    public void cargarDatos()
    {
        int cpo = -1;
        int cpd = -1;
        float distancia = -1;
        boolean continuar = true;

        cpo = inputReader.scanCp("Origen(cp)");
        continuar = cpo != -1;

        if(continuar)
        {
            cpd = inputReader.scanCp("Destino(cp)");
            continuar = cpd != -1;
        }

        if(continuar)
        {
            distancia = inputReader.scanFloat("Distancia");
            continuar = distancia != -1;
        }

        if(continuar)
        {
            insertar(cpo, cpd, distancia);
        }
    }

    public void insertar(int cpo, int cpd, float distancia)
    {
        if(rutas.insertarArco(cpo, cpd, ciudades))
            System.out.println("Ruta insertada con exito");
    }
}
