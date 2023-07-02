package com.programmierbeleg.machine_mayhem.Welt;

import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.FeldEigenschaft;
import com.programmierbeleg.machine_mayhem.Daten.ItemEigenschaft;
import com.programmierbeleg.machine_mayhem.Daten.Texturen;
import com.programmierbeleg.machine_mayhem.Daten.Richtung;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.ID_Vergeber;
import com.programmierbeleg.machine_mayhem.SpielObjekte.*;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Boss;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Fernkampf_1;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Schrot_1;
import com.badlogic.gdx.math.Rectangle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Ein Raum ist eine Sammlung aus mehreren Feldern. Er kann Referenzen auf bis zu 4 weitere Räume haben
 */
public class Raum implements EinmalProFrame {
    private Raum RaumNord;
    private Raum RaumSüd;
    private Raum RaumWest;
    private Raum RaumOst;

    /**
     * Die Position des Startpunktes (notwendig für Weltgenerierung)
     */
    private int start_x;
    /**
     * Die Position des Startpunktes (notwendig für Weltgenerierung)
     */
    private int start_y;

    public final int ID;


    /**
     * Alle felder des Raums.
     */
    private Feld[][] felder;
    /**
     * Alle Spawnpunkte für Gegner des Raums
     */
    private ArrayList<Feld> gegnerSpawns;
    /**
     * Alle Türen des Raums
     */
    private ArrayList<Tür> türen;

    /**
     * Die Anzahl an Gegnern, die besiegt werden müssen, damit sich die Türen wieder öffnen.
     */
    private int gegnerAnzahl;
    /**
     * Die Position des zuletzt besiegten Gegners. Dort können Items spawnen.
     */
    private Vector2 positionLetzterGegner;
    private boolean sichtbar;
    private boolean bossRaum;
    private boolean kampfAktiv;
    /**
     * Soll ein Schlüssel nach Besiegen der Gegner gespawnt werden?
     */
    private boolean hatSchlüssel;

    public Raum(BufferedImage image, Vector2 vector2){
        sichtbar=false;
        kampfAktiv=false;
        bossRaum=false;
        hatSchlüssel=false;
        ID= ID_Vergeber.instanz.vergebeID();
        start_x=(int)vector2.x;
        start_y=(int)vector2.y;
        türen =new ArrayList<>();
        gegnerAnzahl=0;
        erstelleRaumFelder(image, (int) vector2.x, (int) -vector2.y);

        if(SpielAnzeige.physikObjekte==null){
            System.err.println("Fehler: SpielAnzeige.physikObjekte ist null");
        }else{
            SpielAnzeige.physikObjekte.add(this);
        }

    }

    @Override
    public void einmalProFrame(float delta) {
        if(sichtbar && !bossRaum && kampfAktiv){
            if(gegnerAnzahl<=0){
                kampfAktiv=false;
                öffneTüren();
                if(getRaumNord()!=null){
                    getRaumNord().öffneTüren();
                }
                if(getRaumSüd()!=null){
                    getRaumSüd().öffneTüren();
                }
                if(getRaumWest()!=null){
                    getRaumWest().öffneTüren();
                }
                if(getRaumOst()!=null){
                    getRaumOst().öffneTüren();
                }

                if(hatSchlüssel){
                    SpielAnzeige.items.add(new Item(ItemEigenschaft.Schlüssel,
                            positionLetzterGegner.x, positionLetzterGegner.y));
                    SpielAnzeige.spieler1.setChanceAufItem(SpielAnzeige.spieler1.getChanceAufItem()+25);
                }else{
                    Random rnd = new Random();
                    if(SpielAnzeige.spieler1.getChanceAufItem()>=(rnd.nextInt(99)+1)){
                        SpielAnzeige.spieler1.setChanceAufItem(0);
                        SpielAnzeige.items.add(new Item(ItemEigenschaft.Batterie,
                                positionLetzterGegner.x, positionLetzterGegner.y));
                    }else{
                        SpielAnzeige.spieler1.setChanceAufItem(SpielAnzeige.spieler1.getChanceAufItem()+25);
                    }
                }
            }
        }
    }

