package com.programmierbeleg.machine_mayhem.Angriffe;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Angriff {

    protected int dauer;
    //Zeit in Durchläufen, wie lange der Angriff aktiv ist
    protected int abklingzeit;
    //Zeit in Durchläufen, wie lange der Gegner bis zum nächsten aktivieren eines Angriffes wartet
    protected float wahrscheinlichkeit;
    //wie wahrscheinlich ein Angriff ist: 0.0f=unmöglich 1.0f=normal
    protected boolean aktiv;
    protected boolean bewegenErlaubt;
    protected TextureRegion[] texturen;

    public Angriff(int dauer, int abklingzeit, int wahrscheinlichkeit, TextureRegion[] texturen){
        this.dauer=dauer;
        this.abklingzeit=abklingzeit;
        this.wahrscheinlichkeit=wahrscheinlichkeit;
        this.texturen=texturen;
    }

    public int getDauer() {
        return dauer;
    }

    public void setDauer(int dauer) {
        this.dauer = dauer;
    }

    public int getAbklingzeit() {
        return abklingzeit;
    }

    public void setAbklingzeit(int abklingzeit) {
        this.abklingzeit = abklingzeit;
    }

    public float getWahrscheinlichkeit() {
        return wahrscheinlichkeit;
    }

    public void setWahrscheinlichkeit(float wahrscheinlichkeit) {
        this.wahrscheinlichkeit = wahrscheinlichkeit;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public boolean isBewegenErlaubt() {
        return bewegenErlaubt;
    }

    public void setBewegenErlaubt(boolean bewegenErlaubt) {
        this.bewegenErlaubt = bewegenErlaubt;
    }

    public TextureRegion[] getTexturen() {
        return texturen;
    }

    public void setTexturen(TextureRegion[] texturen) {
        this.texturen = texturen;
    }
}
