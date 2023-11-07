package src.mudanzas;

import lib.conjuntistas.TablaHash;

public class ClientesManager {
    private InputReader inputReader;
    private TablaHash clientes;

    public ClientesManager(InputReader inputReader, TablaHash clientes)
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
        String clave;
        boolean continuar;
        
        // cargar clave de cliente
        clave = inputReader.scanClaveCliente();
        continuar = !clave.equals("q");

        // cargar datos de cliente
        if(continuar)
        {
            sInputs = inputReader.cargarStringsSc(stringValues);
            continuar = !sInputs[stringValues.length].equals("q");
        }

        // almacenar los datos en la estructura
        if(continuar)
        {
            insertar(clave, sInputs[0], sInputs[1], sInputs[2], sInputs[3]);
        }
    }

    public void insertar(Comparable clave, String nombres, String apellidos, String telefono, String email)
    {
            if(clientes.insertar(new Cliente(clave, nombres, apellidos, telefono, email)))
                System.out.println("Cliente insertado con exito");
    }

}
