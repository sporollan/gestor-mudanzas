package mudanzas;

import estructuras.grafo.Grafo;
import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.Diccionario;
import estructuras.propositoEspecifico.MapeoAMuchos;

public class RutasManager {
    private InputReader inputReader;
    private Grafo rutas;
    private Diccionario ciudades;

    public RutasManager(InputReader inputReader, Diccionario ciudades, Grafo rutas, LogOperacionesManager logOperacionesManager)
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
        }  
    }

    public void mostrarEstructura()
    {
        System.out.println(rutas.toString());
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

    private void consultarRuta()
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

    private void cargarDatos()
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
            if(insertar(codigos[0], codigos[1], distancia))
            {
                System.out.println("Ruta insertada con exito");
            }
            else
            {
                System.out.println("Error insertando ruta");
            }
        }
    }

    public boolean insertar(int cpo, int cpd, float distancia)
    {
        boolean exito = false;
        if(!rutas.existeArco(cpo, cpd))
            exito = rutas.insertarArco(cpo, cpd, distancia);
        return exito;
    }

}
