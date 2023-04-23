package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Gegner extends SpielObjekt implements GrafikAusgabe {
    public Gegner(float x, float y, float breite, float höhe) {
        super(x, y, breite, höhe, true,"Gegner");
        this.setKlassenName("Gegner");
    }

    @Override
    public void render(float delta) {

    }

    public void update(float delta) {

    }
}
