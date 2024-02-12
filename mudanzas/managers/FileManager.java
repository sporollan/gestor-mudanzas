package mudanzas.managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import estructuras.grafo.Grafo;
import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.Diccionario;
import estructuras.propositoEspecifico.MapeoAMuchos;
import estructuras.propositoEspecifico.MapeoAUno;
import mudanzas.datos.Ciudad;
import mudanzas.datos.Cliente;
import mudanzas.datos.Solicitud;
import mudanzas.librerias.InputReader;

// clase para leer y escribir estructuras a archivo

public class FileManager {
    private MapeoAUno clientes;
    private ClientesManager clientesManager;
    private Diccionario ciudades;
    private CiudadesManager ciudadesManager;
    private SolicitudesManager solicitudesManager;
    private MapeoAMuchos solicitudes;
    private Grafo rutas;
    private RutasManager rutasManager;
    private int[] count;

    public FileManager(MapeoAUno clientes, ClientesManager clientesManager, Diccionario ciudades, CiudadesManager ciudadesManager, SolicitudesManager solicitudesManager, MapeoAMuchos solicitudes, Grafo rutas, RutasManager rutasManager)
    {
        this.clientes = clientes;
        this.clientesManager = clientesManager;
        this.ciudades = ciudades;
        this.ciudadesManager = ciudadesManager;
        this.solicitudesManager = solicitudesManager;
        this.solicitudes = solicitudes;
        this.rutas = rutas;
        this.rutasManager = rutasManager;
        this.inicializarConteo();
    }

