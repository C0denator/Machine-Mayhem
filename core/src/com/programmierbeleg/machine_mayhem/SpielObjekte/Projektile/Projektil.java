package com.programmierbeleg.machine_mayhem.SpielObjekte.Projektile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Sonstiges.LöschKlasse;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Gegner;
import com.programmierbeleg.machine_mayhem.SpielObjekte.SpielObjekt;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Random;

public class Projektil extends SpielObjekt implements EinmalProFrame {

    protected int schaden;
    protected Vector2 bewegungsVektor;

    protected Raum aktiverRaum;
    //Der Raum in dem sich das Projektil befindet (aus Performance-Gründen)
    protected boolean vonGegner;
    //wurde das Projektil von einem Gegner abgefeuert?

    protected Animation animation;
    public Projektil(float x, float y, float winkel,
                     TextureRegion textur, int schaden, Vector2 bewegungsVektor, Raum raum, boolean vonGegner) {
        super(x, y, textur.getRegionWidth(), textur.getRegionHeight(), winkel, true);
        this.textur=textur;
        this.bewegungsVektor=bewegungsVektor;
        this.schaden=schaden;
        this.aktiverRaum=raum;
        this.vonGegner=vonGegner;

        if(!(this instanceof Rakete)){
            if(!vonGegner){
                animation=new Animation(this, new TextureRegion[]{
                        Spiel.instanz.atlas.findRegion("laser_gelb",1),
                        Spiel.instanz.atlas.findRegion("laser_gelb",2),
                }, 0.1f,true);
            }else {
                animation=new Animation(this, new TextureRegion[]{
                        Spiel.instanz.atlas.findRegion("laser_rot",1),
                        Spiel.instanz.atlas.findRegion("laser_rot",2),
                }, 0.1f,true);
            }
            animation.starteVonVorn();
        }

        if(SpielAnzeige.physikObjekte==null){
            System.err.println("Fehler: SpielAnzeige.physikObjekte ist null");
        }else{
            SpielAnzeige.physikObjekte.add(this);
        }
    }

    @Override
    public void einmalProFrame(float delta) {
        bewegen(bewegungsVektor, delta);

        if(!vonGegner){
            for(Gegner g : SpielAnzeige.gegner){
                if(g.kollidiertMit(this)){
                    g.bekommeSchaden(schaden);
                    löschen();
                }
            }
        }else{
            if(SpielAnzeige.spieler1.kollidiertMit(this)){
                SpielAnzeige.spieler1.bekommeSchaden(schaden);
                löschen();
            }
        }


        if(aktiverRaum.kollidiertMitWand(this)){
            löschen();
        }
    }

    public void löschen(){
        LöschKlasse.lösche(this);
    }

    public int getSchaden() {
        return schaden;
    }

    public void setSchaden(int schaden) {
        this.schaden = schaden;
    }

    public boolean isVonGegner() {
        return vonGegner;
    }
}
