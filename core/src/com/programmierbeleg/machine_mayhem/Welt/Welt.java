package com.programmierbeleg.machine_mayhem.Welt;

import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.Richtung;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Welt {
    private Raum startraum;
    private int raumAnzahl;
    private int maxRaumAnzahl;
    private File räumeOrdner;
    //der Ordner mit allen Räumen
    private File[] räume;
    //alle Räume als Array


    public Welt(int maxRaumAnzahl) {
        räumeOrdner = new File("assets/Räume");
        räume = räumeOrdner.listFiles();


        this.maxRaumAnzahl=maxRaumAnzahl;
        raumAnzahl=0;
        generiereWelt();
    }

    public void generiereWelt() {
        Random rnd = new Random();
        int randomInt;

        try{
            startraum=new Raum(ImageIO.read(new File("assets/start.png")),new Vector2());
            SpielAnzeige.räume.add(startraum);
            Raum derzeitigerRaum = startraum;

            while(raumAnzahl<maxRaumAnzahl){

                //Norden
                randomInt=rnd.nextInt(räume.length);
                if(raumAnzahl<maxRaumAnzahl && !derzeitigerRaum.hasNord()){
                    derzeitigerRaum.fügeRaumAn(ImageIO.read(räume[randomInt]), Richtung.Nord);

                    if(istRaumKollisionsfrei(derzeitigerRaum.getRaumNord(),SpielAnzeige.räume)){
                        SpielAnzeige.räume.add(derzeitigerRaum.getRaumNord());
                        raumAnzahl++;
                    }else{
                        System.out.println("Kollision entdeckt");
                        derzeitigerRaum.setRaumNord(null);
                    }

                }

                //Süden
                randomInt=rnd.nextInt(räume.length);
                if(raumAnzahl<maxRaumAnzahl && !derzeitigerRaum.hasSüd()){
                    derzeitigerRaum.fügeRaumAn(ImageIO.read(räume[randomInt]), Richtung.Süd);

                    if(istRaumKollisionsfrei(derzeitigerRaum.getRaumSüd(),SpielAnzeige.räume)){
                        SpielAnzeige.räume.add(derzeitigerRaum.getRaumSüd());
                        raumAnzahl++;
                    }else{
                        System.out.println("Kollision entdeckt");
                        derzeitigerRaum.setRaumSüd(null);
                    }

                }

                //Osten
                randomInt=rnd.nextInt(räume.length);
                if(raumAnzahl<maxRaumAnzahl && !derzeitigerRaum.hasOst()){
                    derzeitigerRaum.fügeRaumAn(ImageIO.read(räume[randomInt]), Richtung.Ost);

                    if(istRaumKollisionsfrei(derzeitigerRaum.getRaumOst(),SpielAnzeige.räume)){
                        SpielAnzeige.räume.add(derzeitigerRaum.getRaumOst());
                        raumAnzahl++;
                    }else{
                        System.out.println("Kollision entdeckt");
                        derzeitigerRaum.setRaumOst(null);
                    }

                }

                //Westen
                randomInt=rnd.nextInt(räume.length);
                if(raumAnzahl<maxRaumAnzahl && !derzeitigerRaum.hasWest()){
                    derzeitigerRaum.fügeRaumAn(ImageIO.read(räume[randomInt]), Richtung.West);

                    if(istRaumKollisionsfrei(derzeitigerRaum.getRaumWest(),SpielAnzeige.räume)){
                        SpielAnzeige.räume.add(derzeitigerRaum.getRaumWest());
                        raumAnzahl++;
                    }else{
                        System.out.println("Kollision entdeckt");
                        derzeitigerRaum.setRaumWest(null);
                    }

                }

                if(raumAnzahl<maxRaumAnzahl){
                    //den aktuellen Raum wechseln
                    int tmp = rnd.nextInt(4);
                    switch (tmp){
                        case 0:
                            if(derzeitigerRaum.hasNord()) {
                                derzeitigerRaum = derzeitigerRaum.getRaumNord();
                            }
                            break;
                        case 1:
                            if(derzeitigerRaum.hasOst()) {
                                derzeitigerRaum = derzeitigerRaum.getRaumOst();
                            }
                            break;
                        case 2:
                            if(derzeitigerRaum.hasSüd()) {
                                derzeitigerRaum = derzeitigerRaum.getRaumSüd();
                            }
                            break;
                        case 3:
                            if(derzeitigerRaum.hasWest()) {
                                derzeitigerRaum = derzeitigerRaum.getRaumWest();
                            }
                            break;
                    }
                }

            }

        }catch (IOException e){
            System.err.println("Fehler beim Laden der Raum-Dateien");
        }

        startraum.setGegnerAnzahl(0);
        SpielAnzeige.spieler.get(0).ändereAktuellenRaum(startraum);
        startraum.öffneTüren();

    }

    private boolean istRaumKollisionsfrei(Raum raum, ArrayList<Raum> räume){
        //prüft, ob der übergebene Raum mit einem Raum aus der Liste kollidiert
        for(Raum r : räume){
            if(r.kollidiertMit(raum)) return false;
        }

        return true;
    }
}
