package lineales.dinamicas;

public class Lista {
    Nodo cabecera;
    int len;

    public Lista()
    {
        this.cabecera = null;
        this.len = 0;
    }

    public boolean insertar(Object elem, int i)
    {   
        boolean exito = false;
        if(i == 1)
        {   
            this.cabecera = new Nodo(elem, this.cabecera);
            exito = true;
        }
        else
        {
            Nodo cabeceraAux = this.cabecera;
            int j = 1;
            while(cabeceraAux != null && j < i-1)
            {
                cabeceraAux = cabeceraAux.getEnlace();
                j+=1;
            }
            if(cabeceraAux != null)
            {
                cabeceraAux.setEnlace(new Nodo(elem, cabeceraAux.getEnlace()));
                exito = true;
            }
        }
        if(exito)
            this.len += 1;
        return exito;
    }

    public boolean eliminar(int i)
    {
        boolean exito = false;
        if(i == 1 && this.cabecera != null)
        {
            this.cabecera = this.cabecera.getEnlace();
            exito = true;
        }
        else
        {
            Nodo cabeceraAux = this.cabecera;
            int j = 1;
            while(j < i-1 && cabeceraAux != null)
            {
                cabeceraAux = cabeceraAux.getEnlace();
                j+=1;
            }
            if(cabeceraAux != null){
                Nodo next = cabeceraAux.getEnlace();
                if(next != null)
                {
                    next = next.getEnlace();
                    cabeceraAux.setEnlace(next);
                    exito = true;
                }
            }
        }
        if(exito)
            this.len-=1;
        return exito;
    }

    public Object recuperar(int i)
    {
        Object n = null;
        if(i == 1 && this.cabecera != null)
        {
            n = this.cabecera.getElem();
        }
        else
        {
            Nodo cabeceraAux = this.cabecera;
            int j = 1;
            while(cabeceraAux != null && j < i)
            {
                cabeceraAux = cabeceraAux.getEnlace();
                j+=1;
            }
            if(cabeceraAux != null)
            {
                n = cabeceraAux.getElem();
            }
        }
        return n;
    }

    public int localizar(Object elem)
    {
        Nodo cabeceraAux = this.cabecera;
        int j = 1;
        while(cabeceraAux != null && !(cabeceraAux.getElem().equals(elem)))
        {
            cabeceraAux = cabeceraAux.getEnlace();
            j+=1;
        }
        return j > this.len ? -1 : j;
    }

    public int longitud()
    {
        return this.len;
    }

    public boolean esVacia()
    {
        return this.len == 0;
    }

    public Lista clone()
    {
        Lista l = new Lista();
        Nodo cabeceraAuxO = this.cabecera;
        if(cabeceraAuxO != null)
        {
            l.cabecera = new Nodo(cabeceraAuxO.getElem(), null);
            cabeceraAuxO = cabeceraAuxO.getEnlace();
        }
        Nodo cabeceraAuxN = l.cabecera;
        while(cabeceraAuxO!=null)
        {
            cabeceraAuxN.setEnlace(new Nodo(cabeceraAuxO.getElem(), null));
            cabeceraAuxN = cabeceraAuxN.getEnlace();
            cabeceraAuxO = cabeceraAuxO.getEnlace();
        }
        return l;
    }

    public void vaciar()
    {
        this.cabecera = null;
    }

    public String toString()
    {
        String lista = "[";
        Nodo cabeceraAux = this.cabecera;
    
        if(cabeceraAux != null)
        {
            lista += cabeceraAux.getElem();
            cabeceraAux = cabeceraAux.getEnlace();
        }

        while(cabeceraAux != null)
        {
            lista += ", " + cabeceraAux.getElem();
            cabeceraAux = cabeceraAux.getEnlace();
        }

        return lista + "]";
    }
}
