package tests;

public class Test {
    protected boolean _assertEquals(Object obtenido, Object esperado, String testName)
    {
        boolean exito;
        if(obtenido == esperado || (obtenido != null && obtenido.equals(esperado)))
        {
            System.out.println(testName + " OK");
            exito = true;
        }
        else
        {
            System.out.println(testName + " FAIL");
            System.out.println("\tEsperado:\n" + esperado);
            System.out.println("\tObtenido:\n" + obtenido);
            exito = false;
        }
        return exito;
    }
    public boolean evaluarCaso(Object[] caso, Object[] indices, Object[] esperados, int casoI)
    {
        return false;
    }

    public boolean runTests(Object[][] casos, Object[][] indices, Object[][] esperados)
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
                casosI
            );
            casosI+=1;
        }
        return exito;
    }
}
