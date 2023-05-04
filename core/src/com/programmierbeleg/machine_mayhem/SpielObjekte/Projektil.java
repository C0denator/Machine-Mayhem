package com.programmierbeleg.machine_mayhem.SpielObjekte;

public class Projektil extends SpielObjekt{

    private int schaden;
    private boolean vonGegner;
    //wurde das Projektil von einem Gegner abgefeuert?
    public Projektil(float x, float y, float breite, float höhe, int schaden) {
        super(x, y, breite, höhe, true, "Projektil");
        this.schaden=schaden;
    }

    public int getSchaden() {
        return schaden;
    }

    public void setSchaden(int schaden) {
        this.schaden = schaden;
    }

    public boolean isVonGegner() {
        return vonGegner;
    }
}
