package estructuras.propositoEspecifico;

import estructuras.lineales.dinamicas.Lista;

public class NodoAVLMapeoM {
    private Comparable dominio;
    private Lista rango;
    private NodoAVLMapeoM izquierdo;
    private NodoAVLMapeoM derecho;
    private int altura;
    public NodoAVLMapeoM(Comparable dominio, Lista rango, NodoAVLMapeoM izquierdo, NodoAVLMapeoM derecho) {
        this.dominio = dominio;
        this.rango = rango;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.altura = 0;
    }
    private int _recalcularAlturaAux(NodoAVLMapeoM n, int a)
    {
        int ai;
        int ad;
        if( n != null )
        {
            ai = this._recalcularAlturaAux(n.izquierdo, a);
            ad = this._recalcularAlturaAux(n.derecho, a);
            a = ai > ad ? ai : ad;
            n.altura = a;
        }
        else
        {
            return 0;
        }
        return a + 1;

    }
    public void recalcularAltura() {
        int ai = 0;
        int ad = 0;
        ai = this._recalcularAlturaAux(this.izquierdo, 0);
        ad = this._recalcularAlturaAux(this.derecho, 0);
        this.altura = ai > ad ? ai : ad;
    }
    public Comparable getDominio() {
        return dominio;
    }
    public void setDominio(Comparable dominio) {
        this.dominio = dominio;
    }
    public Lista getRango() {
        return rango;
    }
    public void setRango(Lista rango) {
        this.rango = rango;
    }
    public NodoAVLMapeoM getIzquierdo() {
        return izquierdo;
    }
    public void setIzquierdo(NodoAVLMapeoM izquierdo) {
        this.izquierdo = izquierdo;
    }
    public NodoAVLMapeoM getDerecho() {
        return derecho;
    }
    public void setDerecho(NodoAVLMapeoM derecho) {
        this.derecho = derecho;
    }
    public int getAltura()
    {
        return this.altura;
    }
}
