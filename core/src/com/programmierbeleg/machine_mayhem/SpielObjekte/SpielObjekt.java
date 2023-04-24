package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Spiel;

public abstract class SpielObjekt {

    private float x;
    private float y;
    private float breite;
    private float höhe;
    private TextureRegion[] regions;
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

        //Zuweisen der jeweiligen Texturen
        switch (this.klassenName){
            case "Spieler":
                regions=new TextureRegion[1];
                regions[0]= Spiel.instanz.atlas.findRegion("SpielerTest");
                break;
            case "Gegner":
                regions=new TextureRegion[3];
                regions[0]=Spiel.instanz.atlas.findRegion("robot",1);
                regions[1]=Spiel.instanz.atlas.findRegion("robot",2);
                regions[2]=Spiel.instanz.atlas.findRegion("robot",3);
                break;
            case "Knopf":
                break;
            default:
                System.err.println("FEHLER: Objekt konnte keine Texture zugewiesen werden");
                System.err.println("Fehlerhafter Name: "+klassenName);
                Gdx.app.exit();
        }


    }

    public void bewegen(float x, float y){
        this.x+=x;
        this.y+=y;
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

    public TextureRegion[] getRegions() {
        return regions;
    }

    public String getKlassenName() {
        return klassenName;
    }

    public void setKlassenName(String klassenName) {
        this.klassenName = klassenName;
    }
}
