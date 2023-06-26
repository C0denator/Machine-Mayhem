package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.FeldEigenschaft;
import com.programmierbeleg.machine_mayhem.Daten.FeldTextur;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

public class Tür extends Feld {

    Animation öffnen;
    Animation schließen;

    public Tür(float x, float y, Raum raum){
        super(FeldTextur.TürOffen, FeldEigenschaft.Tür,raum,x,y,true);

        öffnen=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("tür1_zu"),
                Spiel.instanz.atlas.findRegion("tür1",1),
                Spiel.instanz.atlas.findRegion("tür1",2),
                Spiel.instanz.atlas.findRegion("tür1",3),
                Spiel.instanz.atlas.findRegion("tür1",4),
                Spiel.instanz.atlas.findRegion("tür1_offen"),
        },0.1f,false);
        schließen=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("tür1_offen"),
                Spiel.instanz.atlas.findRegion("tür1",4),
                Spiel.instanz.atlas.findRegion("tür1",3),
                Spiel.instanz.atlas.findRegion("tür1",2),
                Spiel.instanz.atlas.findRegion("tür1",1),
                Spiel.instanz.atlas.findRegion("tür1_zu"),
        },0.1f,false);

        SpielAnzeige.physikObjekte.add(öffnen);
        SpielAnzeige.physikObjekte.add(schließen);
    }

    public void öffnen(){
        öffnen.starteVonVorn();
        setLaufbar(true);
    }

    public void schließen(){
        schließen.starteVonVorn();
        setLaufbar(false);
    }
}
