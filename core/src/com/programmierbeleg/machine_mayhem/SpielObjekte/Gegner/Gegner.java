package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.SpielObjekt;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

import java.util.ArrayList;

public abstract class Gegner extends SpielObjekt {
    protected int leben;
    protected int maxLeben;
    protected int laufGeschwindigkeit;

    private Raum raum;

    private ArrayList<Gegner> globaleListe;
    //die ArrayList gegner in Spielanzeige
    private ArrayList<Gegner> lokaleListe;
    //die ArrayList aktiveGegner in Raum

    //Referenzen, um sich selber löschen zu können

    private float sekundenBisNächsterAngriff;

    public Gegner (float x, float y, Raum raum) {
        super (x, y,
                Spiel.instanz.atlas.findRegion("robot",1).getRegionWidth(),
                Spiel.instanz.atlas.findRegion("robot",1).getRegionHeight(),
                0, true);
        this.raum=raum;
        lokaleListe=raum.getAktiveGegner();
        globaleListe= SpielAnzeige.gegner;
    }

    public void bekommeSchaden(int schaden){
        leben-=schaden;
        if(leben<=0){
            stirb();
        }
    }

    public void stirb(){
        //alle Referenzen auf dieses Objekt werden auf null gesetzt
        int index=0;
        int debug=0;
        for(int i=0; i<lokaleListe.size(); i++){
            if(lokaleListe.get(i).equals(this)){
                lokaleListe.remove(i);
                debug++;
            }
        }
        for(int i=0; i<globaleListe.size(); i++){
            if(globaleListe.get(i).equals(this)){
                globaleListe.remove(i);
                debug++;
            }
        }

        if(debug!=2) System.err.print("Fehler beim Löschen des Gegners: "+toString()+" Debug: "+debug);
    }
    protected abstract boolean angriff();
    public abstract void denke();



}
