package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Spiel;

public abstract class SpielObjekt {

    protected float x;
    protected float y;
    protected int breite;
    protected int höhe;
    protected TextureRegion[] texturen;
    private boolean Sichtbar;
    private String klassenName;
    /*Identifikator --> !Muss exakt mit dem Klassennamen übereinstimmen
        Die Methode getClass().getSimpleName() funktioniert nicht, da anonyme Klasse
    */


    public SpielObjekt(float x, float y, int breite, int höhe, boolean Sichtbar, String klassenName){
        this.breite=breite*Spiel.instanz.skalierung;
        this.höhe=höhe*Spiel.instanz.skalierung;
        this.x=x-this.breite/2.0f;
        this.y=y-this.höhe/2.0f;
        this.Sichtbar=Sichtbar;
        this.klassenName=klassenName;
    }


    public void bewegen(Vector2 v, float delta){
        this.x+=v.x * delta;
        this.y+=v.y * delta;
    }

    public void bewegen(float x, float y, float delta){
        this.x+=x*delta;
        this.y+=y*delta;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getBreite() {
        return breite;
    }

    public void setBreite(int breite) {
        this.breite = breite;
    }

    public int getHöhe() {
        return höhe;
    }

    public void setHöhe(int höhe) {
        this.höhe = höhe;
    }

    public boolean isSichtbar() {
        return Sichtbar;
    }

    public void setSichtbar(boolean sichtbar) {
        Sichtbar = sichtbar;
    }

    public TextureRegion[] getTexturen() {
        return texturen;
    }

    public String getKlassenName() {
        return klassenName;
    }

    public void setKlassenName(String klassenName) {
        this.klassenName = klassenName;
    }
}
