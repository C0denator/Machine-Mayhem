package com.programmierbeleg.machine_mayhem.Anzeigen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Feld;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Projektil;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Spieler;

import java.util.ArrayList;

public class SpielAnzeige extends ScreenAdapter {


    private SpriteBatch batch;
    private ArrayList<Feld> felder;
    private ArrayList<Spieler> spieler;
    private ArrayList<Gegner> gegner;
    private ArrayList<Projektil> projektile;
    private OrthographicCamera camera;

    public SpielAnzeige(){
        batch=new SpriteBatch();
        camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        if(spieler == null) spieler= new ArrayList<Spieler>();
        if(gegner == null) gegner=new ArrayList<Gegner>();

        spieler.add(new Spieler(0.0f,0.0f,
                Spiel.instanz.atlas.findRegion("SpielerTest").getRegionWidth(),
                Spiel.instanz.atlas.findRegion("SpielerTest").getRegionHeight()));

        gegner.add(new Gegner(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10,
                Spiel.instanz.atlas.findRegion("robot",1).getRegionWidth(),
                Spiel.instanz.atlas.findRegion("robot",1).getRegionHeight()));

    }

    @Override
    public void render(float delta) {
        physik(delta);

        //ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClearColor(0.4f,0.9f,0.4f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        ////////////////////////////////////////////////////////////

        if(spieler.size()==1){
            camera.position.set(spieler.get(0).getX(),spieler.get(0).getY(),0);
        }else{
            System.err.println("FEHLER: spieler != 1");
        }

        if(felder!=null) {
            for (int i = 0; i < felder.size(); i++) ;
        }
        if(spieler!=null) {
            for (int i = 0; i < spieler.size(); i++) {
                if(spieler.get(i).isSichtbar()) batch.draw(spieler.get(i).getRegions()[0], spieler.get(i).getX(), spieler.get(i).getY(), spieler.get(i).getBreite(), spieler.get(i).getHöhe());

            }
        }
        if(gegner!=null) {
            for (int i = 0; i < gegner.size(); i++) {
                if(gegner.get(i).isSichtbar()) batch.draw(gegner.get(i).getRegions()[0], gegner.get(i).getX(), gegner.get(i).getY(), gegner.get(i).getBreite(), gegner.get(i).getHöhe());

            }
        }
        if(projektile!=null) {
            for (int i = 0; i < projektile.size(); i++) ;
        }

        camera.update();
        ////////////////////////////////////////////////////////////
        batch.end();

    }

    private void physik(float delta){
        if(spieler.size()==1){
           spieler.get(0).prüfeEingabe(delta);
        }
    }
}
