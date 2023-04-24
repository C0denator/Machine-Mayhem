package com.programmierbeleg.machine_mayhem.Anzeigen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Spieler;

import java.util.ArrayList;

public class SpielAnzeige extends ScreenAdapter {


    private SpriteBatch batch;
    public Spieler spieler;

    public SpielAnzeige(){
        batch=new SpriteBatch();
        if(Spiel.instanz.spieler == null) Spiel.instanz.spieler= new ArrayList<Spieler>();
        if(Spiel.instanz.gegner == null) Spiel.instanz.gegner=new ArrayList<Gegner>();

        Spiel.instanz.spieler.add(new Spieler(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,
                Spiel.instanz.atlas.findRegion("SpielerTest").getRegionWidth(),
                Spiel.instanz.atlas.findRegion("SpielerTest").getRegionHeight()));

        Spiel.instanz.gegner.add(new Gegner(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4,
                Spiel.instanz.atlas.findRegion("robot",1).getRegionWidth(),
                Spiel.instanz.atlas.findRegion("robot",1).getRegionHeight()));

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
