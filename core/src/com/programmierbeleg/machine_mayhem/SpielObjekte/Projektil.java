package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Sonstiges.LöschKlasse;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Gegner;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;

public class Projektil extends SpielObjekt implements EinmalProFrame {

    private int schaden;
    private Vector2 bewegungsVektor;

    private Raum aktiverRaum;
    //Der Raum in dem sich das Projektil befindet (aus Performance-Gründen)
    private boolean vonGegner;
    //wurde das Projektil von einem Gegner abgefeuert?

    private Animation animation;
    public Projektil(float x, float y, float winkel,
                     TextureRegion textur, int schaden, Vector2 bewegungsVektor, Raum raum) {
        super(x, y, textur.getRegionWidth(), textur.getRegionHeight(), winkel, true);
        this.textur=textur;
        this.bewegungsVektor=bewegungsVektor;
        this.schaden=schaden;
        this.aktiverRaum=raum;

        animation=new Animation(this, new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("laser_gelb",1),
                Spiel.instanz.atlas.findRegion("laser_gelb",2),
        }, 0.1f,true);

        animation.starteVonVorn();

        if(SpielAnzeige.physikObjekte==null){
            System.err.println("Fehler: SpielAnzeige.physikObjekte ist null");
        }else{
            SpielAnzeige.physikObjekte.add(this);
        }
    }

    @Override
    public void einmalProFrame(float delta) {
        bewegen(bewegungsVektor, delta);

        for(Gegner g : SpielAnzeige.gegner){
            if(g.kollidiertMit(this)){
                g.bekommeSchaden(schaden);
                löschen();
            }
        }

        if(aktiverRaum.kollidiertMitWand(this)){
            löschen();
        }
    }

    public void löschen(){
        LöschKlasse.lösche(this, new ArrayList[]{SpielAnzeige.projektile, SpielAnzeige.physikObjekte});
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
