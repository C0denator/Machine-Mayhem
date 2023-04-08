package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class SpielObjekt {

    private float x;
    private float y;
    private float breite;
    private float höhe;
    private boolean Sichtbar;

    public SpielObjekt(float x, float y, float breite, float höhe, boolean Sichtbar){
        this.x=x-breite/2;
        this.y=y-höhe/2;
        this.breite=breite;
        this.höhe=höhe;
        this.Sichtbar=Sichtbar;

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
}