    private void spawneGegner(){
        for(Feld f : gegnerSpawns){
            switch (f.getFeldEigenschaft()){
                case FernkampfSpawn:
                    SpielAnzeige.gegner.add(new Fernkampf_1(f.getX(),f.getY(),this));
                    gegnerAnzahl++;
                    break;
                case NahkampfSpawn:
                    SpielAnzeige.gegner.add(new Schrot_1(f.getX(),f.getY(),this));
                    gegnerAnzahl++;
                    break;
                case BossSpawn:
                    SpielAnzeige.gegner.add(new Boss(f.getX(),f.getY(),f.getRaum()));
                    gegnerAnzahl++;
            }
        }
    }

    public void raumBetreten(Raum vorherigerRaum){
        if(!sichtbar){
            sichtbar=true;
            kampfAktiv=true;

            if(getRaumNord()!=null && vorherigerRaum.equals(getRaumNord())){
                vorherigerRaum.schließeTür(Richtung.Süd);
                schließeTür(Richtung.Süd);
                schließeTür(Richtung.West);
                schließeTür(Richtung.Ost);
            }else if(getRaumSüd()!=null && vorherigerRaum.equals(getRaumSüd())){
                vorherigerRaum.schließeTür(Richtung.Nord);
                schließeTür(Richtung.Nord);
                schließeTür(Richtung.West);
                schließeTür(Richtung.Ost);
            }else if(getRaumOst()!=null && vorherigerRaum.equals(getRaumOst())){
                vorherigerRaum.schließeTür(Richtung.West);
                schließeTür(Richtung.Nord);
                schließeTür(Richtung.Süd);
                schließeTür(Richtung.West);
            }else if(getRaumWest()!=null && vorherigerRaum.equals(getRaumWest())){
                vorherigerRaum.schließeTür(Richtung.Ost);
                schließeTür(Richtung.Nord);
                schließeTür(Richtung.Süd);
                schließeTür(Richtung.Ost);
            }
            spawneGegner();
        }
    }

    public void öffneTüren(){
        if(getRaumNord()!=null){
            öffneTür(Richtung.Nord);
        }
        if(getRaumSüd()!=null){
            öffneTür(Richtung.Süd);
        }
        if(getRaumWest()!=null){
            öffneTür(Richtung.West);
        }
        if(getRaumOst()!=null){
            öffneTür(Richtung.Ost);
        }
    }

    public void schließeTüren(){
        for(Tür t : türen){
            t.schließen();
        }
    }

    public void schließeTür(Richtung richtung){
        findeTürObjekt(richtung).schließen();
    }

    public void öffneTür(Richtung richtung){
        if(!findeTürObjekt(richtung).isBossTür()){
            findeTürObjekt(richtung).öffnen();
        }
    }


    /**
     * Prüft, ob der Raum mit dem übergebenen Raum kollidiert.
     */
    public boolean kollidiertMit(Raum r){

        Rectangle r1 = new Rectangle(felder[0][0].getX(),felder[0][0].getY(),
                felder[0][0].getBreite()*felder.length,
                felder[0][0].getHöhe()*felder[0].length);

        Rectangle r2 = new Rectangle(r.felder[0][0].getX(),r.felder[0][0].getY(),
                r.felder[0][0].getBreite()*r.felder.length,
                r.felder[0][0].getHöhe()*r.felder[0].length);

        return r1.overlaps(r2);
    }


    /**
     * Prüft, ob das übergebene Spielobjekt mit einem nicht laufbaren Feld des Raums kollidiert.
     */
    public boolean kollidiertMitWand(SpielObjekt objekt){

        for(int x=0; x<felder.length; x++){
            for(int y=0; y<felder[x].length; y++){
                if(!felder[x][y].isLaufbar() && felder[x][y].kollidiertMit(objekt)) return true;
            }
        }

        return false;
    }

    /**
     * Prüft, ob der übergebene Spieler eine Tür des Raumes berührt (wichtig für den Raumwechsel)
     */
    public boolean kollidiertMitTüren(Spieler s){
        Rectangle r1 = new Rectangle(s.getX(),s.getY(),s.getBreite(),s.getHöhe());
        Rectangle r2;

        for(Tür t : türen){
            r2 = new Rectangle(t.getX(),t.getY(),t.getBreite(),t.getHöhe());
            if(r1.overlaps(r2)) return true;
        }

        return false;
    }

