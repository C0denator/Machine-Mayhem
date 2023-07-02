package com.programmierbeleg.machine_mayhem.Interfaces;

/**
 * Dieses Interface muss von allen Objekten implementiert werden,
 * welche Methoden beinhalten, die 1x pro Frame aufgerufen werden müssen.
 * Objekte, die dieses Interface implementieren, müssen der ArrayList "physikObjekte" in SpielAnzeige hinzugefügt werden.
 */
public interface EinmalProFrame {
    /**
     * Wird ein mal pro Frame aufgrufen.
     */
    public void einmalProFrame(float delta);

}
