package mudanzas;

import estructuras.grafo.Grafo;
import estructuras.propositoEspecifico.Diccionario;

public class CiudadesManager {
    private InputReader inputReader;
    private Diccionario ciudades;
    private Grafo rutas;

    public CiudadesManager(InputReader inputReader, Diccionario ciudades, Grafo rutas)
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
            else if("3".equals(seleccion))
            {
                mostrarPorPrefijo();
            }
            else if("4".equals(seleccion))
            {
                eliminar();
            }
            else if("5".equals(seleccion))
            {
                modificar();
            }
        }
    }

    private void modificar()
    {
        int cp = this.inputReader.scanCp("Codigo Postal");
        Ciudad c = (Ciudad)this.ciudades.obtenerInformacion((Comparable)cp);
    }

    private void eliminar()
    {
        int cpo = inputReader.scanCp("Codigo Postal");
        if(this.ciudades.eliminar((Comparable)cpo))
        {
            System.out.println("Eliminado con exito");
            //this.rutas.eliminar();
        }
        else
        {
            System.out.println("No se ha encontrado la ciudad");
        }
    }

    private void mostrarPorPrefijo()
    {
        int cpo = inputReader.scanPrefijo("Prefijo");
        cpo = cpo*100;
        int cpf = cpo + 100;
        Ciudad ciudad;

        for(int i = cpo; i < cpf; i++)
        {
            ciudad = (Ciudad)this.ciudades.obtenerInformacion(i);
            if(ciudad != null)
                System.out.println(ciudad);
        }
    }


    private void mostrarMenu()
    {
        System.out.println("Gestionar Ciudades");
        System.out.println("1. Mostrar Todo");
        System.out.println("2. Insertar");
        System.out.println("3. Mostrar dado un prefijo");
        System.out.println("4. Eliminar");
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
        if(ciudades.insertar(cpo, new Ciudad((Comparable)cpo, nombre, provincia)))
        {
            System.out.println("Insertado con exito");
            if (rutas.insertarVertice((Comparable)cpo))
                System.out.println("Vertice creado con exito");
        }
    }
}
