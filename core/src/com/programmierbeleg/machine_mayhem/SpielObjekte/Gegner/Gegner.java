package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Sonstiges.LöschKlasse;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.SpielObjekt;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

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
                16,
                16,
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
        raum.setPositionLetzterGegner(new Vector2(x,y));
    }
    protected abstract void angriff(float delta);
    public abstract void denke(float delta);



}
