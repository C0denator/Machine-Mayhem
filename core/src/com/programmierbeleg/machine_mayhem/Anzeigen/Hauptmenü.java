package com.programmierbeleg.machine_mayhem.Anzeigen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Knopf;

import java.util.ArrayList;

public class Hauptmenü extends ScreenAdapter {

    SpriteBatch batch;
    Texture img;
    BitmapFont bitmapFont;
    float tmpX =Gdx.graphics.getWidth()-700;
    float tmpY =Gdx.graphics.getHeight()-700;
    float velX =100.0f;
    float velY =100.0f;
    private ArrayList<Knopf> knöpfe;
    private TextureRegion testBild;


    public Hauptmenü(){
        batch = new SpriteBatch();
        //img = new Texture("badlogic.jpg");
        testBild= Spiel.instanz.atlas.findRegion("badlogic");
        knöpfe=new ArrayList<Knopf>();
        bitmapFont = new BitmapFont();

        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.6f,500,100,"Spielen"){
            @Override
            public void action(){
                Spiel.instanz.aktiverBildschirm=new SpielAnzeige();
                Spiel.instanz.aktiverBildschirm.show();
            }
        });
        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.5f,500,100,"Beenden"){
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
        batch.begin();
        ////////////////////////////////////////////////////////////
        batch.draw(testBild, tmpX,tmpY,testBild.getRegionWidth(),testBild.getRegionHeight());
        bitmapFont.getData().setScale(5.0f);
        bitmapFont.draw(batch,"Machine Mayhem", Gdx.graphics.getWidth()/2-275, Gdx.graphics.getHeight()*0.9f);

        for (int i=0; i<knöpfe.size(); i++){
            if(knöpfe.get(i)!=null){
                knöpfe.get(i).render();
            }
        }

        ////////////////////////////////////////////////////////////
        batch.end();

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
