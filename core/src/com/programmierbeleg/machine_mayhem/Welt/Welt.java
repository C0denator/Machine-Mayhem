package com.programmierbeleg.machine_mayhem.Welt;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.Richtung;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Die Klasse der Spielwelt. Hat eine Referenz zum Startraum.
 */
public class Welt {
    private Raum startraum;
    private int raumAnzahl;
    /**
     * Maximale Anzahl an Räumen, die bei der Weltgenerierung erzeugt werden sollen
     */
    private int maxRaumAnzahl;

    /**
     * Referenz zum letzten erzeugten Raum. Wird zum Boss-Raum
     */
    private Raum letzterRaum;
   
    /**
     * Alle Raum-Bilder
     */
    private FileHandle[] räume;



    public Welt(int maxRaumAnzahl) {


        try{
            räume = Gdx.files.internal("assets/räume").list();
        }catch (Exception e){
            System.err.println("Fehler beim Laden der einzelnen Räume");
        }

        System.out.println("Größe des Raum-Arrays: "+räume.length);

        if(räume==null){
            System.err.println("räume ist null");
        }


        this.maxRaumAnzahl=maxRaumAnzahl;
        raumAnzahl=0;
        generiereWelt();
        fügeSchlüsselHinzu();
    }

    public void generiereWelt() {
        Random rnd = new Random();
        int randomInt;

        try{
            startraum=new Raum(ImageIO.read(Gdx.files.internal("assets/start.png").file()),new Vector2());
        }catch (IOException e){
            System.err.println("Fehler beim Startraum laden");
        }

        SpielAnzeige.räume.add(startraum);
        Raum derzeitigerRaum = startraum;

        while(raumAnzahl<maxRaumAnzahl){

            BufferedImage image = null;

            //Norden
            randomInt=rnd.nextInt(räume.length);
            if(raumAnzahl<maxRaumAnzahl && !derzeitigerRaum.hasNord()){

                if(raumAnzahl==maxRaumAnzahl-1){
                    try{
                        image=ImageIO.read(Gdx.files.internal("assets/bossRaum.png").file());
                    }catch (IOException e){
                        System.err.println("Fehler beim Laden des Bossraums");
                    }
                }else{
                    try {
                        image=ImageIO.read(räume[randomInt].file());
                    }catch(IOException e){
                        System.err.println("Fehler beim Laden des zufälligen Raumes");
                    }

                }

                if(image==null){
                    System.err.println("FEHLER: image ist null");
                }

                derzeitigerRaum.fügeRaumAn(image, Richtung.Nord);

                if(istRaumKollisionsfrei(derzeitigerRaum.getRaumNord(),SpielAnzeige.räume)){
                    SpielAnzeige.räume.add(derzeitigerRaum.getRaumNord());
                    letzterRaum=derzeitigerRaum.getRaumNord();
                    raumAnzahl++;
                }else{
                    System.out.println("Kollision entdeckt");
                    derzeitigerRaum.setRaumNord(null);
                }

            }

            //Süden
            randomInt=rnd.nextInt(räume.length);
            if(raumAnzahl<maxRaumAnzahl && !derzeitigerRaum.hasSüd()){

                if(raumAnzahl==maxRaumAnzahl-1){
                    try{
                        image=ImageIO.read(Gdx.files.internal("assets/bossRaum.png").file());
                    }catch (IOException e){
                        System.err.println("Fehler beim Laden des Bossraums");
                    }
                }else{
                    try {
                        image=ImageIO.read(räume[randomInt].file());
                    }catch(IOException e){
                        System.err.println("Fehler beim Laden des zufälligen Raumes");
                    }

                }

                if(image==null){
                    System.err.println("FEHLER: image ist null");
                }

                derzeitigerRaum.fügeRaumAn(image, Richtung.Süd);

                if(istRaumKollisionsfrei(derzeitigerRaum.getRaumSüd(),SpielAnzeige.räume)){
                    SpielAnzeige.räume.add(derzeitigerRaum.getRaumSüd());
                    letzterRaum=derzeitigerRaum.getRaumSüd();
                    raumAnzahl++;
                }else{
                    System.out.println("Kollision entdeckt");
                    derzeitigerRaum.setRaumSüd(null);
                }

            }

            //Osten
            randomInt=rnd.nextInt(räume.length);
            if(raumAnzahl<maxRaumAnzahl && !derzeitigerRaum.hasOst()){

                if(raumAnzahl==maxRaumAnzahl-1){
                    try{
                        image=ImageIO.read(Gdx.files.internal("assets/bossRaum.png").file());
                    }catch (IOException e){
                        System.err.println("Fehler beim Laden des Bossraums");
                    }
                }else{
                    try {
                        image=ImageIO.read(räume[randomInt].file());
                    }catch(IOException e){
                        System.err.println("Fehler beim Laden des zufälligen Raumes");
                    }

                }

                if(image==null){
                    System.err.println("FEHLER: image ist null");
                }

                derzeitigerRaum.fügeRaumAn(image, Richtung.Ost);

                if(istRaumKollisionsfrei(derzeitigerRaum.getRaumOst(),SpielAnzeige.räume)){
                    SpielAnzeige.räume.add(derzeitigerRaum.getRaumOst());
                    letzterRaum=derzeitigerRaum.getRaumOst();
                    raumAnzahl++;
                }else{
                    System.out.println("Kollision entdeckt");
                    derzeitigerRaum.setRaumOst(null);
                }

            }

            //Westen
            randomInt=rnd.nextInt(räume.length);
            if(raumAnzahl<maxRaumAnzahl && !derzeitigerRaum.hasWest()){

                if(raumAnzahl==maxRaumAnzahl-1){
                    try{
                        image=ImageIO.read(Gdx.files.internal("assets/bossRaum.png").file());
                    }catch (IOException e){
                        System.err.println("Fehler beim Laden des Bossraums");
                    }
                }else{
                    try {
                        image=ImageIO.read(räume[randomInt].file());
                    }catch(IOException e){
                        System.err.println("Fehler beim Laden des zufälligen Raumes");
                    }

                }

                if(image==null){
                    System.err.println("FEHLER: image ist null");
                }

                derzeitigerRaum.fügeRaumAn(image, Richtung.West);

                if(istRaumKollisionsfrei(derzeitigerRaum.getRaumWest(),SpielAnzeige.räume)){
                    SpielAnzeige.räume.add(derzeitigerRaum.getRaumWest());
                    letzterRaum=derzeitigerRaum.getRaumWest();
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


        letzterRaum.setBossRaum(true);
        letzterRaum.setGegnerAnzahl(1);
        if(letzterRaum.hasNord()){
            letzterRaum.getRaumNord().findeTürObjekt(Richtung.Süd).wandleInBossTürUm();
        }
        if(letzterRaum.hasSüd()){
            letzterRaum.getRaumSüd().findeTürObjekt(Richtung.Nord).wandleInBossTürUm();
        }
        if(letzterRaum.hasOst()){
            letzterRaum.getRaumOst().findeTürObjekt(Richtung.West).wandleInBossTürUm();
        }
        if(letzterRaum.hasWest()){
            letzterRaum.getRaumWest().findeTürObjekt(Richtung.Ost).wandleInBossTürUm();
        }

        startraum.schließeTüren();
        SpielAnzeige.spieler1.ändereAktuellenRaum(startraum);

    }

    private void fügeSchlüsselHinzu(){
        int anzahlSchlüssel=0;
        do{
            Random rnd = new Random();
            int tmp = rnd.nextInt(SpielAnzeige.räume.size());
            if(SpielAnzeige.räume.get(tmp)!=null && !SpielAnzeige.räume.get(tmp).hatSchlüssel() && !SpielAnzeige.räume.get(tmp).equals(startraum)){
                SpielAnzeige.räume.get(tmp).setSchlüssel(true);
                anzahlSchlüssel++;
            }
        }while(anzahlSchlüssel<3);
        System.out.println("Anzahl Schlüssel: "+Integer.toString(anzahlSchlüssel));
    }

    /**
     * Prüft, ob der Raum mit einem bereits erzeugten Raum kollidiert
     */
    private boolean istRaumKollisionsfrei(Raum raum, ArrayList<Raum> räume){
        //prüft, ob der übergebene Raum mit einem Raum aus der Liste kollidiert
        for(Raum r : räume){
            if(r.kollidiertMit(raum)) return false;
        }

        return true;
    }
}
