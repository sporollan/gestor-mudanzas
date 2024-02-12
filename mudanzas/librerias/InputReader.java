package mudanzas.librerias;

import java.util.Scanner;
import java.util.StringTokenizer;

// clase donde se maneja el input y se comprueba la validez de los datos

public class InputReader {

    /*  Funciones comprobar
     *  Se encargan de validar un dato dado utilizando excepciones  
     */
    public static void comprobarFecha(String fecha) throws Exception
    {
        StringTokenizer tokenizer = new StringTokenizer(fecha, "/");
        if(fecha.length() != 10)
            throw new Exception();
        int dia = Integer.parseInt(tokenizer.nextToken());
        if(dia < 1 || dia > 30)
            throw new Exception();
        int mes = Integer.parseInt(tokenizer.nextToken());
        if(mes < 1 || mes > 12)
            throw new Exception();
        String y = tokenizer.nextToken();
        if(y.length() != 4)
            throw new Exception();
        Integer.parseInt(y);
    }

    public static void comprobarTipoDocumento(String s) throws Exception
    {
        String[] tiposAceptados = {
            "PAS",
            "DNI"
        };
        int i = 0;
        boolean valido = false;
        while(!valido && i < tiposAceptados.length)
        {
            valido = s.equals(tiposAceptados[i]);
            i += 1;
        }
        if(!valido)
        {
            throw new Exception();
        }

    }
    public static void comprobarNumeroDocumento(String s) throws Exception
    {
        int i = Integer.parseInt(s);
        if(i < 0)
        {
            throw new Exception();
        }

    }

    public static void comprobarCp(int cp) throws Exception
    {
        int m;
        m = cp / 1000;
        if(m < 1 || m > 9)
        {
            System.out.println("Codigo Postal no valido");
            throw new Exception();
        }
    }

    public static void comprobarPrefijo(int cp) throws Exception
    {
        int m;
        m = cp / 10;
        if(m < 1 || m > 9)
        {
            System.out.println("Prefijo no valido");
            throw new Exception();
        }
    }
/*  Funciones scan
 *  reciben que mensaje mostrar al usuario
 *  devuelven un dato de tipo correspondiente dado por el usuario 
 */ 

    public static String scanString(String message)
    {
        String s = "";
        Scanner sc = new Scanner(System.in);
        while(s.equals("") && !s.equals("q"))
        {
            try
            {
                System.out.print(message + ": ");
                s = sc.nextLine();
            } catch (Exception e){}
        }
        sc.close();
        return s;
    }

    public static int scanPrefijo(String message)
    {
        String s = "";
        int pf = -1;
        boolean valido = false;
        Scanner sc = new Scanner(System.in);
        while(!valido && !s.equals("q"))
        {
            try
            {
                System.out.println(message);
                s = sc.nextLine();
                if(!s.equals("q"))
                {
                    pf = Integer.parseInt(s);
                    comprobarPrefijo(pf);
                    valido = true;
                }

            } catch (Exception e){
                pf = -1;
                valido = false;
            }
        }
        sc.close();
        return pf;
    }

    public static int scanCp(String message)
    {
        String s = "";
        int cp = -1;
        boolean valido = false;
        Scanner sc = new Scanner(System.in);
        while(!valido && !s.equals("q"))
        {
            try
            {
                System.out.println(message);
                s = sc.nextLine();
                if(!s.equals("q"))
                {
                    cp = Integer.parseInt(s);
                    comprobarCp(cp);
                    valido = true;
                }

            } catch (Exception e){
                cp = -1;
                valido = false;
            }
        }
        sc.close();
        return cp;
    }

    public static int[] scanCodigos(String[] names)
    {
        // devuelve un array con codigos postales
        // segun la longitud de names
        int[] codigos = new int[names.length];
        codigos[names.length-1] = -1;
        int i = 0;
        boolean continuar;
        do{
            codigos[i] = scanCp(names[i]);
            continuar = codigos[i] != -1;
            i = i+1;
        } while (continuar && i < names.length);
        return codigos;
    }

