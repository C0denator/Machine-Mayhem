package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;

public class Projektil extends SpielObjekt implements EinmalProFrame {

    private int schaden;
    private int speed;
    private boolean vonGegner;
    //wurde das Projektil von einem Gegner abgefeuert?
    public Projektil(float x, float y, TextureRegion textur, int schaden, int speed) {
        super(x, y, textur.getRegionWidth(), textur.getRegionHeight(), true);
        this.schaden=schaden;
    }

    @Override
    public void einmalProFrame(float delta) {

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
