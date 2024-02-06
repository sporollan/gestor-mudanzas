package mudanzas;

import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.Diccionario;

public class SolicitudesManager {
    InputReader inputReader;
    Diccionario ciudades;
    LogOperacionesManager logOperacionesManager;

    public SolicitudesManager(InputReader inputReader, Diccionario ciudades, LogOperacionesManager logOperacionesManager)
    {
        this.inputReader = inputReader;
        this.ciudades = ciudades;
        this.logOperacionesManager = logOperacionesManager;
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

    private void cargarDatos()
    {
        // se cargan los datos de la solicitud
        // se la crea y se la inserta en la ciudad correspondiente
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
            estaPago = inputReader.scanBool("Esta Pago?");
        }

        // si esta todo correcto se crea la solicitud y se la inserta
        if(continuar)
        {
            Solicitud solicitud = new Solicitud(ciudadDestino, fecha, cliente, intInputs[0], intInputs[1], strInputs[0], strInputs[1], estaPago);
            if(insertar(ciudadOrigen, solicitud))
            {
                System.out.println("Solicitud insertada con exito");
                logOperacionesManager.escribirInsercion("la solicitud de " + ciudadOrigen.getCodigo() + " a " + 
                solicitud.getDestino().getCodigo() + " " + solicitud.getMetrosCubicos() + " metros cubicos");
            }
            else
            {
                System.out.println("Error insertando solicitud");
            }
        }
    }

    private void eliminar()
    {
        // se obtiene origen y destino
        // luego se recorren todas las solicitudes entre las ciudades
        // solicitando si deben ser eliminadas
        Ciudad ciudadOrigen = inputReader.scanCiudad("Origen");
        Ciudad ciudadDestino = null;
        if(ciudadOrigen != null)
        {
            ciudadDestino = inputReader.scanCiudad("Destino");
        }
        
        if(ciudadDestino != null)
        {
            // obtengo las solicitudes hacia la ciudad
            Lista listaSolicitudes = ciudadOrigen.obtenerSolicitudes(ciudadDestino.getCodigo());
            Solicitud s = (Solicitud)listaSolicitudes.recuperar(1);
            int i = 1;
            // las recorro consultando por cada una si se desea eliminar
            while(s != null)
            {
                System.out.println(s.getFecha());
                System.out.println(((Cliente)s.getCliente()).getNombres());
                System.out.println(s.getMetrosCubicos() +" metros cubicos");
                if(inputReader.scanBool("Eliminar? s/n"))
                {
                    if(listaSolicitudes.eliminar(i))
                    {
                        System.out.println("Eliminado con exito");
                        logOperacionesManager.escribirEliminacion("la solicitud de " + ciudadOrigen.getCodigo() + " a " + 
                        s.getDestino().getCodigo() + " " + s.getMetrosCubicos() + " metros cubicos");
                    }
                }
                else
                {
                    i+=1;
                }
                s = (Solicitud)listaSolicitudes.recuperar(i);
            }
        }
    }

