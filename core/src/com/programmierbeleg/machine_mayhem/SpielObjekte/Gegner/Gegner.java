package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.LöschKlasse;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.SpielObjekt;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

import java.util.ArrayList;

public abstract class Gegner extends SpielObjekt implements EinmalProFrame {
    protected int leben;
    protected int maxLeben;
    protected int laufGeschwindigkeit;

    private Raum raum;

    private float sekundenBisNächsterAngriff;

    public Gegner (float x, float y, Raum raum) {
        super (x, y,
                Spiel.instanz.atlas.findRegion("robot",1).getRegionWidth(),
                Spiel.instanz.atlas.findRegion("robot",1).getRegionHeight(),
                0, true);
        this.raum=raum;

        if(SpielAnzeige.physikObjekte==null){
            System.err.println("Fehler: SpielAnzeige.physikObjekte ist null");
        }else{
            SpielAnzeige.physikObjekte.add(this);
        }
    }

    @Override
    public void einmalProFrame(float delta) {

    }

    public void bekommeSchaden(int schaden){
        leben-=schaden;
        if(leben<=0){
            stirb();
        }
    }

    public void stirb(){
        //alle Referenzen auf dieses Objekt werden auf null gesetzt
        LöschKlasse.lösche(this,new ArrayList[]{SpielAnzeige.gegner, SpielAnzeige.physikObjekte});
    }
    protected abstract boolean angriff();
    public abstract void denke();



}