    /**
     * Gibt die Stelle der Tür in Pixeln zurück.
     * @param richtung die Richtung, wo die Tür gesucht werden soll
     */
    public Vector2 findeTür(Richtung richtung){
        //gibt die Stelle der Tür in Pixeln/Feldern zurück
        switch(richtung){
            case Nord:
                for(int x=0; x<felder.length; x++){
                    if(felder[x][0].getFeldEigenschaft()==FeldEigenschaft.Tür){

                        return new Vector2(start_x+x,start_y+0);
                    }
                }
            case Süd:
                for(int x=0; x<felder.length; x++){
                    if(felder[x][felder[x].length-1].getFeldEigenschaft()==FeldEigenschaft.Tür){

                        return new Vector2(start_x+x,start_y+felder[x].length-1);
                    }
                }
            case Ost:
                for(int y=0; y<felder.length; y++){
                    if(felder[felder.length-1][y].getFeldEigenschaft()==FeldEigenschaft.Tür){

                        return new Vector2(start_x+felder.length-1,-start_y+y);
                    }
                }
            case West:
                for(int y=0; y<felder.length; y++){
                    if(felder[0][y].getFeldEigenschaft()==FeldEigenschaft.Tür){

                        return new Vector2(start_x+0,-start_y+y);
                    }
                }
            default:
                throw new IllegalArgumentException("Ungülige Richtung");
        }
    }

    /**
     * Gibt die Stelle der Tür in Pixeln zurück.
     * @param image Das Bild des Raumes
     * @param richtung die Richtung, wo die Tür gesucht werden soll
     */
    public Vector2 findeTür(Richtung richtung, BufferedImage image){
        //gibt die Stelle der Tür in Pixeln zurück
        switch(richtung){
            case Nord:
                for(int x=0; x<image.getWidth(); x++){
                    if(ermittleFeldeigenschaft(ermittleRGB(image.getRGB(x,0)))==FeldEigenschaft.Tür){

                        return new Vector2(x,0);
                    }
                }
            case Süd:
                for(int x=0; x<image.getWidth(); x++){
                    if(ermittleFeldeigenschaft(ermittleRGB(image.getRGB(x,image.getHeight()-1)))==FeldEigenschaft.Tür){

                        return new Vector2(x,image.getHeight()-1);
                    }
                }
            case Ost:
                for(int y=0; y<image.getHeight(); y++){
                    if(ermittleFeldeigenschaft(ermittleRGB(image.getRGB(image.getWidth()-1,y)))==FeldEigenschaft.Tür){

                        return new Vector2(image.getWidth()-1,y);
                    }
                }
            case West:
                for(int y=0; y<image.getHeight(); y++){
                    if(ermittleFeldeigenschaft(ermittleRGB(image.getRGB(0,y)))==FeldEigenschaft.Tür){

                        return new Vector2(0,y);
                    }
                }
            default:
                throw new IllegalArgumentException("Ungülige Richtung");
                //System.err.println("Ungültige Richtung");
                //return new Vector2();
        }
    }


    /**
     * Gibt die Tür als Referenz zurück.
     * @param richtung die Richtung, wo die Tür gesucht werden soll
     */
    public Tür findeTürObjekt(Richtung richtung){
        switch(richtung){
            case Nord:
                for(int x=0; x<felder.length; x++){
                    if(felder[x][0].getFeldEigenschaft()==FeldEigenschaft.Tür){
                        return (Tür) felder[x][0];
                    }
                }
            case Süd:
                for(int x=0; x<felder.length; x++){
                    if(felder[x][felder[0].length-1].getFeldEigenschaft()==FeldEigenschaft.Tür){
                        return (Tür) felder[x][felder[0].length-1];
                    }
                }
            case Ost:
                for(int y=0; y<felder[felder.length-1].length; y++){
                    if(felder[felder.length-1][y].getFeldEigenschaft()==FeldEigenschaft.Tür){
                        return (Tür) felder[felder.length-1][y];
                    }
                }
            case West:
                for(int y=0; y<felder[0].length; y++){
                    if(felder[0][y].getFeldEigenschaft()==FeldEigenschaft.Tür){
                        return (Tür) felder[0][y];
                    }
                }
            default:
                throw new IllegalArgumentException("Ungülige Richtung");
        }
    }

