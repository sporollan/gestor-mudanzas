package mudanzas;

import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.Diccionario;

public class SolicitudesManager {
    InputReader inputReader;
    Diccionario ciudades;

    public SolicitudesManager(InputReader inputReader, Diccionario ciudades)
    {
        this.inputReader = inputReader;
        this.ciudades = ciudades;
    }
    private void mostrarMenu()
    {
        System.out.println("Gestionar Solicitudes");
        System.out.println("1. Insertar");
        System.out.println("2. Eliminar");
        System.out.println("3. Modificar");
        System.out.println("4. Mostrar Pedidos entre Ciudades");
    }
    public void gestionar()
    {
        String seleccion = "";
        while(!seleccion.equals("q"))
        {
            mostrarMenu();
            seleccion = inputReader.scanString("Seleccion");
            if(seleccion.equals("1"))
                cargarDatos();
            else if(seleccion.equals("2"))
                eliminar();
            else if(seleccion.equals("3"))
                modificar();
            else if(seleccion.equals("4"))
                mostrarPedidosEntreCiudades();
            
        }
    }

    private void mostrarPedidosEntreCiudades()
    {
        String[] names = {"Origen(cp)", "Destino(cp)"};
        int[] codigos = inputReader.scanCodigos(names);
        Ciudad c = (Ciudad)ciudades.obtenerInformacion(codigos[0]);
        Lista pedidos=null;
        if(c != null)
        {
            pedidos = c.obtenerSolicitudes(codigos[1]);
        }
        if(pedidos != null)
        {
            System.out.println("Pedidos entre " + c.getNombre() + 
                                " y " + 
                                ((Ciudad)ciudades.obtenerInformacion(codigos[1])).getNombre());
            
            Solicitud pedido;
            Cliente cliente;
            int metrosCubicos = 0;
            for(int i = 1; i <= pedidos.longitud(); i++)
            {
                pedido = (Solicitud)pedidos.recuperar(i);
                System.out.println("Pedido " + i);
                cliente = pedido.getCliente();
                System.out.println(cliente.getNombres() + " " + cliente.getApellidos());
                System.out.println("Bultos "+pedido.getBultos());
                System.out.println("Metros cubicos "+pedido.getMetrosCubicos());
                metrosCubicos = metrosCubicos + pedido.getMetrosCubicos();
            }
            System.out.println("Espacio necesario para transportar: " + metrosCubicos + " metros cubicos");
        }
    }

    private void eliminar()
    {
        Ciudad ciudadOrigen = inputReader.scanCiudad("Origen");
        Ciudad ciudadDestino = null;
        if(ciudadOrigen != null)
        {
            ciudadDestino = inputReader.scanCiudad("Destino");
        }
        
        Lista listaSolicitudes = ciudadOrigen.obtenerSolicitudes(ciudadDestino.getCodigo());
        Solicitud s = (Solicitud)listaSolicitudes.recuperar(1);
        int i = 1;
        while(s != null)
        {
            System.out.println(s.getFecha());
            System.out.println(((Cliente)s.getCliente()).getNombres());
            if(inputReader.scanBool("Eliminar? s/n"))
            {
                if(listaSolicitudes.eliminar(i))
                    System.out.println("Eliminado con exito");
            }
            else
            {
                i+=1;
            }
            s = (Solicitud)listaSolicitudes.recuperar(i);
        }
    }

