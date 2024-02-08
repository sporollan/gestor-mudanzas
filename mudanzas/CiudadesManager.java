package mudanzas;

import estructuras.grafo.Grafo;
import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.Diccionario;

public class CiudadesManager {
    private InputReader inputReader;
    private Diccionario ciudades;
    private Grafo rutas;
    private LogOperacionesManager logOperacionesManager;

    public CiudadesManager(InputReader inputReader, Diccionario ciudades, Grafo rutas, LogOperacionesManager logOperacionesManager)
    {
        this.inputReader = inputReader;
        this.ciudades = ciudades;
        this.rutas = rutas;
        this.logOperacionesManager = logOperacionesManager;
    }

    private void mostrarMenu()
    {
        System.out.println("Gestionar Ciudades");
        System.out.println("1. Insertar");
        System.out.println("2. Eliminar");
        System.out.println("3. Modificar");
        System.out.println("4. Mostrar Datos Ciudad");
        System.out.println("5. Mostrar por Prefijo");
    }

    public void gestionar()
    {
        String seleccion = "";
        while(!seleccion.equals("q"))
        {
            mostrarMenu();
            seleccion = inputReader.scanString("Seleccion");
            if("1".equals(seleccion))
                cargarDatos();
            else if("2".equals(seleccion))
                eliminar();
            else if("3".equals(seleccion))
                modificar();
            else if("4".equals(seleccion))
                mostrarDatosCiudad();
            else if("5".equals(seleccion))
                mostrarPorPrefijo();
        }
    }

    private void cargarDatos()
    {
        // se leen los datos de ciudad
        int cpo = -1;
        String[] strNames = {"Nombre", "Provincia"};
        String[] strInputs = new String[strNames.length+1];
        boolean continuar = true;

        cpo = inputReader.scanCp("Codigo Postal");
        continuar = cpo != -1;

        if(continuar)
        {
            strInputs = inputReader.cargarStringsSc(strNames);
            continuar = !strInputs[strNames.length].equals("q");
        }

        if (continuar)
        {
            // se crea la ciudad y se la inserta
            if(insertar(new Ciudad((Comparable)cpo, strInputs[0], strInputs[1], inputReader)))
            {
                System.out.println("Ciudad insertada con exito");
                logOperacionesManager.escribirInsercion("la ciudad " + cpo + ": " + strInputs[0] +", "+ strInputs[1]);
                logOperacionesManager.escribirInsercion("vertice " + cpo);

            }
            else
            {
                System.out.println("Error insertando ciudad");
            }
        }
    }

    private void eliminar()
    {
        //  Eliminar una ciudad consiste en eliminar la ciudad,
        //  luego todas las rutas que la incluyan,
        //  y finalmente el vertice del grafo.
        int cpo = inputReader.scanCp("Codigo Postal");
        if(cpo != -1)
        {
            Ciudad c = (Ciudad)this.ciudades.obtenerInformacion((Comparable)cpo);
            Lista destinos = rutas.listarArcos(cpo);
            if(this.ciudades.eliminar((Comparable)cpo))
            {
                System.out.println("Eliminado con exito");
                logOperacionesManager.escribirEliminacion("la ciudad " + c.getCodigo() + ": " + c.getNombre() +", "+ c.getProvincia());

                // elimino todas las rutas que incluyan esta ciudad
                String str;
                for(int i = 1; i <= destinos.longitud(); i++)
                {
                    int cpd = (int)destinos.recuperar(i);
                    str = "la ruta entre " + cpo + " y " + cpd;
                    if(rutas.eliminarArco(cpo, cpd))
                    {
                        System.out.println("Se elimino " + str);
                        logOperacionesManager.escribirEliminacion(str);
                    }
                    else
                    {
                        System.out.println("Error eliminando "+str);
                    }
                }

                // elimino el vertice
                if(rutas.eliminarVertice((Comparable)cpo))
                {
                    str = "el vertice " + cpo;
                    System.out.println("Se elimino " + str);
                    logOperacionesManager.escribirEliminacion(str);
                }
                else
                {
                    System.out.println("Error eliminando vertice");
                }
            }
            else
            {
                System.out.println("No se ha encontrado la ciudad");
            }
        }
    }

    private void modificar()
    {
        // se obtiene la ciudad
        int cp = this.inputReader.scanCp("Codigo Postal");
        Ciudad c = null;
        if(cp != -1)
            c = (Ciudad)this.ciudades.obtenerInformacion((Comparable)cp);

        // si existe se la modifica
        if(c != null)
        {
            System.out.println("Modificando " + c.getNombre() + " CP: " + c.getCodigo());
            boolean modificado = false;
            if(inputReader.scanBool("Cambiar nombre?"))
            {
                c.setNombre(inputReader.scanString("Nombre"));
                modificado = true;
            }
            if(inputReader.scanBool("Cambiar provincia?"))
            {
                c.setProvincia(inputReader.scanString("Provincia"));
                modificado = true;
            }

            if(modificado)
            {
                System.out.println("Modificado con exito");
                this.logOperacionesManager.escribirModificacion("la ciudad " + c.getCodigo() + ": " + c.getNombre() +", "+ c.getProvincia());
            }   
        }
    }

    private void mostrarDatosCiudad()
    {
        Ciudad c = ((Ciudad)ciudades.obtenerInformacion(inputReader.scanCp("CP")));
        if( c != null)
        {
            System.out.println();
            System.out.println(c.getCodigo());
            System.out.println("Nombre");
            System.out.println(c.getNombre());
            System.out.println("Provincia");
            System.out.println(c.getProvincia());
        }
    }

    private void mostrarPorPrefijo()
    {
        Comparable pf = inputReader.scanPrefijo("Prefijo");
        if(pf.compareTo(-1) != 0)
        {
            Lista listaPorPrefijo = ciudades.listarPrefijo(pf);
            System.out.println("Ciudades para el prefijo " + pf);
            for(int i = 1; i < listaPorPrefijo.longitud()+1; i++)
            {
                System.out.println(listaPorPrefijo.recuperar(i));
            }
        }
    }

    public void mostrarEstructura()
    {
        System.out.println(ciudades.toString());
    }

    public boolean insertar(Ciudad ciudad)
    {
        boolean exito = false;
        if(!ciudades.existeClave(ciudad.getCodigo()))
        {
            if(ciudades.insertar(ciudad.getCodigo(), ciudad))
            {
                exito = rutas.insertarVertice(ciudad.getCodigo());
            }
        }
        return exito;
    }
}
