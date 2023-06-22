package com.programmierbeleg.machine_mayhem.Welt;

import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.FeldTextur;
import com.programmierbeleg.machine_mayhem.Daten.Richtung;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Feld;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Fernkampf_1;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Spieler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Welt {
    private Raum startraum;
    private int raumAnzahl;
    private int maxRaumAnzahl;


    public Welt(int maxRaumAnzahl)  {
        this.maxRaumAnzahl=maxRaumAnzahl;
        raumAnzahl=0;
        generiereWelt();
    }

    public void generiereWelt(){
        BufferedImage bild1 = null;
        try{
            bild1 = ImageIO.read(new File("assets/Räume/start.png"));
        }catch(IOException e){
            System.err.println("Startraum konnte nicht geladen erden");
        }

        BufferedImage bild2 = null;
        try{
            bild2 = ImageIO.read(new File("assets/Räume/raum5.png"));
        }catch(IOException e){
            System.err.println("Raum konnte nicht geladen erden");
        }

        startraum=new Raum(bild1,0,0);
        startraum.setRaumWest(new Raum(bild2,new Vector2(25,25)));
        SpielAnzeige.räume.add(startraum);
        SpielAnzeige.räume.add(startraum.getRaumWest());
        SpielAnzeige.spieler.get(0).setAktuellerRaum(startraum);
        /*
        boolean kollisionEntdeckt=false;
        while(raumAnzahl<maxRaumAnzahl){
            if(startraum==null){
                BufferedImage bild = null;
                try{
                    bild = ImageIO.read(new File("assets/Räume/start.png"));
                }catch(IOException e){
                    System.err.println("Startraum konnte nicht geladen erden");
                }
                startraum=new Raum(bild);
                SpielAnzeige.räume.add(startraum);
                SpielAnzeige.spieler.get(0).setAktuellerRaum(startraum);
                raumAnzahl++;
            }else{

            }
        }

        */

    }
}
