package mudanzas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import estructuras.grafo.Grafo;
import estructuras.propositoEspecifico.Diccionario;
import estructuras.propositoEspecifico.MapeoAUno;

public class FileManager {
    private MapeoAUno clientes;
    private ClientesManager clientesManager;
    private Diccionario ciudades;
    private CiudadesManager ciudadesManager;
    private SolicitudesManager solicitudesManager;
    private Grafo rutas;
    private RutasManager rutasManager;
    private InputReader inputReader;
    private int[] count;

    public FileManager(InputReader inputReader, MapeoAUno clientes, ClientesManager clientesManager, Diccionario ciudades, CiudadesManager ciudadesManager, SolicitudesManager solicitudesManager, Grafo rutas, RutasManager rutasManager)
    {
        this.clientes = clientes;
        this.clientesManager = clientesManager;
        this.ciudades = ciudades;
        this.ciudadesManager = ciudadesManager;
        this.solicitudesManager = solicitudesManager;
        this.rutas = rutas;
        this.rutasManager = rutasManager;
        this.inputReader = inputReader;
        this.inicializarConteo();
    }

    private void inicializarConteo()
    {
        this.count = new int[4];
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
        mostrarConteo();
        inicializarConteo();
    }

    private void mostrarConteo()
    {
        System.out.println();
        System.out.println("Carga Completada");
        System.out.println("Insertados:");
        System.out.println(this.count[0] + " Clientes");
        System.out.println(this.count[1] + " Ciudades");
        System.out.println(this.count[2] + " Solicitudes");
        System.out.println(this.count[3] + " Rutas");
        System.out.println();
    }

    private void cargarCliente(StringTokenizer tokenizer)
    {
        String tipo, num, apellido, nombre, telefono, email;
        tipo = tokenizer.nextToken();
        num = tokenizer.nextToken();
        apellido = tokenizer.nextToken();
        nombre = tokenizer.nextToken();
        telefono = tokenizer.nextToken();
        email = tokenizer.nextToken();
        Cliente c = new Cliente(tipo, num, nombre, apellido, telefono, email);
        if(!clientesManager.insertar(c))
            System.out.println("Error insertando cliente " + tipo+num);
        else
            this.count[0] += 1;
    }

    private void cargarCiudad(StringTokenizer tokenizer)
    {
        Comparable codigo;
        String nombre, provincia;

        codigo = Integer.parseInt(tokenizer.nextToken());
        nombre = tokenizer.nextToken();
        provincia = tokenizer.nextToken();

        Ciudad c = new Ciudad(codigo, nombre, provincia, inputReader);
        if(ciudadesManager.insertar(c))
        {
            this.count[1]+=1;
        }

    }

    private void cargarSolicitud(StringTokenizer tokenizer)
    {
        int cpo=-1, cpd=-1;
        String fecha="";
        String tipo="";
        String num="";
        int metrosCubicos=-1, bultos=-1;
        String dirRetiro="", dirEntrega="";
        Boolean esPago=false;
        Boolean cargaValida = false;

        try
        {
            cpo = Integer.parseInt(tokenizer.nextToken());
            inputReader.comprobarCp(cpo);

            cpd = Integer.parseInt(tokenizer.nextToken());
            inputReader.comprobarCp(cpd);

            fecha = tokenizer.nextToken();
            inputReader.comprobarFecha(fecha);

            tipo = tokenizer.nextToken();
            num = tokenizer.nextToken();
            inputReader.comprobarClaveCliente(tipo+num);

            metrosCubicos = Integer.parseInt(tokenizer.nextToken());
            bultos = Integer.parseInt(tokenizer.nextToken());
            dirRetiro = tokenizer.nextToken();
            dirEntrega = tokenizer.nextToken();
            esPago = tokenizer.nextToken().equals("T");
            cargaValida = true;
        } catch(Exception e) {
            System.out.println("Error con la carga de datos");
        }

        if(cargaValida)
        {            
            Ciudad ciudadOrigen = (Ciudad)ciudades.obtenerInformacion(cpo);
            Solicitud solicitud = new Solicitud(
                (Ciudad)(ciudades.obtenerInformacion(cpd)), fecha, 
                (Cliente)clientes.obtenerValor(tipo+num), metrosCubicos, 
                bultos, dirRetiro, dirEntrega, esPago);

            if(!solicitudesManager.insertar(ciudadOrigen, solicitud))
            {
                System.out.println("Error insertando solicitud " + cpo + " " + cpd);
            }
            else
            {
                this.count[2] += 1;
            }
        }
    }

    private void cargarRuta(StringTokenizer tokenizer)
    {
        int cpo, cpd;
        float distancia;
        cpo = Integer.parseInt(tokenizer.nextToken());
        cpd = Integer.parseInt(tokenizer.nextToken());
        distancia = Float.parseFloat(tokenizer.nextToken());

        if(!rutasManager.insertar(cpo, cpd, distancia))
            System.out.println("Error insertando ruta " + cpo + " " + cpd);
        else
            this.count[3] += 1;
    }

    public void cargar(String data)
    {
        StringTokenizer tokenizer = new StringTokenizer(data, ";");
        String tipo = tokenizer.nextToken();
        
        if(tipo.equals("P"))
        {
            cargarCliente(tokenizer);
        }
        else if (tipo.equals("C"))
        {
            cargarCiudad(tokenizer);
        }
        else if (tipo.equals("S"))
        {
            cargarSolicitud(tokenizer);
        }
        else if (tipo.equals("R"))
        {
            cargarRuta(tokenizer);
        }
    }
}
