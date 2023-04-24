package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Knopf extends SpielObjekt {

    ShapeRenderer shaperenderer = new ShapeRenderer();
    BitmapFont bitmapFont = new BitmapFont();
    private String schriftzug;
    private boolean wurdeGedrückt=false;
    private float linienbreite;

    private SpriteBatch testBatch;

    public Knopf(float x, float y, float breite, float höhe, String schriftzug){
        super(x,y,breite,höhe,true,"Knopf");
        this.setKlassenName("Knopf");
        this.schriftzug=schriftzug;
        linienbreite=5.0f;

        testBatch=new SpriteBatch();
    }

    public void render(float delta){
        shaperenderer.begin(ShapeRenderer.ShapeType.Filled);

        if(Gdx.input.getX() >= super.getX() && Gdx.input.getX() <= super.getX()+super.getBreite() &&
                Gdx.graphics.getHeight()-Gdx.input.getY() >= super.getY() &&
                Gdx.graphics.getHeight()-Gdx.input.getY() <= super.getY()+super.getHöhe()){

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

        testBatch.begin();
        bitmapFont.getData().setScale(2.0f);
        bitmapFont.draw(testBatch,schriftzug,super.getX()+10.0f,(super.getY()+super.getHöhe()/2)+6*bitmapFont.getScaleY());
        testBatch.end();
    }

    private void zeichneRechteck(){
        shaperenderer.rect(super.getX(),super.getY(),super.getBreite(),super.getHöhe());
    }

    private void zeichneRand(){
        shaperenderer.rectLine(super.getX()+linienbreite/2,super.getY(),super.getX()+linienbreite/2,super.getY()+super.getHöhe(),linienbreite);
        shaperenderer.rectLine(super.getX(),super.getY()+super.getHöhe()-linienbreite/2,super.getX()+super.getBreite(),super.getY()+super.getHöhe()-linienbreite/2,linienbreite);
        shaperenderer.rectLine(super.getX()+super.getBreite()-linienbreite/2,super.getY()+super.getHöhe(),super.getX()+super.getBreite()-linienbreite/2,super.getY(),linienbreite);
        shaperenderer.rectLine(super.getX()+super.getBreite(),super.getY()+linienbreite/2,super.getX(),super.getY()+linienbreite/2,linienbreite);
    }

    public void action(){

    }

    public void dispose(){
        shaperenderer.dispose();
        bitmapFont.dispose();
    }

}
