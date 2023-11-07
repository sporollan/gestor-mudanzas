package src.mudanzas;

import lib.conjuntistas.ArbolAVL;
import lib.lineales.dinamicas.Lista;

public class SolicitudesManager {
    InputReader inputReader;
    ArbolAVL ciudades;

    public SolicitudesManager(InputReader inputReader, ArbolAVL ciudades)
    {
        this.inputReader = inputReader;
        this.ciudades = ciudades;
    }
    public void gestionar()
    {
        String seleccion = "";
        while(!seleccion.equals("q"))
        {
            mostrarMenu();
            seleccion = inputReader.scanString("Seleccion:");
            if(seleccion.equals("1"))
                mostrarSolicitudes();
            if(seleccion.equals("2"))
                cargarDatos();
        }
    }
    private void mostrarMenu()
    {
        System.out.println("Gestionar Solicitudes");
        System.out.println("1. Mostrar");
        System.out.println("2. Insertar");
    }
    public void cargarDatos()
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

        ciudadOrigen = inputReader.scanCiudad("Origen");
        continuar = ciudadOrigen != null;

        ciudadDestino = null;
        if(continuar)
        {
            ciudadDestino = inputReader.scanCiudad("Destino");
            continuar = ciudadDestino != null;
        }

        if(continuar)
        {
            sInputs = inputReader.cargarStringsSc(stringValues);
            continuar = !sInputs[stringValues.length].equals("q");
        }

        if(continuar)
        {
            iInputs = inputReader.cargarIntsSc(intValues);
            continuar = !(iInputs[intValues.length] == -1);
        }

        cliente = null;
        if(continuar)
        {
            cliente = inputReader.scanCliente();
            continuar = cliente != null;
        }

        estaPago = false;
        if(continuar)
        {
            estaPago = false;
            input = "";
            while(!(input.equals("y") || input.equals("n") || input.equals("q")))
            {
                input = inputReader.scanString("Esta Pago? y/n");
                estaPago = input == "y";
            }
            continuar = !input.equals("q");
        }

        if(continuar)
        {
            insertar(ciudadOrigen, ciudadDestino, sInputs[0], cliente, iInputs[0], iInputs[1], sInputs[1], sInputs[2], estaPago);
        }
    }

    private void insertar(Ciudad ciudadOrigen, Comparable destino, String fecha, Object cliente, int metrosCubicos, int bultos, String domicilioRetiro, String domicilioEntrega, boolean estaPago)
    {
        Solicitud solicitud = new Solicitud(destino, fecha, cliente, metrosCubicos, bultos, domicilioRetiro, domicilioEntrega, estaPago);
        if(ciudadOrigen.insertarSolicitud(solicitud))
            System.out.println("Solicitud creada con exito");
    }

    public void mostrarSolicitudes()
    {
        Ciudad ciudadOrigen, ciudadDestino;
        Lista solicitudes;
        boolean continuar;

        ciudadOrigen = inputReader.scanCiudad("Codigo Postal Origen");
        continuar = ciudadOrigen != null;

        ciudadDestino = null;
        if(continuar)
        {
            ciudadDestino = inputReader.scanCiudad("Codigo Postal Destino");
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


}
