package mudanzas.managers;

import estructuras.propositoEspecifico.MapeoAUno;
import mudanzas.Cliente;
import mudanzas.LogOperacionesManager;
import mudanzas.librerias.InputReader;

public class ClientesManager {
    private MapeoAUno clientes;
    private LogOperacionesManager logOperacionesManager;

    public ClientesManager(MapeoAUno clientes, LogOperacionesManager logOperacionesManager)
    {
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
            seleccion = InputReader.scanString("Seleccion");
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
        Cliente c = scanCliente();
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

    public Cliente scanCliente()
    {
        // se piden datos de clientes hasta dar con uno existente
        // y se lo devuelve
        Cliente cliente = null;
        String[] c;
        do
        {
            c = InputReader.scanClaveCliente();
            cliente = (Cliente)this.clientes.obtenerValor(c[0]+c[1]);
            if(cliente == null)
            {
                System.out.println("No se encuentra cliente especificado");
            }
        } while (cliente == null && !c[0].equals("q"));
        return cliente;
    }

    private void eliminar()
    {
        // es obtiene cliente y si existe se lo borra
        Cliente c = scanCliente();
        if(c != null)
        {
            System.out.println("Cliente:");
            System.out.println(c.getNombres() + " " + c.getApellidos());
            System.out.println(c.getTipo() + c.getNum());
            if(InputReader.scanBool("Eliminar?"))
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
        // se obtiene el cliente y si existe se recorren uno a uno sus campos dando la opcion de modificarlos
        Cliente c = scanCliente();
        if(c != null)
        {
            System.out.println("Cliente:");
            System.out.println(c.getNombres() + " " + c.getApellidos());
            System.out.println(c.getTipo() + c.getNum());
            if(InputReader.scanBool("Modificar?"))
            {
                boolean modificado = false;
                if(InputReader.scanBool("Nombres: " + c.getNombres() + " Modificar?"))
                {
                    c.setNombres(InputReader.scanString("Nombres"));
                    modificado = true;
                }
                if(InputReader.scanBool("Apellidos: " + c.getApellidos() + " Modificar?"))
                {
                    c.setApellidos(InputReader.scanString("Apellidos"));
                    modificado = true;
                }
                if(InputReader.scanBool("Telefono: " + c.getTelefono() + " Modificar?"))
                {
                    c.setTelefono(InputReader.scanString("Telefono"));
                    modificado = true;
                }
                if(InputReader.scanBool("Email: " + c.getEmail() + " Modificar?"))
                {
                    c.setEmail(InputReader.scanString("Email"));
                    modificado = true;
                }
                if(modificado)
                {
                    System.out.println("Modificado con exito");
                    logOperacionesManager.escribirModificacion("el cliente " + c.getTipo() + c.getNum() + " " + c.getApellidos()+", "+c.getNombres());
                }
            }
        }
    }

    private void cargarDatos()
    {
        // cargo datos, creo nuevo cliente y lo inserto en la estructura
        String[] stringValues = {
            "Nombres", "Apellidos", "Telefono", "Email"
        };
        String[] sInputs = new String[stringValues.length+1];
        String[] clave;
        boolean continuar;
        
        // cargar clave de cliente
        clave = InputReader.scanClaveCliente();
        continuar = !clave[0].equals("q");

        // cargar datos de cliente
        if(continuar)
        {
            sInputs = InputReader.cargarStringsSc(stringValues);
            continuar = !sInputs[stringValues.length].equals("q");
        }

        // almacenar los datos en la estructura
        if(continuar)
        {
            Cliente c = new Cliente(clave[0], clave[1], sInputs[0], sInputs[1], sInputs[2], sInputs[3]);
            if(insertar(c))
            {
                System.out.println("Cliente insertado con exito");
                logOperacionesManager.escribirInsercion("el cliente " + c.getTipo() + c.getNum() + " " + c.getApellidos()+", "+c.getNombres());
            }
            else
                System.out.println("Error insertando cliente");
        }
    }

    public boolean insertar(Cliente c)
    {
        // se comprueba la existencia antes de insertar el dato dado
        boolean exito = false;
        if(!clientes.existeClave(c.getTipo()+c.getNum()))
        {
            exito = clientes.asociar(c.getTipo()+c.getNum(), c);
        }
        return exito;
    }
}
