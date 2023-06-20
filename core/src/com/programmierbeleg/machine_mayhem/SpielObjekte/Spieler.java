package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

import java.util.ArrayList;

public class Spieler extends SpielObjekt implements EinmalProFrame {

    private int leben;
    private int maxLeben;
    private float geschwindigkeit;
    private Vector2 bewegungsVektor;
    private double winkel;

    private Raum aktuellerRaum;

    //Angriffsparameter
    private int schaden;
    private int maxGenauigkeitAbzug;
    //Angabe in Grad
    private int reichweite;
    //Angabe in Sekunden
    private ArrayList<Projektil> spielerProjektile;

    public Spieler(float x, float y){
        super(x,y,Spiel.instanz.atlas.findRegion("SpielerTest").getRegionWidth(),
                Spiel.instanz.atlas.findRegion("SpielerTest").getRegionHeight(),
                true, "Spieler");
        leben=100;
        maxLeben=100;
        geschwindigkeit=75.0f *Spiel.instanz.skalierung;
        bewegungsVektor =new Vector2(0.0f,0.0f);
        winkel=0.0;

        textur= Spiel.instanz.atlas.findRegion("SpielerTest");

    }

    private boolean prüfeKollision(Raum r, Vector2 v, float delta){
        //prüft ob die zukünftigen Felder, die der Spieler berühren wird, laufbar sind, oder nicht
        //true: Bewegung erlaubt

        //Ein imaginärer Spieler -> mit diesem wird die Kollision geprüft
        Spieler zukünftigerSpieler = new Spieler(x,y);
        zukünftigerSpieler.bewegen(v,delta);

        //alle Felder finden, die berührt werden
        //falls eines davon nicht laufbar ist -> false
        boolean alleLaufbar=true;
        for(int x=0; x<r.getFelder().length; x++){
            for(int y=0; y<r.getFelder()[x].length;y++){
                if(zukünftigerSpieler.kollidiert(r.getFelder()[x][y]) && !r.getFelder()[x][y].isLaufbar()){
                    alleLaufbar=false;
                }
                if(!alleLaufbar) break;
            }
            if(!alleLaufbar) break;
        }


        return alleLaufbar;
    }

    public void prüfeEingabe(float delta){
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
            }else if(prüfeKollision(aktuellerRaum,new Vector2(bewegungsVektor.x,0.0f),delta)){
                bewegen(bewegungsVektor.x,0.0f, delta);
            }else if(prüfeKollision(aktuellerRaum,new Vector2(0.0f,bewegungsVektor.y),delta)) {
                bewegen(0.0f,bewegungsVektor.y, delta);
            }
        }
    }

    public void schauAufMauzeiger(){
        //setzt den Winkel so, dass Spieler in Richtung Mauszeiger schaut
        double a = Gdx.input.getX()-(Gdx.graphics.getWidth()/2.0f);
        double b = Gdx.input.getY()-(Gdx.graphics.getHeight()/2.0f);

        if(a>=0){
            winkel= -((180/Math.PI)*Math.atan(b/a)+90.0);
        }else{
            winkel= -((180/Math.PI)*Math.atan(b/a)-90.0);
        }

        //System.out.println(winkel);
    }


    public double getWinkel() {
        return winkel;
    }

    public int getWinkelInt(){
        return (int)Math.round(winkel);
    }

    public void setWinkel(double winkel) {
        this.winkel = winkel;
    }

    @Override
    public void einmalProFrame(float delta) {
        prüfeEingabe(delta);
        schauAufMauzeiger();
    }

    public Raum getAktuellerRaum() {
        return aktuellerRaum;
    }

    public void setAktuellerRaum(Raum aktuellerRaum) {
        this.aktuellerRaum = aktuellerRaum;
    }
}
