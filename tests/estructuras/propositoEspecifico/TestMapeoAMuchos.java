package tests.estructuras.propositoEspecifico;

import estructuras.lineales.dinamicas.Lista;
import estructuras.propositoEspecifico.MapeoAMuchos;
import tests.Test;

public class TestMapeoAMuchos extends Test{

    @Override
    public boolean evaluarCaso(Object[] caso, Object[] indices, Object[] esperados, int casosI, boolean v)
    {
        boolean exito = false;
        MapeoAMuchos map = new MapeoAMuchos();
        int L = caso.length;
        map.asociar((Comparable)caso[0], caso[1]);
        map.asociar((Comparable)caso[0], caso[2]);
        Lista lista = new Lista();
        lista.insertar(caso[1], 1);
        lista.insertar(caso[2], 2);
        this._assertEquals(map.obtenerValor((Comparable)caso[0]).toString(), lista.toString(), "TestAsociar", v);
        return exito;
    }

    public boolean runTests(boolean v)
    {
        Comparable[][] casos = {
            {8300, "v1", "v2"},
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
