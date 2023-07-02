package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

public class Boss extends Gegner implements EinmalProFrame {

    Animation animationLaufen;
    Animation animationSpezial;

    public Boss(float x, float y, Raum raum){
        super(x,y,raum);
        breite=32;
        höhe=32;
        textur= Spiel.instanz.atlas.findRegion("Boss_idle");
        leben=200;
        maxLeben=leben;

        animationLaufen=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("Boss_lauf",1),
                Spiel.instanz.atlas.findRegion("Boss_idle"),
                Spiel.instanz.atlas.findRegion("Boss_lauf",2),
        },0.2f,true);

        animationSpezial=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("Boss_spezial",1),
                Spiel.instanz.atlas.findRegion("Boss_spezial",2),
                Spiel.instanz.atlas.findRegion("Boss_spezial",3),
                Spiel.instanz.atlas.findRegion("Boss_idle"),
                Spiel.instanz.atlas.findRegion("Boss_spezial",4),
                Spiel.instanz.atlas.findRegion("Boss_spezial",5),
                Spiel.instanz.atlas.findRegion("Boss_spezial",6),
                Spiel.instanz.atlas.findRegion("Boss_idle"),
        },0.2f,true);

        if(SpielAnzeige.physikObjekte==null){
            System.err.println("Fehler: SpielAnzeige.physikObjekte ist null");
        }else{
            SpielAnzeige.physikObjekte.add(this);
        }

        angriffAktiv=false;
        angriffCooldown=5.0f;
        angriffTimer=angriffCooldown;

        SpielAnzeige.instanz.setBossAktiv(true);
    }

    @Override
    public void einmalProFrame(float delta) {
        if(angriffAktiv){
            angriff(delta);
        }else{
            denke(delta);
        }
    }

    @Override
    public void bekommeSchaden(int schaden) {
        super.bekommeSchaden(schaden);
        if(leben<=0){
            SpielAnzeige.instanz.setGewonnen(true);
            SpielAnzeige.instanz.setBossAktiv(false);
        }
    }

    @Override
    protected void angriff(float delta) {

    }

    @Override
    public void denke(float delta) {
        if(animationLaufen.isPausiert()){
            animationLaufen.starteVonVorn();
        }
        bewegenInRichtung(winkelZu(SpielAnzeige.spieler1),10.0f,delta);
    }
}
