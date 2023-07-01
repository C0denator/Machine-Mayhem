package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.LöschKlasse;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.SpielObjekt;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

import java.util.ArrayList;

public abstract class Gegner extends SpielObjekt {
    protected int leben;
    protected int maxLeben;
    protected int laufGeschwindigkeit;

    protected Raum raum;

    protected float angriffCooldown;
    protected float angriffTimer;

    protected boolean angriffAktiv;


    public Gegner (float x, float y, Raum raum) {
        super (x, y,
                Spiel.instanz.atlas.findRegion("robot",1).getRegionWidth(),
                Spiel.instanz.atlas.findRegion("robot",1).getRegionHeight(),
                0, true);
        this.raum=raum;

    }

    public void bekommeSchaden(int schaden){
        leben-=schaden;
        if(leben<=0){
            stirb();
        }
    }

    public void stirb(){
        //alle Referenzen auf dieses Objekt werden auf null gesetzt
        LöschKlasse.lösche(this);
        raum.setGegnerAnzahl(raum.getGegnerAnzahl()-1);
    }
    protected abstract void angriff(float delta);
    public abstract void denke(float delta);



}
