package tests.conjuntistas;

import lib.conjuntistas.ArbolAVL;
import tests.Test;

public class TestArbolAVL extends Test {
    public boolean evaluarCaso(Comparable<Object>[] caso, Comparable[] indices, Object[] esperados, int casosI, boolean v)
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
            //a.eliminar(indices[0]);
            //exito = this._assertEquals(a.toString(), esperados[1], "TestAVLEliminar"+casosI, v);
        }
        return exito;
    }
    public boolean runTests(boolean v)
    {
        Comparable[][] casos = {
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
            {100, 50, 150, 25, 30, 60, 55},
            {'x', 'm', 'z', 'f', 'g', 'o', 'n'}

        };
        Comparable[][] indices = {
            {1},
            {1},
            {7},
            {4},
            {},
            {},
            {},
            {}
        };
        Object[][] esperados = {
            {"", ""},
            {"1(0): null, null", ""},
            {
             "5(2): 4(1), 6(1)\n"+   //       5
             "4(1): 3(0), null\n"+   //     4   6
             "3(0): null, null\n"+   //   3       7
             "6(1): null, 7(0)\n" +
             "7(0): null, null",
                                     // eliminar 7
             "5(2): 4(1), 6(1)\n"+   //        5
             "4(1): 3(0), null\n"+   //     4      6
             "3(0): null, null\n"+   //   3  
             "6(1): null, null"
            
        },
            {"2(2): 1(0), 4(1)\n"+   //       2
             "1(0): null, null\n"+   //    1     4
              "4(1): 3(0), 5(0)\n"+  //         3   5
              "3(0): null, null\n"+
              "5(0): null, null",
                                     // eliminar 4
              "2(2): 1(0), 3(1)\n" + //       2
              "1(0): null, null\n" + //     1    3
              "3(1): null, 5(0)"},   //            5
            {
                "4(2): 2(1), 5(0)\n" +//      4
                "2(1): 1(0), 3(0)\n" +//    2   5
                "1(0): null, null\n" +//   1 3
                "3(0): null, null\n" +
                "5(0): null, null",
                                    // eliminar 4
                "5(2): 2(1), null\n" +//      2
                "2(1): 1(0), 3(0)\n" +//    1   5 
                "1(0): null, null\n" +//     3
                "3(0): null, null"

            },
            {
                "7(1): 5(0), 10(0)\n"+ //     7
                "5(0): null, null\n"+  //    5 10
                "10(0): null, null",   //  
                                        // eliminar 5  
                "7(1): null, 10(0)\n"+ // 7                 
                "10(0): null, null"    //   10
                
            },
            {
                "50(3): 30(1), 100(2)\n"+ //      50
                "30(1): 25(0), null\n"+   //   30     100
                "25(0): null, null\n"+     // 25     60   150
                "100(2): 60(1), 150(0)\n"+   //       55
                "60(1): null, 55(0)\n"+
                "55(0): null, null\n"+     //  
                "150(0): null, null",
                                           // eliminar 30
                "60(2): 50(1), 100(1)\n"+ //      60
                "50(1): 25(0), 55(0)\n"+   //   50      100
                "25(0): null, null\n"+    //  25 55       150 
                "55(0): null, null\n"+   // 
                "100(1): null, 150(0)\n"+
                "150(0): null, null",
            },
            {
                "m(3): g(1), x(2)\n" + //
                        "g(1): f(0), null\n" + //        m
                        "f(0): null, null\n" + //    g        x
                        "x(2): o(1), z(0)\n" + //  f         o z
                        "o(0): n(0), null\n" + //           n
                        "n(0): null, null\n" + //
                        "z(0): null, null",
                "o(2): g(1), x(1)\n" + //      eliminar m
                        "g(1): f(0), n(0)\n" + //        o
                        "f(0): null, null\n" + //    g        x
                        "n(0): null, null\n" + //  f   n       z
                        "x(1): null, z(0)\n" + //           
                        "z(0): null, null"     //
            }
        };
        return super.runTests(casos, indices, esperados, v);
    }
}
