package mudanzas.managers;

import estructuras.grafo.Grafo;
import mudanzas.librerias.InputReader;
import mudanzas.librerias.LogOperacionesManager;

public class RutasManager {
    private Grafo rutas;
    String path ="files/operaciones.log";

    public RutasManager(Grafo rutas)
    {
        this.rutas = rutas;
    }
    private void mostrarMenu()
    {
            System.out.println("Gestionar Rutas");
            System.out.println("1. Insertar");
            System.out.println("2. Eliminar");
            System.out.println("3. Modificar");
    }
    public void gestionar()
    {
        String seleccion = "";
        while(!seleccion.equals("q"))
        {
            mostrarMenu();
            seleccion = InputReader.scanString("Seleccion");
            if(seleccion.equals("1"))
                cargarDatos();
            else if(seleccion.equals("2"))
                eliminarArco();
            else if(seleccion.equals("3"))
                modificar();
        }  
    }

    private void cargarDatos()
    {
        // se cargan los datos y luego se los inserta en la estructura
        float distancia = -1;
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = InputReader.scanCodigos(names);
        boolean continuar = codigos[codigos.length-1]!=-1;

        if(continuar)
        {
            distancia = InputReader.scanFloat("Distancia");
            continuar = distancia != -1;
        }

        // inserto los datos de la nueva ruta
        if(continuar)
        {
            if(insertar(codigos[0], codigos[1], distancia))
            {
                System.out.println("Ruta insertada con exito");
                LogOperacionesManager.escribirInsercion("la ruta entre " + codigos[0] + " y " +codigos[1], path);
            }
            else
            {
                System.out.println("Error insertando ruta");
            }
        }
    }

    private void eliminarArco()
    {
        // obtengo origen y destino, luego intento eliminar el arco
        int origenCP = InputReader.scanCp("CP origen");
        int destinoCP = -1;
        if(origenCP != -1)
            destinoCP = InputReader.scanCp("CP destino");

        // elimino el arco 
        if(destinoCP != -1)
        {
            if(rutas.eliminarArco(origenCP, destinoCP))
            {
                System.out.println("Eliminado con exito");
                LogOperacionesManager.escribirEliminacion("la ruta entre " + origenCP + " y " + destinoCP + " de distancia ", path);
            }
            else
                System.out.println("No se pudo eliminar");
        }
    }

    private void modificar()
    {
        // cargo origen y destino. Si existe el arco solicito nueva distancia y realizo la modificacion.
        String[] names = {"Origen(cp)", "Destino(cp)"};

        int[] codigos = InputReader.scanCodigos(names);

        // compruebo si existe el arco
        if(codigos[1] != -1)
        {
            try
            {
                if(rutas.existeArco(codigos[0], codigos[1]))
                {
                    // ingreso nueva distancia
                    float nuevaDistancia = InputReader.scanFloat("Nueva distancia");
                    if(nuevaDistancia != -1)
                    {
                        // modifico el arco
                        // modificar consiste en eliminar el arco y volverlo a insertar
                        if(rutas.eliminarArco(codigos[0], codigos[1]))
                        {
                            if(rutas.insertarArco(codigos[0], codigos[1], nuevaDistancia))
                            {
                                System.out.println("Modificado con exito");
                                LogOperacionesManager.escribirModificacion("la ruta entre " + codigos[0] + " y " +codigos[1], path);
                            }
                        }
                    }
                }
                else
                {
                    System.out.println("La ruta no existe");
                }
            } catch(Exception e)
            {
                System.out.println("Codigos incorrectos");
            }

        }
    }

    public void mostrarEstructura()
    {
        System.out.println(rutas.toString());
    }

    public boolean insertar(int cpo, int cpd, float distancia)
    {
        // se obtienen origen, destino, y distancia.
        // se comprueba la existencia de la ruta para luego insertarla
        boolean exito = false;
        if(!rutas.existeArco(cpo, cpd))
            exito = rutas.insertarArco(cpo, cpd, distancia);
        return exito;
    }
}
