package com.programmierbeleg.machine_mayhem.Welt;

import com.badlogic.gdx.math.Vector2;

public class Raum {
    private Raum RaumNord;
    private Raum RaumSüd;
    private Raum RaumWest;
    private Raum RaumOst;

    private int start_x;
    private int start_y;


    private int[][] felder;
    private int gegnerAnzahl;
    private boolean sichtbar;
    private boolean bossRaum;
    private boolean kampfAktiv;

    public void raumBetreten(){
        sichtbar=true;
        if(gegnerAnzahl>0){
            kampfAktiv=true;
        }
    }

    public Raum getRaumNord() {
        return RaumNord;
    }

    public void setRaumNord(Raum raumNord) {
        RaumNord = raumNord;
    }

    public Raum getRaumSüd() {
        return RaumSüd;
    }

    public void setRaumSüd(Raum raumSüd) {
        RaumSüd = raumSüd;
    }

    public Raum getRaumWest() {
        return RaumWest;
    }

    public void setRaumWest(Raum raumWest) {
        RaumWest = raumWest;
    }

    public Raum getRaumOst() {
        return RaumOst;
    }

    public void setRaumOst(Raum raumOst) {
        RaumOst = raumOst;
    }

    public int getStart_x() {
        return start_x;
    }

    public void setStart_x(int start_x) {
        this.start_x = start_x;
    }

    public int getStart_y() {
        return start_y;
    }

    public void setStart_y(int start_y) {
        this.start_y = start_y;
    }


    public int[][] getFelder() {
        return felder;
    }

    public void setFelder(int[][] felder) {
        this.felder = felder;
    }

    public int getGegnerAnzahl() {
        return gegnerAnzahl;
    }

    public void setGegnerAnzahl(int gegnerAnzahl) {
        this.gegnerAnzahl = gegnerAnzahl;
    }

    public boolean isSichtbar() {
        return sichtbar;
    }

    public void setSichtbar(boolean sichtbar) {
        this.sichtbar = sichtbar;
    }

    public boolean isBossRaum() {
        return bossRaum;
    }

    public void setBossRaum(boolean bossRaum) {
        this.bossRaum = bossRaum;
    }

    public boolean isKampfAktiv() {
        return kampfAktiv;
    }

    public void setKampfAktiv(boolean kampfAktiv) {
        this.kampfAktiv = kampfAktiv;
    }
}
