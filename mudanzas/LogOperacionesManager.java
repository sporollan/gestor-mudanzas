package mudanzas;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

// clase para escribir operaciones realizadas

public class LogOperacionesManager {

    String path;

    public LogOperacionesManager(String path)
    {
        this.path = path;
        crearArchivo(path);
    }

    private void crearArchivo(String path)
    {
        // necesario para reestablecer el archivo
        // caso contrario se duplican entradas
        // con cada carga inicial
        try(
            FileWriter file = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(file);
            PrintWriter out = new PrintWriter(bw);
        )
        {
            out.println();
            out.close();
        } catch (Exception e)
        {
            System.out.println("Error escribiendo");
        }
    }

    private void escribir(String str)
    {
        try(
            FileWriter file = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(file);
            PrintWriter out = new PrintWriter(bw);
        )
        {
            out.println(str);
            out.close();
        } catch (Exception e)
        {
            System.out.println("Error escribiendo");
        }
    }

    public void escribirInsercion(String str)
    {
        escribir("Se creo " + str);
    }

    public void escribirModificacion(String str)
    {
        escribir("Se modifico " + str);
    }   

    public void escribirEliminacion(String str)
    {
        escribir("Se elimino " + str);
    }
}
