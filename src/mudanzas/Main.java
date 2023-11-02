package src.mudanzas;

import lib.conjuntistas.ArbolAVL;

public class Main {
    public static void main(String[] args)
    {
        MudanzasCompartidas mc = new MudanzasCompartidas();
        mc.cargarArchivo("files/inicial.txt");
        mc.run();
    }
}
