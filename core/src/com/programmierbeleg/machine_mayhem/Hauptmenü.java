package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Hauptmenü extends ScreenAdapter {

    SpriteBatch batch;
    Texture img;
    float tmpX =500.0f;
    float tmpY =0.0f;
    float velX =100.0f;
    float velY =100.0f;
    private ArrayList<Knopf> knöpfe;


    public Hauptmenü(){
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        knöpfe=new ArrayList<Knopf>();

        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.8f,600,100,"Spielen"){
            @Override
            public void action(){
                System.out.println("ACTION!!!!111");
            }
        });
        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.7f,600,100,"Optionen"){
            @Override
            public void action(){
                System.out.println("Optionen!!!!11");
            }
        });
        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.6f,600,100,"Beenden"){
            @Override
            public void action(){
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void render(float delta) {
        update();
        //ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClearColor(0.2f,0.2f,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        ////////////////////////////////////////////////////////////
        batch.draw(img, tmpX, tmpY);
        ////////////////////////////////////////////////////////////
        batch.end();

        for (int i=0; i<knöpfe.size(); i++){
            if(knöpfe.get(i)!=null){
                knöpfe.get(i).render();
            }
        }

        Spiel.instanz.renderDebug();
    }

    private void update(){
        tmpX+=velX* Gdx.graphics.getDeltaTime();
        tmpY+=velY* Gdx.graphics.getDeltaTime();
        if(tmpX>=Gdx.graphics.getWidth()-256.0f){
            tmpX=Gdx.graphics.getWidth()-256.0f;
            velX*=-1;
        }
        if(tmpX<=0){
            tmpX=0;
            velX*=-1;
        }
        if(tmpY>=Gdx.graphics.getHeight()-256.0f){
            tmpY=Gdx.graphics.getHeight()-256.0f;
            velY*=-1;
        }
        if(tmpY<=0){
            tmpY=0;
            velY*=-1;
        }
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
