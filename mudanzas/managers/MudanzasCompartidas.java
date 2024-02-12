package mudanzas.managers;

import estructuras.grafo.Grafo;
import estructuras.propositoEspecifico.Diccionario;
import estructuras.propositoEspecifico.MapeoAMuchos;
import estructuras.propositoEspecifico.MapeoAUno;
import mudanzas.librerias.InputReader;
import mudanzas.librerias.LogOperacionesManager;
import mudanzas.managers.RutasManager;


public class MudanzasCompartidas {
    MapeoAUno clientes;
    Grafo rutas;
    Diccionario ciudades;
    MapeoAMuchos solicitudes;

    CiudadesManager ciudadesManager;
    SolicitudesManager solicitudesManager;
    RutasManager rutasManager;
    ClientesManager clientesManager;
    FileManager fileManager;
    LogOperacionesManager logOperacionesManager;
    ViajesManager viajesManager;


    public MudanzasCompartidas()
    {
        rutas = new Grafo();
        ciudades = new Diccionario();
        clientes = new MapeoAUno();
        solicitudes = new MapeoAMuchos();
        this.ciudadesManager = new CiudadesManager(ciudades, rutas);
        this.solicitudesManager = new SolicitudesManager(solicitudes);
        this.rutasManager = new RutasManager(rutas);
        this.clientesManager = new ClientesManager(clientes);
        this.fileManager = new FileManager(clientes, clientesManager, ciudades, ciudadesManager, solicitudesManager, solicitudes, rutas, rutasManager);
        this.viajesManager = new ViajesManager(ciudades, rutas, solicitudes);
    }

    public void gestionar()
    {
        String seleccion = "";
        while(!seleccion.equals("q"))
        {
            mostrarMenu();
            seleccion = InputReader.scanString("Seleccion");
            if(seleccion.equals("1"))
                ciudadesManager.gestionar();
            else if(seleccion.equals("2"))
                solicitudesManager.gestionar();
            else if(seleccion.equals("3"))
                rutasManager.gestionar();
            else if(seleccion.equals("4"))
                clientesManager.gestionar();
            else if(seleccion.equals("5"))
                viajesManager.gestionar();
            else if(seleccion.equals("6"))
                mostrarSistema();
        }
    }

    private void mostrarMenu()
    {
        System.out.println("Sistema de Mudanzas Compartidas");
        System.out.println("1. Gestionar Ciudades");
        System.out.println("2. Gestionar Solicitudes");
        System.out.println("3. Gestionar Rutas");
        System.out.println("4. Gestionar Clientes");
        System.out.println("5. Gestionar Viajes");
        System.out.println("6. Mostrar Sistema");
    }

    private void mostrarSistema()
    {
        System.out.println("Ciudades");
        ciudadesManager.mostrarEstructura();
        System.out.println("Solicitudes");
        solicitudesManager.mostrarEstructura();
        System.out.println("Rutas");
        rutasManager.mostrarEstructura();
        System.out.println("Clientes");
        clientesManager.mostrarEstructura();
    }

    public void run()
    {
        fileManager.leerArchivo("files/inicial.txt");
        fileManager.leerArchivo("files/estructuras.log");
        gestionar();
        fileManager.escribirArchivo("files/estructuras.log");
    }
}
