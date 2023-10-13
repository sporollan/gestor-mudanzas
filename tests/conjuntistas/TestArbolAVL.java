package tests.conjuntistas;

import conjuntistas.ArbolAVL;
import tests.Test;

public class TestArbolAVL extends Test {
    public boolean evaluarCaso(Object[] caso, Object[] indices, Object[] esperados, int casosI)
    {
        boolean exito = false;
        ArbolAVL a = new ArbolAVL();
        int L = caso.length;

        for(int i = 0; i <L; i++)
        {
            a.insertar(caso[i]);
        }
        exito = this._assertEquals(a.toString(), esperados[0], "TestAVLInsertar"+casosI);
        if(exito)
        {
            
        }
        return exito;
    }
    public boolean runTests()
    {
        boolean exito = true;
        Object[][] casos = {
            {},
            {1},
            {5, 4, 6, 3, 7},
            {1, 2, 3, 4, 5}
        };
        Object[][] indices = {
            {},
            {},
            {},
            {}
        };
        Object[][] esperados = {
            {"[]"},
            {"[1: null, null]"},
            {""},
            {""}
        };
        super.runTests(casos, indices, esperados);
        return exito;
    }
}
