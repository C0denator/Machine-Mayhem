package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Spiel;

public abstract class SpielObjekt {

    protected float x;
    protected float y;
    protected float breite;
    protected float höhe;
    protected TextureRegion[] texturen;
    private boolean Sichtbar;
    private String klassenName;
    /*Identifikator um die richtige Textur zuzuweisen --> !Muss exakt mit dem Klassennamen übereinstimmen
        Die Methode getClass().getSimpleName() funktioniert nicht, da anonyme Klasse
    */


    public SpielObjekt(float x, float y, float breite, float höhe, boolean Sichtbar, String klassenName){
        this.x=x-breite/2;
        this.y=y-höhe/2;
        this.breite=breite;
        this.höhe=höhe;
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

    public float getBreite() {
        return breite;
    }

    public void setBreite(float breite) {
        this.breite = breite;
    }

    public float getHöhe() {
        return höhe;
    }

    public void setHöhe(float höhe) {
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