    private void modificar()
    {
        // ingreso origen y destino
        // luego pregunto por cada dato si se debe modificar
        Ciudad ciudadOrigen = inputReader.scanCiudad("Origen");
        Ciudad ciudadDestino = null;
        if(ciudadOrigen != null)
        {
            ciudadDestino = inputReader.scanCiudad("Destino");
        }

        if(ciudadDestino!=null)
        {
            Lista listaSolicitudes = ciudadOrigen.obtenerSolicitudes(ciudadDestino.getCodigo());
            if(listaSolicitudes != null)
            {
                Solicitud s = (Solicitud)listaSolicitudes.recuperar(1);
                int i = 1;
                // se recorren todas las solicitudes consultando si se la quiere modificar
                // al modificar se recorren todos los datos consultando uno por uno si se lo quiere modificar
                while(s != null)
                {
                    System.out.println(s.getFecha());
                    System.out.println(((Cliente)s.getCliente()).getNombres());
                    if(inputReader.scanBool("Modificar?"))
                    {
                        boolean modificado = false;
                        if(inputReader.scanBool("Modificar fecha?"))
                        {
                            s.setFecha(inputReader.scanFecha());
                            modificado = true;
                        }
                        if(inputReader.scanBool("Domicilio Retiro: " + s.getDomicilioRetiro() + " Modificar?"))
                        {
                            s.setDomicilioRetiro(inputReader.scanString("Domicilio Retiro"));
                            modificado = true;
                        }
                        if(inputReader.scanBool("Domicilio Entrega: " + s.getDomicilioEntrega() + " Modificar?"))
                        {
                            s.setDomicilioEntrega(inputReader.scanString("Domicilio Entrega"));
                            modificado = true;
                        }
                        if(inputReader.scanBool("Metros Cubicos: " + s.getMetrosCubicos() + " Modificar?"))
                        {
                            s.setMetrosCubicos(inputReader.scanInt("Metros Cubicos"));
                            modificado = true;
                        }
                        if(inputReader.scanBool("Bultos: " + s.getBultos() + " Modificar?"))
                        {
                            s.setBultos(inputReader.scanInt("Bultos"));
                            modificado = true;
                        }
                        if(inputReader.scanBool("Esta pago: " + (s.isEstaPago()?"T":"F") + " Modificar?"))
                        {
                            s.setEstaPago(inputReader.scanBool("Esta pago?"));
                            modificado = true;
                        }
                        if(inputReader.scanBool("Cliente: " + ((Cliente)s.getCliente()).getNombres() + " Modificar?"))
                        {
                            s.setCliente(inputReader.scanCliente());
                            modificado = true;
                        }
                        if(modificado)
                        {
                            System.out.println("Modificado con exito");
                            logOperacionesManager.escribirModificacion("la solicitud de " + ciudadOrigen.getCodigo() + " a " + 
                            s.getDestino().getCodigo() + " " + s.getMetrosCubicos() + " metros cubicos");
                        }
                    }
                    i+=1;
                    s = (Solicitud)listaSolicitudes.recuperar(i);
                }
            }
        }
    }

    private void mostrarPedidosEntreCiudades()
    {
        // se ingresa origen y destino
        // luego se recorren todas las solicitudes entre las ciudades mostrando cada una
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
                System.out.println();
                System.out.println("Pedido " + i);
                cliente = pedido.getCliente();
                System.out.println(cliente.getNombres() + " " + cliente.getApellidos());
                System.out.println("Fecha " + pedido.getFecha());
                System.out.println("Bultos "+pedido.getBultos());
                System.out.println("Metros cubicos "+pedido.getMetrosCubicos());
                metrosCubicos = metrosCubicos + pedido.getMetrosCubicos();
            }
            System.out.println("Espacio necesario para transportar: " + metrosCubicos + " metros cubicos");
        }
    }

    public void mostrarEstructura()
    {
        // se obtienen una lista con todas las ciudades
        // se recorre cada ciudad mostrando su estructura de solicitudes
        Lista listaCiudades = ciudades.listarDatos();
        Ciudad c;
        for(int i = 1; i <= listaCiudades.longitud(); i++)
        {
            c = (Ciudad)listaCiudades.recuperar(i);
            System.out.println("Solicitudes de " + c.getNombre() + " " + c.getCodigo());
            c.mostrarEstructuraSolicitudes();
        }
    }

    public boolean insertar(Ciudad ciudadOrigen, Solicitud solicitud)
    {
        // se ingresa una ciudad y una solicitud
        // se comprueba si existe y luego se inserta la solicitud a la ciudad dada
        // se consideran duplicadas las solicitudes con mismo cliente, origen y destino
        boolean exito = false;
        if(!ciudadOrigen.existeSolicitud(solicitud))
        {   
            exito = ciudadOrigen.insertarSolicitud(solicitud);
        }
        return exito;
    }
}
