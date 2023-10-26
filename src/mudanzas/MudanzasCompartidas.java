package src.mudanzas;

import java.util.Scanner;

import lib.conjuntistas.ArbolAVL;
import lib.conjuntistas.TablaHash;

public class MudanzasCompartidas {
    boolean isRunning;
    Scanner sc;
    ArbolAVL ciudades;
    TablaHash solicitudes;

    public MudanzasCompartidas()
    {
        sc = new Scanner(System.in);
        ciudades = new ArbolAVL();
        solicitudes = new TablaHash();
    }

    public void mostrarMenu()
    {
        String i = "";
        while(!i.equals("q"))
        {
            System.out.println("Sistema de Mudanzas Compartidas");
            System.out.println("1. Gestionar Ciudades");
            System.out.println("2. Gestionar Solicitudes");
            i = sc.nextLine();
            if(i.equals("1"))
                mostrarMenuCiudades();
            if(i.equals("2"))
                mostrarMenuSolicitudes();
        }
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

    public void mostrarMenuSolicitudes()
    {
        String i = "";
        while(!i.equals("q"))
        {
            System.out.println("Gestionar Solicitudes");
            System.out.println("1. Mostrar Todas");
            System.out.println("2. Insertar");
            System.out.println("3. Mostrar segun origen y destino");
            i = sc.nextLine();
            if(i.equals("1"))
                System.out.println(solicitudes.toString());
            if(i.equals("2"))
                insertarSolicitud();
            if(i.equals("3"))
                mostrarSolicitudes();
        }
    }

    public void insertarCiudad()
    {
        Comparable cp;
        String nombre;
        try
        {
            System.out.println("Codigo Postal");
            cp = (Comparable)sc.nextInt();
            sc.nextLine();
            System.out.println("Nombre");
            nombre = sc.nextLine();

            if (ciudades.insertar(new Ciudad(cp, nombre)))
                System.out.println("Insertado con exito");
        } 
        catch (Exception e)
        {
            System.out.println("Error de input");
        }

    }

    public void insertarSolicitud()
    {
        Comparable cp1, cp2;
        String nombre;
        try
        {
            System.out.println("Codigo Postal Origen");
            cp1 = (Comparable)sc.nextInt();
            sc.nextLine();
            System.out.println("Codigo Postal Destino");
            cp2 = (Comparable)sc.nextInt();
            sc.nextLine();
            System.out.println("Nombre");
            nombre = sc.nextLine();

            solicitudes.insertar(new Solicitud(cp1, cp2, nombre));
        } 
        catch (Exception e)
        {
            System.out.println("Error de input");
        }
    }

    public void mostrarSolicitudes()
    {
        Comparable cp1, cp2;
        String clave;
        try
        {
            System.out.println("Codigo Postal Origen");
            cp1 = (Comparable)sc.nextInt();
            sc.nextLine();
            System.out.println("Codigo Postal Destino");
            cp2 = (Comparable)sc.nextInt();
            sc.nextLine();

            clave = (String)cp1 + (String)cp2;
            System.out.println(solicitudes.toString(clave));
        } 
        catch (Exception e)
        {
            System.out.println("Error de input");
        }
    }

    public void run()
    {
        isRunning = true;
        mostrarMenu();
    }
}
