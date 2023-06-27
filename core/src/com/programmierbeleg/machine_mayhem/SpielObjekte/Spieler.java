package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.Richtung;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.Animation;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

import java.util.ArrayList;

public class Spieler extends SpielObjekt implements EinmalProFrame {

    private int leben;
    private int maxLeben;

    private int kollisionsBreite;
    private int kollisionsHöhe;
    private float geschwindigkeit;
    private Vector2 bewegungsVektor;

    private Raum aktuellerRaum;
    private Feld[] benachbarteTüren;

    private Animation laufAnimation;

    //Angriffsparameter
    private int schaden;
    private int maxGenauigkeitAbzug;
    //Angabe in Grad
    private int reichweite;
    //Angabe in Sekunden
    private ArrayList<Projektil> spielerProjektile;

    public Spieler(float x, float y){
        super(x,y,Spiel.instanz.atlas.findRegion("Spieler_idle").getRegionWidth()-1,
                Spiel.instanz.atlas.findRegion("Spieler_idle").getRegionHeight()-1,
                0, true);
        leben=100;
        maxLeben=100;
        geschwindigkeit=75.0f *Spiel.instanz.skalierung;
        bewegungsVektor =new Vector2(0.0f,0.0f);

        kollisionsBreite=15;
        kollisionsHöhe=15;

        textur= Spiel.instanz.atlas.findRegion("Spieler_idle");
        laufAnimation = new Animation(this, new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("Spieler_lauf1"),
                Spiel.instanz.atlas.findRegion("Spieler_idle"),
                Spiel.instanz.atlas.findRegion("Spieler_lauf2"),
                Spiel.instanz.atlas.findRegion("Spieler_idle"),
        }, 0.25f,true);
        SpielAnzeige.physikObjekte.add(laufAnimation);

    }

    @Override
    public void einmalProFrame(float delta) {
        if(!aktuellerRaum.isKampfAktiv()) prüfeNachTüren();
        prüfeEingabe(delta);
        schauAufMauzeiger();
    }


    private void prüfeNachTüren(){
        //hat der Spieler den Raum gewechselt?
        if(benachbarteTüren[0]!=null && benachbarteTüren[0].kollidiertMit(this) && !aktuellerRaum.kollidiertMitTüren(this)){
            ändereAktuellenRaum(benachbarteTüren[0].getRaum());
        }else if(benachbarteTüren[1]!=null && benachbarteTüren[1].kollidiertMit(this) && !aktuellerRaum.kollidiertMitTüren(this)){
            ändereAktuellenRaum(benachbarteTüren[1].getRaum());
        }else if(benachbarteTüren[2]!=null && benachbarteTüren[2].kollidiertMit(this) && !aktuellerRaum.kollidiertMitTüren(this)){
            ändereAktuellenRaum(benachbarteTüren[2].getRaum());
        }else if(benachbarteTüren[3]!=null && benachbarteTüren[3].kollidiertMit(this) && !aktuellerRaum.kollidiertMitTüren(this)){
            ändereAktuellenRaum(benachbarteTüren[3].getRaum());
        }
    }

    public void ändereAktuellenRaum(Raum raum){
        aktuellerRaum=raum;

        benachbarteTüren=null;
        benachbarteTüren=new Feld[4];

        if(aktuellerRaum.hasNord()){
            benachbarteTüren[0]=aktuellerRaum.getRaumNord().findeTürObjekt(Richtung.Süd);
        }

        if(aktuellerRaum.hasOst()){
            benachbarteTüren[1]=aktuellerRaum.getRaumOst().findeTürObjekt(Richtung.West);
        }

        if(aktuellerRaum.hasSüd()){
            benachbarteTüren[2]=aktuellerRaum.getRaumSüd().findeTürObjekt(Richtung.Nord);
        }

        if(aktuellerRaum.hasWest()){
            benachbarteTüren[3]=aktuellerRaum.getRaumWest().findeTürObjekt(Richtung.Ost);
        }

        aktuellerRaum.raumBetreten();
    }

    public void prüfeEingabe(float delta){

        if(Gdx.input.isButtonJustPressed(Input.Buttons.FORWARD) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_ADD)){
            SpielAnzeige.setZoomLevel(SpielAnzeige.getZoomLevel()-0.25f);
        }else if(Gdx.input.isButtonJustPressed(Input.Buttons.BACK ) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_SUBTRACT)){
            SpielAnzeige.setZoomLevel(SpielAnzeige.getZoomLevel()+0.25f);
        }

        bewegungsVektor.x=0.0f;
        bewegungsVektor.y=0.0f;

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            bewegungsVektor.y+=1.0f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            bewegungsVektor.y-=1.0f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            bewegungsVektor.x-=1.0f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            bewegungsVektor.x+=1.0f;
        }

        if(bewegungsVektor.x!=0.0f || bewegungsVektor.y!=0.0f){
            //Vektor wird normalisiert
            float längeVektor = bewegungsVektor.len();
            bewegungsVektor.x/=längeVektor;
            bewegungsVektor.y/=längeVektor;
            bewegungsVektor.x*=geschwindigkeit;
            bewegungsVektor.y*=geschwindigkeit;

            //es wird geprüft ob der Spieler sich in die gewünschte Richtung bewegen kann
            //falls nicht wird geprüft ob der Spieler sich an der Wand entlang bewegen kann
            //es werden also 3 Vektoren geprüft
            if(prüfeKollision(aktuellerRaum,bewegungsVektor,delta)){
                bewegen(bewegungsVektor,delta);
                if(laufAnimation.isPausiert()) laufAnimation.starteVonVorn();
            }else if(prüfeKollision(aktuellerRaum,new Vector2(bewegungsVektor.x,0.0f),delta)){
                bewegen(bewegungsVektor.x,0.0f, delta);
                if(laufAnimation.isPausiert()) laufAnimation.starteVonVorn();
            }else if(prüfeKollision(aktuellerRaum,new Vector2(0.0f,bewegungsVektor.y),delta)) {
                bewegen(0.0f,bewegungsVektor.y, delta);
                if(laufAnimation.isPausiert()) laufAnimation.starteVonVorn();
            } else if (prüfeKollision(aktuellerRaum, new Vector2(0.1f, 0.1f),delta)) {
                bewegen(0.1f, 0.1f, delta);
                if(laufAnimation.isPausiert()) laufAnimation.starteVonVorn();
            }else{
                if(!laufAnimation.isPausiert()) laufAnimation.stop();
                setTextur(Spiel.instanz.atlas.findRegion("Spieler_idle"));
            }

            //x=(float)(Math.round(x*10.0)/10.0f);
            //y=(float)(Math.round(y*10.0)/10.0f);
            System.out.println("X: "+x+"| Y: "+y);
        }else{
            if(!laufAnimation.isPausiert()) laufAnimation.stop();
            setTextur(Spiel.instanz.atlas.findRegion("Spieler_idle"));
        }
    }

    private boolean prüfeKollision(Raum r, Vector2 v, float delta){
        //prüft ob die zukünftigen Felder, die der Spieler berühren wird, laufbar sind, oder nicht
        //true: Bewegung erlaubt

        //Ein imaginärer Spieler -> mit diesem wird die Kollision geprüft
        Spieler zukünftigerSpieler = new Spieler(x,y);
        //zukünftigerSpieler.setBreite(kollisionsBreite);
        //zukünftigerSpieler.setHöhe(kollisionsHöhe);
        zukünftigerSpieler.bewegen(v,delta);

        //alle Felder finden, die berührt werden
        //falls eines davon nicht laufbar ist -> false
        boolean alleLaufbar=true;
        for(int x=0; x<r.getFelder().length; x++){
            for(int y=0; y<r.getFelder()[x].length;y++){
                if(zukünftigerSpieler.kollidiertMit(r.getFelder()[x][y]) && !r.getFelder()[x][y].isLaufbar()){
                    alleLaufbar=false;
                }
                if(!alleLaufbar) break;
            }
            if(!alleLaufbar) break;
        }


        return alleLaufbar;
    }

    public void schauAufMauzeiger(){
        //setzt den Winkel so, dass Spieler in Richtung Mauszeiger schaut
        double a = Gdx.input.getX()-(Gdx.graphics.getWidth()/2.0f);
        double b = Gdx.input.getY()-(Gdx.graphics.getHeight()/2.0f);
        double ergebnis;

        if(a>=0){
            ergebnis= -((180/Math.PI)*Math.atan(b/a)+90.0);
        }else{
            ergebnis= -((180/Math.PI)*Math.atan(b/a)-90.0);
        }

        winkel=(float)ergebnis;

    }

    public float getWinkel() {
        return winkel;
    }

    public int getWinkelInt(){
        return (int)Math.round(winkel);
    }


    public Raum getAktuellerRaum() {
        return aktuellerRaum;
    }

    public int getKollisionsBreite() {
        return kollisionsBreite;
    }

    public int getGetKollisionsHöhe() {
        return kollisionsHöhe;
    }
}
