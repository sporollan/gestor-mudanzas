package mudanzas.managers;

import estructuras.grafo.Grafo;
import estructuras.propositoEspecifico.Diccionario;
import estructuras.propositoEspecifico.MapeoAMuchos;
import estructuras.propositoEspecifico.MapeoAUno;
import mudanzas.LogOperacionesManager;
import mudanzas.librerias.InputReader;
import mudanzas.managers.RutasManager;


public class MudanzasCompartidas {
    MapeoAUno clientes;
    Grafo rutas;
    Diccionario ciudades;

    CiudadesManager ciudadesManager;
    InputReader inputReader;
    SolicitudesManager solicitudesManager;
    RutasManager rutasManager;
    ClientesManager clientesManager;
    FileManager fileManager;
    LogOperacionesManager logOperacionesManager;
    ViajesManager viajesManager;
    MapeoAMuchos solicitudes;


    public MudanzasCompartidas()
    {
        rutas = new Grafo();
        ciudades = new Diccionario();
        clientes = new MapeoAUno();
        solicitudes = new MapeoAMuchos();
        this.logOperacionesManager = new LogOperacionesManager("files/operaciones.log");
        this.inputReader = new InputReader(clientes, ciudades);
        this.ciudadesManager = new CiudadesManager(this.inputReader, ciudades, rutas, logOperacionesManager);
        this.solicitudesManager = new SolicitudesManager(inputReader, solicitudes, logOperacionesManager);
        this.rutasManager = new RutasManager(inputReader, rutas, logOperacionesManager);
        this.clientesManager = new ClientesManager(inputReader, clientes, logOperacionesManager);
        this.fileManager = new FileManager(inputReader, clientes, clientesManager, ciudades, ciudadesManager, solicitudesManager, solicitudes, rutas, rutasManager);
        this.viajesManager = new ViajesManager(inputReader, ciudades, rutas, solicitudes);
    }

    public void gestionar()
    {
        String seleccion = "";
        while(!seleccion.equals("q"))
        {
            mostrarMenu();
            seleccion = inputReader.scanString("Seleccion");
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
