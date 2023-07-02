package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.FeldEigenschaft;
import com.programmierbeleg.machine_mayhem.Daten.Texturen;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

public class Tür extends Feld implements EinmalProFrame {

    Animation öffnen;
    Animation schließen;
    private boolean bossTür;

    public Tür(float x, float y, Raum raum, boolean bossTür){
        super(Texturen.TürZu, FeldEigenschaft.Tür,raum,x,y,true);
        this.bossTür=bossTür;
        if(bossTür){
            textur=Spiel.instanz.atlas.findRegion("bossTür_verschlossen");

            öffnen=new Animation(this,new TextureRegion[]{
                    Spiel.instanz.atlas.findRegion("bossTür_zu"),
                    Spiel.instanz.atlas.findRegion("bossTür",1),
                    Spiel.instanz.atlas.findRegion("bossTür",2),
                    Spiel.instanz.atlas.findRegion("bossTür",3),
                    Spiel.instanz.atlas.findRegion("bossTür",4),
                    Spiel.instanz.atlas.findRegion("bossTür_offen"),
            },0.1f,false);
            schließen=new Animation(this,new TextureRegion[]{
                    Spiel.instanz.atlas.findRegion("bossTür_offen"),
                    Spiel.instanz.atlas.findRegion("bossTür",4),
                    Spiel.instanz.atlas.findRegion("bossTür",3),
                    Spiel.instanz.atlas.findRegion("bossTür",2),
                    Spiel.instanz.atlas.findRegion("bossTür",1),
                    Spiel.instanz.atlas.findRegion("bossTür_zu"),
            },0.1f,false);
        }else{
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
        }
    }

    @Override
    public void einmalProFrame(float delta) {
        if(bossTür && SpielAnzeige.spieler1.getAktuellerRaum().equals(raum)){
            if(SpielAnzeige.spieler1.getAnzahlSchlüssel()>=3 && SpielAnzeige.spieler1.distanzZu(this)<=50){
                if(!isLaufbar()){
                    öffnen();
                    SpielAnzeige.spieler1.setAnzahlSchlüssel(0);
                }
            }
        }
    }

    public void öffnen(){
        öffnen.starteVonVorn();
        setLaufbar(true);
    }

    public void schließen(){
        schließen.starteVonVorn();
        setLaufbar(false);
    }

    public void wandleInBossTürUm(){
        bossTür=true;
        textur=Spiel.instanz.atlas.findRegion("bossTür_verschlossen");
        öffnen=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("bossTür_zu"),
                Spiel.instanz.atlas.findRegion("bossTür",1),
                Spiel.instanz.atlas.findRegion("bossTür",2),
                Spiel.instanz.atlas.findRegion("bossTür",3),
                Spiel.instanz.atlas.findRegion("bossTür",4),
                Spiel.instanz.atlas.findRegion("bossTür_offen"),
        },0.1f,false);
        schließen=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("bossTür_offen"),
                Spiel.instanz.atlas.findRegion("bossTür",4),
                Spiel.instanz.atlas.findRegion("bossTür",3),
                Spiel.instanz.atlas.findRegion("bossTür",2),
                Spiel.instanz.atlas.findRegion("bossTür",1),
                Spiel.instanz.atlas.findRegion("bossTür_zu"),
        },0.1f,false);
        SpielAnzeige.physikObjekte.add(this);
    }

    public boolean isBossTür() {
        return bossTür;
    }

    public void setBossTür(boolean bossTür) {
        this.bossTür = bossTür;
    }
}
