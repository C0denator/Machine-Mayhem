package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.programmierbeleg.machine_mayhem.Spiel;

public class Spieler extends SpielObjekt {

    private int leben;
    private int maxLeben;
    private float geschwindigkeit;

    public Spieler(float x, float y, float breite, float höhe){
        super(x,y,breite,höhe,true, "Spieler");
        leben=100;
        maxLeben=100;
        geschwindigkeit=3.0f;

    }

    public void prüfeEingabe(float delta){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            move(0.0f,geschwindigkeit);
            System.out.println("W gedrückt");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            move(0.0f,-geschwindigkeit);
            System.out.println("S gedrückt");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            move(-geschwindigkeit,0.0f);
            System.out.println("A gedrückt");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            move(geschwindigkeit,0.0f);
            System.out.println("D gedrückt");
        }
    }

}
