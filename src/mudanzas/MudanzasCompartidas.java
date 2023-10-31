package src.mudanzas;

import java.util.Scanner;

import lib.conjuntistas.ArbolAVL;
import lib.conjuntistas.Grafo;
import lib.conjuntistas.TablaHash;
import lib.lineales.dinamicas.Lista;

public class MudanzasCompartidas {
    boolean isRunning;
    Scanner sc;
    ArbolAVL ciudades;
    TablaHash clientes;
    Grafo rutas;

    public MudanzasCompartidas()
    {
        sc = new Scanner(System.in);
        ciudades = new ArbolAVL();
        clientes = new TablaHash();
        rutas = new Grafo();
    }

    public void mostrarMenu()
    {
        String i = "";
        while(!i.equals("q"))
        {
            System.out.println("Sistema de Mudanzas Compartidas");
            System.out.println("1. Gestionar Ciudades");
            System.out.println("2. Gestionar Solicitudes");
            System.out.println("3. Gestionar Rutas");
            System.out.println("4. Gestionar Clientes");
            i = sc.nextLine();
            if(i.equals("1"))
                mostrarMenuCiudades();
            if(i.equals("2"))
                mostrarMenuSolicitudes();
            if(i.equals("3"))
                mostrarMenuRutas();
        }
    }

    public void mostrarMenuRutas()
    {
        String i = "";
        while(!i.equals("q"))
        {
            System.out.println("Gestionar Rutas");
            System.out.println("1. Mostrar");
            System.out.println("2. Insertar");
            System.out.println("3. Consultar Ruta");
            i = sc.nextLine();
            if(i.equals("1"))
                System.out.println(rutas.listarEnProfundidad());
            if(i.equals("2"))
                insertarRuta();
            if(i.equals("3"))
                consultarRuta();
        }  
    }

    public void consultarRuta()
    {
        int cpo, cpd, m;
        try
        {
            System.out.println("Origen(cp)");
            cpo = sc.nextInt();
            sc.nextLine();
            m = cpo / 1000;
            if(m < 1 || m > 9)
                throw new Exception();

            System.out.println("Destino(cp)");
            cpd = sc.nextInt();
            sc.nextLine();
            m = cpd / 1000;
            if(m < 1 || m > 9)
                throw new Exception();

            if(rutas.existeCamino(cpo, cpd))
                System.out.println("La ruta existe");
            else
                System.out.println("La ruta no existe");
        } 
        catch (Exception e)
        {
            System.out.println("Error de input");
        } 
    }

    public void mostrarMenuCiudades()
    {
        String i = "";
        while(!i.equals("q"))
        {
            System.out.println("Gestionar Ciudades");
            System.out.println("1. Mostrar");
            System.out.println("2. Insertar");
            i = sc.nextLine();
            if(i.equals("1"))
                System.out.println(ciudades.toString());
            if(i.equals("2"))
                insertarCiudad();
        }
    }

    public void insertarRuta()
    {
        int cpo, cpd, m;
        int distancia;
        try
        {
            System.out.println("Origen(cp)");
            cpo = sc.nextInt();
            sc.nextLine();
            m = cpo / 1000;
            if(m < 1 || m > 9)
                throw new Exception();

            System.out.println("Destino(cp)");
            cpd = sc.nextInt();
            sc.nextLine();
            m = cpd / 1000;
            if(m < 1 || m > 9)
                throw new Exception();

            System.out.println("Distancia");
            distancia = sc.nextInt();
            sc.nextLine();

            if(rutas.insertarArco(cpo, cpd, distancia))
                System.out.println("Insertado con exito");
        } 
        catch (Exception e)
        {
           System.out.println("Error de input");
        }

    }

    public void mostrarMenuSolicitudes()
    {
        String i = "";
        while(!i.equals("q"))
        {
            System.out.println("Gestionar Solicitudes");
            System.out.println("1. Mostrar");
            System.out.println("2. Insertar");
            i = sc.nextLine();
            if(i.equals("1"))
                mostrarSolicitudes();
            if(i.equals("2"))
                insertarSolicitud();
        }
    }

