package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.programmierbeleg.machine_mayhem.Daten.FeldEigenschaft;
import com.programmierbeleg.machine_mayhem.Daten.Texturen;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

/**
 * Ein einzelnes Feld. Kann entweder laufbar sein, oder zu Kollisionen führen.
 */
public class Feld extends SpielObjekt{

    private boolean laufbar;
    private final FeldEigenschaft feldEigenschaft;
    private final Texturen feldTextur;

    protected final Raum raum;
    private int winkel;
    public Feld(Texturen textur, FeldEigenschaft feldEigenschaft, Raum raum,
                float x, float y, boolean laufbar) {
        super(x, y, 16, 16, 0,
                true);
        this.feldEigenschaft = feldEigenschaft;
        this.feldTextur=textur;
        this.raum=raum;
        this.textur =textur.getTexturRegion();
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

    public Texturen getFeldTextur() {
        return feldTextur;
    }

    public Raum getRaum() {
        return raum;
    }
}