    /**
     * Fügt einen Raum an diesen Raum an
     * @param image Das Bild des Raumes, der angefügt werden soll
     * @param richtung Die Richtung, in die der Raum angefügt werden soll
     */
    public Raum fügeRaumAn(BufferedImage image, Richtung richtung){
        //fügt einen Raum an (! Abfrage nach Kollision muss vorher erfolgen)
        Vector2 türVater;
        Vector2 türKind;
        Vector2 startpunktKind;

        türVater=findeTür(richtung);
        türKind=findeTür(Richtung.GegenRichtung(richtung), image);

        startpunktKind=berechneStartpunkt(richtung,türVater,türKind);

        switch (richtung){
            case Nord:
                RaumNord=new Raum(image,startpunktKind);
                RaumNord.setRaumSüd(this);
                return RaumNord;

            case Süd:
                RaumSüd=new Raum(image,startpunktKind);
                RaumSüd.setRaumNord(this);
                return RaumSüd;

            case West:
                RaumWest=new Raum(image,startpunktKind);
                RaumWest.setRaumOst(this);
                return RaumWest;

            case Ost:
                RaumOst=new Raum(image,startpunktKind);
                RaumOst.setRaumWest(this);
                return RaumOst;

        }

        return null;
    }

    /**
     * Gibt den Startpunkt des zu generierenden Raumes zurück. Ist abhängig von der Richtung.
     * @param türVater Koordinaten der Tür des alten Raumes.
     * @param türKind Koordinaten der Tür des neuen Raumes.
     * @return
     */
    private static Vector2 berechneStartpunkt(Richtung richtung, Vector2 türVater, Vector2 türKind){
        //ermittelt den Startpunkt für die Erstellung eines zukünftigen Raumes anhand der Türen von Vater und (zukünftiges) Kind
        //in Pixel
        switch (richtung){
            case Nord:

                return new Vector2(türVater.x-türKind.x,türVater.y+25);

            case Süd:

                return new Vector2(türVater.x-türKind.x,türVater.y-49);

            case West:

                return new Vector2(türVater.x-25,türKind.y-türVater.y);

            case Ost:

                return new Vector2(türVater.x+1,türKind.y-türVater.y);

        }

        throw new RuntimeException("Startpunkt verkackt");
    }

    /**
     * Liest alle Pixel des übergebenen Bildes, und erstellt anhand der RGB-Werte die einzelnen Spielobjekte
     * @param image Das Bild des Raumes
     * @param start_x Der Startpunkt des Raumes
     * @param start_y Der Startpunkt des Raumes
     */
    private void erstelleRaumFelder(BufferedImage image, int start_x, int start_y) {
        //dekodiert das übergebene Bild, und erstellt damit alle Felder des Raumes

        felder=new Feld[image.getWidth()][image.getHeight()];
        gegnerSpawns = new ArrayList<>();

        int[] rgb = new int[3];
        int r;
        int g;
        int b;
        for(int x=0; x<image.getWidth(); x++) {
            for(int y=0; y<image.getHeight(); y++) {
                rgb=ermittleRGB(image.getRGB(x,y));
                r=rgb[0];
                g=rgb[1];
                b=rgb[2];
                ///////////////////////////////////////
                if(r==0){
                    //WAND
                    if(g==0 && b==0){
                        //Normale Wand
                        felder[x][y]=new Feld
                                (ermittleFeldtextur(image,x,y), FeldEigenschaft.Keine, this,
                                        (x+start_x)*16,
                                        (-y-start_y)*16,
                                        false);
                        setzeRotation(felder[x][y],image,x,y);
                    } else if (g==255 && b==0) {
                        //Tür
                        felder[x][y]=new Tür
                                ((x+start_x)*16,
                                        (-y-start_y)*16,
                                        this, false);
                        türen.add((Tür)felder[x][y]);
                        setzeRotation(felder[x][y],image,x,y);
                    } else{
                        //FEHLER
                        felder[x][y]=new Feld
                                (Texturen.Unbekannt, FeldEigenschaft.Keine, this,
                                        (x+start_x)*16,
                                        (-y-start_y)*16,
                                        true);
                    }

                }else if(r==255){
                    //BODEN
                    if(g==255 && b==255){
                        //Normaler Boden
                        felder[x][y]=new Feld
                                (Texturen.Boden_1, FeldEigenschaft.Keine, this,
                                (x+start_x)*16,
                                        (-y-start_y)*16,
                                        true);
                    } else if (g==0 && b==0) {
                        //Normaler Boden mit FernkampfSpawn
                        felder[x][y]=new Feld
                                (Texturen.Boden_1, FeldEigenschaft.FernkampfSpawn, this,
                                        (x+start_x)*16,
                                        (-y-start_y)*16,
                                        true);
                        gegnerSpawns.add(felder[x][y]);
                    }else if (g==100 && b==0) {
                        //Normaler Boden mit NahkampfSpawn
                        felder[x][y]=new Feld
                                (Texturen.Boden_1, FeldEigenschaft.NahkampfSpawn, this,
                                        (x+start_x)*16,
                                        (-y-start_y)*16,
                                        true);
                        gegnerSpawns.add(felder[x][y]);
                    }else if (g==0 && b==255) {
                        //Normaler Boden mit BossSpawn
                        felder[x][y]=new Feld
                                (Texturen.Boden_1, FeldEigenschaft.BossSpawn, this,
                                        (x+start_x)*16,
                                        (-y-start_y)*16,
                                        true);
                        gegnerSpawns.add(felder[x][y]);
                    } else if (g==255 && b==0) {
                        //Normaler Boden mit Spielerspawn
                        felder[x][y]=new Feld
                                (Texturen.Boden_1, FeldEigenschaft.SpielerSpawn, this,
                                        (x+start_x)*16,
                                        (-y-start_y)*16,
                                        true);
                        if(SpielAnzeige.spieler1==null){
                            SpielAnzeige.spieler1 = new Spieler((x+start_x)*16,
                                    (-y-start_y)*16, this);
                        }
                    } else{
                        //FEHLER
                        felder[x][y]=new Feld
                                (Texturen.Unbekannt, FeldEigenschaft.Keine, this,
                                        (x+start_x)*16,
                                        (-y-start_y)*16,
                                        true);
                    }
                }else{
                    //FEHLER
                    felder[x][y]=new Feld
                            (Texturen.Unbekannt, FeldEigenschaft.Keine, this,
                                    (x-start_x)*16,
                                    (-y-start_y)*16,
                                    true);
                }
                ///////////////////////////////////////

            }
        }
    }

