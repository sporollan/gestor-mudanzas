package tests;

import tests.lineales.dinamicas.TestLista;
import tests.conjuntistas.TestArbolAVL;

public class Main {

    public static void main(String[] args)
    {
        TestLista testLista = new TestLista();
        TestArbolAVL testArbolAVL = new TestArbolAVL();
        boolean exito;

        exito = testLista.runTests();
        if(exito)
        {
            exito = testArbolAVL.runTests();
        }

        if(exito)
        {
            System.out.println("Todos los tests OK");
        }
    }
}
