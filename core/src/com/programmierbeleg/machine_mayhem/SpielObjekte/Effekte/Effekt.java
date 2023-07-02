package com.programmierbeleg.machine_mayhem.SpielObjekte.Effekte;

import com.programmierbeleg.machine_mayhem.SpielObjekte.SpielObjekt;

/**
 * Eine Klasse für grafische Effekte
 */
public abstract class Effekt extends SpielObjekt {

    public Effekt(float x, float y, int breite, int höhe){
        super (x, y, breite, höhe, 0, true);
    }

}
