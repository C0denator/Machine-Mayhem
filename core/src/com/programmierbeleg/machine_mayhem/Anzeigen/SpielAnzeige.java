package com.programmierbeleg.machine_mayhem.Anzeigen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.programmierbeleg.machine_mayhem.Daten.Texturen;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.LöschKlasse;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.*;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Effekte.Effekt;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Boss;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Gegner;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Projektile.Projektil;
import com.programmierbeleg.machine_mayhem.Welt.Raum;
import com.programmierbeleg.machine_mayhem.Welt.Welt;

import java.util.ArrayList;

public class SpielAnzeige extends ScreenAdapter {


    private SpriteBatch kameraBatch;
    private SpriteBatch bildschirmBatch;

    private ShapeRenderer shapeRenderer;
    public static ArrayList<Raum> räume;
    public static ArrayList<Gegner> gegner;
    public static ArrayList<Projektil> projektile;
    public static ArrayList<EinmalProFrame> physikObjekte;
    public static ArrayList<Knopf> knöpfe;
    public static ArrayList<Item> items;

    public static ArrayList<Effekt> effekte;

    public static Spieler spieler1;
    public static Spieler spieler2;
    private OrthographicCamera camera;
    private Viewport viewport;

    public static SpielAnzeige instanz;
    private boolean pausiert = false;

    private Sound musik;
    private boolean gameOver;
    private boolean gewonnen;
    private boolean bossAktiv;
    private Boss boss;
    private float gameOverAlpha;

    private float zoomLevel;

