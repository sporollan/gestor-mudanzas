package mudanzas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import estructuras.conjuntistas.ArbolAVL;
import estructuras.conjuntistas.TablaHash;
import estructuras.grafo.Grafo;

public class FileManager {
    private TablaHash clientes;
    private ArbolAVL ciudades;
    private Grafo rutas;
    private InputReader inputReader;
    private int[] count;

    public FileManager(InputReader inputReader, TablaHash clientes, ArbolAVL ciudades, Grafo rutas)
    {
        this.clientes = clientes;
        this.ciudades = ciudades;
        this.rutas = rutas;
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
        String claveCliente, apellido, nombre, telefono, email;
        claveCliente = tokenizer.nextToken();
        claveCliente = claveCliente + tokenizer.nextToken();
        apellido = tokenizer.nextToken();
        nombre = tokenizer.nextToken();
        telefono = tokenizer.nextToken();
        email = tokenizer.nextToken();

        if(!clientes.insertar(new Cliente(claveCliente, nombre, apellido, telefono, email)))
            System.out.println("Error insertando cliente " + claveCliente);
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

        if(ciudades.insertar(new Ciudad(codigo, nombre, provincia)))
        {
            this.count[1]+=1;
            if(!rutas.insertarVertice(codigo))
                System.out.println("Error creando vertice " + codigo);
        }
        else
        {
            System.out.println("Error insertando ciudad " + codigo);
        }
    }

    private void cargarSolicitud(StringTokenizer tokenizer)
    {
        int cpo=-1, cpd=-1;
        String fecha="";
        String claveCliente="";
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

            claveCliente = tokenizer.nextToken() + tokenizer.nextToken();
            System.out.println(claveCliente);
            inputReader.comprobarClaveCliente(claveCliente);

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
            Ciudad ciudadOrigen = (Ciudad)ciudades.obtener(cpo);
            if(!ciudadOrigen.insertarSolicitud(new Solicitud((Ciudad)ciudades.obtener(cpd), fecha, clientes.obtener(claveCliente), metrosCubicos, bultos, dirRetiro, dirEntrega, esPago)))
                System.out.println("Error insertando solicitud " + cpo + " " + cpd);
            else
                this.count[2] += 1;
        }
    }

    private void cargarRuta(StringTokenizer tokenizer)
    {
        Comparable cpo, cpd;
        float distancia;
        cpo = Integer.parseInt(tokenizer.nextToken());
        cpd = Integer.parseInt(tokenizer.nextToken());
        distancia = Float.parseFloat(tokenizer.nextToken());

        if(!rutas.insertarArco(cpo, cpd, distancia))
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
