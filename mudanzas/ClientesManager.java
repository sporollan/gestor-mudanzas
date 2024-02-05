package mudanzas;

import estructuras.propositoEspecifico.MapeoAUno;

public class ClientesManager {
    private InputReader inputReader;
    private MapeoAUno clientes;
    private LogOperacionesManager logOperacionesManager;

    public ClientesManager(InputReader inputReader, MapeoAUno clientes, LogOperacionesManager logOperacionesManager)
    {
        this.inputReader = inputReader;
        this.clientes = clientes;
        this.logOperacionesManager = logOperacionesManager;
    }
    
    private void mostrarMenu()
    {
        System.out.println("Gestionar Clientes");
        System.out.println("1. Insertar");
        System.out.println("2. Eliminar");
        System.out.println("3. Modificar");
        System.out.println("4. Mostrar");
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
                eliminar();
            else if(seleccion.equals("3"))
                modificar();
            else if(seleccion.equals("4"))
                mostrar();
        }  
    }

    public void mostrarEstructura()
    {
        System.out.println(clientes.toString());
    }

    private void mostrar()
    {
        Cliente c = inputReader.scanCliente();
        if(c != null)
        {
            System.out.println("Cliente");
            System.out.println(c.getTipo() + c.getNum());
            System.out.println("Nombres");
            System.out.println(c.getNombres());
            System.out.println("Apellidos");
            System.out.println(c.getApellidos());
            System.out.println("Email");
            System.out.println(c.getEmail());
            System.out.println("Telefono");
            System.out.println(c.getTelefono());
        }
    }

    private void eliminar()
    {
        Cliente c = inputReader.scanCliente();
        if(c != null)
        {
            System.out.println("Cliente:");
            System.out.println(c.getNombres() + " " + c.getApellidos());
            System.out.println(c.getTipo() + c.getNum());
            if(inputReader.scanBool("Eliminar? s/n"))
            {
                if(clientes.desasociar(c.getTipo() + c.getNum()))
                {
                    System.out.println("Eliminado con exito");
                    logOperacionesManager.escribirEliminacion("el cliente " + c.getTipo() + c.getNum() + " " + c.getApellidos()+", "+c.getNombres());
                }
            }
        }
    }

    private void modificar()
    {
        Cliente c = inputReader.scanCliente();
        if(c != null)
        {
            System.out.println("Cliente:");
            System.out.println(c.getNombres() + " " + c.getApellidos());
            System.out.println(c.getTipo() + c.getNum());
            if(inputReader.scanBool("Modificar? s/n"))
            {
                if(inputReader.scanBool("Nombres: " + c.getNombres() + " Modificar? s/n"))
                    c.setNombres(inputReader.scanString("Nombres"));
                if(inputReader.scanBool("Apellidos: " + c.getApellidos() + " Modificar? s/n"))
                    c.setApellidos(inputReader.scanString("Apellidos"));
                if(inputReader.scanBool("Telefono: " + c.getTelefono() + " Modificar? s/n"))
                    c.setTelefono(inputReader.scanString("Telefono"));
                if(inputReader.scanBool("Email: " + c.getEmail() + " Modificar? s/n"))
                    c.setEmail(inputReader.scanString("Email"));
                logOperacionesManager.escribirModificacion("el cliente " + c.getTipo() + c.getNum() + " " + c.getApellidos()+", "+c.getNombres());
            }
        }
    }



    private void cargarDatos()
    {
        String[] stringValues = {
            "Nombres", "Apellidos", "Telefono", "Email"
        };
        String[] sInputs = new String[stringValues.length+1];
        String[] clave;
        boolean continuar;
        
        // cargar clave de cliente
        clave = inputReader.scanClaveCliente();
        continuar = !clave[0].equals("q");

        // cargar datos de cliente
        if(continuar)
        {
            sInputs = inputReader.cargarStringsSc(stringValues);
            continuar = !sInputs[stringValues.length].equals("q");
        }

        // almacenar los datos en la estructura
        if(continuar)
        {
            Cliente c = new Cliente(clave[0], clave[1], sInputs[0], sInputs[1], sInputs[2], sInputs[3]);
            if(insertar(c))
                System.out.println("Cliente insertado con exito");
            else
                System.out.println("Error insertando cliente");
        }
    }

    public boolean insertar(Cliente c)
    {
        boolean exito = false;
        if(!clientes.existeClave(c.getTipo()+c.getNum()))
        {
            exito = clientes.asociar(c.getTipo()+c.getNum(), c);
        }
        if(exito)
        {
            logOperacionesManager.escribirInsercion("el cliente " + c.getTipo() + c.getNum() + " " + c.getApellidos()+", "+c.getNombres());
        }
        return exito;
    }
}