    public void insertarCiudad()
    {
        int cpo = -1;
        String nombre, provincia, input;
        boolean valido;

        valido = false;
        input = "";
        while(!valido && !input.equals("q"))
        {
            try
            {
                System.out.println("Codigo Postal");
                input = sc.nextLine();
                cpo = Integer.parseInt(input);
                valido = comprobarCp(cpo);
            } catch (Exception e){}
        }

        nombre = "";
        while(nombre.equals("") && !input.equals("q"))
        {
            try
            {
                System.out.println("Nombre");
                input = sc.nextLine();
                nombre = input;
            } catch (Exception e){}
        }

        provincia = "";
        while(provincia.equals("") && !input.equals("q"))
        {
            try
            {
                System.out.println("Provincia");
                input = sc.nextLine();
                provincia = input;
            } catch (Exception e){}
        }


        if (!input.equals("q") && ciudades.insertar(new Ciudad((Comparable)cpo, nombre, provincia)))
        {
            System.out.println("Insertado con exito");
            if (rutas.insertarVertice((Comparable)cpo))
                System.out.println("Vertice creado con exito");
        }
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

    public void insertarSolicitud()
    {
        int cp1, cp2, metrosCubicos, bultos;
        boolean estaPago;
        String nombre, fecha, claveCliente, domicilioRetiro, domicilioEntrega, input;
        Solicitud solicitud;
        Ciudad ciudadOrigen, ciudadDestino;
        Object cliente;

        input = "";
        ciudadOrigen = null;
        while(ciudadOrigen == null && !input.equals("q"))
        {
            try
            {
                System.out.println("Codigo Postal Origen");
                input = sc.nextLine();
                cp1 = Integer.parseInt(input);
                comprobarCp(cp1);
                ciudadOrigen = (Ciudad)this.ciudades.obtener((Comparable)cp1);
            } catch (Exception e){}
        }

        ciudadDestino = null;
        while(ciudadDestino == null && !input.equals("q"))
        {
            try
            {
                System.out.println("Codigo Postal Destino");
                input = sc.nextLine();
                cp2 = Integer.parseInt(input);
                comprobarCp(cp2);
                ciudadDestino = (Ciudad)this.ciudades.obtener(cp2);
            } catch (Exception e){}
        }

        nombre = "";
        while(nombre.equals("") && !input.equals("q"))
        {
            try
            {
                System.out.println("Nombre");
                input = sc.nextLine();
                nombre = input;
            } catch (Exception e){}
        }

        fecha = "";
        while(!fecha.equals("") && !input.equals("q"))
        {
            try
            {
                System.out.println("Fecha");
                input = sc.nextLine();
                fecha = input;
            } catch (Exception e){}
        }

        cliente = null;
        claveCliente = "";
        while(cliente == null && !claveCliente.equals("q") && !input.equals("q"))
        {
            try{
                System.out.println("Clave Cliente (ej: dni12345678)");
                input = sc.nextLine();
                claveCliente = input;
                comprobarClaveCliente(claveCliente);
                cliente = clientes.obtener(claveCliente);
            } catch(Exception e){};
        }

        metrosCubicos = -1;
        while(metrosCubicos < 0 && !input.equals("q"))
        {
            try
            {
                System.out.println("Metros Cubicos");
                input = sc.nextLine();
                metrosCubicos = Integer.parseInt(input);
            } catch (Exception e){}
        }

        bultos = -1;
        while(bultos < 0 && !input.equals("q"))
        {
            try
            {
                System.out.println("Cantidad Bultos");
                input = sc.nextLine();
                bultos = Integer.parseInt(input);
            } catch (Exception e){}
        }

        domicilioRetiro = "";
        while(domicilioRetiro.equals("") && !input.equals("q"))
        {
            try
            {
                System.out.println("Domicilio Retiro");
                input = sc.nextLine();
                domicilioRetiro = input;
            } catch (Exception e){}
        }

        domicilioEntrega = "";
        while(domicilioEntrega.equals("") && !input.equals("q"))
        {
            try
            {
                System.out.println("Domicilio Entrega");
                input = sc.nextLine();
                domicilioEntrega = input;
            } catch (Exception e){}
        }

        estaPago = false;
        input = !input.equals("q") ? "" : "q";
        while(!(input.equals("y") || input.equals("n") || input.equals("q")))
        {
            try
            {
                System.out.println("Esta Pago? y/n");
                input = sc.nextLine();
                estaPago = input == "y";
            } catch (Exception e){}
        }

        if(!input.equals("q"))
        {
            solicitud = new Solicitud(ciudadDestino, nombre, fecha, cliente, metrosCubicos, bultos, domicilioRetiro, domicilioEntrega, estaPago);
            if(ciudadOrigen.insertarSolicitud(solicitud))
                System.out.println("Solicitud creada con exito");
        }
            
            /*
            Lista solicitudes = ciudadOrigen.obtenerSolicitudes(cp2);

            if(solicitudes != null)
                solicitudes.insertar(new Solicitud(cp2, nombre, fecha, cliente, metrosCubicos, bultos, domicilioRetiro, domicilioEntrega, estaPago), 1);
            else
            {
                solicitudes = new Lista();
                solicitudes.insertar(new Solicitud(cp2, nombre, fecha, cliente, metrosCubicos, bultos, domicilioRetiro, domicilioEntrega, estaPago), 1);
                ciudadOrigen.insertarSolicitud(solicitudes);
            }
            */
    }

    public void mostrarSolicitudes()
    {
        int cp1, cp2;
        String input = "";
        Ciudad ciudadOrigen, ciudadDestino;
        Lista solicitudes;

        ciudadOrigen = null;
        while(ciudadOrigen == null && !input.equals("q"))
        {
            try
            {
                System.out.println("Codigo Postal Origen");
                input = sc.nextLine();
                cp1 = Integer.parseInt(input);
                comprobarCp(cp1);
                ciudadOrigen = (Ciudad)ciudades.obtener(cp1);
            }
            catch (Exception e)
            {}
        }

        ciudadDestino = null;
        while(ciudadDestino == null && !input.equals("q"))
        {
            try
            {
                System.out.println("Codigo Postal Destino");
                input = sc.nextLine();
                cp2 = Integer.parseInt(input);
                comprobarCp(cp2);
                ciudadDestino = (Ciudad)ciudades.obtener(cp2);
                if(ciudadDestino != null)
                {
                    solicitudes = ciudadOrigen.obtenerSolicitudes(cp2);
                    System.out.println("Solicitudes entre las ciudades:");
                    System.out.println(ciudadOrigen + " y " + ciudadDestino);
                    System.out.println(solicitudes);
                }
                else
                {
                    System.out.println("Ciudad no encontrada para el codigo dado");
                }
            }
            catch (Exception e)
            {}
        }
    }

    public void run()
    {
        isRunning = true;
        mostrarMenu();
    }
}
