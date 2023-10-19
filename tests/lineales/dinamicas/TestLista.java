package tests.lineales.dinamicas;

import lib.lineales.dinamicas.Lista;
import tests.Test;

public class TestLista extends Test{

    @Override
    public boolean evaluarCaso(Comparable<Object>[] caso, Object[] indices, Object[] esperados, int casosI, boolean v)
    {
        boolean exito = false;
        Lista lista = new Lista();
        int L = caso.length;


        for(int i = 0; i < L; i++)
        {
            lista.insertar(caso[i], (int)indices[i]);
        }
        exito = this._assertEquals(lista.toString(), esperados[0], "TestInsertar"+casosI, v);

        if(exito)
        {
            lista.eliminar((int)indices[L]);
            exito = this._assertEquals(lista.toString(), esperados[1], "TestEliminar"+casosI, v);
        }

        if(exito)
        {
            Object n = lista.recuperar((int)indices[L+1]);
            exito = this._assertEquals(n, esperados[2], "TestRecuperar"+casosI, v);
        }

        if(exito)
        {
            int loc = lista.localizar(indices[L+2]);
            exito = this._assertEquals(loc, esperados[3], "TestLocalizar"+casosI, v);
        }

        if(exito)
        {
            int len = lista.longitud();
            exito = this._assertEquals(len, esperados[4], "TestLongitud"+casosI, v);
        }

        if(exito)
        {
            boolean vac = lista.esVacia();
            exito = this._assertEquals(vac, esperados[5], "TestEsVacia"+casosI, v);
        }

        if(exito)
        {
            Lista lista2 = lista.clone();
            exito = this._assertEquals(lista2.toString(), esperados[1], "TestClone"+casosI, v);
        }

        if(exito)
        {
            lista.vaciar();
            exito = this._assertEquals(lista.toString(), "[]", "TestVaciar"+casosI, v);
        }
        return exito;
    }

    public boolean runTests(boolean v)
    {
        Comparable[][] casosLista = {
            {},
            {9, 8, 7, 6},
            {1, 2, 3, 4},
            {1},
            {'a', 'b', 'c', 'd'}
        };
        Object[][] indicesLista = {
            {1, 1, 1},
            {1, 1, 1, 1, 1, 1, 7},
            {1, 2, 3, 4, 2, 2, 3},
            {1, 1, 9, 'b'},
            {1, 2, 3, 4, 3, 2, 'd'}
        };
        Object[][] esperadosLista = {
            {"[]", "[]", null, -1, 0, true},
            {"[6, 7, 8, 9]", "[7, 8, 9]", 7, 1, 3, false},
            {"[1, 2, 3, 4]", "[1, 3, 4]", 3, 2, 3, false},
            {"[1]", "[]", null, -1, 0, true},
            {"[a, b, c, d]", "[a, b, d]", 'b', 3, 3, false}
        };
        return super.runTests(casosLista, indicesLista, esperadosLista, v);
    }

}
