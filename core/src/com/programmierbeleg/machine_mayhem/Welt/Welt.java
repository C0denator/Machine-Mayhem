package com.programmierbeleg.machine_mayhem.Welt;

import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.FeldTextur;
import com.programmierbeleg.machine_mayhem.Daten.Richtung;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Feld;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Fernkampf_1;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Spieler;
import sun.security.provider.ConfigFile;

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
            bild2 = ImageIO.read(new File("assets/Räume/raum1.png"));
        }catch(IOException e){
            System.err.println("Raum konnte nicht geladen erden");
        }

        BufferedImage bild3 = null;
        try{
            bild3 = ImageIO.read(new File("assets/Räume/raum2.png"));
        }catch(IOException e){
            System.err.println("Raum konnte nicht geladen erden");
        }

        BufferedImage bild4 = null;
        try{
            bild4 = ImageIO.read(new File("assets/Räume/raum3.png"));
        }catch(IOException e){
            System.err.println("Raum konnte nicht geladen erden");
        }

        BufferedImage bild5 = null;
        try{
            bild5 = ImageIO.read(new File("assets/Räume/raum4.png"));
        }catch(IOException e){
            System.err.println("Raum konnte nicht geladen erden");
        }

        startraum=new Raum(bild1,0,0);
        startraum.fügeRaumAn(bild2,Richtung.Nord);
        startraum.fügeRaumAn(bild3,Richtung.Ost);
        startraum.fügeRaumAn(bild4,Richtung.Süd);
        startraum.fügeRaumAn(bild5,Richtung.West);
        SpielAnzeige.räume.add(startraum);
        SpielAnzeige.räume.add(startraum.getRaumNord());
        SpielAnzeige.räume.add(startraum.getRaumOst());
        SpielAnzeige.räume.add(startraum.getRaumSüd());
        SpielAnzeige.räume.add(startraum.getRaumWest());
        SpielAnzeige.spieler.get(0).ändereAktuellenRaum(startraum);
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