    private void inicializarConteo()
    {
        this.count = new int[4];
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

    public void leerArchivo(String path)
    {
        // se lee un archivo y se cargan sus datos a las estructuras
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
        // se escriben las estructuras al archivo dado
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

    private void escribirCiudades(BufferedWriter out) throws Exception
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

    private void escribirClientes(BufferedWriter out) throws Exception
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

    private void escribirRutas(BufferedWriter out) throws Exception
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
    private void escribirSolicitudes(BufferedWriter out) throws Exception
    {
        Lista ciudadesLista = this.ciudades.listarDatos();
        Ciudad c;
        String s;
        for(int cpoIndex = 1; cpoIndex < ciudadesLista.longitud(); cpoIndex++)
        {
            // se recorren todas las ciudades almacenadas
            // por cada una se consiguen todas las solicitudes que parten de la misma
            // por cada ciudad destino hay una lista de solicitudes
            // por lo tanto solicitudesTotalLista es una lista de listas
            c = (Ciudad)ciudadesLista.recuperar(cpoIndex);
            Comparable cpo = c.getCodigo();
            Solicitud sol;
            String cl;
            for(int cpdIndex = cpoIndex+1; cpdIndex <= ciudadesLista.longitud(); cpdIndex++)
            {
                Comparable cpd = ((Ciudad)ciudadesLista.recuperar(cpdIndex)).getCodigo();
                String  codigoIdaStr = cpo +""+ cpd;
                String codigoVueltaStr = cpd + "" + cpo;
                int codigoIda = Integer.parseInt(codigoIdaStr);
                int codigoVuelta = Integer.parseInt(codigoVueltaStr);
                Lista solicitudesIda = solicitudes.obtenerValor(codigoIda);
                Lista solicitudesVuelta = solicitudes.obtenerValor(codigoVuelta);
                if(solicitudesIda != null)
                {
                    for(int solIndex = 1; solIndex<=solicitudesIda.longitud(); solIndex++)
                    {
                        // se recorren las solicitudes
                        sol = (Solicitud)solicitudesIda.recuperar(solIndex);
                        String codigo = ""+sol.getCodigo();
                        String origen = "";
                        String destino = "";
                        for(int i = 0; i < 4; i++)
                        {
                            origen = origen + codigo.charAt(i);
                            destino = destino + codigo.charAt(i+4);
                        }

                        String tipo = "";
                        String num = "";
                        cl = sol.getCliente();
                        for(int i = 0; i < 3; i++)
                        {
                            tipo = tipo + cl.charAt(i);
                        }
                        for(int i = 3; i < cl.length(); i++)
                        {
                            num = num + cl.charAt(i);
                        }
                        s = "S;" + origen + ";" + destino + ";" + 
                        sol.getFecha() + ";" + tipo + ";" + num + ";"
                        + sol.getMetrosCubicos() + ";" +
                        sol.getBultos() + ";" + sol.getDomicilioRetiro() + ";" +
                        sol.getDomicilioEntrega() + ";" + (sol.isEstaPago()?"T":"F");

                        out.write(s);
                        out.newLine();
                    }
                }
                if(solicitudesVuelta != null)
                {
                    for(int solIndex = 1; solIndex<=solicitudesVuelta.longitud(); solIndex++)
                    {
                        // se recorren las solicitudes
                        sol = (Solicitud)solicitudesVuelta.recuperar(solIndex);
                        String codigo = ""+sol.getCodigo();
                        String origen = "";
                        String destino = "";
                        for(int i = 0; i < 4; i++)
                        {
                            origen = origen + codigo.charAt(i);
                            destino = destino + codigo.charAt(i+4);
                        }
                        String tipo = "";
                        String num = "";
                        cl = sol.getCliente();
                        for(int i = 0; i < 3; i++)
                        {
                            tipo = tipo + cl.charAt(i);
                        }
                        for(int i = 3; i < cl.length(); i++)
                        {
                            num = num + cl.charAt(i);
                        }                        
                        s = "S;" + origen + ";" + destino + ";" + 
                        sol.getFecha() + ";" + tipo + ";" + num + ";"
                        + sol.getMetrosCubicos() + ";" +
                        sol.getBultos() + ";" + sol.getDomicilioRetiro() + ";" +
                        sol.getDomicilioEntrega() + ";" + (sol.isEstaPago()?"T":"F");

                        out.write(s);
                        out.newLine();
                    }
                }
            }
        }
    }

    private void cargar(String line)
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
                e.printStackTrace();
            }
        }
    }

    private void cargarCliente(StringTokenizer tokenizer) throws Exception
    {
        String tipo, num, apellido, nombre, telefono, email;
        tipo = tokenizer.nextToken();
        num = tokenizer.nextToken();
        apellido = tokenizer.nextToken();
        nombre = tokenizer.nextToken();
        telefono = tokenizer.nextToken();
        email = tokenizer.nextToken();

        // se comprueba la validez de los datos
        InputReader.comprobarTipoDocumento(tipo);
        InputReader.comprobarNumeroDocumento(num);
        Cliente c = new Cliente(tipo, num, nombre, apellido, telefono, email);
        if(clientesManager.insertar(c))
        {
            this.count[0] += 1;
        }

    }

    private void cargarCiudad(StringTokenizer tokenizer) throws Exception
    {
        int codigo;
        String nombre, provincia;

        codigo = Integer.parseInt(tokenizer.nextToken());
        nombre = tokenizer.nextToken();
        provincia = tokenizer.nextToken();

        // se comprueba la validez de los datos
        InputReader.comprobarCp(codigo);
        Ciudad c = new Ciudad((Comparable)codigo, nombre, provincia);
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
            InputReader.comprobarCp(cpo);

            cpd = Integer.parseInt(tokenizer.nextToken());
            InputReader.comprobarCp(cpd);

            fecha = tokenizer.nextToken();
            InputReader.comprobarFecha(fecha);

            tipo = tokenizer.nextToken();
            num = tokenizer.nextToken();

            metrosCubicos = Integer.parseInt(tokenizer.nextToken());
            bultos = Integer.parseInt(tokenizer.nextToken());
            dirRetiro = tokenizer.nextToken();
            dirEntrega = tokenizer.nextToken();
            esPago = tokenizer.nextToken().equals("T");
            cargaValida = true;
        } catch(Exception e) {
            System.out.println("Error con la carga de datos");
            e.printStackTrace();
        }

        if(cargaValida)
        {
            // si la carga es valida se inserta en su estructura
            String codigoStr = cpo + "" + cpd;
            int codigo = Integer.parseInt(codigoStr);
            Solicitud solicitud = new Solicitud(
                (Comparable)codigo, fecha, 
                (tipo+num), metrosCubicos, 
                bultos, dirRetiro, dirEntrega, esPago);


            if(solicitudesManager.insertar((Comparable)codigo, solicitud))
            {
                this.count[2] += 1;
            }
            else
            {
                //System.out.println("Error insertando solicitud " + cpo + " " + cpd);
            }
        }
    }

    private void cargarRuta(StringTokenizer tokenizer) throws Exception
    {
        int cpo, cpd;
        float distancia;
        cpo = Integer.parseInt(tokenizer.nextToken());
        cpd = Integer.parseInt(tokenizer.nextToken());
        distancia = Float.parseFloat(tokenizer.nextToken());

        // se comprueba la validez de los datos
        InputReader.comprobarCp(cpo);
        InputReader.comprobarCp(cpd);
        if(rutasManager.insertar(cpo, cpd, distancia))
        {
            this.count[3] += 1;
        }
        else
        {
            //System.out.println("Error insertando ruta " + cpo + " " + cpd);
        }
    }
}
