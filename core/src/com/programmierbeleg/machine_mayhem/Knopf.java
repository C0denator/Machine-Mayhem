package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import jdk.internal.org.jline.terminal.MouseEvent;

public abstract class Knopf {

    ShapeRenderer shaperenderer = new ShapeRenderer();
    BitmapFont bitmapFont = new BitmapFont();
    SpriteBatch batch=new SpriteBatch();
    private float x;
    private float y;
    private float breite;
    private float höhe;
    private String schriftzug;
    private boolean wurdeGedrückt=false;
    private float linienbreite;

    public Knopf(float x, float y, float breite, float höhe, String schriftzug){
        this.x=x-breite/2;
        this.y=y-höhe/2;
        this.breite=breite;
        this.höhe=höhe;
        this.schriftzug=schriftzug;
        linienbreite=5.0f;
    }

    public void render(){
        shaperenderer.begin(ShapeRenderer.ShapeType.Filled);

        if(Gdx.input.getX() >= x && Gdx.input.getX() <= x+breite && Gdx.graphics.getHeight()-Gdx.input.getY() >= y && Gdx.graphics.getHeight()-Gdx.input.getY() <= y+höhe){

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

    }

    public void dispose(){
        shaperenderer.dispose();
        bitmapFont.dispose();
        batch.dispose();
    }

}
