package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.programmierbeleg.machine_mayhem.Angriffe.Angriff;
import com.programmierbeleg.machine_mayhem.Daten.GegnerTyp;
import com.programmierbeleg.machine_mayhem.Spiel;

import java.util.Random;

public class Gegner extends SpielObjekt{

    private Angriff[] angriffe;
    private Angriff aktiverAngriff;
    private float zeitBisAngriff;
    protected int maxLeben;
    protected int geschwindigkeit;

    public Gegner (GegnerTyp gegnerTyp, float x, float y, float breite, float höhe) {
        super (x, y, breite, höhe, true,"Gegner");
        this.setKlassenName("Gegner");
        aktiverAngriff=null;
        zeitBisAngriff=5.0f;

        maxLeben=gegnerTyp.getMaxLeben();
        geschwindigkeit=gegnerTyp.getGeschwindigkeit();
        texturen=gegnerTyp.getTexturen();
        angriffe=gegnerTyp.getAngriffe();
    }

    public void denke(){
        //muss einmal pro Frame aufgerufen werden

        if(aktiverAngriff==null){
            bewege();
            zeitBisAngriff-= Spiel.instanz.delta;
            if(zeitBisAngriff<0 && angriffe!=null){
                if(angriffe.length==1){
                    aktiverAngriff=angriffe[0];
                    System.out.println("ANGRIFF!!!!!");
                }else{
                    aktiverAngriff=angriffe[new Random().nextInt(angriffe.length)];
                }
            }
        }else{
            if(aktiverAngriff.isBewegenErlaubt()){
                bewege();
            }
        }

    }

    protected void bewege(){

    }
}
