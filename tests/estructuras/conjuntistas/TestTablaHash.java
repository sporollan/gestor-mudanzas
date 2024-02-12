package tests.estructuras.conjuntistas;

import estructuras.conjuntistas.ArbolAVL;
import estructuras.conjuntistas.TablaHash;
import mudanzas.datos.Solicitud;
import tests.Test;

public class TestTablaHash extends Test {
    public boolean evaluarCaso(Object[] caso, Object[] indices, Object[] esperados, int casosI, boolean v)
    {
        boolean exito = false;
        TablaHash t = new TablaHash();
        int L = caso.length;

        for(int i = 0; i <L; i++)
        {
            t.insertar(caso[i]);
        }
        exito = this._assertEquals(t.toString(), esperados[0], "TestTablaHash"+casosI, v);
        if(exito)
        {
            //a.eliminar(indices[0]);
            //exito = this._assertEquals(a.toString(), esperados[1], "TestAVLEliminar"+casosI, v);
        }
        return exito;
    }
    public boolean runTests(boolean v)
    {
        Object[][] casos = {
            {1, 2, 3, 4, 5},
        };
        Comparable[][] indices = {
            {},
            {}
        };
        Object[][] esperados = {
            {
                "1 2 3 4 5 ",
            },
            {
                "S1 "
            }

        };
        return super.runTests(casos, indices, esperados, v);
    }
}
