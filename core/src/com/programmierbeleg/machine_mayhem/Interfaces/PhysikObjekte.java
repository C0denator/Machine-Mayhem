package com.programmierbeleg.machine_mayhem.Interfaces;

public interface PhysikObjekte {
    /*
    Muss von allen Objekten implementiert werden, die Berechnungen jeglicher Art machen
    z.B. für Bewegung, Input-Abfragen etc.
     */

    public void berechnePhysik(float delta);
    //wird einmal pro Frame aufgerufen (!for dem rendern)

}
