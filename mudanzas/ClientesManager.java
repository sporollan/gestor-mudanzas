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
            seleccion = inputReader.scanString("Seleccion:");
            if(seleccion.equals("1"))
                System.out.println(clientes);
            if(seleccion.equals("2"))
                cargarDatos();
            if(seleccion.equals("3"))
                System.out.println(inputReader.scanCliente());
        }  
    }

    private void mostrarMenu()
    {
        System.out.println("Gestionar Clientes");
        System.out.println("1. Listar");
        System.out.println("2. Insertar");
        System.out.println("3. Mostrar");
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
            insertar(clave[0], clave[1], sInputs[0], sInputs[1], sInputs[2], sInputs[3]);
        }
    }

    public void insertar(String tipo, String num, String nombres, String apellidos, String telefono, String email)
    {
            if(clientes.asociar(tipo+num, new Cliente(tipo, num, nombres, apellidos, telefono, email)))
                System.out.println("Cliente insertado con exito");
    }

}
