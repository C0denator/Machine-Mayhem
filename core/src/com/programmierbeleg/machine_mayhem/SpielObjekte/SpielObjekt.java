package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Sonstiges.ID_Vergeber;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

/**
 * Oberklasse für alle Objekte, die Teil der Spielwelt sind
 */
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
        this.breite=breite;
        this.höhe=höhe;
        this.x=x;
        this.y=y;
        this.winkel=winkel;
        this.Sichtbar=Sichtbar;
        this.ID= ID_Vergeber.instanz.vergebeID();
    }


    /**
     * Bewegt den Spieler um den übergebenen Vektor.
     */
    public void bewegen(Vector2 v, float delta){
        this.x+=v.x * delta;
        this.y+=v.y * delta;
    }

    /**
     * Bewegt den Spieler um die übergenen Werte.
     */
    public void bewegen(float x, float y, float delta){
        this.x+=x*delta;
        this.y+=y*delta;
    }

    /**
     * Bewegt den Spieler in die Richtung des übergebenen Winkels.
     * @param winkel der Winkel, in den das Spielobjekt bewegt werden soll
     * @param multiplikator multipliziert den Vektor
     */
    public void bewegenInRichtung(float winkel, float multiplikator, float delta){
        //bewegt das Objekt in die jeweilige Richtung
        Vector2 v = new Vector2(
                (float) (-Math.sin( (winkel/180) * Math.PI)) * multiplikator,
                (float) (Math.cos( (winkel/180) * Math.PI)) * multiplikator);
        bewegen(v,delta);

    }

    /**
     * Gibt an, ob ein Objekt kollidieren würde, wenn es sich um den übergebenen Wert bewegen würde.
     * @param raum der Raum wo die Kollisionsabfrage stattfindet
     * @param v die Bewegung, die überprüft werden soll
     */
    public boolean kollidiertInZukunft(Raum raum, Vector2 v, float delta){
        //prüft ob es zu einer Kollision im Raum kommt, wenn sich das Objekt um den übergebenen Betrag bewegen würde

        SpielObjekt zukünftigesObjekt = new SpielObjekt(x,y, breite, höhe, winkel, false);

        zukünftigesObjekt.bewegen(v,delta);

        for(int x=0; x<raum.getFelder().length; x++){
            for(int y=0; y<raum.getFelder()[x].length;y++){
                if(zukünftigesObjekt.kollidiertMit(raum.getFelder()[x][y]) && !raum.getFelder()[x][y].isLaufbar()){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Gibt an, ob ein Objekt kollidieren würde, wenn es sich um den übergebenen Wert bewegen würde.
     * Diese Methode überprüft 2 Räume statt einen (wichtig für Spielerkollision)
     * @param raum der Raum wo die Kollisionsabfrage stattfindet
     * @param v die Bewegung, die überprüft werden soll
     */
    public boolean kollidiertInZukunft(Raum raum, Raum vorherigerRaum, Vector2 v, float delta){
        //dasselbe wie oben, nur mit 2 Räumen (für Spielerkollision)
        SpielObjekt zukünftigesObjekt = new SpielObjekt(x,y, breite, höhe, 0, false);

        zukünftigesObjekt.bewegen(v,delta);

        for(int x=0; x<raum.getFelder().length; x++){
            for(int y=0; y<raum.getFelder()[x].length;y++){
                if(zukünftigesObjekt.kollidiertMit(raum.getFelder()[x][y]) && !raum.getFelder()[x][y].isLaufbar()){
                    return true;
                }
            }
        }

        if(vorherigerRaum!=null){
            for(int x=0; x<vorherigerRaum.getFelder().length; x++){
                for(int y=0; y<vorherigerRaum.getFelder()[x].length;y++){
                    if(zukünftigesObjekt.kollidiertMit(vorherigerRaum.getFelder()[x][y]) && !vorherigerRaum.getFelder()[x][y].isLaufbar()){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Gibt an, ob das Objekt mit dem übergebenen Objekt jetzt gerade kollidiert
     */
    public boolean kollidiertMit(SpielObjekt o){
        //kollidieren die Objekte?
        //true: Objekte berühren sich
        //false: sie tun es nicht
        Rectangle r1 = new Rectangle(x,y,breite,höhe);
        Rectangle r2 = new Rectangle(o.getX(),o.getY(),o.getBreite(),o.getHöhe());
        return r1.overlaps(r2);
    }

    /**
     * Gibt die Distanz des Objekts zu dem übergebenen Objekt zurück
     */
    public float distanzZu(SpielObjekt o){
        float xKathete = x-o.getX();
        float yKathete = y-o.getY();
        return (float)Math.sqrt((xKathete*xKathete)+(yKathete*yKathete));
    }

    /**
     * Gibt den Winkel zurück, den das Objekt haben muss, um auf das übergebene Objekt zu zeigen.
     */
    protected float winkelZu(SpielObjekt s){
        //der Winkel, den dieses Objekt haben muss, um zum übergebenen Objekt zu schauen
        double a = x-s.getX();
        double b = y-s.getY();
        double ergebnis;

        if(a>=0){
            ergebnis= ((180/Math.PI)*Math.atan(b/a)+90.0);
        }else{
            ergebnis= ((180/Math.PI)*Math.atan(b/a)-90.0);
        }

        return (float)ergebnis;
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

    public int getHöhe() {
        return höhe;
    }

    public boolean isSichtbar() {
        return Sichtbar;
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
