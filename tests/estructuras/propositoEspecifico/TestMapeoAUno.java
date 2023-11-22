package tests.estructuras.propositoEspecifico;

import estructuras.propositoEspecifico.MapeoAUno;
import tests.Test;

public class TestMapeoAUno extends Test{

    @Override
    public boolean evaluarCaso(Object[] caso, Object[] indices, Object[] esperados, int casosI, boolean v)
    {
        boolean exito = false;
        MapeoAUno map = new MapeoAUno();
        int L = caso.length;
        map.asociar(caso[0], caso[1]);
        map.asociar(caso[2], caso[3]);
        this._assertEquals(map.obtenerValor(caso[0]), caso[1], "TestAsociar", v);
        this._assertEquals(map.obtenerValor(caso[2]), caso[3], "TestAsociar", v);
        return exito;
    }

    public boolean runTests(boolean v)
    {
        Comparable[][] casos = {
            {"DNI35678965", "n1", "PAS21923847", "n2"},
        };
        Comparable[][] indices = {
            {}
        };
        Object[][] esperados = {
            {}
        };
        return super.runTests(casos, indices, esperados, v);
    }

}
