package com.programmierbeleg.machine_mayhem.Interfaces;

public interface EinmalProFrame {
    /*
    Muss von allen Objekten implementiert werden, die vor dem Rendern noch Berechnungen jeglicher Art machen müssen
    z.B. für Bewegung, Input-Abfragen etc.
     */

    public void einmalProFrame(float delta);
    //wird einmal pro Frame aufgerufen (vor dem Rendern)

}
