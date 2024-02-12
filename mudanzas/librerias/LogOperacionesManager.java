package mudanzas.librerias;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

// clase para escribir operaciones realizadas

public class LogOperacionesManager {


    private static void escribir(String str, String path)
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

    public static void escribirInsercion(String str, String path)
    {
        escribir("Se creo " + str, path);
    }

    public static void escribirModificacion(String str, String path)
    {
        escribir("Se modifico " + str, path);
    }   

    public static void escribirEliminacion(String str, String path)
    {
        escribir("Se elimino " + str, path);
    }
}
