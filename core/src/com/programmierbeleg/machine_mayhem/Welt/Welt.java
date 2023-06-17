package com.programmierbeleg.machine_mayhem.Welt;

import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.FeldTextur;
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

    }

    /*
    public Raum erstelleStartRaum(BufferedImage bild){
        Raum raum= new Raum();
        raum.setFelder(leseRaumPixel(bild));

        boolean startGefunden=false;
        for(int x=0; x<raum.getFelder().length; x++) {
            for(int y=0; y<raum.getFelder()[x].length; y++) {
                if(ermittleFeldtyp(raum.getFelder()[x][y])== FeldTextur.SpielerSpawn){
                    raum.setStart_x(x);
                    raum.setStart_y(y);
                    startGefunden=true;
                    break;
                }
            }
        }
        if(!startGefunden)System.err.println("FEHLER: Kein SpielerSpawn gefunden");

        for(int x=0; x<raum.getFelder().length; x++) {
            for(int y=0; y<raum.getFelder()[x].length; y++) {
                int[] rgb=ermittleRGB(raum.getFelder()[x][y]);

                if ((rgb[0]==0 && rgb[1]==0) || (rgb[0]==255 && rgb[1]==255)){
                    //Pixel ist Wand oder Feld
                    SpielAnzeige.räume.add(new Feld(ermittleFeldtyp(raum.getFelder()[x][y])
                            ,(x-raum.getStart_x())*16*Spiel.instanz.skalierung,(-(y-raum.getStart_y()))*16*Spiel.instanz.skalierung, feldEigenschaft));
                }else if (rgb[0]==255 && rgb[1]==0){
                    //Pixel ist Gegner
                    if(rgb[2]==0){
                        SpielAnzeige.gegner.add(new Fernkampf_1((x-raum.getStart_x())*16*Spiel.instanz.skalierung,(-(y-raum.getStart_y()))*16*Spiel.instanz.skalierung));
                        SpielAnzeige.räume.add(new Feld(findeBenachbartesFeld(raum.getFelder(),x,y)
                                ,(x-raum.getStart_x())*16*Spiel.instanz.skalierung,(-(y-raum.getStart_y()))*16*Spiel.instanz.skalierung, feldEigenschaft));
                    }
                }else if(rgb[0]==0 && rgb[1]==255){
                    //Pixel ist Spieler
                    if(rgb[2]==0){
                        SpielAnzeige.spieler.add(new Spieler((x-raum.getStart_x())*16*Spiel.instanz.skalierung,(-(y-raum.getStart_y()))*16*Spiel.instanz.skalierung));
                        SpielAnzeige.räume.add(new Feld(findeBenachbartesFeld(raum.getFelder(),x,y)
                                ,(x-raum.getStart_x())*16*Spiel.instanz.skalierung,(-(y-raum.getStart_y()))*16*Spiel.instanz.skalierung, feldEigenschaft));
                    }
                }

            }
        }

        return raum;
    }*/

    /*private FeldTyp findeBenachbartesFeld(int[][] felder, int x, int y){
        int[] rgb=ermittleRGB(felder[x][y+1]);
        if(rgb[0]==255 && rgb[1]==255){
            return ermittleFeldtyp(felder[x][y+1]);
        }

        rgb=ermittleRGB(felder[x][y-1]);
        if(rgb[0]==255 && rgb[1]==255){
            return ermittleFeldtyp(felder[x][y-1]);
        }

        rgb=ermittleRGB(felder[x+1][y]);
        if(rgb[0]==255 && rgb[1]==255){
            return ermittleFeldtyp(felder[x+1][y]);
        }

        rgb=ermittleRGB(felder[x-1][y]);
        if(rgb[0]==255 && rgb[1]==255){
            return ermittleFeldtyp(felder[x-1][y]);
        }

        System.err.println("FELER: Kein benachbartes laufbares Feld gefunden");
        return null;
    }*/
}
