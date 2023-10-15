package tests;

import tests.lineales.dinamicas.TestLista;
import tests.conjuntistas.TestArbolAVL;

public class Main {

    public static void main(String[] args)
    {
        TestLista testLista = new TestLista();
        TestArbolAVL testArbolAVL = new TestArbolAVL();
        boolean exito;
        boolean v = args.length>0 && args[0].equals("-v") ? true: false;

        exito = testLista.runTests(v);
        if(exito)
        {
            exito = testArbolAVL.runTests(v);
        }

        if(exito)
        {
            System.out.println("Todos los tests OK");
        }
    }
}
