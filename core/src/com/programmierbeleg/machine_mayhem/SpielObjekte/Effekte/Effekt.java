package com.programmierbeleg.machine_mayhem.SpielObjekte.Effekte;

import com.programmierbeleg.machine_mayhem.SpielObjekte.SpielObjekt;

public abstract class Effekt extends SpielObjekt {

    public Effekt(float x, float y){
        super (x, y, 16, 16, 0, true);
    }

}
