package tests.conjuntistas;

import lib.conjuntistas.ArbolAVL;
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
            {10, 5, 7},
            {100, 50, 150, 25, 30, 60, 55}

        };
        Object[][] indices = {
            {},
            {},
            {},
            {},
            {},
            {},
            {}
        };
        Object[][] esperados = {
            {"", -1},
            {"1(0): null, null", 0},
            {
             "5(2): 4(1), 6(1)\n"+
             "4(1): 3(0), null\n"+
             "3(0): null, null\n"+
             "6(1): null, 7(0)\n" +
             "7(0): null, null",
            2
        },
            {"2(2): 1(0), 4(1)\n"+
             "1(0): null, null\n"+
              "4(1): 3(0), 5(0)\n"+
              "3(0): null, null\n"+
              "5(0): null, null",
            2},
            {
                "4(2): 2(1), 5(0)\n" +
                "2(1): 1(0), 3(0)\n" +
                "1(0): null, null\n" +
                "3(0): null, null\n" +
                "5(0): null, null",
                2
            },
            {
                "7(1): 5(0), 10(0)\n"+
                "5(0): null, null\n"+
                "10(0): null, null",
                1
            },
            {
                "100(3): 30(2), 150(0)\n"+
                "30(2): 25(0), 55(1)\n"+
                "25(0): null, null\n"+
                "55(1): 50(0), 60(0)\n"+
                "50(0): null, null\n"+
                "60(0): null, null\n"+
                "150(0): null, null",
                3
            }
        };
        return super.runTests(casos, indices, esperados, v);
    }
}
