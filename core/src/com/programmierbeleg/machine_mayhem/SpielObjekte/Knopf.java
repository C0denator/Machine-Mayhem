package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.programmierbeleg.machine_mayhem.Spiel;

public class Knopf extends SpielObjekt {

    private String schriftzug;
    private boolean wurdeGedrückt=false;
    private float linienbreite;
    private ShapeRenderer shaperenderer;
    private BitmapFont bitmapFont;
    private SpriteBatch batch;

    public Knopf(float x, float y, int breite, int höhe, String schriftzug){
        super(x,y,breite,höhe,true);
        this.schriftzug=schriftzug;
        this.x=x-breite* Spiel.instanz.skalierung/2;
        this.y=y-höhe* Spiel.instanz.skalierung/2;
        linienbreite=5.0f;

        shaperenderer = new ShapeRenderer();
        bitmapFont = new BitmapFont();
        batch =new SpriteBatch();
    }

    public void render(){
        shaperenderer.begin(ShapeRenderer.ShapeType.Filled);

        //prüft ob der Mauszeiger den Knopf berührt
        if(Gdx.input.getX() >= x && Gdx.input.getX() <= x+breite &&
                Gdx.graphics.getHeight()-Gdx.input.getY() >= y &&
                Gdx.graphics.getHeight()-Gdx.input.getY() <= y+höhe){

            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                wurdeGedrückt=true;
            }

            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                //Zeiger drückt Knopf durchgehend
                shaperenderer.setColor(0.3f,0.3f,0.3f,1.0f);
                zeichneRechteck();
                shaperenderer.setColor(0.4f,0.4f,0.4f,1.0f);
                zeichneRand();
            }else{
                if(wurdeGedrückt){
                    action();
                    wurdeGedrückt=false;
                }
                //Zeiger berührt Knopf; klickt aber nicht
                shaperenderer.setColor(0.6f,0.6f,0.6f,1.0f);
                zeichneRechteck();
                shaperenderer.setColor(0.4f,0.4f,0.4f,1.0f);
                zeichneRand();
            }

        }
        else{
            //Zeiger berührt nicht den Knopf
            wurdeGedrückt=false;
            shaperenderer.setColor(0.5f,0.5f,0.5f,1.0f);
            zeichneRechteck();
            shaperenderer.setColor(0.3f,0.3f,0.3f,1.0f);
            zeichneRand();
        }
        shaperenderer.end();

        batch.begin();
        bitmapFont.getData().setScale(2.0f);
        bitmapFont.draw(batch,schriftzug,x+10.0f,(y+höhe/2)+6*bitmapFont.getScaleY());
        batch.end();
    }

    private void zeichneRechteck(){
        shaperenderer.rect(x,y,breite,höhe);
    }

    private void zeichneRand(){
        shaperenderer.rectLine(x+linienbreite/2,y,x+linienbreite/2,y+höhe,linienbreite);
        shaperenderer.rectLine(x,y+höhe-linienbreite/2,x+breite,y+höhe-linienbreite/2,linienbreite);
        shaperenderer.rectLine(x+breite-linienbreite/2,y+höhe,x+breite-linienbreite/2,y,linienbreite);
        shaperenderer.rectLine(x+breite,y+linienbreite/2,x,y+linienbreite/2,linienbreite);
    }

    public void action(){
        //muss überschrieben werden
    }

    public void dispose(){
        shaperenderer.dispose();
        bitmapFont.dispose();
    }

}