    //public static Cliente scanCliente()
    //{
    //    // se piden datos de clientes hasta dar con uno existente
    //    // y se lo devuelve
    //    Cliente cliente = null;
    //    String[] c;
    //    do
    //    {
    //        c = scanClaveCliente();
    //        cliente = (Cliente)this.clientes.obtenerValor(c[0]+c[1]);
    //        if(cliente == null)
    //        {
    //            System.out.println("No se encuentra cliente especificado");
    //        }
    //    } while (cliente == null && !c[0].equals("q"));
    //    return cliente;
    //}

    //public static Ciudad scanCiudad(String message)
    //{
    //    // se piden CP hasta dar con uno existente
    //    // y se devuelve la ciudad
    //    Ciudad ciudad = null;
    //    int cp = 0;
    //    while(cp != -1 && ciudad == null)
    //    {
    //        cp = scanCp("Codigo Postal " + message);
    //        ciudad = (Ciudad)this.ciudades.obtenerInformacion((Comparable)cp);
    //    }
    //    return ciudad;
    //}


    public static String[] scanClaveCliente()
    {
        // devuelve un array con tipo y numero de documento
        String[] clave = {"", ""};
        boolean valida = false;
        Scanner sc = new Scanner(System.in);

        while(!valida && !clave[0].equals("q"))
        {
            try
            {
                System.out.println("Tipo Documento (ej. DNI)");
                clave[0] = sc.nextLine();
                comprobarTipoDocumento(clave[0]);
                if(!clave[0].equals("q"))
                {
                    System.out.println("Numero Documento");
                    clave[1] = sc.nextLine();
                    comprobarNumeroDocumento(clave[1]);
                    valida = true;
                }
            } catch (Exception e){
                System.out.println("Error, formato incorrecto");
                valida = false;
            }
        }
        sc.close();
        return clave;
    }

    public static boolean scanBool(String name)
    {
        // devuelve true o false segun input (s o n)
        boolean b = false;
        String input = "";
        while(!(input.equals("s") || input.equals("n") || input.equals("q")))
        {
            input = scanString(name+" (s/n)");
            b = (input.equals("s"));
        }
        return b;
    }

    public static String scanFecha()
    {
        String s = "";
        Scanner sc = new Scanner(System.in);
        while(s.equals("") && !s.equals("q"))
        {
            try
            {
                System.out.print("Fecha: (dd/mm/aaaa)");
                s = sc.nextLine();
                if(!s.equals("q"))
                    comprobarFecha(s);
            } catch (Exception e){
                s = "";
            }
        }
        sc.close();
        return s;
    }

    public static int scanInt(String message)
    {
        int i = -1;
        String input ="";
        Scanner sc = new Scanner(System.in);
        while(i < 1 && !input.equals("q"))
        {
            try
            {
                System.out.println(message);
                input = sc.nextLine();
                i = Integer.parseInt(input);
            } catch (Exception e){}
        }
        sc.close();
        return i;
    }

    public static float scanFloat(String message)
    {
        float i = -1;
        String input ="";
        Scanner sc = new Scanner(System.in);
        while(i < 1 && !input.equals("q"))
        {
            try
            {
                System.out.println(message);
                input = sc.nextLine();
                i = Float.parseFloat(input);
            } catch (Exception e){}
        }
        sc.close();
        return i;
    }

    /* Funciones cargar
     * devuelven un arreglo con input del usuario, segun el tipo correspondiente
     * reciben un arreglo con mensajes para mostrar para cada dato
     * la longitud de los arreglos dependen de la longitud del arreglo de entrada
     */ 
    public static String[] cargarStringsSc(String[] stringValues)
    {
        int sL = stringValues.length;
        String[] scannedInputs = new String[sL+1];
        int i = 0;
        do
        {
            scannedInputs[i] = scanString(stringValues[i]);
            i+=1;
        } while(!scannedInputs[i-1].equals("q") && i < sL);

        // se almacena el ultimo valor ingresado al final, para leer q
        scannedInputs[sL] = scannedInputs[i-1];

        return scannedInputs;
    }

    public static int[] cargarIntsSc(String[] intValues)
    {
        int sL = intValues.length;
        int[] scannedInputs = new int[sL+1];
        int i = 0;
        do
        {
            scannedInputs[i] = scanInt(intValues[i]);
            i+=1;
        } while(scannedInputs[i-1] != -1 && i < sL);

        // se almacena el ultimo valor ingresado al final, para leer q
        scannedInputs[sL] = scannedInputs[i-1];

        return scannedInputs;
    }



}