    //private void erstelleRaumFelder(BufferedImage image, Vector2)

    /**
     * Rotiert die übergebenen Felder, wenn nötig. (damit es schön aussieht)
     */
    private static void setzeRotation(Feld feld, BufferedImage image, int x, int y){
        //ermittelt, ob und wie sich das übergebene Feld drehen muss
        boolean wandN=false;
        boolean wandS=false;
        boolean wandO=false;
        boolean wandW=false;
        if(y>0 && ermittleRGB(image.getRGB(x,(y-1)))[0]==0){
            wandN=true;
        }
        if(y<image.getHeight()-1 && ermittleRGB(image.getRGB(x,(y+1)))[0]==0){
            wandS=true;
        }
        if(x>0 && ermittleRGB(image.getRGB(x-1,y))[0]==0){
            wandW=true;
        }
        if(x<image.getWidth()-1 && ermittleRGB(image.getRGB(x+1,y))[0]==0){
            wandO=true;
        }

        if(feld.getFeldEigenschaft()==FeldEigenschaft.Tür){
            if(wandN && wandS){
                feld.setWinkel(90);
            }
        }else{
            switch (feld.getFeldTextur()){
                case Wand_gerade:
                    if(wandN && wandS){
                        feld.setWinkel(90);
                    }
                    break;
                case Wand_ecke:
                    if(wandW && wandN){
                        feld.setWinkel(-90);
                    }else if(wandN && wandO){
                        feld.setWinkel(-180);
                    }else if(wandO && wandS){
                        feld.setWinkel(-270);
                    }
                    break;
                case Wand_ende:
                    if(wandN){
                        feld.setWinkel(-90);
                    }else if(wandO){
                        feld.setWinkel(-180);
                    }else if(wandS){
                        feld.setWinkel(-270);
                    }
            }
        }


    }

