package src.mudanzas;

import lib.conjuntistas.ArbolAVL;
import lib.conjuntistas.Grafo;

public class CiudadesManager {
    private InputReader inputReader;
    private ArbolAVL ciudades;
    private Grafo rutas;

    public CiudadesManager(InputReader inputReader, ArbolAVL ciudades, Grafo rutas)
    {
        this.inputReader = inputReader;
        this.ciudades = ciudades;
        this.rutas = rutas;
    }

    public void gestionar()
    {
        String seleccion = "";
        while(!seleccion.equals("q"))
        {
            mostrarMenu();
            seleccion = inputReader.scanString("Seleccion");
            if("1".equals(seleccion))
            {
                System.out.println(ciudades);
            }
            else if("2".equals(seleccion))
            {
                cargarDatos();
            }
        }
    }

    private void mostrarMenu()
    {
        System.out.println("Gestionar Ciudades");
        System.out.println("1. Mostrar");
        System.out.println("2. Insertar");
    }

    private void cargarDatos()
    {
        int cpo = -1;
        String[] stringValues = {"Nombre", "Provincia"};
        String[] sInputs = new String[stringValues.length+1];
        boolean continuar = true;

        cpo = inputReader.scanCp("Codigo Postal");
        continuar = cpo != -1;

        if(continuar)
        {
            sInputs = inputReader.cargarStringsSc(stringValues);
            continuar = !sInputs[stringValues.length].equals("q");
        }

        if (continuar)
        {
            insertar(cpo, sInputs[0], sInputs[1]);
        }
    }

    public void insertar(int cpo, String nombre, String provincia)
    {
        if(ciudades.insertar(new Ciudad((Comparable)cpo, nombre, provincia)))
        {
            System.out.println("Insertado con exito");
            if (rutas.insertarVertice((Comparable)cpo))
                System.out.println("Vertice creado con exito");
        }
    }
}
