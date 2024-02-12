package mudanzas.managers;

import estructuras.grafo.Grafo;
import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.Diccionario;
import mudanzas.datos.Ciudad;
import mudanzas.librerias.InputReader;
import mudanzas.librerias.LogOperacionesManager;

public class CiudadesManager {
    private Diccionario ciudades;
    private Grafo rutas;
    private String path = "files/operaciones.log";

    public CiudadesManager(Diccionario ciudades, Grafo rutas)
    {
        this.ciudades = ciudades;
        this.rutas = rutas;
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
            seleccion = InputReader.scanString("Seleccion");
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

        cpo = InputReader.scanCp("Codigo Postal");
        continuar = cpo != -1;

        if(continuar)
        {
            strInputs = InputReader.cargarStringsSc(strNames);
            continuar = !strInputs[strNames.length].equals("q");
        }

        if (continuar)
        {
            // se crea la ciudad y se la inserta
            if(insertar(new Ciudad((Comparable)cpo, strInputs[0], strInputs[1])))
            {
                System.out.println("Ciudad insertada con exito");
                LogOperacionesManager.escribirInsercion("la ciudad " + cpo + ": " + strInputs[0] +", "+ strInputs[1], path);
                LogOperacionesManager.escribirInsercion("vertice " + cpo, path);

            }
            else
            {
                System.out.println("Error insertando ciudad");
            }
        }
    }

    public Ciudad scanCiudad(String message)
    {
        // se piden CP hasta dar con uno existente
        // y se devuelve la ciudad
        Ciudad ciudad = null;
        int cp = 0;
        while(cp != -1 && ciudad == null)
        {
            cp = InputReader.scanCp("Codigo Postal " + message);
            ciudad = (Ciudad)this.ciudades.obtenerInformacion((Comparable)cp);
        }
        return ciudad;
    }

    private void eliminar()
    {
        //  Eliminar una ciudad consiste en eliminar la ciudad,
        //  luego todas las rutas que la incluyan,
        //  y finalmente el vertice del grafo.
        int cpo = InputReader.scanCp("Codigo Postal");
        if(cpo != -1)
        {
            Ciudad c = (Ciudad)this.ciudades.obtenerInformacion((Comparable)cpo);
            Lista destinos = rutas.listarArcos(cpo);
            if(this.ciudades.eliminar((Comparable)cpo))
            {
                System.out.println("Eliminado con exito");
                LogOperacionesManager.escribirEliminacion("la ciudad " + c.getCodigo() + ": " + c.getNombre() +", "+ c.getProvincia(), path);

                // elimino todas las rutas que incluyan esta ciudad
                String str;
                for(int i = 1; i <= destinos.longitud(); i++)
                {
                    int cpd = (int)destinos.recuperar(i);
                    str = "la ruta entre " + cpo + " y " + cpd;
                    if(rutas.eliminarArco(cpo, cpd))
                    {
                        System.out.println("Se elimino " + str);
                        LogOperacionesManager.escribirEliminacion(str, path);
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
                    LogOperacionesManager.escribirEliminacion(str, path);
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
        int cp = InputReader.scanCp("Codigo Postal");
        Ciudad c = null;
        if(cp != -1)
            c = (Ciudad)this.ciudades.obtenerInformacion((Comparable)cp);

        // si existe se la modifica
        if(c != null)
        {
            System.out.println("Modificando " + c.getNombre() + " CP: " + c.getCodigo());
            boolean modificado = false;
            if(InputReader.scanBool("Cambiar nombre?"))
            {
                c.setNombre(InputReader.scanString("Nombre"));
                modificado = true;
            }
            if(InputReader.scanBool("Cambiar provincia?"))
            {
                c.setProvincia(InputReader.scanString("Provincia"));
                modificado = true;
            }

            if(modificado)
            {
                System.out.println("Modificado con exito");
                LogOperacionesManager.escribirModificacion("la ciudad " + c.getCodigo() + ": " + c.getNombre() +", "+ c.getProvincia(), path);
            }   
        }
    }

    private void mostrarDatosCiudad()
    {
        Ciudad c = ((Ciudad)ciudades.obtenerInformacion(InputReader.scanCp("CP")));
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
        Comparable pf = InputReader.scanPrefijo("Prefijo");
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
        if(ciudades.insertar(ciudad.getCodigo(), ciudad))
        {
            exito = rutas.insertarVertice(ciudad.getCodigo());
        }
        return exito;
    }
}
