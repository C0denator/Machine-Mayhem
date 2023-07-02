package com.programmierbeleg.machine_mayhem.SpielObjekte.Effekte;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Sonstiges.LöschKlasse;
import com.programmierbeleg.machine_mayhem.Spiel;

public class Explosion extends Effekt implements EinmalProFrame {

    private int schaden;
    //-1 : kein Schaden
    private boolean schadenAusgeteilt;
    private Animation explosion;

    public Explosion(float x, float y, int breite, int höhe, int schaden){
        super(x,y, breite, höhe);
        this.schaden=schaden;
        schadenAusgeteilt=false;
        textur=Spiel.instanz.atlas.findRegion("Explosion",1);
        explosion=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("Explosion",1),
                Spiel.instanz.atlas.findRegion("Explosion",3),
                Spiel.instanz.atlas.findRegion("Explosion",3),
                Spiel.instanz.atlas.findRegion("Explosion",4),
                Spiel.instanz.atlas.findRegion("Explosion",5),
                Spiel.instanz.atlas.findRegion("Explosion",6),
        },0.1f,false);

        if(SpielAnzeige.physikObjekte==null){
            System.err.println("Fehler: SpielAnzeige.physikObjekte ist null");
        }else{
            SpielAnzeige.physikObjekte.add(this);
        }

        explosion.starteVonVorn();
    }

    @Override
    public void einmalProFrame(float delta) {
        if(explosion.isFertig()){
            LöschKlasse.lösche(this);
        }else{
            if(!schadenAusgeteilt){
                if(kollidiertMit(SpielAnzeige.spieler1)){
                    schadenAusgeteilt=true;
                    SpielAnzeige.spieler1.bekommeSchaden(schaden);
                }
            }
        }
    }
}
