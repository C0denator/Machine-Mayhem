package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.FeldEigenschaft;
import com.programmierbeleg.machine_mayhem.Daten.FeldTextur;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

public class Tür extends Feld {

    Animation öffnen;
    Animation schließen;

    public Tür(float x, float y, Raum raum){
        super(FeldTextur.TürOffen, FeldEigenschaft.Tür,raum,x,y,true);

        öffnen=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("tür2_zu"),
                Spiel.instanz.atlas.findRegion("tür2_mitte"),
                Spiel.instanz.atlas.findRegion("tür2_offen"),
        },0.25f,false);
        schließen=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("tür2_offen"),
                Spiel.instanz.atlas.findRegion("tür2_mitte"),
                Spiel.instanz.atlas.findRegion("tür2_zu"),
        },0.25f,false);

        SpielAnzeige.physikObjekte.add(öffnen);
        SpielAnzeige.physikObjekte.add(schließen);
    }

    public void öffne(){
        öffnen.starteVonVorn();
        setLaufbar(true);
    }

    public void schließen(){
        schließen.starteVonVorn();
        setLaufbar(false);
    }
}
