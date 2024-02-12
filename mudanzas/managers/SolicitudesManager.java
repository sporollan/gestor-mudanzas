package mudanzas.managers;

import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.MapeoAMuchos;
import mudanzas.Ciudad;
import mudanzas.Cliente;
import mudanzas.Solicitud;
import mudanzas.librerias.InputReader;
import mudanzas.librerias.LogOperacionesManager;

public class SolicitudesManager {
    MapeoAMuchos solicitudes;
    String path = "files/operaciones.log";

    public SolicitudesManager(MapeoAMuchos solicitudes)
    {
        this.solicitudes = solicitudes;
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
            seleccion = InputReader.scanString("Seleccion");
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
        Comparable ciudadOrigen, ciudadDestino;
        String[] cliente;
        boolean continuar = true;

        ciudadOrigen = InputReader.scanCp("Origen");
        continuar = ciudadOrigen != null;

        ciudadDestino = null;
        if(continuar)
        {
            ciudadDestino = InputReader.scanCp("Destino");
            continuar = ciudadDestino != null;
        }

        if(continuar)
        {
            fecha = InputReader.scanFecha();
            continuar = !fecha.equals("q");
        }

        if(continuar)
        {
            strInputs = InputReader.cargarStringsSc(strNames);
            continuar = !strInputs[strNames.length].equals("q");
        }

        if(continuar)
        {
            intInputs = InputReader.cargarIntsSc(intNames);
            continuar = !(intInputs[intNames.length] == -1);
        }

        cliente = null;
        if(continuar)
        {
            cliente = InputReader.scanClaveCliente();
            continuar = cliente != null;
        }

        estaPago = false;
        if(continuar)
        {
            estaPago = InputReader.scanBool("Esta Pago?");
        }

        // si esta todo correcto se crea la solicitud y se la inserta
        if(continuar)
        {
            String codigoStr = ciudadOrigen + "" + ciudadDestino;
            int codigo = Integer.parseInt(codigoStr);
            Solicitud solicitud = new Solicitud((Comparable)codigo, fecha, cliente[0]+cliente[1], intInputs[0], intInputs[1], strInputs[0], strInputs[1], estaPago);

            if(insertar((Comparable)codigo, solicitud))
            {
                System.out.println("Solicitud insertada con exito");
                LogOperacionesManager.escribirInsercion("la solicitud de " + ciudadOrigen + " a " + 
                solicitud.getCodigo(), path);
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
        Comparable ciudadOrigen = InputReader.scanCp("Origen");
        Comparable ciudadDestino = null;
        if(ciudadOrigen != null)
        {
            ciudadDestino = InputReader.scanCp("Destino");
        }
        
        if(ciudadDestino != null)
        {
            String codigoStr = ciudadOrigen + "" + ciudadDestino;
            int codigo = Integer.parseInt(codigoStr);
            Lista solicitudesADestino = solicitudes.obtenerValor((Comparable)codigo);
            Solicitud s = (Solicitud)solicitudesADestino.recuperar(1);
            int i = 1;
            while(s != null)
            {
                System.out.println(s.getFecha());
                System.out.println(s.getCliente());
                if(InputReader.scanBool("Eliminar?"))
                {
                    if(solicitudes.desasociar(ciudadDestino, s))
                    {
                        System.out.println("Eliminado con exito");
                        LogOperacionesManager.escribirEliminacion("la solicitud " + ciudadOrigen, path);                 }
                }
                else
                {
                    i+=1;
                }
                s = (Solicitud)solicitudesADestino.recuperar(i);
            }
        }
    }

    private void modificar()
    {
        // ingreso origen y destino
        // luego pregunto por cada dato si se debe modificar
        Comparable ciudadOrigen = InputReader.scanCp("Origen");
        Comparable ciudadDestino = null;
        if(ciudadOrigen != null)
        {
            ciudadDestino = InputReader.scanCp("Destino");
        }

        if(ciudadDestino!=null)
        {
            String codigoStr = ciudadOrigen + "" + ciudadDestino;
            int codigo = Integer.parseInt(codigoStr);
            Lista listaSolicitudes = solicitudes.obtenerValor((Comparable)codigo);
            if(listaSolicitudes != null)
            {
                Solicitud s = (Solicitud)listaSolicitudes.recuperar(1);
                int i = 1;
                // se recorren todas las solicitudes consultando si se la quiere modificar
                // al modificar se recorren todos los datos consultando uno por uno si se lo quiere modificar
                while(s != null)
                {
                    System.out.println(s.getFecha());
                    System.out.println(s.getCliente());
                    if(InputReader.scanBool("Modificar?"))
                    {
                        boolean modificado = false;
                        if(InputReader.scanBool("Modificar fecha?"))
                        {
                            s.setFecha(InputReader.scanFecha());
                            modificado = true;
                        }
                        if(InputReader.scanBool("Domicilio Retiro: " + s.getDomicilioRetiro() + " Modificar?"))
                        {
                            s.setDomicilioRetiro(InputReader.scanString("Domicilio Retiro"));
                            modificado = true;
                        }
                        if(InputReader.scanBool("Domicilio Entrega: " + s.getDomicilioEntrega() + " Modificar?"))
                        {
                            s.setDomicilioEntrega(InputReader.scanString("Domicilio Entrega"));
                            modificado = true;
                        }
                        if(InputReader.scanBool("Metros Cubicos: " + s.getMetrosCubicos() + " Modificar?"))
                        {
                            s.setMetrosCubicos(InputReader.scanInt("Metros Cubicos"));
                            modificado = true;
                        }
                        if(InputReader.scanBool("Bultos: " + s.getBultos() + " Modificar?"))
                        {
                            s.setBultos(InputReader.scanInt("Bultos"));
                            modificado = true;
                        }
                        if(InputReader.scanBool("Esta pago: " + (s.isEstaPago()?"T":"F") + " Modificar?"))
                        {
                            s.setEstaPago(InputReader.scanBool("Esta pago?"));
                            modificado = true;
                        }
                        if(InputReader.scanBool("Cliente: " + s.getCliente() + " Modificar?"))
                        {
                            String[] c = InputReader.scanClaveCliente();
                            s.setCliente(c[0]+c[1]);
                            modificado = true;
                        }
                        if(modificado)
                        {
                            System.out.println("Modificado con exito");
                            LogOperacionesManager.escribirModificacion("la solicitud de " + ciudadOrigen + " a " + 
                            s.getCodigo(), path);
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
        int[] codigos = InputReader.scanCodigos(names);
        Lista pedidos=null;
        if(codigos[codigos.length-1] != -1)
        {
            String codigoStr = codigos[0] +""+ codigos[1];
            int codigo = Integer.parseInt(codigoStr);
            pedidos = solicitudes.obtenerValor(codigo);
        }
        if(pedidos != null)
        {
            System.out.println("Pedidos entre " + codigos[0] + 
                                " y " + codigos[1]);
            
            Solicitud pedido;
            String cliente;
            int metrosCubicos = 0;
            for(int i = 1; i <= pedidos.longitud(); i++)
            {
                pedido = (Solicitud)pedidos.recuperar(i);
                System.out.println();
                System.out.println("Pedido " + i);
                cliente = pedido.getCliente();
                System.out.println(cliente);
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
        System.out.println(solicitudes);
    }

    public boolean insertar(Comparable codigo, Solicitud solicitud)
    {
        // se ingresa una ciudad y una solicitud
        // se comprueba si existe y luego se inserta la solicitud a la ciudad dada
        // se consideran duplicadas las solicitudes con mismo cliente, origen y destino
        return solicitudes.asociar(codigo, solicitud);

    }
}
