package com.programmierbeleg.machine_mayhem.Welt;

import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.FeldEigenschaft;
import com.programmierbeleg.machine_mayhem.Daten.FeldTextur;
import com.programmierbeleg.machine_mayhem.Daten.Richtung;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Feld;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Fernkampf_1;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Gegner;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Spieler;
import com.badlogic.gdx.math.Rectangle;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Tür;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

public class Raum implements EinmalProFrame {
    private Raum RaumNord;
    private Raum RaumSüd;
    private Raum RaumWest;
    private Raum RaumOst;

    private int start_x;
    private int start_y;


    private Feld[][] felder;
    private ArrayList<Feld> gegnerSpawns;
    private ArrayList<Feld> potentielleTüren;
    private ArrayList<Tür> realeTüren;
    private ArrayList<Gegner> aktiveGegner;
    private float feldGröße;
    private int gegnerAnzahl;
    //die Anzahl an Gegner die besiegt werden müssen, bevor die Türen wieder geöffnet werden
    private boolean sichtbar;
    private boolean bossRaum;
    private boolean kampfAktiv;

    public Raum(BufferedImage image, Vector2 vector2){
        sichtbar=false;
        kampfAktiv=false;
        start_x=(int)vector2.x;
        start_y=(int)vector2.y;
        aktiveGegner = new ArrayList<>();
        potentielleTüren=new ArrayList<>();
        realeTüren=new ArrayList<>();
        gegnerAnzahl=5;
        erstelleRaumFelder(image, (int) vector2.x, (int) -vector2.y);
        SpielAnzeige.physikObjekte.add(this);
    }

    @Override
    public void einmalProFrame(float delta) {
        if(sichtbar && kampfAktiv){
            if(aktiveGegner.size()>0){
                for(Gegner g : aktiveGegner){
                    g.denke();
                }
            }else{
                if(gegnerAnzahl>0){
                    for(Feld f : gegnerSpawns){
                        if(gegnerAnzahl>0){
                            Gegner g = new Fernkampf_1(f.getX(),f.getY());
                            aktiveGegner.add(g);
                            SpielAnzeige.gegner.add(g);
                        }
                    }
                }else{
                    kampfAktiv=false;
                }
            }
        }
    }

