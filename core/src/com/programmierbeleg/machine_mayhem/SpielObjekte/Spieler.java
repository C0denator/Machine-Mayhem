package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Spiel;

public class Spieler extends SpielObjekt {

    private int leben;
    private int maxLeben;
    private float geschwindigkeit;
    private Vector2 bewegungsVektor;
    public Spieler(float x, float y, float breite, float höhe){
        super(x,y,breite,höhe,true, "Spieler");
        leben=100;
        maxLeben=100;
        geschwindigkeit=100.0f;
        bewegungsVektor =new Vector2(0.0f,0.0f);

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

}
