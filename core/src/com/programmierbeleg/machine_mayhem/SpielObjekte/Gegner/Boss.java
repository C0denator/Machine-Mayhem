package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Projektil;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

import java.util.Random;

public class Boss extends Gegner implements EinmalProFrame {

    Animation animationLaufen;
    Animation animationSpezial;
    boolean spezialAktiv;
    float spezialTimer;
    float spezialCooldown;
    float spezialDauer;
    float spezialDauerMAX;
    Sound intro;

    boolean schussLinks;
    private float schussSpeed = 100.0f;

    public Boss(float x, float y, Raum raum){
        super(x,y,raum);
        breite=32;
        höhe=32;
        textur= Spiel.instanz.atlas.findRegion("Boss_idle");
        leben=200;
        maxLeben=leben;

        intro=Gdx.audio.newSound(Gdx.files.internal("Sounds/EpischKurz.mp3"));

        animationLaufen=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("Boss_lauf",1),
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

        spezialAktiv=false;
        spezialCooldown=7.0f;
        spezialTimer=spezialCooldown;
        spezialDauer=5.0f;
        spezialDauerMAX=spezialDauer;

        angriffCooldown=0.3f;
        angriffTimer=angriffCooldown;

        schussLinks=true;


        SpielAnzeige.instanz.setBossAktiv(true);
        intro.play();
    }

    @Override
    public void einmalProFrame(float delta) {
        if(spezialAktiv){
            angriff(delta);
        }else{
            denke(delta);
        }
    }

    @Override
    protected void angriff(float delta) {
        if(animationSpezial.isPausiert()){
            animationSpezial.starteVonVorn();
        }

        if(spezialDauer>0){
            spezialDauer-=delta;
        }else{
            spezialDauer=spezialDauerMAX;
            spezialAktiv=false;
            animationSpezial.stop();
        }
    }

    @Override
    public void denke(float delta) {
        if(animationLaufen.isPausiert()){
            animationLaufen.starteVonVorn();
        }
        bewegenInRichtung(winkelZu(SpielAnzeige.spieler1),25.0f,delta);

        if(angriffTimer>0){
            angriffTimer-=delta;
        }else {
            Random rnd = new Random();
            angriffTimer=angriffCooldown;

            float tmpWinkel = winkelZu(SpielAnzeige.spieler1) + (float)(rnd.nextInt(91)-45);

            if(schussLinks){
                SpielAnzeige.projektile.add(new Projektil(x,y+16,tmpWinkel,Spiel.instanz.atlas.findRegion("laser_rot",1),5, new Vector2(
                        (float) (-Math.sin( (tmpWinkel/180) * Math.PI)) * schussSpeed,
                        (float) (Math.cos( (tmpWinkel/180) * Math.PI)) * schussSpeed),
                        raum, true));
                schussLinks=false;
            }else{
                SpielAnzeige.projektile.add(new Projektil(x+32,y+16,tmpWinkel,Spiel.instanz.atlas.findRegion("laser_rot",1),5, new Vector2(
                        (float) (-Math.sin( (tmpWinkel/180) * Math.PI)) * schussSpeed,
                        (float) (Math.cos( (tmpWinkel/180) * Math.PI)) * schussSpeed),
                        raum, true));
                schussLinks=true;
            }
        }

        if(spezialTimer>0){
            spezialTimer-=delta;
        }else{
            spezialTimer=spezialCooldown;
            spezialAktiv=true;
            animationLaufen.stop();
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

}
