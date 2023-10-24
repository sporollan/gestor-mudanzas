package tests;

public class Test {
    protected boolean _assertEquals(Object obtenido, Object esperado, String testName, boolean v)
    {
        boolean exito;
        if(obtenido == esperado || (obtenido != null && obtenido.equals(esperado)))
        {
            if(v)
            {
                System.out.println(testName + " OK");
                System.out.println("\tEsperado:\n" + esperado);
                System.out.println("\tObtenido:\n" + obtenido);
                System.out.println();
            }
            exito = true;
        }
        else
        {
            System.out.println(testName + " FAIL");
            System.out.println("\tEsperado:\n" + esperado);
            System.out.println("\tObtenido:\n" + obtenido);
            System.out.println();
            exito = false;
        }
        return exito;
    }
    public boolean evaluarCaso(Comparable<Object>[] caso, Comparable[] indices, Object[] esperados, int casoI, boolean v)
    {
        System.out.println("evaluarCaso");
        return false;
    }

    public boolean runTests(Comparable<Object>[][] casos, Comparable[][] indices, Object[][] esperados, boolean v)
    {
        boolean exito = true;

        int casosL = casos.length;
        int casosI = 0;
        while(exito && casosI < casosL)
        {
            exito = this.evaluarCaso(
                casos[casosI],
                indices[casosI],
                esperados[casosI],
                casosI,
                v
            );
            casosI+=1;
        }
        System.out.println("Casos evaluados: " + casosI + "/" + casosL);
        return exito;
    }
}