    public SpielAnzeige(){
        if(instanz==null){
            instanz=this;
        }else{
            new IllegalStateException("Mehrere SpielAnzeige-Instanzen :(");
        }

        musik=Gdx.audio.newSound(Gdx.files.internal("Sounds/musik_1.mp3"));
        musik.loop(0.1f);

        gameOver=false;
        gewonnen=false;

        kameraBatch =new SpriteBatch();
        bildschirmBatch= new SpriteBatch();
        shapeRenderer=new ShapeRenderer();
        camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        zoomLevel=0.5f;
        viewport=new ScreenViewport(camera);

        räume= new ArrayList<Raum>();
        gegner=new ArrayList<Gegner>();
        projektile= new ArrayList<Projektil>();
        physikObjekte = new ArrayList<>();
        knöpfe = new ArrayList<Knopf>();
        items = new ArrayList<Item>();
        effekte = new ArrayList<Effekt>();
        /////////////////
        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.8f,500,100,"Fortfahren"){
            @Override
            public void action(){
                setPausiert(false);
            }
        });
        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.3f,500,100,"Zurück zum Hauptmenü"){
            @Override
            public void action(){
                zumHauptmenü();
            }
        });
        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.2f,500,100,"Zurück zum Desktop"){
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

        if(!pausiert && !gameOver){
            for(int i=0; i<physikObjekte.size(); i++){
                physikObjekte.get(i).einmalProFrame(delta);
            }
        }

        if(spieler1!=null && spieler2==null){
            camera.position.set(spieler1.getX()+spieler1.getBreite()/2,spieler1.getY()+spieler1.getHöhe()/2,0);
            camera.update();
        }else{
            System.err.println("FEHLER: Ungültige Spieleranzahl");
        }

        Gdx.gl.glClearColor(0.4f,0.4f,0.4f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        kameraBatch.enableBlending();
        kameraBatch.setProjectionMatrix(camera.combined);
        kameraBatch.begin();
        ////////////////////////////////////////////////////////////


        if(räume !=null) {
            for (int i = 0; i < räume.size(); i++) {
                if(räume.get(i).isSichtbar()){
                    for(int x=0; x<räume.get(i).getFelder().length; x++){
                        for(int y=0; y<räume.get(i).getFelder()[x].length; y++){
                            kameraBatch.draw(räume.get(i).getFelder()[x][y].getTextur(),
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
                    kameraBatch.draw(projektile.get(i).getTextur(), projektile.get(i).getX(), projektile.get(i).getY(),
                            projektile.get(i).getBreite()/2, projektile.get(i).getHöhe()/2,
                            projektile.get(i).getBreite(), projektile.get(i).getHöhe(), 1.0f, 1.0f, projektile.get(i).getWinkel());

                }

            }
        }
        if(gegner!=null) {
            for (int i = 0; i < gegner.size(); i++) {
                if(gegner.get(i).isSichtbar()) {
                    kameraBatch.draw(gegner.get(i).getTextur(), gegner.get(i).getX(), gegner.get(i).getY(), gegner.get(i).getBreite(), gegner.get(i).getHöhe());
                    //zeichneKollisionen(gegner.get(i));
                }

            }
        }
        for(Item i : items){
            kameraBatch.draw(i.getTextur(),i.getX(),i.getY(),i.getBreite(),i.getHöhe());
        }
        if(spieler1!=null) {
            kameraBatch.draw(spieler1.getTextur(), spieler1.getX(), spieler1.getY(),
                    spieler1.getBreite()/2, spieler1.getHöhe()/2,
                    spieler1.getBreite(), spieler1.getHöhe(), 1.0f, 1.0f, spieler1.getWinkelInt());
        }
        if(spieler2!=null) {
            kameraBatch.draw(spieler2.getTextur(), spieler2.getX(), spieler2.getY(),
                    spieler2.getBreite()/2, spieler2.getHöhe()/2,
                    spieler2.getBreite(), spieler2.getHöhe(), 1.0f, 1.0f, spieler2.getWinkelInt());
        }

        for(Effekt e : effekte){
            kameraBatch.draw(e.getTextur(),e.getX(),e.getY(),e.getBreite(),e.getHöhe());
        }

        ////////////////////////////////////////////////////////////
        kameraBatch.end();


        ///Ausblenden bei GameOver
        if(gameOver){
            musik.stop();

            gameOverAlpha+=delta/4.0f;
            if(gameOverAlpha>1.0f)gameOverAlpha=1.0f;

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            ShapeRenderer s = new ShapeRenderer();
            s.setColor(0,0,0,gameOverAlpha);
            s.setAutoShapeType(true);

            s.begin(ShapeRenderer.ShapeType.Filled);
            s.rect(0,0,
                    Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            s.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            if(gameOverAlpha==1.0f){
                zumHauptmenü();
            }
        }
        ///

        zeichneUI();

        //Wird nur angezeigt, wenn Spiel pausiert ist:
        if(pausiert && !gameOver){
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

    private void zeichneUI(){
        if(!gameOver){
            //Lebensbalken
            float lBreite=500.0f;
            float lHöhe=50.0f;
            float lPosX=10.0f;
            float lPosY=Gdx.graphics.getHeight()-lHöhe-10;
            float lRand=5.0f;

            shapeRenderer=new ShapeRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.0f,0.0f,0.0f,1.0f);
            shapeRenderer.rect(lPosX,lPosY,lBreite,lHöhe);

            shapeRenderer.setColor(1.0f,0.0f,0.0f,1.0f);
            shapeRenderer.rect(lPosX+lRand,lPosY+lRand,lBreite-(lRand*2),lHöhe-(lRand*2));

            float breiteGrün= (lBreite-(lRand*2)) * ((float)spieler1.getLeben()/(float)spieler1.getMaxLeben());
            if(breiteGrün<0) breiteGrün=0;
            shapeRenderer.setColor(0.0f,1.0f,0.0f,1.0f);
            shapeRenderer.rect(lPosX+lRand,lPosY+lRand, breiteGrün,lHöhe-(lRand*2));

            shapeRenderer.end();

            //Boss-Lebensbalken
            if(bossAktiv){
                float bBreite=500.0f;
                float bHöhe=30.0f;
                float bPosX=(Gdx.graphics.getWidth()/2)-bBreite/2;
                float bPosY=10;
                float bRand=5.0f;

                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0.3f,0.3f,0.3f,1.0f);
                shapeRenderer.rect(bPosX,bPosY,bBreite,bHöhe);

                shapeRenderer.setColor(0.0f,0.0f,0.0f,1.0f);
                shapeRenderer.rect(bPosX+bRand,bPosY+bRand,bBreite-(bRand*2),bHöhe-(bRand*2));

                float breiteGelb= (bBreite-(bRand*2)) * ((float)boss.getLeben()/(float)boss.getMaxLeben());
                if(breiteGelb<0) breiteGelb=0;
                shapeRenderer.setColor(1.0f,1.0f,0.0f,1.0f);
                shapeRenderer.rect(bPosX+bRand,bPosY+bRand, breiteGelb,bHöhe-(bRand*2));

                shapeRenderer.end();

            }

            //Schlüssel zeichnen
            bildschirmBatch.begin();
            for(int i=0; i<spieler1.getAnzahlSchlüssel(); i++){
                bildschirmBatch.draw(Texturen.Schlüssel.getTexturRegion(), 25+(i*50), Gdx.graphics.getHeight()-125,
                        32, 32,
                        64, 64, 1.0f, 1.0f, 45);
            }
            bildschirmBatch.end();
        }
        ////
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
    }

    public void setPausiert(boolean pausiert) {
        this.pausiert = pausiert;
    }



    public float getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(float zoom) {
        zoomLevel = zoom;
        if(zoomLevel<0.1f) {
            zoomLevel=0.1f;
        }else if(zoomLevel>1.0f){
            zoomLevel=1.0f;
        }
    }

    public void gameOver(){
        //wenn der Spieler verloren hat
        gameOver=true;
        gameOverAlpha=0.0f;
    }

    private void zumHauptmenü(){
        musik.stop();
        spieler1=null;
        spieler2=null;
        räume=null;
        gegner=null;
        projektile=null;
        physikObjekte=null;
        SpielAnzeige.instanz=null;
        Spiel.instanz.aktiverBildschirm=new Hauptmenü();
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public void zeichneKollisionen(SpielObjekt objekt){
        //für Debug-Zwecke
        boolean batchBeendet=false;
        if(kameraBatch.isDrawing()){
            kameraBatch.end();
            batchBeendet=true;
        }
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1.0f,0.0f,0.0f,0.5f);
        shapeRenderer.rect(objekt.getX(),objekt.getY(),objekt.getBreite(),objekt.getHöhe());
        shapeRenderer.end();

        if(batchBeendet){
            kameraBatch.begin();
        }
    }

    public boolean isBossAktiv() {
        return bossAktiv;
    }

    public void setBossAktiv(boolean bossAktiv) {
        this.bossAktiv = bossAktiv;
    }

    public boolean isGewonnen() {
        return gewonnen;
    }

    public void setGewonnen(boolean gewonnen) {
        this.gewonnen = gewonnen;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }
}
