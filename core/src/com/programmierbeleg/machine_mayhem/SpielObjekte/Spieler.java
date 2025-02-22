package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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
    private Raum vorherigerRaum;
    //für die Tür-Animationen notwendig
    private Feld[] benachbarteTüren;

    private Animation laufAnimation;

    //Angriffsparameter
    private int schaden = 10;
    private int chanceAufItem;
    //0-100%

    private int anzahlSchlüssel;




    private float schussAbklingzeit = 0.5f;
    private float abklingzeitTimer=schussAbklingzeit;
    private int schussSpeed=250;

    private Sound schussSound;

    public Spieler(float x, float y, Raum raum){
        super(x,y,Spiel.instanz.atlas.findRegion("Spieler_idle").getRegionWidth()-1,
                Spiel.instanz.atlas.findRegion("Spieler_idle").getRegionHeight()-1,
                0, true);
        leben=100;
        maxLeben=100;
        geschwindigkeit=75.0f;
        bewegungsVektor =new Vector2(0.0f,0.0f);
        aktuellerRaum=raum;

        kollisionsBreite=15;
        kollisionsHöhe=15;

        textur= Spiel.instanz.atlas.findRegion("Spieler_idle");
        laufAnimation = new Animation(this, new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("Spieler_lauf1"),
                Spiel.instanz.atlas.findRegion("Spieler_idle"),
                Spiel.instanz.atlas.findRegion("Spieler_lauf2"),
                Spiel.instanz.atlas.findRegion("Spieler_idle"),
        }, 0.25f,true);

        benachbarteTüren=new Feld[4];

        if(SpielAnzeige.physikObjekte==null){
            System.err.println("Fehler: SpielAnzeige.physikObjekte ist null");
        }else{
            SpielAnzeige.physikObjekte.add(this);
        }

        schussSound=Gdx.audio.newSound(Gdx.files.internal("Sounds/laser.wav"));
        chanceAufItem=0;
        anzahlSchlüssel=0;
    }

    private Spieler(float x, float y, Raum raum, boolean fake){
        super(x,y,Spiel.instanz.atlas.findRegion("Spieler_idle").getRegionWidth()-1,
                Spiel.instanz.atlas.findRegion("Spieler_idle").getRegionHeight()-1,
                0, true);
        leben=100;
        maxLeben=100;
        geschwindigkeit=75.0f;
        bewegungsVektor =new Vector2(0.0f,0.0f);
        aktuellerRaum=raum;

        kollisionsBreite=15;
        kollisionsHöhe=15;

        textur= Spiel.instanz.atlas.findRegion("Spieler_idle");
        laufAnimation = new Animation(this, new TextureRegion[]{
                Spiel.instanz.atlas.findRegion("Spieler_lauf1"),
                Spiel.instanz.atlas.findRegion("Spieler_idle"),
                Spiel.instanz.atlas.findRegion("Spieler_lauf2"),
                Spiel.instanz.atlas.findRegion("Spieler_idle"),
        }, 0.25f,true);

        benachbarteTüren=new Feld[4];
    }

    @Override
    public void einmalProFrame(float delta) {
        System.out.println("Chance auf Item: "+Integer.toString(chanceAufItem));

        if(leben>0){
            if(aktuellerRaum!=null){
                if(!aktuellerRaum.isKampfAktiv()) prüfeNachTüren();
                prüfeEingabe(delta);
                schauAufMauzeiger();
                prüfeSchießen(delta);
            }else{
                System.err.println("Aktueller Raum des Spielers ist null!");
            }
        }else{
            if(!SpielAnzeige.instanz.isGameOver()){
                laufAnimation.stop();
                SpielAnzeige.instanz.gameOver();
            }
        }

    }

    private void prüfeSchießen(float delta){
        abklingzeitTimer-=delta;
        if(abklingzeitTimer<=0){
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                SpielAnzeige.projektile.add(new Projektil(x+breite/2,y, winkel,Spiel.instanz.atlas.findRegion("laser_gelb",1),schaden, new Vector2(
                        (float) (-Math.sin( (winkel/180) * Math.PI)) * schussSpeed,
                        (float) (Math.cos( (winkel/180) * Math.PI)) * schussSpeed),
                        aktuellerRaum, false));
                schussSound.play(0.2f);
                abklingzeitTimer=schussAbklingzeit;
            }
        }
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
        vorherigerRaum=aktuellerRaum;
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

        aktuellerRaum.raumBetreten(vorherigerRaum);
    }

    public void prüfeEingabe(float delta){

        if(Gdx.input.isButtonJustPressed(Input.Buttons.FORWARD) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_ADD)){
            SpielAnzeige.instanz.setZoomLevel(SpielAnzeige.instanz.getZoomLevel()-0.1f);
        }else if(Gdx.input.isButtonJustPressed(Input.Buttons.BACK ) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_SUBTRACT)){
            SpielAnzeige.instanz.setZoomLevel(SpielAnzeige.instanz.getZoomLevel()+0.1f);
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
            if(!kollidiertInZukunft(aktuellerRaum,vorherigerRaum,bewegungsVektor,delta)){
                bewegen(bewegungsVektor,delta);
                if(laufAnimation.isPausiert()) laufAnimation.starteVonVorn();
            }else if(!kollidiertInZukunft(aktuellerRaum, vorherigerRaum, new Vector2(bewegungsVektor.x,0.0f),delta)){
                bewegen(bewegungsVektor.x,0.0f, delta);
                if(laufAnimation.isPausiert()) laufAnimation.starteVonVorn();
            }else if(!kollidiertInZukunft(aktuellerRaum, vorherigerRaum, new Vector2(0.0f,bewegungsVektor.y),delta)) {
                bewegen(0.0f,bewegungsVektor.y, delta);
                if(laufAnimation.isPausiert()) laufAnimation.starteVonVorn();
            } else if (!kollidiertInZukunft(aktuellerRaum, vorherigerRaum, new Vector2(0.1f, 0.1f),delta)) {
                bewegen(0.1f, 0.1f, delta);
                if(laufAnimation.isPausiert()) laufAnimation.starteVonVorn();
            }else{
                if(!laufAnimation.isPausiert()) laufAnimation.stop();
                setTextur(Spiel.instanz.atlas.findRegion("Spieler_idle"));
            }

            //x=(float)(Math.round(x*10.0)/10.0f);
            //y=(float)(Math.round(y*10.0)/10.0f);
            //System.out.println("X: "+x+"| Y: "+y);
        }else{
            if(!laufAnimation.isPausiert()) laufAnimation.stop();
            setTextur(Spiel.instanz.atlas.findRegion("Spieler_idle"));
        }
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
        //System.out.println(Float.toString(winkel));

    }

    public void bekommeSchaden(int schaden){
        leben-=schaden;
        if(leben<=0){
            leben=0;
            SpielAnzeige.instanz.gameOver();
        }
    }

    public void heilen(int anzahl){
        leben+=anzahl;
        if(leben>maxLeben)leben=maxLeben;
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

    public int getLeben() {
        return leben;
    }

    public int getMaxLeben() {
        return maxLeben;
    }

    public int getChanceAufItem() {
        return chanceAufItem;
    }

    public void setChanceAufItem(int chanceAufItem) {
        this.chanceAufItem = chanceAufItem;
    }

    public int getAnzahlSchlüssel() {
        return anzahlSchlüssel;
    }

    public void setAnzahlSchlüssel(int anzahlSchlüssel) {
        this.anzahlSchlüssel = anzahlSchlüssel;
    }
}
