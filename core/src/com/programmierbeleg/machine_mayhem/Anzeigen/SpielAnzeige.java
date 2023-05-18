package com.programmierbeleg.machine_mayhem.Anzeigen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.programmierbeleg.machine_mayhem.Daten.GegnerTyp;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Feld;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Fernkampf_1;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Gegner;
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
    private Viewport viewport;

    public SpielAnzeige(){
        batch=new SpriteBatch();
        camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewport=new ScreenViewport(camera);

        if(spieler == null) spieler= new ArrayList<Spieler>();
        if(gegner == null) gegner=new ArrayList<Gegner>();

        spieler.add(new Spieler(0.0f,0.0f));

        //gegner.add(new Gegner(GegnerTyp.FERNKAMPF_1,100.0f,100.0f));
        gegner.add(new Fernkampf_1(100.0f,100.0f));

        //gegner.add(new Gegner(GegnerTyp.FERNKAMPF_1,960.0f,540.0f));

    }

    @Override
    public void render(float delta) {
        physik(delta);

        if(spieler.size()==1){
            camera.position.set(spieler.get(0).getX()+spieler.get(0).getBreite()/2,spieler.get(0).getY()+spieler.get(0).getHöhe()/2,0);
            camera.update();
        }else{
            System.err.println("FEHLER: spieler != 1");
        }


        //ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClearColor(0.4f,0.4f,0.4f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        ////////////////////////////////////////////////////////////


        if(felder!=null) {
            for (int i = 0; i < felder.size(); i++) {

            }
        }
        if(spieler!=null) {
            for (int i = 0; i < spieler.size(); i++) {
                if(spieler.get(i).isSichtbar()) {
                    //batch.draw(spieler.get(i).getTexturen()[0], spieler.get(i).getX(), spieler.get(i).getY(), spieler.get(i).getBreite(), spieler.get(i).getHöhe());
                    batch.draw(spieler.get(i).getTexturen()[0], spieler.get(i).getX(), spieler.get(i).getY(),
                            spieler.get(i).getBreite()/2, spieler.get(i).getHöhe()/2,
                            spieler.get(i).getBreite(), spieler.get(i).getHöhe(), 1.0f, 1.0f, spieler.get(i).getWinkelInt());
                }

            }
        }
        if(gegner!=null) {
            for (int i = 0; i < gegner.size(); i++) {
                if(gegner.get(i).isSichtbar()) {
                    batch.draw(gegner.get(i).getTexturen()[0], gegner.get(i).getX(), gegner.get(i).getY(), gegner.get(i).getBreite(), gegner.get(i).getHöhe());
                }

            }
        }
        if(projektile!=null) {
            for (int i = 0; i < projektile.size(); i++) {

            }
        }

        ////////////////////////////////////////////////////////////
        batch.end();

    }

    private void physik(float delta){
        spieler.get(0).prüfeEingabe(delta);
        spieler.get(0).schauAufMauzeiger();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
    }
}
