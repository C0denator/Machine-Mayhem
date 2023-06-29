package com.programmierbeleg.machine_mayhem.Interfaces;

public interface PhysikObjekte {
    /*
    Muss von allen Objekten implementiert werden, die vor dem Rendern noch Berechnungen jeglicher Art machen müssen
    z.B. für Bewegung, Input-Abfragen etc.
     */

    public void berechnePhysik(float delta);
    //wird einmal pro Frame aufgerufen (vor dem Rendern)

}
