package src.mudanzas;

import java.util.Scanner;

import lib.conjuntistas.ArbolAVL;

public class Sistema {
    boolean isRunning;
    Scanner sc;
    ArbolAVL ciudades;

    public Sistema()
    {
        sc = new Scanner(System.in);
        ciudades = new ArbolAVL();
    }

    public void mostrarMenu()
    {
        String i = "";
        while(!i.equals("q"))
        {
            System.out.println("Sistema de Mudanzas Compartidas");
            System.out.println("1. Gestionar Ciudades");
            i = sc.nextLine();
            if(i.equals("1"))
                mostrarMenuCiudades();
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

            ciudades.insertar(new Ciudad(cp, nombre));
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