    public boolean kollidiertMit(Raum r){
        //Prüft, ob der Raum mit dem übergebenen Raum kollidiert
        Rectangle r1 = new Rectangle(felder[0][0].getX(),felder[0][0].getY(),
                felder[0][0].getBreite()*felder.length,
                felder[0][0].getHöhe()*felder[0].length);

        Rectangle r2 = new Rectangle(r.felder[0][0].getX(),r.felder[0][0].getY(),
                r.felder[0][0].getBreite()*r.felder.length,
                r.felder[0][0].getHöhe()*r.felder[0].length);

        return r1.overlaps(r2);
    }

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
        }
    }

    public Feld findeTürObjekt(Richtung richtung){
        //gibt die Tür als Referenz zurück
        switch(richtung){
            case Nord:
                for(int x=0; x<felder.length; x++){
                    if(felder[x][0].getFeldEigenschaft()==FeldEigenschaft.Tür){
                        return  felder[x][0];
                    }
                }
            case Süd:
                for(int x=0; x<felder.length; x++){
                    if(felder[x][felder[0].length-1].getFeldEigenschaft()==FeldEigenschaft.Tür){
                        return  felder[x][felder[0].length-1];
                    }
                }
            case Ost:
                for(int y=0; y<felder[felder.length-1].length; y++){
                    if(felder[felder.length-1][y].getFeldEigenschaft()==FeldEigenschaft.Tür){
                        return  felder[felder.length-1][y];
                    }
                }
            case West:
                for(int y=0; y<felder[0].length; y++){
                    if(felder[0][y].getFeldEigenschaft()==FeldEigenschaft.Tür){
                        return  felder[0][y];
                    }
                }
            default:
                throw new IllegalArgumentException("Ungülige Richtung");
        }
    }

    public Raum fügeRaumAn(BufferedImage image, Richtung richtung){
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

    private static Vector2 berechneStartpunkt(Richtung richtung, Vector2 türVater, Vector2 türKind){
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

    private void erstelleRaumFelder(BufferedImage image, int start_x, int start_y) {
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
                                        (x+start_x)*16* Spiel.instanz.skalierung,
                                        (-y-start_y)*16*Spiel.instanz.skalierung,
                                        false);
                        ermittleRotation(felder[x][y],image,x,y);
                    } else if (g==255 && b==0) {
                        //Tür
                        felder[x][y]=new Tür
                                ((x+start_x)*16* Spiel.instanz.skalierung,
                                        (-y-start_y)*16*Spiel.instanz.skalierung,
                                        this);
                        potentielleTüren.add(felder[x][y]);
                        ermittleRotation(felder[x][y],image,x,y);
                    } else{
                        //FEHLER
                        felder[x][y]=new Feld
                                (FeldTextur.Unbekannt, FeldEigenschaft.Keine, this,
                                        (x+start_x)*16* Spiel.instanz.skalierung,
                                        (-y-start_y)*16*Spiel.instanz.skalierung,
                                        false);
                    }

                }else if(r==255){
                    //BODEN
                    if(g==255 && b==255){
                        //Normaler Boden
                        felder[x][y]=new Feld
                                (FeldTextur.Boden_1, FeldEigenschaft.Keine, this,
                                (x+start_x)*16* Spiel.instanz.skalierung,
                                        (-y-start_y)*16*Spiel.instanz.skalierung,
                                        true);
                    } else if (g==0 && b==0) {
                        //Normaler Boden mit Gegnerspawn
                        felder[x][y]=new Feld
                                (FeldTextur.Boden_1, FeldEigenschaft.Gegnerspawn, this,
                                        (x+start_x)*16* Spiel.instanz.skalierung,
                                        (-y-start_y)*16*Spiel.instanz.skalierung,
                                        true);
                        gegnerSpawns.add(felder[x][y]);
                    } else if (g==255 && b==0) {
                        //Normaler Boden mit Spielerspawn
                        felder[x][y]=new Feld
                                (FeldTextur.Boden_1, FeldEigenschaft.Spielerspawn, this,
                                        (x+start_x)*16* Spiel.instanz.skalierung,
                                        (-y-start_y)*16*Spiel.instanz.skalierung,
                                        true);
                        if(SpielAnzeige.spieler.size()<1){
                            Spieler spieler = new Spieler((x+start_x)*16* Spiel.instanz.skalierung,
                                    (-y-start_y)*16*Spiel.instanz.skalierung);
                            SpielAnzeige.spieler.add(spieler);
                            SpielAnzeige.physikObjekte.add(spieler);
                        }
                    }else{
                        //FEHLER
                        felder[x][y]=new Feld
                                (FeldTextur.Unbekannt, FeldEigenschaft.Keine, this,
                                        (x+start_x)*16* Spiel.instanz.skalierung,
                                        (-y-start_y)*16*Spiel.instanz.skalierung,
                                        false);
                    }
                }else{
                    //FEHLER
                    felder[x][y]=new Feld
                            (FeldTextur.Unbekannt, FeldEigenschaft.Keine, this,
                                    (x-start_x)*16* Spiel.instanz.skalierung,
                                    (-y-start_y)*16*Spiel.instanz.skalierung,
                                    false);
                }
                ///////////////////////////////////////

            }
        }
    }

    //private void erstelleRaumFelder(BufferedImage image, Vector2)

    private static void ermittleRotation(Feld feld, BufferedImage image, int x, int y){
        //ermittelt, ob und wie sich eine Textur drehen muss
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

    private FeldTextur ermittleFeldtextur(BufferedImage image, int x, int y){
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
            return FeldTextur.Wand_gerade;
        }else if(w==2){
            return FeldTextur.Wand_ecke;
        }else if(w==1){
            return FeldTextur.Wand_ende;
        }else if(w==0 || w==3 || w==4){
            return FeldTextur.Wand_block;
        }else{
            return FeldTextur.Unbekannt;
        }

    }

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

    private static FeldEigenschaft ermittleFeldeigenschaft(int[] rgb){
        int r = rgb[0];
        int g = rgb[1];
        int b = rgb[2];

        if(r==0 && g==255 && b==0) {
            return FeldEigenschaft.Tür;
        }else if(r==255 && g==0 && b==0){
            return FeldEigenschaft.Gegnerspawn;
        }else if(r==255 && g==255 && b==0){
            return FeldEigenschaft.Spielerspawn;
        }else{
            return FeldEigenschaft.Keine;
        }
    }

    public void raumBetreten(){
        sichtbar=true;
        if(gegnerAnzahl>0){
            kampfAktiv=true;
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

    public int getStart_x() {
        return start_x;
    }

    public void setStart_x(int start_x) {
        this.start_x = start_x;
    }

    public int getStart_y() {
        return start_y;
    }

    public void setStart_y(int start_y) {
        this.start_y = start_y;
    }


    public Feld[][] getFelder() {
        return felder;
    }

    public void setFelder(Feld[][] felder) {
        this.felder = felder;
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

    public void setSichtbar(boolean sichtbar) {
        this.sichtbar = sichtbar;
    }

    public boolean isBossRaum() {
        return bossRaum;
    }

    public void setBossRaum(boolean bossRaum) {
        this.bossRaum = bossRaum;
    }

    public boolean isKampfAktiv() {
        return kampfAktiv;
    }

    public void setKampfAktiv(boolean kampfAktiv) {
        this.kampfAktiv = kampfAktiv;
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
}
