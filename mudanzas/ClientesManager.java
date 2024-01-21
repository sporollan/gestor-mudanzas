package mudanzas;

import estructuras.propositoEspecifico.MapeoAUno;

public class ClientesManager {
    private InputReader inputReader;
    private MapeoAUno clientes;

    public ClientesManager(InputReader inputReader, MapeoAUno clientes)
    {
        this.inputReader = inputReader;
        this.clientes = clientes;
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
            if(seleccion.equals("2"))
                eliminar();
            if(seleccion.equals("3"))
                modificar();
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
                System.out.println(c.getTipo() + c.getNum());
                if(clientes.desasociar(c.getTipo() + c.getNum()))
                {
                    System.out.println("Eliminado con exito");
                }
            }
        }
    }

    private void modificar()
    {

    }

    private void mostrarMenu()
    {
        System.out.println("Gestionar Clientes");
        System.out.println("1. Insertar");
        System.out.println("2. Eliminar");
        System.out.println("3. Modificar");
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
            if(clientes.asociar((clave[0] + clave[1]), new Cliente(clave[0], clave[1], sInputs[0], sInputs[1], sInputs[2], sInputs[3])))
                System.out.println("Cliente insertado con exito");
        }
    }
}
