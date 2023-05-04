package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Spiel;

public class Spieler extends SpielObjekt {

    private int leben;
    private int maxLeben;
    private float geschwindigkeit;
    private Vector2 bewegungsVektor;
    private double winkel;

    public Spieler(float x, float y){
        super(x,y,Spiel.instanz.atlas.findRegion("SpielerTest").getRegionWidth(),
                Spiel.instanz.atlas.findRegion("SpielerTest").getRegionHeight(),
                true, "Spieler");
        leben=100;
        maxLeben=100;
        geschwindigkeit=200.0f;
        bewegungsVektor =new Vector2(0.0f,0.0f);
        winkel=0.0;

        texturen =new TextureRegion[1];
        texturen[0]= Spiel.instanz.atlas.findRegion("SpielerTest");

    }

    public void prüfeEingabe(float delta){
        bewegungsVektor.x=0.0f;
        bewegungsVektor.y=0.0f;

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            bewegungsVektor.y+=1.0f;
            System.out.println("W gedrückt");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            bewegungsVektor.y-=1.0f;
            System.out.println("S gedrückt");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            bewegungsVektor.x-=1.0f;
            System.out.println("A gedrückt");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            bewegungsVektor.x+=1.0f;
            System.out.println("D gedrückt");
        }

        if(bewegungsVektor.x!=0.0f || bewegungsVektor.y!=0.0f){
            //Vektor wird normalisiert
            float längeVektor = bewegungsVektor.len();
            bewegungsVektor.x/=längeVektor;
            bewegungsVektor.y/=längeVektor;
            bewegungsVektor.x*=geschwindigkeit;
            bewegungsVektor.y*=geschwindigkeit;
            bewegen(bewegungsVektor,delta);
        }
    }

    public void prüfeRotation(){
        //setzt den Winkel so, dass Spieler in Richtung Mauszeiger schaut
        double a = Gdx.input.getX()-(Gdx.graphics.getWidth()/2.0f);
        double b = Gdx.input.getY()-(Gdx.graphics.getHeight()/2.0f);

        if(a>=0){
            winkel= -((180/Math.PI)*Math.atan(b/a)+90.0);
        }else{
            winkel= -((180/Math.PI)*Math.atan(b/a)-90.0);
        }

        //System.out.println(winkel);
    }

    public double getWinkel() {
        return winkel;
    }

    public int getWinkelInt(){
        return (int)Math.round(winkel);
    }

    public void setWinkel(double winkel) {
        this.winkel = winkel;
    }
}
