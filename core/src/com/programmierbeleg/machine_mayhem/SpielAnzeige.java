package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class SpielAnzeige extends ScreenAdapter {


    SpriteBatch batch;

    public SpielAnzeige(){
        batch=new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        update(delta);
        //ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClearColor(0.4f,0.9f,0.4f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        ////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////
        batch.end();

    }

    private void update(float delta){

    }
}
