package src.mudanzas;

import lib.conjuntistas.ArbolAVL;
import lib.conjuntistas.Grafo;
import lib.conjuntistas.TablaHash;


public class MudanzasCompartidas {
    TablaHash clientes;
    Grafo rutas;
    ArbolAVL ciudades;

    CiudadesManager ciudadesManager;
    InputReader inputReader;
    SolicitudesManager solicitudesManager;
    RutasManager rutasManager;
    ClientesManager clientesManager;
    FileManager fileManager;


    public MudanzasCompartidas()
    {
        rutas = new Grafo();
        ciudades = new ArbolAVL();
        clientes = new TablaHash();
        this.inputReader = new InputReader(clientes, ciudades);
        this.ciudadesManager = new CiudadesManager(this.inputReader, ciudades, rutas);
        this.solicitudesManager = new SolicitudesManager(inputReader, ciudades);
        this.rutasManager = new RutasManager(inputReader, ciudades, rutas);
        this.clientesManager = new ClientesManager(inputReader, clientes);
        this.fileManager = new FileManager(inputReader, clientes, ciudades, rutas);
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
                clientesManager.gestionar();        }
    }

    private void mostrarMenu()
    {
        System.out.println("Sistema de Mudanzas Compartidas");
        System.out.println("1. Gestionar Ciudades");
        System.out.println("2. Gestionar Solicitudes");
        System.out.println("3. Gestionar Rutas");
        System.out.println("4. Gestionar Clientes");
    }



    public void run()
    {
        fileManager.cargarArchivo("files/inicial.txt");
        gestionar();
    }
}
