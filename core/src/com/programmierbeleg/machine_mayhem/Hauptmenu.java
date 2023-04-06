package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Hauptmenu extends ScreenAdapter {

    SpriteBatch batch;
    Texture img;
    float tmp;


    public Hauptmenu(){
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        tmp=0.0f;
    }

    @Override
    public void render(float delta) {
        update();
        //ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClearColor(0.2f,0.2f,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        ////////////////////////////////////////////////////////////
        batch.draw(img, tmp, tmp);
        ////////////////////////////////////////////////////////////
        batch.end();
    }

    private void update(){
        tmp+=100* Gdx.graphics.getDeltaTime();
    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