    /**
     * Ermittelt anhand des RGB-Wertes, welche Textur das übergebene Feld bekommen soll
     */
    private Texturen ermittleFeldtextur(BufferedImage image, int x, int y){
        //ermittelt die richtige Textur für das Feld
        boolean wandN=false;
        boolean wandS=false;
        boolean wandO=false;
        boolean wandW=false;
        int w=0;
        if(y>0 && ermittleRGB(image.getRGB(x,(y-1)))[0]==0){
            wandN=true;
            w++;
        }
        if(y<image.getHeight()-1 && ermittleRGB(image.getRGB(x,(y+1)))[0]==0){
            wandS=true;
            w++;
        }
        if(x>0 && ermittleRGB(image.getRGB(x-1,y))[0]==0){
            wandW=true;
            w++;
        }
        if(x<image.getWidth()-1 && ermittleRGB(image.getRGB(x+1,y))[0]==0){
            wandO=true;
            w++;
        }

        if((wandN && wandS && !wandO && !wandW) || (!wandN && !wandS && wandO && wandW)){
            return Texturen.Wand_gerade;
        }else if(w==2){
            return Texturen.Wand_ecke;
        }else if(w==1){
            return Texturen.Wand_ende;
        }else if(w==0 || w==3 || w==4){
            return Texturen.Wand_block;
        }else{
            return Texturen.Unbekannt;
        }

    }

    /**
     * Gibt einen Array mit einem Rot-, Gelb- und Grün-Anteil zurück
     * @param feld der Pixel als Integer-Wert
     */
    private static int[] ermittleRGB(int feld){
        /*
        Integer:    0000 0000 0000 0000 0000 0000 0000 0000 = 32 Bit
                    ---- ---- RRRR RRRR GGGG GGGG BBBB BBBB
                    ---- ---- ---- ---- ---- ---- RRRR RRRR >> nach rechts verschieben
                                                  1111 1111 & logische Und-Verknüpfung (0xff = 256)
         */
        int[] rgb = new int[3];
        rgb[0] = (feld >> 16) & 0xff;
        rgb[1] = (feld >> 8) & 0xff;
        rgb[2] = (feld) & 0xff;
        return rgb;
    }

    /**
     * Ermittelt anhand des RGB-Wertes die Feldeigenschaft
     * @param rgb die RGB-Werte als Array
     */
    private static FeldEigenschaft ermittleFeldeigenschaft(int[] rgb){
        int r = rgb[0];
        int g = rgb[1];
        int b = rgb[2];

        if(r==0 && g==255 && b==0) {
            return FeldEigenschaft.Tür;
        }else if(r==255 && g==0 && b==0){
            return FeldEigenschaft.FernkampfSpawn;
        }else if(r==255 && g==255 && b==0){
            return FeldEigenschaft.SpielerSpawn;
        }else{
            return FeldEigenschaft.Keine;
        }
    }

    public Raum getRaumNord() {
        return RaumNord;
    }

    public void setRaumNord(Raum raumNord) {
        RaumNord = raumNord;
    }

    public Raum getRaumSüd() {
        return RaumSüd;
    }

    public void setRaumSüd(Raum raumSüd) {
        RaumSüd = raumSüd;
    }

    public Raum getRaumWest() {
        return RaumWest;
    }

    public void setRaumWest(Raum raumWest) {
        RaumWest = raumWest;
    }

    public Raum getRaumOst() {
        return RaumOst;
    }

    public void setRaumOst(Raum raumOst) {
        RaumOst = raumOst;
    }

    public Feld[][] getFelder() {
        return felder;
    }

    public int getGegnerAnzahl() {
        return gegnerAnzahl;
    }

    public void setGegnerAnzahl(int gegnerAnzahl) {
        this.gegnerAnzahl = gegnerAnzahl;
    }

    public boolean isSichtbar() {
        return sichtbar;
    }

    public void setBossRaum(boolean bossRaum) {
        this.bossRaum = bossRaum;
    }

    public boolean isKampfAktiv() {
        return kampfAktiv;
    }

    public boolean hasNord(){
        return RaumNord!=null;
    }

    public boolean hasSüd(){
        return RaumSüd!=null;
    }

    public boolean hasOst(){
        return RaumOst!=null;
    }

    public boolean hasWest(){
        return RaumWest!=null;
    }

    public void setPositionLetzterGegner(Vector2 positionLetzterGegner) {
        this.positionLetzterGegner = positionLetzterGegner;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Raum){
            return ID==((Raum) obj).ID;
        }else{
            return false;
        }
    }

    public boolean hatSchlüssel() {
        return hatSchlüssel;
    }

    public void setSchlüssel(boolean hatSchlüssel) {
        this.hatSchlüssel = hatSchlüssel;
    }
}
