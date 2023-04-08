package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class SpielAnzeige extends ScreenAdapter {


    SpriteBatch batch;
    ArrayList<Feld> felder;
    ArrayList<Spieler> spieler;
    ArrayList<Gegner> gegner;
    ArrayList<Projektil> projektile;


    public SpielAnzeige(){
        batch=new SpriteBatch();
        felder = new ArrayList<Feld>();
        spieler = new ArrayList<Spieler>();
        gegner = new ArrayList<Gegner>();
        projektile = new ArrayList<Projektil>();
    }

    @Override
    public void render(float delta) {
        update(delta);
        //ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClearColor(0.1f,0.1f,0.1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        ////////////////////////////////////////////////////////////

        for(int i=0; i<felder.size(); i++) felder.get(i).render(batch, delta);
        for(int i=0; i<spieler.size(); i++) spieler.get(i).render(batch, delta);
        for(int i=0; i<gegner.size(); i++) gegner.get(i).render(batch, delta);
        for(int i=0; i<projektile.size(); i++) projektile.get(i).render(batch, delta);

        ////////////////////////////////////////////////////////////
        batch.end();



        Spiel.instanz.renderDebug();
    }

    private void update(float delta){

    }
}
