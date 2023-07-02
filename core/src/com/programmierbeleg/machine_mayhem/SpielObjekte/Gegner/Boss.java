package com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Effekte.Explosion;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Projektile.Projektil;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Projektile.Rakete;
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
    float raketenCooldown;
    float raketenTimer;
    Sound intro;

    boolean schussLinks;
    private float schussSpeed = 125.0f;

    Random rnd = new Random();

    public Boss(float x, float y, Raum raum){
        super(x,y,raum);
        breite=32;
        höhe=32;
        textur= Spiel.instanz.atlas.findRegion("Boss_idle");
        leben=1000;
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

        raketenCooldown=0.5f;
        raketenTimer=0.0f;

        schussLinks=true;


        SpielAnzeige.instanz.setBossAktiv(true);
        SpielAnzeige.instanz.setBoss(this);
        intro.play(0.2f);
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
            raketenTimer-=delta;

            ////
            if(raketenTimer<=0){
                Vector2 pos = new Vector2(SpielAnzeige.spieler1.getX(),SpielAnzeige.spieler1.getY());

                if(schussLinks){
                    pos.x+= (rnd.nextFloat()-0.5f)*64;
                    pos.y+= (rnd.nextFloat()-0.5f)*64;
                    schussLinks=false;
                }else{
                    pos.x+= (rnd.nextFloat()-0.5f)*128;
                    pos.y+= (rnd.nextFloat()-0.5f)*128;
                    schussLinks=true;
                }

                SpielAnzeige.projektile.add(new Rakete(pos.x,pos.y,15,raum,true));
                raketenTimer=raketenCooldown;
            }
            ////

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
            angriffTimer=angriffCooldown;

            float tmpWinkel = winkelZu(SpielAnzeige.spieler1) + (float)(rnd.nextInt(46)-23);

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
            stirb();
        }
    }

    @Override
    public void stirb() {
        super.stirb();
        SpielAnzeige.instanz.setGewonnen(true);
    }
}
