package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import jdk.internal.org.jline.terminal.MouseEvent;

public class Knopf {

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
        //Gdx.input.getX() >= x && Gdx.input.getX() <= x+breite && Gdx.input.getY() >= y && Gdx.input.getY() <= y+höhe
        if(Gdx.input.getX() >= x && Gdx.input.getX() <= x+breite && Gdx.graphics.getHeight()-Gdx.input.getY() >= y && Gdx.graphics.getHeight()-Gdx.input.getY() <= y+höhe){
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                //Zeiger drückt Knopf durchgehend
                shaperenderer.setColor(0.3f,0.3f,0.3f,1.0f);
                shaperenderer.rect(x,y,breite,höhe);
                shaperenderer.setColor(0.5f,0.5f,0.5f,1.0f);
                shaperenderer.rectLine(x+linienbreite/2,y,x+linienbreite/2,y+höhe,linienbreite);
                shaperenderer.rectLine(x,y+höhe-linienbreite/2,x+breite,y+höhe-linienbreite/2,linienbreite);
                shaperenderer.rectLine(x+breite-linienbreite/2,y+höhe,x+breite-linienbreite/2,y,linienbreite);
                shaperenderer.rectLine(x+breite,y+linienbreite/2,x,y+linienbreite/2,linienbreite);
                wurdeGedrückt=true;
            }else{
                if(wurdeGedrückt){
                    action();
                }
                //Zeiger berührt Knopf; klickt aber nicht
                shaperenderer.setColor(0.5f,0.5f,0.5f,1.0f);
                shaperenderer.rect(x,y,breite,höhe);
                shaperenderer.setColor(0.4f,0.4f,0.4f,1.0f);
                shaperenderer.rectLine(x+linienbreite/2,y,x+linienbreite/2,y+höhe,linienbreite);
                shaperenderer.rectLine(x,y+höhe-linienbreite/2,x+breite,y+höhe-linienbreite/2,linienbreite);
                shaperenderer.rectLine(x+breite-linienbreite/2,y+höhe,x+breite-linienbreite/2,y,linienbreite);
                shaperenderer.rectLine(x+breite,y+linienbreite/2,x,y+linienbreite/2,linienbreite);
            }

        }
        else{
            //Zeiger berührt nicht den Knopf
            wurdeGedrückt=false;
            shaperenderer.setColor(0.5f,0.5f,0.5f,1.0f);
            shaperenderer.rect(x,y,breite,höhe);
            shaperenderer.setColor(0.3f,0.3f,0.3f,1.0f);
            shaperenderer.rectLine(x+linienbreite/2,y,x+linienbreite/2,y+höhe,linienbreite);
            shaperenderer.rectLine(x,y+höhe-linienbreite/2,x+breite,y+höhe-linienbreite/2,linienbreite);
            shaperenderer.rectLine(x+breite-linienbreite/2,y+höhe,x+breite-linienbreite/2,y,linienbreite);
            shaperenderer.rectLine(x+breite,y+linienbreite/2,x,y+linienbreite/2,linienbreite);
        }
        shaperenderer.end();

        batch.begin();
        bitmapFont.draw(batch,schriftzug,x+10.0f,(y+höhe/2)+5.0f);
        batch.end();
    }

    private void action(){
        System.out.println("ACTION!!!!111");
        wurdeGedrückt=false;
    }

    public void dispose(){
        shaperenderer.dispose();
        bitmapFont.dispose();
        batch.dispose();
    }

}
