package com.programmierbeleg.machine_mayhem.SpielObjekte.Projektile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Sonstiges.LöschKlasse;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Effekte.Explosion;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

public class Rakete extends Projektil implements EinmalProFrame {

    public Rakete(float x, float y, int schaden, Raum raum, boolean vonGegner) {
        super(x, y,0, Spiel.instanz.atlas.findRegion("Rakete",1), schaden, new Vector2(), raum, vonGegner);

        animation=new Animation(this,new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("Rakete",1),
                Spiel.instanz.atlas.findRegion("Rakete",2),
                Spiel.instanz.atlas.findRegion("Rakete",3),
                Spiel.instanz.atlas.findRegion("Rakete",4),
                Spiel.instanz.atlas.findRegion("Rakete",5),
                Spiel.instanz.atlas.findRegion("Rakete",6),
                Spiel.instanz.atlas.findRegion("Rakete",7),
        },0.1f,false);

        animation.starteVonVorn();

    }

    @Override
    public void einmalProFrame(float delta) {
        if(animation.isFertig()){
            SpielAnzeige.effekte.add(new Explosion(x,y,32,32,15));
            LöschKlasse.lösche(this);
        }
    }
}
