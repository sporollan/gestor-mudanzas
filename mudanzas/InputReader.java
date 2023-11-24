package mudanzas;

import java.util.Scanner;
import java.util.StringTokenizer;

import estructuras.propositoEspecifico.Diccionario;
import estructuras.propositoEspecifico.MapeoAUno;

public class InputReader {
    private Scanner sc;
    private MapeoAUno clientes;
    private Diccionario ciudades;

    public InputReader(MapeoAUno clientes, Diccionario ciudades)
    {
        sc = new Scanner(System.in);
        this.clientes = clientes;
        this.ciudades = ciudades;
    }

    public void comprobarFecha(String fecha) throws Exception
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
    public void comprobarTipoDocumento(String s) throws Exception
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
    public void comprobarNumeroDocumento(String s) throws Exception
    {
        boolean valido = false;
        int i = Integer.parseInt(s);
        if(i < 0)
        {
            throw new Exception();
        }

    }
    public void comprobarClaveCliente(String clave) throws Exception
    {
        if(clientes.obtenerValor(clave).equals(null))
        {
            throw new Exception();
        }
    }

    public void comprobarCp(int cp) throws Exception
    {
        int m;
        m = cp / 1000;
        if(m < 1 || m > 9)
        {
            System.out.println("Codigo Postal no valido");
            throw new Exception();
        }
    }

    public void comprobarPrefijo(int cp) throws Exception
    {
        int m;
        m = cp / 10;
        if(m < 1 || m > 9)
        {
            System.out.println("Prefijo no valido");
            throw new Exception();
        }
    }


    public String scanString(String message)
    {
        String s = "";
        while(s.equals("") && !s.equals("q"))
        {
            try
            {
                System.out.print(message + ": ");
                s = sc.nextLine();
            } catch (Exception e){}
        }
        return s;
    }

    public int scanPrefijo(String message)
    {
        String s = "";
        int pf = -1;
        boolean valido = false;
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
        return pf;
    }

    public int scanCp(String message)
    {
        String s = "";
        int cp = -1;
        boolean valido = false;
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
        return cp;
    }

    public Cliente scanCliente()
    {
        Cliente cliente = null;
        String[] c;
        do
        {
            c = scanClaveCliente();
            cliente = (Cliente)this.clientes.obtenerValor(c[0]+c[1]);
            if(cliente == null)
            {
                System.out.println("No se encuentra cliente especificado");
            }
        } while (cliente == null && !c[0].equals("q"));
        return cliente;
    }

    public Ciudad scanCiudad(String message)
    {
        Ciudad ciudad = null;
        int cp = 0;
        while(cp != -1 && ciudad == null)
        {
            cp = scanCp("Codigo Postal " + message);
            ciudad = (Ciudad)this.ciudades.obtener((Comparable)cp);
        }
        return ciudad;
    }


    public String[] scanClaveCliente()
    {
        String[] clave = {"", ""};
        boolean valida = false;

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
        return clave;
    }

    public String[] cargarStringsSc(String[] stringValues)
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

    public int[] cargarIntsSc(String[] intValues)
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

    public int scanInt(String message)
    {
        int i = -1;
        String input ="";
        while(i < 1 && !input.equals("q"))
        {
            try
            {
                System.out.println(message);
                input = sc.nextLine();
                i = Integer.parseInt(input);
            } catch (Exception e){}
        }
        return i;
    }

    public float scanFloat(String message)
    {
        float i = -1;
        String input ="";
        while(i < 1 && !input.equals("q"))
        {
            try
            {
                System.out.println(message);
                input = sc.nextLine();
                i = Float.parseFloat(input);
            } catch (Exception e){}
        }
        return i;
    }

}
