package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Gegner;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

public class Projektil extends SpielObjekt implements EinmalProFrame {

    private int schaden;
    private Vector2 bewegungsVektor;

    private Raum aktiverRaum;
    //Der Raum in dem sich das Projektil befindet (aus Performance-Gründen)
    private boolean vonGegner;
    //wurde das Projektil von einem Gegner abgefeuert?
    public Projektil(float x, float y, float winkel,
                     TextureRegion textur, int schaden, Vector2 bewegungsVektor, Raum raum) {
        super(x, y, textur.getRegionWidth(), textur.getRegionHeight(), winkel, true);
        this.bewegungsVektor=bewegungsVektor;
        this.schaden=schaden;
        this.aktiverRaum=raum;
    }

    @Override
    public void einmalProFrame(float delta) {
        bewegen(bewegungsVektor, delta);

        for(Gegner g : aktiverRaum.getAktiveGegner()){
            if(g.kollidiertMit(this)){

            }
        }
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
