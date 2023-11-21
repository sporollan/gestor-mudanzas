package tests;

import tests.estructuras.conjuntistas.TestArbolAVL;
import tests.estructuras.conjuntistas.TestTablaHash;
import tests.estructuras.lineales.dinamicas.TestLista;

public class Main {

    public static void main(String[] args)
    {
        TestLista testLista = new TestLista();
        TestArbolAVL testArbolAVL = new TestArbolAVL();
        TestTablaHash testTablaHash = new TestTablaHash();
        boolean exito;
        boolean v = args.length>0 && args[0].equals("-v") ? true: false;

        System.out.println("Tests Lista");
        exito = testLista.runTests(v);
        if(exito)
        {
            System.out.println("Tests ArbolAVL");
            exito = testArbolAVL.runTests(v);
        }
        if(exito)
        {
            System.out.println("Tests TablaHash");
            exito = testTablaHash.runTests(v);
        }

        if(exito)
        {
            System.out.println("Todos los tests OK");
        }
    }
}
