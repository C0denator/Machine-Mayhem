package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.programmierbeleg.machine_mayhem.Daten.FeldEigenschaft;
import com.programmierbeleg.machine_mayhem.Daten.FeldTextur;

public class Feld extends SpielObjekt{

    private boolean laufbar;
    private final FeldEigenschaft feldEigenschaft;
    public Feld(FeldTextur textur, FeldEigenschaft feldEigenschaft, float x, float y, boolean laufbar) {
        super(x, y, 16, 16,
                true, "Feld");
        this.feldEigenschaft = feldEigenschaft;
        this.textur =textur.getTextur();
        this.laufbar=laufbar;
    }

    public boolean isLaufbar() {
        return laufbar;
    }

    public void setLaufbar(boolean laufbar) {
        this.laufbar = laufbar;
    }

    public FeldEigenschaft getFeldEigenschaft() {
        return feldEigenschaft;
    }

    public String toString(){
        String s="Feld: ";
        s+="X: ["+x+"]  Y: ["+y+"]";
        return s;
    }
}
