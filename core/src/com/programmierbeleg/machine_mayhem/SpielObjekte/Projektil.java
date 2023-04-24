package com.programmierbeleg.machine_mayhem.SpielObjekte;

public class Projektil extends SpielObjekt{

    private int schaden;
    public Projektil(float x, float y, float breite, float höhe, int schaden) {
        super(x, y, breite, höhe, true, "Projektil");
        this.schaden=schaden;
    }
}
