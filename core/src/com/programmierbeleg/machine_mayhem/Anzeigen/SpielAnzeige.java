package com.programmierbeleg.machine_mayhem.Anzeigen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.LöschKlasse;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Gegner;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Knopf;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Projektil;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Spieler;
import com.programmierbeleg.machine_mayhem.Welt.Raum;
import com.programmierbeleg.machine_mayhem.Welt.Welt;

import java.util.ArrayList;

public class SpielAnzeige extends ScreenAdapter {


    private SpriteBatch batch;
    public static ArrayList<Raum> räume;
    public static ArrayList<Spieler> spieler;
    public static ArrayList<Gegner> gegner;
    public static ArrayList<Projektil> projektile;
    public static ArrayList<EinmalProFrame> physikObjekte;
    public static ArrayList<Knopf> knöpfe;
    private OrthographicCamera camera;
    private Viewport viewport;

    public static SpielAnzeige instanz;
    private boolean pausiert = false;

    private Sound musik;

    private static float zoomLevel;

    public SpielAnzeige(){
        if(instanz==null){
            instanz=this;
        }else{
            new IllegalStateException("Mehrere SpielAnzeige-Instanzen :(");
        }

        musik=Gdx.audio.newSound(Gdx.files.internal("Sounds/musik_1.mp3"));
        musik.loop(0.1f);

        batch=new SpriteBatch();
        camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        zoomLevel=1.0f;
        viewport=new ScreenViewport(camera);

        räume= new ArrayList<Raum>();
        spieler= new ArrayList<Spieler>();
        gegner=new ArrayList<Gegner>();
        projektile= new ArrayList<Projektil>();
        physikObjekte = new ArrayList<>();
        knöpfe = new ArrayList<Knopf>();
        /////////////////
        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.8f,200,30,"Fortfahren"){
            @Override
            public void action(){
                setPausiert(false);
            }
        });
        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.3f,200,30,"Zurück zum Hauptmenü"){
            @Override
            public void action(){
                musik.stop();
                spieler=null;
                räume=null;
                gegner=null;
                projektile=null;
                physikObjekte=null;
                Spiel.instanz.aktiverBildschirm=new Hauptmenü();
            }
        });
        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.2f,200,30,"Zurück zum Desktop"){
            @Override
            public void action(){
                Gdx.app.exit();
            }
        });
        //////////////

        Welt welt = new Welt(10);

    }




    @Override
    public void render(float delta) {
        camera.zoom = zoomLevel;

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if(pausiert) pausiert=false;
            else pausiert=true;
        }

        if(!pausiert){
            for(int i=0; i<physikObjekte.size(); i++){
                physikObjekte.get(i).einmalProFrame(delta);
            }
        }

        if(spieler.size()==1){
            camera.position.set(spieler.get(0).getX()+spieler.get(0).getBreite()/2,spieler.get(0).getY()+spieler.get(0).getHöhe()/2,0);
            camera.update();
        }else{
            System.err.println("FEHLER: spieler != 1");
            System.err.println("Spielergröße: "+spieler.size());
        }

        Gdx.gl.glClearColor(0.4f,0.4f,0.4f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.enableBlending();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        ////////////////////////////////////////////////////////////


        if(räume !=null) {
            for (int i = 0; i < räume.size(); i++) {
                if(räume.get(i).isSichtbar()){
                    for(int x=0; x<räume.get(i).getFelder().length; x++){
                        for(int y=0; y<räume.get(i).getFelder()[x].length; y++){
                            batch.draw(räume.get(i).getFelder()[x][y].getTextur(),
                                    räume.get(i).getFelder()[x][y].getX(),
                                    räume.get(i).getFelder()[x][y].getY(),
                                    räume.get(i).getFelder()[x][y].getBreite()/2,
                                    räume.get(i).getFelder()[x][y].getHöhe()/2,
                                    räume.get(i).getFelder()[x][y].getBreite(),
                                    räume.get(i).getFelder()[x][y].getHöhe(),
                                    1.0f,1.0f, räume.get(i).getFelder()[x][y].getWinkel());
                        }
                    }
                }
            }
        }
        if(projektile!=null) {
            for (int i = 0; i < projektile.size(); i++) {
                if(projektile.get(i).isSichtbar()) {
                    //batch.draw(spieler.get(i).getTexturen()[0], spieler.get(i).getX(), spieler.get(i).getY(), spieler.get(i).getBreite(), spieler.get(i).getHöhe());
                    batch.draw(projektile.get(i).getTextur(), projektile.get(i).getX(), projektile.get(i).getY(),
                            projektile.get(i).getBreite()/2, projektile.get(i).getHöhe()/2,
                            projektile.get(i).getBreite(), projektile.get(i).getHöhe(), 1.0f, 1.0f, projektile.get(i).getWinkel());

                }

            }
        }
        if(gegner!=null) {
            for (int i = 0; i < gegner.size(); i++) {
                if(gegner.get(i).isSichtbar()) {
                    batch.draw(gegner.get(i).getTextur(), gegner.get(i).getX(), gegner.get(i).getY(), gegner.get(i).getBreite(), gegner.get(i).getHöhe());
                }

            }
        }
        if(spieler!=null) {
            for (int i = 0; i < spieler.size(); i++) {
                if(spieler.get(i).isSichtbar()) {
                    //batch.draw(spieler.get(i).getTexturen()[0], spieler.get(i).getX(), spieler.get(i).getY(), spieler.get(i).getBreite(), spieler.get(i).getHöhe());
                    batch.draw(spieler.get(i).getTextur(), spieler.get(i).getX(), spieler.get(i).getY(),
                            spieler.get(i).getBreite()/2, spieler.get(i).getHöhe()/2,
                            spieler.get(i).getBreite(), spieler.get(i).getHöhe(), 1.0f, 1.0f, spieler.get(i).getWinkelInt());

                }

            }
        }

        ////////////////////////////////////////////////////////////
        batch.end();


        //Wird nur angezeigt, wenn Spiel pausiert ist:
        if(pausiert){
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            ShapeRenderer s = new ShapeRenderer();
            s.setColor(0,0,0,0.5f);
            s.setAutoShapeType(true);

            s.begin(ShapeRenderer.ShapeType.Filled);
            s.rect(0,0,
                    Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            s.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            for(int i=0; i< knöpfe.size(); i++){
                knöpfe.get(i).render();
            }
        }

        //
        LöschKlasse.löschZyklus();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
    }

    public void setPausiert(boolean pausiert) {
        this.pausiert = pausiert;
    }



    public static float getZoomLevel() {
        return zoomLevel;
    }

    public static void setZoomLevel(float zoom) {
        zoomLevel = zoom;
        if(zoomLevel<0.25f) {
            zoomLevel=0.25f;
        }else if(zoomLevel>1.5f){
            zoomLevel=1.5f;
        }
    }
}
