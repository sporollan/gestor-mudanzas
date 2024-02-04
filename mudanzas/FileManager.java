package mudanzas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import estructuras.grafo.Grafo;
import estructuras.lineales.dinamicas.Lista;
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

    public void leerArchivo(String path)
    {
        try {
            File file = new File(path);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                // recorro todo el archivo linea por linea
                // almacenando su contenido
                String data = myReader.nextLine();
                cargar(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        mostrarConteo();
        inicializarConteo();
    }

    public void escribirArchivo(String path)
    {
        try {
            FileWriter file = new FileWriter(path);
            BufferedWriter out = new BufferedWriter(file);
            System.out.println("Escribiendo");
            escribirClientes(out);
            escribirCiudades(out);
            escribirRutas(out);
            escribirSolicitudes(out);
            out.close();
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void escribirCiudades(BufferedWriter out) throws Exception
    {
        Lista ciudadesLista = this.ciudades.listarDatos();
        Ciudad c;
        String s;
        for(int i = 1; i <= ciudadesLista.longitud(); i++)
        {
            c = (Ciudad)ciudadesLista.recuperar(i);
            s = "C;" + c.getCodigo() + ";" + c.getNombre() + ";" + 
                c.getProvincia();
            out.write(s);
            out.newLine();
        }
    }

    public void escribirClientes(BufferedWriter out) throws Exception
    {
        Lista clientesLista = this.clientes.listarDatos();
        Cliente c;
        String s;
        for(int i = 1; i <= clientesLista.longitud(); i++)
        {
            c = (Cliente)clientesLista.recuperar(i);
            s = "P;" + c.getTipo() + ";" + c.getNum() + ";" + 
                c.getApellidos() + ";" + c.getNombres() + ";" +
                c.getTelefono() + ";" + c.getEmail();
            out.write(s);
            out.newLine();
        }
    }

    public void escribirRutas(BufferedWriter out) throws Exception
    {
        Lista rutasLista = this.rutas.listarDatos();
        Lista r;
        String s;
        for(int i = 1; i <= rutasLista.longitud(); i++)
        {
            r = (Lista)rutasLista.recuperar(i);
            s = "R;" + r.recuperar(1) + ";" + r.recuperar(2) + ";" + 
                r.recuperar(3);
            out.write(s);
            out.newLine();
        }
    }
    public void escribirSolicitudes(BufferedWriter out) throws Exception
    {
        Lista ciudadesLista = this.ciudades.listarDatos();
        Ciudad c;
        String s;
        for(int ciudadIndex = 1; ciudadIndex <= ciudadesLista.longitud(); ciudadIndex++)
        {
            c = (Ciudad)ciudadesLista.recuperar(ciudadIndex);
            Lista solicitudesTotalLista = c.listarSolicitudes();
            Lista solicitudesLista;
            Solicitud sol;
            Cliente cl;
            for(int solTotalIndex = 1; solTotalIndex <= solicitudesTotalLista.longitud(); solTotalIndex++)
            {
                solicitudesLista = (Lista)(solicitudesTotalLista.recuperar(solTotalIndex));
                for(int solIndex = 1; solIndex<=solicitudesLista.longitud(); solIndex++)
                {
                    sol = (Solicitud)solicitudesLista.recuperar(solIndex);
                    cl = sol.getCliente();
                    s = "S;" + c.getCodigo() + ";" + sol.getDestino().getCodigo() + ";" + 
                    sol.getFecha() + ";" + cl.getTipo() + ";" +
                    cl.getNum() + ";" + sol.getMetrosCubicos() + ";" +
                    sol.getBultos() + ";" + sol.getDomicilioRetiro() + ";" +
                    sol.getDomicilioEntrega() + ";" + (sol.isEstaPago()?"T":"F");

                    out.write(s);
                    out.newLine();
                }
            }
        }
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
        if(clientesManager.insertar(c))
        {
            this.count[0] += 1;
        }
        else
        {
            // System.out.println("Error insertando cliente " + tipo+num);
        }
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
            // leo todos los datos
            // si no hay error se considera la carga valida
            cpo = Integer.parseInt(tokenizer.nextToken());
            inputReader.comprobarCp(cpo);

            cpd = Integer.parseInt(tokenizer.nextToken());
            inputReader.comprobarCp(cpd);

            fecha = tokenizer.nextToken();
            inputReader.comprobarFecha(fecha);

            tipo = tokenizer.nextToken();
            num = tokenizer.nextToken();
            // comprobarclavecliente tira error si no existe el cliente
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
            // si la carga es valida se inserta en su estructura
            Ciudad ciudadOrigen = (Ciudad)ciudades.obtenerInformacion(cpo);
            Solicitud solicitud = new Solicitud(
                (Ciudad)(ciudades.obtenerInformacion(cpd)), fecha, 
                (Cliente)clientes.obtenerValor(tipo+num), metrosCubicos, 
                bultos, dirRetiro, dirEntrega, esPago);

            if(solicitudesManager.insertar(ciudadOrigen, solicitud))
            {
                this.count[2] += 1;
            }
            else
            {
                //System.out.println("Error insertando solicitud " + cpo + " " + cpd);
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

        if(rutasManager.insertar(cpo, cpd, distancia))
        {
            this.count[3] += 1;
        }
        else
        {
            //System.out.println("Error insertando ruta " + cpo + " " + cpd);
        }
    }

    public void cargar(String line)
    {
        // cada linea se separa segun ; y se carga segun la primer letra
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        if(tokenizer.hasMoreTokens())
        {
            String tipo = tokenizer.nextToken();
            try
            {
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
            } catch (Exception e)
            {
                System.out.println("Error cargando " + tipo);
            }
        }
    }
}