    private void modificar()
    {
        Ciudad ciudadOrigen = inputReader.scanCiudad("Origen");
        Ciudad ciudadDestino = null;
        if(ciudadOrigen != null)
        {
            ciudadDestino = inputReader.scanCiudad("Destino");
        }
        
        Lista listaSolicitudes = ciudadOrigen.obtenerSolicitudes(ciudadDestino.getCodigo());
        Solicitud s = (Solicitud)listaSolicitudes.recuperar(1);
        int i = 1;
        while(s != null)
        {
            System.out.println(s.getFecha());
            System.out.println(((Cliente)s.getCliente()).getNombres());
            if(inputReader.scanBool("Modificar? s/n"))
            {
                if(inputReader.scanBool("Modificar fecha? s/n"))
                    s.setFecha(inputReader.scanFecha());

                if(inputReader.scanBool("Domicilio Retiro: " + s.getDomicilioRetiro() + " Modificar? s/n"))
                    s.setDomicilioRetiro(inputReader.scanString("Domicilio Retiro"));

                if(inputReader.scanBool("Domicilio Entrega: " + s.getDomicilioEntrega() + " Modificar? s/n"))
                    s.setDomicilioEntrega(inputReader.scanString("Domicilio Retiro"));

                if(inputReader.scanBool("Metros Cubicos: " + s.getMetrosCubicos() + " Modificar? s/n"))
                    s.setMetrosCubicos(inputReader.scanInt("Metros Cubicos"));
                
                if(inputReader.scanBool("Bultos: " + s.getBultos() + " Modificar? s/n"))
                    s.setBultos(inputReader.scanInt("Bultos"));

                s.setEstaPago(inputReader.scanBool("Esta pago?"));

                if(inputReader.scanBool("Cliente: " + ((Cliente)s.getCliente()).getNombres() + " Modificar? s/n"))
                    s.setCliente(inputReader.scanCliente());
            }
            i+=1;
            s = (Solicitud)listaSolicitudes.recuperar(i);
        }
    }

    private void cargarDatos()
    {
        boolean estaPago;
        String fecha="";
        String[] strNames = {"Domicilio Retiro", "Domicilio Entrega"};
        String[] intNames = {"Metros Cubicos", "Bultos"};
        String[] strInputs = new String[strNames.length+1];
        int[] intInputs = new int[intNames.length+1];
        Ciudad ciudadOrigen, ciudadDestino;
        Cliente cliente;
        boolean continuar = true;

        ciudadOrigen = inputReader.scanCiudad("Origen");
        continuar = ciudadOrigen != null;

        ciudadDestino = null;
        if(continuar)
        {
            ciudadDestino = inputReader.scanCiudad("Destino");
            continuar = ciudadDestino != null;
        }

        if(continuar)
        {
            fecha = inputReader.scanFecha();
            continuar = !fecha.equals("q");
        }

        if(continuar)
        {
            strInputs = inputReader.cargarStringsSc(strNames);
            continuar = !strInputs[strNames.length].equals("q");
        }

        if(continuar)
        {
            intInputs = inputReader.cargarIntsSc(intNames);
            continuar = !(intInputs[intNames.length] == -1);
        }

        cliente = null;
        if(continuar)
        {
            cliente = inputReader.scanCliente();
            continuar = cliente != null;
        }

        estaPago = false;
        if(continuar)
        {
            /*
            estaPago = false;
            input = "";
            while(!(input.equals("y") || input.equals("n") || input.equals("q")))
            {
                input = inputReader.scanString("Esta Pago? y/n");
                estaPago = input == "y";
            }
            continuar = !input.equals("q");
            */
            estaPago = inputReader.scanBool("Esta Pago? s/n");
        }

        if(continuar)
        {
            Solicitud solicitud = new Solicitud(ciudadDestino, fecha, cliente, intInputs[0], intInputs[1], strInputs[0], strInputs[1], estaPago);
            if(ciudadOrigen.insertarSolicitud(solicitud))
                System.out.println("Solicitud creada con exito");
        }
    }

    public void mostrarSolicitudes()
    {
        Ciudad ciudadOrigen, ciudadDestino;
        Lista solicitudes;
        boolean continuar;

        ciudadOrigen = inputReader.scanCiudad("Codigo Postal Origen");
        continuar = ciudadOrigen != null;

        ciudadDestino = null;
        if(continuar)
        {
            ciudadDestino = inputReader.scanCiudad("Codigo Postal Destino");
            continuar = ciudadDestino != null;
        }

        if(continuar)
        {
            solicitudes = ciudadOrigen.obtenerSolicitudes(ciudadDestino.getCodigo());
            System.out.println("Solicitudes desde " + ciudadOrigen + " a " + ciudadDestino);
            System.out.println(solicitudes);
        }
        else
        {
            System.out.println("Ciudad no encontrada para el codigo dado");
        }
    }


}
