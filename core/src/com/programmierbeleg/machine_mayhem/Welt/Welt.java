package com.programmierbeleg.machine_mayhem.Welt;

import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.Richtung;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        //BufferedImage bild1 = ImageIO.read(räume[0]);
        Random rnd = new Random();
        //rnd.nextInt(räume.length);

        try{
            startraum=new Raum(ImageIO.read(new File("assets/start.png")),0,0);
            SpielAnzeige.räume.add(startraum);
            Raum derzeitigerRaum = startraum;

            while(raumAnzahl<maxRaumAnzahl){
                if(raumAnzahl<maxRaumAnzahl && !derzeitigerRaum.hasNord()){
                    derzeitigerRaum.fügeRaumAn(ImageIO.read(räume[rnd.nextInt(räume.length)]), Richtung.Nord);
                    SpielAnzeige.räume.add(derzeitigerRaum.getRaumNord());
                    raumAnzahl++;
                }

                if(raumAnzahl<maxRaumAnzahl && !derzeitigerRaum.hasSüd()){
                    derzeitigerRaum.fügeRaumAn(ImageIO.read(räume[rnd.nextInt(räume.length)]), Richtung.Süd);
                    SpielAnzeige.räume.add(derzeitigerRaum.getRaumSüd());
                    raumAnzahl++;
                }

                if(raumAnzahl<maxRaumAnzahl && !derzeitigerRaum.hasOst()){
                    derzeitigerRaum.fügeRaumAn(ImageIO.read(räume[rnd.nextInt(räume.length)]), Richtung.Ost);
                    SpielAnzeige.räume.add(derzeitigerRaum.getRaumOst());
                    raumAnzahl++;
                }

                if(raumAnzahl<maxRaumAnzahl && !derzeitigerRaum.hasWest()){
                    derzeitigerRaum.fügeRaumAn(ImageIO.read(räume[rnd.nextInt(räume.length)]), Richtung.West);
                    SpielAnzeige.räume.add(derzeitigerRaum.getRaumWest());
                    raumAnzahl++;
                }

                if(raumAnzahl<maxRaumAnzahl){
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

        SpielAnzeige.spieler.get(0).ändereAktuellenRaum(startraum);

    }
}
