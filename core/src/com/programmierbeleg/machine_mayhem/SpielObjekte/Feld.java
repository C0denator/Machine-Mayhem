package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.programmierbeleg.machine_mayhem.Daten.FeldEigenschaft;
import com.programmierbeleg.machine_mayhem.Daten.FeldTextur;

public class Feld extends SpielObjekt{

    private boolean laufbar;
    private final FeldEigenschaft feldEigenschaft;
    private final FeldTextur feldTextur;
    private int winkel;
    public Feld(FeldTextur textur, FeldEigenschaft feldEigenschaft, float x, float y, boolean laufbar) {
        super(x, y, 16, 16,
                true, "Feld");
        this.feldEigenschaft = feldEigenschaft;
        this.feldTextur=textur;
        this.textur =textur.getTextur();
        this.laufbar=laufbar;
        winkel=0;
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

    public float getWinkel() {
        return winkel;
    }

    public void setWinkel(int winkel) {
        this.winkel = winkel;
    }

    public FeldTextur getFeldTextur() {
        return feldTextur;
    }
}
