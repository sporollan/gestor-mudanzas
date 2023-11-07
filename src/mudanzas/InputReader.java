package src.mudanzas;

import java.util.Scanner;

import lib.conjuntistas.ArbolAVL;
import lib.conjuntistas.TablaHash;

public class InputReader {
    private Scanner sc;
    private TablaHash clientes;
    private ArbolAVL ciudades;

    public InputReader(TablaHash clientes, ArbolAVL ciudades)
    {
        sc = new Scanner(System.in);
        this.clientes = clientes;
        this.ciudades = ciudades;
    }

    private boolean comprobarCp(int cp) throws Exception
    {
        int m;
        m = cp / 1000;
        if(m < 1 || m > 9)
        {
            System.out.println("Codigo Postal no valido");
            throw new Exception();
        }
        return true;
    }

    private boolean comprobarClaveCliente(String cc) throws Exception
    {
        if(cc.length() != 11)
        {
            System.out.println("Clave Cliente no valida");
            throw new Exception();
        }
        return true;
    }
    public String scanString(String message)
    {
        String s = "";
        while(s.equals("") && !s.equals("q"))
        {
            try
            {
                System.out.println(message);
                s = sc.nextLine();
            } catch (Exception e){}
        }
        return s;
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
                    valido = comprobarCp(cp);
                }

            } catch (Exception e){
                cp = -1;
            }
        }
        return cp;
    }

    public Cliente scanCliente()
    {
        Cliente cliente = null;
        String c = "";
        while(!c.equals("q") && cliente == null)
        {
            c = scanClaveCliente();
            cliente = (Cliente)this.clientes.obtener(c);
        }
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

    public String scanClaveCliente()
    {
        String clave = "";
        boolean valida = false;
        while(!valida && !clave.equals("q"))
        {
            try
            {
                System.out.println("Clave Cliente (ej. dni12345678)");
                clave = sc.nextLine();
                valida = comprobarClaveCliente(clave);
            } catch (Exception e){}
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
