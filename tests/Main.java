package tests;

import tests.estructuras.conjuntistas.TestArbolAVL;
import tests.estructuras.conjuntistas.TestTablaHash;
import tests.estructuras.lineales.dinamicas.TestLista;
import tests.estructuras.propositoEspecifico.TestMapeoAUno;

public class Main {

    public static void main(String[] args)
    {
        TestLista testLista = new TestLista();
        TestArbolAVL testArbolAVL = new TestArbolAVL();
        TestTablaHash testTablaHash = new TestTablaHash();
        TestMapeoAUno testMapeoAUno = new TestMapeoAUno();
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
            System.out.println("Tests MapeoAUno");
            exito = testMapeoAUno.runTests(v);
        }

        if(exito)
        {
            System.out.println("Todos los tests OK");
        }
    }
}
