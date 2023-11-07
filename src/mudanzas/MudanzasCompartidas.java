package src.mudanzas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import lib.conjuntistas.ArbolAVL;
import lib.conjuntistas.Grafo;
import lib.conjuntistas.TablaHash;
import lib.lineales.dinamicas.Lista;

public class MudanzasCompartidas {
    boolean isRunning;
    Scanner sc;
    TablaHash clientes;
    Grafo rutas;
    ArbolAVL ciudades;

    CiudadesManager ciudadesManager;
    InputReader inputReader;
    SolicitudesManager solicitudesManager;
    RutasManager rutasManager;
    ClientesManager clientesManager;

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

    }

    public void gestionarMenu()
    {
        String seleccion = "";
        while(!seleccion.equals("q"))
        {
            mostrarMenu();
            seleccion = inputReader.scanString("Selecion:");
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

    public void cargarArchivo(String path)
    {
        try {
        File file = new File(path);
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            cargar(data);
        }
        myReader.close();
        } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
        }
    }

    public void cargar(String data)
    {
        StringTokenizer tokenizer = new StringTokenizer(data, ";");
        String tipo = tokenizer.nextToken();
        
        if(tipo.equals("P"))
        {
            String claveCliente, apellido, nombre, telefono, email;
            claveCliente = tokenizer.nextToken();
            claveCliente = claveCliente + tokenizer.nextToken();
            apellido = tokenizer.nextToken();
            nombre = tokenizer.nextToken();
            telefono = tokenizer.nextToken();
            email = tokenizer.nextToken();

            if(clientes.insertar(new Cliente(claveCliente, nombre, apellido, telefono, email)))
                System.out.println("Cliente insertado");
        }
        else if (tipo.equals("C"))
        {
            Comparable codigo;
            String nombre, provincia;

            codigo = Integer.parseInt(tokenizer.nextToken());
            nombre = tokenizer.nextToken();
            provincia = tokenizer.nextToken();

            if(ciudades.insertar(new Ciudad(codigo, nombre, provincia)))
            {
                System.out.println("Ciudad insertada");
                if (rutas.insertarVertice(codigo))
                    System.out.println("Vertice creado con exito");
            }
        }
        else if (tipo.equals("S"))
        {
            // S;5000;8300;15/06/2023;DNI;35678965;13;5;Sarmiento 3400;Roca 2100;T
            Comparable cpo, cpd;
            String fecha;
            String claveCliente;
            int metrosCubicos, bultos;
            String dirRetiro, dirEntrega;
            Boolean esPago;

            cpo = Integer.parseInt(tokenizer.nextToken());
            cpd = Integer.parseInt(tokenizer.nextToken());
            fecha = tokenizer.nextToken();
            claveCliente = tokenizer.nextToken() + tokenizer.nextToken();
            metrosCubicos = Integer.parseInt(tokenizer.nextToken());
            bultos = Integer.parseInt(tokenizer.nextToken());
            dirRetiro = tokenizer.nextToken();
            dirEntrega = tokenizer.nextToken();
            esPago = tokenizer.nextToken().equals("T");

            Ciudad ciudadOrigen = (Ciudad)ciudades.obtener(cpo);
            if(ciudadOrigen.insertarSolicitud(new Solicitud((Ciudad)ciudades.obtener(cpd), fecha, clientes.obtener(claveCliente), metrosCubicos, bultos, dirRetiro, dirEntrega, esPago)))
                System.out.println("Solicitud insertada");
        }
        else if (tipo.equals("R"))
        {
            Comparable cpo, cpd;
            float distancia;
            cpo = Integer.parseInt(tokenizer.nextToken());
            cpd = Integer.parseInt(tokenizer.nextToken());
            distancia = Float.parseFloat(tokenizer.nextToken());

            if(rutas.insertarArco(cpo, cpd, distancia))
                System.out.println("Ruta insertada");
            else
                System.out.println("Ruta no insertada");
        }
    }

    public void run()
    {
        isRunning = true;
        gestionarMenu();
    }
}
