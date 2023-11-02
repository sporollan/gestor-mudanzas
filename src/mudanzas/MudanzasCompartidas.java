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
    ArbolAVL ciudades;
    TablaHash clientes;
    Grafo rutas;

    public MudanzasCompartidas()
    {
        sc = new Scanner(System.in);
        ciudades = new ArbolAVL();
        clientes = new TablaHash();
        rutas = new Grafo();
    }

    public void mostrarMenu()
    {
        String i = "";
        while(!i.equals("q"))
        {
            System.out.println("Sistema de Mudanzas Compartidas");
            System.out.println("1. Gestionar Ciudades");
            System.out.println("2. Gestionar Solicitudes");
            System.out.println("3. Gestionar Rutas");
            System.out.println("4. Gestionar Clientes");
            i = sc.nextLine();
            if(i.equals("1"))
                mostrarMenuCiudades();
            if(i.equals("2"))
                mostrarMenuSolicitudes();
            if(i.equals("3"))
                mostrarMenuRutas();
            if(i.equals("4"))
                mostrarMenuClientes();
        }
    }

    public void mostrarMenuClientes()
    {
        String i = "";
        while(!i.equals("q"))
        {
            System.out.println("Gestionar Clientes");
            System.out.println("1. Listar");
            System.out.println("2. Insertar");
            System.out.println("3. Mostrar");
            i = sc.nextLine();
            if(i.equals("1"))
                System.out.println(clientes);
            if(i.equals("2"))
                insertarCliente();
            if(i.equals("3"))
                System.out.println(scanCliente());
        }  
    }

    public void insertarCliente()
    {
        String[] stringValues = {
            "Nombres", "Apellidos", "Telefono", "Email"
        };
        String[] sInputs = new String[stringValues.length+1];
        String clave;
        boolean continuar;
        
        // cargar clave de cliente
        clave = scanClaveCliente();
        continuar = !clave.equals("q");

        // cargar datos de cliente
        if(continuar)
        {
            sInputs = cargarStringsSc(stringValues);
            continuar = !sInputs[stringValues.length].equals("q");
        }

        // almacenar los datos en la estructura
        if(continuar)
        {
            if(clientes.insertar(new Cliente(clave, sInputs[0], sInputs[1], sInputs[2], sInputs[3])))
                System.out.println("Cliente insertado con exito");
        }
    }

    public Cliente scanCliente()
    {
        Cliente cliente = null;
        String c = "";
        while(!c.equals("q") && cliente == null)
        {
            c = scanClaveCliente();
            cliente = (Cliente)this.clientes.obtener(c);
        }
        return cliente;
    }

    public String scanClaveCliente()
    {
        String clave = "";
        boolean valida = false;
        while(!valida && !clave.equals("q"))
        {
            try
            {
                System.out.println("Clave Cliente (ej. dni12345678)");
                clave = sc.nextLine();
                valida = comprobarClaveCliente(clave);
            } catch (Exception e){}
        }
        return clave;
    }

    public String[] cargarStringsSc(String[] stringValues)
    {
        int sL = stringValues.length;
        String[] scannedInputs = new String[sL+1];
        int i = 0;
        do
        {
            scannedInputs[i] = scanString(stringValues[i]);
            i+=1;
        } while(!scannedInputs[i-1].equals("q") && i < sL);

        // se almacena el ultimo valor ingresado al final, para leer q
        scannedInputs[sL] = scannedInputs[i-1];

        return scannedInputs;
    }

    public String scanString(String message)
    {
        String s = "";
        while(s.equals("") && !s.equals("q"))
        {
            try
            {
                System.out.println(message);
                s = sc.nextLine();
            } catch (Exception e){}
        }
        return s;
    }

    public int[] cargarIntsSc(String[] intValues)
    {
        int sL = intValues.length;
        int[] scannedInputs = new int[sL+1];
        int i = 0;
        do
        {
            scannedInputs[i] = scanInt(intValues[i]);
            i+=1;
        } while(scannedInputs[i-1] != -1 && i < sL);

        // se almacena el ultimo valor ingresado al final, para leer q
        scannedInputs[sL] = scannedInputs[i-1];

        return scannedInputs;
    }

    public int scanInt(String message)
    {
        int i = -1;
        String input ="";
        while(i < 1 && !input.equals("q"))
        {
            try
            {
                System.out.println(message);
                input = sc.nextLine();
                i = Integer.parseInt(input);
            } catch (Exception e){}
        }
        return i;
    }

    public void mostrarMenuRutas()
    {
        String i = "";
        while(!i.equals("q"))
        {
            System.out.println("Gestionar Rutas");
            System.out.println("1. Mostrar");
            System.out.println("2. Insertar");
            System.out.println("3. Consultar Ruta");
            i = sc.nextLine();
            if(i.equals("1"))
                System.out.println(rutas.listarEnProfundidad());
            if(i.equals("2"))
                insertarRuta();
            if(i.equals("3"))
                consultarRuta();
        }  
    }

    public void consultarRuta()
    {
        int cpo = -1;
        int cpd = -1;
        boolean continuar = true;

        cpo = scanCp("Origen(cp)");
        continuar = cpo != -1;

        if(continuar)
        {
            cpd = scanCp("Destino(cp)");
            continuar = cpd != -1;
        }

        if(continuar)
        {
            if(rutas.existeCamino(cpo, cpd))
                System.out.println("La ruta existe");
            else
                System.out.println("La ruta no existe");
        }
    }

    public int scanCp(String message)
    {
        String s = "";
        int cp = -1;
        boolean valido = false;
        while(!valido && !s.equals("q"))
        {
            try
            {
                System.out.println(message);
                s = sc.nextLine();
                if(!s.equals("q"))
                {
                    cp = Integer.parseInt(s);
                    valido = comprobarCp(cp);
                }

            } catch (Exception e){
                cp = -1;
            }
        }
        return cp;
    }

    public void mostrarMenuCiudades()
    {
        String i = "";
        while(!i.equals("q"))
        {
            System.out.println("Gestionar Ciudades");
            System.out.println("1. Mostrar");
            System.out.println("2. Insertar");
            i = sc.nextLine();
            if(i.equals("1"))
                System.out.println(ciudades.toString());
            if(i.equals("2"))
                insertarCiudad();
        }
    }

    public void insertarRuta()
    {
        int cpo = -1;
        int cpd = -1;
        int distancia = -1;
        boolean continuar = true;

        cpo = scanCp("Origen(cp)");
        continuar = cpo != -1;

        if(continuar)
        {
            cpd = scanCp("Destino(cp)");
            continuar = cpd != -1;
        }

        if(continuar)
        {
            distancia = scanInt("Distancia");
            continuar = distancia != -1;
        }

        if(continuar)
        {
            if(rutas.insertarArco(cpo, cpd, distancia))
                System.out.println("Insertado con exito");
        }

    }

    public void mostrarMenuSolicitudes()
    {
        String i = "";
        while(!i.equals("q"))
        {
            System.out.println("Gestionar Solicitudes");
            System.out.println("1. Mostrar");
            System.out.println("2. Insertar");
            i = sc.nextLine();
            if(i.equals("1"))
                mostrarSolicitudes();
            if(i.equals("2"))
                insertarSolicitud();
        }
    }

    public void insertarCiudad()
    {
        int cpo = -1;
        String[] stringValues = {"Nombre", "Provincia"};
        String[] sInputs = new String[stringValues.length+1];
        boolean continuar = true;

        cpo = scanCp("Codigo Postal");
        continuar = cpo != -1;

        if(continuar)
        {
            sInputs = cargarStringsSc(stringValues);
            continuar = !sInputs[stringValues.length].equals("q");
        }

        if (continuar && ciudades.insertar(new Ciudad((Comparable)cpo, sInputs[0], sInputs[1])))
        {
            System.out.println("Insertado con exito");
            if (rutas.insertarVertice((Comparable)cpo))
                System.out.println("Vertice creado con exito");
        }
    }

    private boolean comprobarCp(int cp) throws Exception
    {
        int m;
        m = cp / 1000;
        if(m < 1 || m > 9)
        {
            System.out.println("Codigo Postal no valido");
            throw new Exception();
        }
        return true;
    }

    private boolean comprobarClaveCliente(String cc) throws Exception
    {
        if(cc.length() != 11)
        {
            System.out.println("Clave Cliente no valida");
            throw new Exception();
        }
        return true;
    }

    public void insertarSolicitud()
    {
        boolean estaPago;
        String input;

        String[] stringValues = {"Fecha", "Domicilio Retiro", "Domicilio Entrega"};
        String[] intValues = {"Metros Cubicos", "Bultos"};
        String[] sInputs = new String[stringValues.length+1];
        int[] iInputs = new int[intValues.length+1];

        Solicitud solicitud;
        Ciudad ciudadOrigen, ciudadDestino;
        Cliente cliente;
        boolean continuar = true;

        ciudadOrigen = scanCiudad("Origen");
        continuar = ciudadOrigen != null;

        ciudadDestino = null;
        if(continuar)
        {
            ciudadDestino = scanCiudad("Destino");
            continuar = ciudadDestino != null;
        }

        if(continuar)
        {
            sInputs = cargarStringsSc(stringValues);
            continuar = !sInputs[stringValues.length].equals("q");
        }

        if(continuar)
        {
            iInputs = cargarIntsSc(intValues);
            continuar = !(iInputs[intValues.length] == -1);
        }

        cliente = null;
        if(continuar)
        {
            cliente = scanCliente();
            continuar = cliente != null;
        }

        estaPago = false;
        if(continuar)
        {
            estaPago = false;
            input = "";
            while(!(input.equals("y") || input.equals("n") || input.equals("q")))
            {
                try
                {
                    System.out.println("Esta Pago? y/n");
                    input = sc.nextLine();
                    estaPago = input == "y";
                } catch (Exception e){}
            }
            continuar = !input.equals("q");
        }

        if(continuar)
        {
            solicitud = new Solicitud(ciudadDestino, sInputs[0], cliente, iInputs[0], iInputs[1], sInputs[1], sInputs[2], estaPago);
            if(ciudadOrigen.insertarSolicitud(solicitud))
                System.out.println("Solicitud creada con exito");
        }
    }

    public Ciudad scanCiudad(String message)
    {
        Ciudad ciudad = null;
        int cp = 0;
        while(cp != -1 && ciudad == null)
        {
            cp = scanCp("Codigo Postal " + message);
            ciudad = (Ciudad)this.ciudades.obtener((Comparable)cp);
        }
        return ciudad;
    }

    public void mostrarSolicitudes()
    {
        Ciudad ciudadOrigen, ciudadDestino;
        Lista solicitudes;
        boolean continuar;

        ciudadOrigen = scanCiudad("Codigo Postal Origen");
        continuar = ciudadOrigen != null;

        ciudadDestino = null;
        if(continuar)
        {
            ciudadDestino = scanCiudad("Codigo Postal Destino");
            continuar = ciudadDestino != null;
        }

        if(continuar)
        {
            solicitudes = ciudadOrigen.obtenerSolicitudes(ciudadDestino.getCodigo());
            System.out.println("Solicitudes entre las ciudades:");
            System.out.println(ciudadOrigen + " y " + ciudadDestino);
            System.out.println(solicitudes);
        }
        else
        {
            System.out.println("Ciudad no encontrada para el codigo dado");
        }
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
                System.out.println("Ciudad insertada");
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
    }

    public void run()
    {
        isRunning = true;
        mostrarMenu();
    }
}
