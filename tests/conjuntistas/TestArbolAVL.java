package tests.conjuntistas;

import conjuntistas.ArbolAVL;
import tests.Test;

public class TestArbolAVL extends Test {
    public boolean evaluarCaso(Object[] caso, Object[] indices, Object[] esperados, int casosI, boolean v)
    {
        boolean exito = false;
        ArbolAVL a = new ArbolAVL();
        int L = caso.length;

        for(int i = 0; i <L; i++)
        {
            a.insertar(caso[i]);
        }
        exito = this._assertEquals(a.toString(), esperados[0], "TestAVLInsertar"+casosI, v);
        if(exito)
        {
            this._assertEquals(a.getAltura(), esperados[1], "TestAVLAltura"+casosI, v);
        }
        return exito;
    }
    public boolean runTests(boolean v)
    {
        Object[][] casos = {
            {},
            {1},
            {5, 4, 6, 3, 7},
            /*
             *     5
             *   4   6
             * 3        7
             */
            {1, 2, 3, 4, 5},
            /*
             *  3
             * 1   4
             *    2  5
             *         
             */
            {5, 4, 3, 2, 1},
            {10, 5, 7}

        };
        Object[][] indices = {
            {},
            {},
            {},
            {},
            {},
            {}
        };
        Object[][] esperados = {
            {"", -1},
            {"1: null, null", 0},
            {
             "5: 4, 6\n"+
             "4: 3, null\n"+
             "3: null, null\n"+
             "6: null, 7\n" +
             "7: null, null",
            2
        },
            {"2: 1, 4\n"+
             "1: null, null\n"+
              "4: 3, 5\n"+
              "3: null, null\n"+
              "5: null, null",
            2},
            {
                "4: 2, 5\n" +
                "2: 1, 3\n" +
                "1: null, null\n" +
                "3: null, null\n" +
                "5: null, null",
                2
            },
            {
                "7: 5, 10\n"+
                "5: null, null\n"+
                "10: null, null",
                1
            }
        };
        return super.runTests(casos, indices, esperados, v);
    }
}
