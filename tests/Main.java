package tests;

import tests.estructuras.lineales.dinamicas.TestLista;
import tests.estructuras.propositoEspecifico.TestDiccionario;
import tests.estructuras.propositoEspecifico.TestMapeoAMuchos;
import tests.estructuras.propositoEspecifico.TestMapeoAUno;

public class Main {

    public static void main(String[] args)
    {
        TestLista testLista = new TestLista();
        TestMapeoAUno testMapeoAUno = new TestMapeoAUno();
        TestMapeoAMuchos testMapeoAMuchos = new TestMapeoAMuchos();
        TestDiccionario  testDiccionario = new TestDiccionario();
        boolean exito;
        boolean v = args.length>0 && args[0].equals("-v") ? true: false;

        System.out.println("Tests Lista");
        exito = testLista.runTests(v);

        if(exito)
        {
            System.out.println("Tests MapeoAUno");
            exito = testMapeoAUno.runTests(v);
        }
        if(exito)
        {
            System.out.println("Tests Diccionario");
            exito = testDiccionario.runTests(v);
        }
        if(exito)
        {
            System.out.println("Tests MapeoAMuchos");
            exito = testMapeoAMuchos.runTests(v);
        }

        if(exito)
        {
            System.out.println("Todos los tests OK");
        }


    }
}
