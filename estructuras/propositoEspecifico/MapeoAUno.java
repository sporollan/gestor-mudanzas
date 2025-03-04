package estructuras.propositoEspecifico;

import estructuras.lineales.dinamicas.Lista;

public class MapeoAUno {
    private static int TAM = 20;
    private NodoHashMapeo[] tabla;
    private int cant;

    public MapeoAUno()
    {
        this.tabla = new NodoHashMapeo[TAM];
        this.cant = 0;
    }

    public boolean existeClave(Object clave)
    {
        NodoHashMapeo aux;
        boolean existe = false;
        int i = 0;
        while(i < TAM && !existe)
        {
            aux = tabla[i];
            while(aux != null && !existe)
            {
                existe = aux.getDominio().equals(clave);
                aux = aux.getEnlace();
            }
            i = i + 1;
        }
        return existe;
    }

    public String toString()
    {
        String s = "";
        NodoHashMapeo aux;
        for(int i = 0; i < TAM; i++)
        {
            aux = tabla[i];
            while(aux != null)
            {
                s = s + "\n" +aux.getDominio() + ": " + aux.getRango();
                aux = aux.getEnlace();
            }
        }
        return s;
    }

    public boolean asociar(Object dominio, Object rango)
    {
        int pos = dominio.hashCode() % TAM;
        if(pos < 0)
            pos = pos * -1;
        NodoHashMapeo aux = this.tabla[pos];
        boolean encontrado = false;
        while(!encontrado && aux != null)
        {
            encontrado = aux.getDominio().equals(dominio);
            aux = aux.getEnlace();
        }

        if(!encontrado)
        {
            this.tabla[pos] = new NodoHashMapeo(dominio, rango, this.tabla[pos]);
            this.cant+=1;
        }
        return !encontrado;
    }

    public boolean desasociar(Object dominio)
    {
        // calculo el valor de la posicion
        int pos = dominio.hashCode() % TAM;
        if(pos < 0)
            pos = pos * -1;
        NodoHashMapeo aux = this.tabla[pos];
        boolean encontrado = false;
        // busco el nodo del dominio
        if(aux != null)
        {
            if(aux.getDominio().equals(dominio))
            {
                // caso el primer nodo es el buscado
                this.tabla[pos] = aux.getEnlace();
                encontrado = true;
            }
            else
            {
                // caso recorro los demas nodos
                NodoHashMapeo prev = aux;
                aux = aux.getEnlace();
                while(aux != null && !encontrado)
                {
                    if(aux.getDominio().equals(dominio))
                    {
                        // eliminar nodo
                        prev.setEnlace(aux.getEnlace());
                        encontrado = true;
                    }
                    else
                    {
                        // continuar recorriendo
                        prev = aux;
                        aux = aux.getEnlace();
                    }   
                }
            }
        }
        return encontrado;
    }

    public Object obtenerValor(Object dominio)
    {
        int pos = dominio.hashCode() % TAM;
        if(pos < 0)
            pos = pos *-1;
        NodoHashMapeo aux = this.tabla[pos];
        while(aux!=null && !aux.getDominio().equals(dominio))
        {
            aux = aux.getEnlace();
        }
        return aux == null ? null : aux.getRango();
    }

    public Lista listarDatos()
    {
        Lista datos = new Lista();
        NodoHashMapeo aux;
        for(int i = 0; i < TAM; i++)
        {
            aux = tabla[i];
            while(aux != null)
            {
                datos.insertar(aux.getRango(), datos.longitud()+1);
                aux = aux.getEnlace();
            }
        }
        return datos;
    }
}
