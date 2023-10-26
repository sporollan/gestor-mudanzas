package src.mudanzas;

import java.util.Scanner;

import lib.conjuntistas.ArbolAVL;
import lib.conjuntistas.TablaHash;
import lib.lineales.dinamicas.Lista;

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
        int cp1, cp2;
        String nombre, clave;
        try
        {
            System.out.println("Codigo Postal Origen");
            cp1 = sc.nextInt();
            sc.nextLine();
            System.out.println("Codigo Postal Destino");
            cp2 = sc.nextInt();
            sc.nextLine();
            System.out.println("Nombre");
            nombre = sc.nextLine();

            clave = cp1 +""+ cp2;
            if(solicitudes.pertenece(clave))
                ((Lista)solicitudes.obtener(clave)).insertar((Object)new Solicitud(cp1, cp2, nombre), 1);
            else
            {
                Lista s = new Lista();
                s.insertar(new Solicitud(cp1, cp2, nombre), 1);
                solicitudes.insertar(s);
            }
        } 
        catch (Exception e)
        {
            System.out.println("Error de input");
        }
    }

    public void mostrarSolicitudes()
    {
        int cp1, cp2;
        String clave;
        try
        {
            System.out.println("Codigo Postal Origen");
            cp1 = sc.nextInt();
            sc.nextLine();
            System.out.println("Codigo Postal Destino");
            cp2 = sc.nextInt();
            sc.nextLine();

            clave = cp1 + ""+cp2;
            System.out.println(solicitudes.obtener(clave));
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
