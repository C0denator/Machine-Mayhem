package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Sonstiges.ID_Vergeber;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

public class SpielObjekt {

    protected float x;
    protected float y;
    protected int breite;
    protected int höhe;

    protected float winkel;

    protected TextureRegion textur;
    private boolean Sichtbar;

    public final int ID;


    public SpielObjekt(float x, float y, int breite, int höhe, float winkel, boolean Sichtbar){
        this.breite=breite*Spiel.instanz.skalierung;
        this.höhe=höhe*Spiel.instanz.skalierung;
        this.x=x;
        this.y=y;
        this.winkel=winkel;
        this.Sichtbar=Sichtbar;
        this.ID= ID_Vergeber.instanz.vergebeID();
    }



    public void bewegen(Vector2 v, float delta){
        this.x+=v.x * delta;
        this.y+=v.y * delta;
    }

    public void bewegen(float x, float y, float delta){
        this.x+=x*delta;
        this.y+=y*delta;
    }

    public boolean kollidiertMit(SpielObjekt o){
        //true: Objekte berühren sich
        //false: sie tun es nicht
        Rectangle r1 = new Rectangle(x,y,breite,höhe);
        Rectangle r2 = new Rectangle(o.getX(),o.getY(),o.getBreite(),o.getHöhe());
        return r1.overlaps(r2);
    }

    public boolean kollidiertMit(Rectangle r2){
        //dasselbe wie oben, nur das ein Rectangle übergeben wird
        //true: Objekte berühren sich
        //false: sie tun es nicht
        Rectangle r1 = new Rectangle(x,y,breite,höhe);
        return r1.overlaps(r2);
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

    public TextureRegion getTextur() {
        return textur;
    }

    public void setTextur(TextureRegion textur) {
        this.textur = textur;
    }

    public float getWinkel() {
        return winkel;
    }

    public void setWinkel(float winkel) {
        this.winkel = winkel;
    }

    public String toString(){
        String s="";
        s+="ID: "+ID+"| X: "+x+"| Y: "+y+"| Breite: "+breite+"| Höhe: "+höhe+"| Winkel: "+Float.toString(winkel);
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SpielObjekt){
            return ID==((SpielObjekt) obj).ID;
        }else{
            return false;
        }
    }
}
