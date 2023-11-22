package estructuras.propositoEspecifico;

// clase utilizada por MapeoAUno
public class NodoHashMapeo {
    private Object tipoDominio;
    private Object rango;
    private NodoHashMapeo enlace;


    public NodoHashMapeo(Object tipoDominio, Object rango, NodoHashMapeo enlace) {
        this.tipoDominio = tipoDominio;
        this.rango = rango;
        this.enlace = enlace;
    }
    public Object getTipoDominio() {
        return tipoDominio;
    }
    public void setTipoDominio(Object tipoDominio) {
        this.tipoDominio = tipoDominio;
    }
    public Object getRango() {
        return rango;
    }
    public void setRango(Object rango) {
        this.rango = rango;
    }
    public NodoHashMapeo getEnlace() {
        return enlace;
    }
    public void setEnlace(NodoHashMapeo enlace) {
        this.enlace = enlace;
    }
}
