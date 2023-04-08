package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Alle Spielobjekte die gerendert werden müssen
public abstract class SpielObjekt {

    float x;
    float y;
    float breite;
    float höhe;
    Sprite sprite;

    public void render(SpriteBatch batch, float delta){
        update(delta);
    }

    private void update(float delta){

    }

}
