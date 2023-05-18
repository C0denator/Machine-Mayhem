package com.programmierbeleg.machine_mayhem.Welt;

import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.FeldTyp;
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


    public Welt(int MaxRaumAnzahl)  {
        BufferedImage bild = null;
        try{
            bild = ImageIO.read(new File("assets/Räume/start.png"));
        }catch(IOException e){
            System.err.println("Startraum konnte nicht geladen erden");
        }

        startraum=erstelleStartRaum(bild);
        raumAnzahl++;

    }

    public Raum erstelleStartRaum(BufferedImage bild){
        Raum raum= new Raum();
        raum.setFelder(leseRaumPixel(bild));

        boolean startGefunden=false;
        for(int x=0; x<raum.getFelder().length; x++) {
            for(int y=0; y<raum.getFelder()[x].length; y++) {
                if(ermittleFeldtyp(raum.getFelder()[x][y])==FeldTyp.SpielerSpawn){
                    raum.setStart_x(x);
                    raum.setStart_y(y);
                    startGefunden=true;
                }
            }
        }
        if(!startGefunden)System.err.println("FEHLER: Kein SpielerSpawn gefunden");

        for(int x=0; x<raum.getFelder().length; x++) {
            for(int y=0; y<raum.getFelder()[x].length; y++) {
                int[] rgb=ermittleRGB(raum.getFelder()[x][y]);

                if ((rgb[0]==0 && rgb[1]==0) || (rgb[0]==255 && rgb[1]==255)){
                    //Pixel ist Wand oder Feld
                    SpielAnzeige.felder.add(new Feld(ermittleFeldtyp(raum.getFelder()[x][y])
                            ,(x-raum.getStart_x())*16*Spiel.instanz.skalierung,(-(y-raum.getStart_y()))*16*Spiel.instanz.skalierung));
                }else if (rgb[0]==255 && rgb[1]==0){
                    //Pixel ist Gegner
                    if(rgb[2]==0){
                        SpielAnzeige.gegner.add(new Fernkampf_1((x-raum.getStart_x())*16*Spiel.instanz.skalierung,(-(y-raum.getStart_y()))*16*Spiel.instanz.skalierung));
                        SpielAnzeige.felder.add(new Feld(findeBenachbartesFeld(raum.getFelder(),x,y)
                                ,(x-raum.getStart_x())*16*Spiel.instanz.skalierung,(-(y-raum.getStart_y()))*16*Spiel.instanz.skalierung));
                    }
                }else if(rgb[0]==0 && rgb[1]==255){
                    //Pixel ist Spieler
                    if(rgb[2]==0){
                        SpielAnzeige.spieler.add(new Spieler((x-raum.getStart_x())*16*Spiel.instanz.skalierung,(-(y-raum.getStart_y()))*16*Spiel.instanz.skalierung));
                        SpielAnzeige.felder.add(new Feld(findeBenachbartesFeld(raum.getFelder(),x,y)
                                ,(x-raum.getStart_x())*16*Spiel.instanz.skalierung,(-(y-raum.getStart_y()))*16*Spiel.instanz.skalierung));
                    }
                }

            }
        }

        return raum;
    }

    private int[][] leseRaumPixel(BufferedImage image) {
        if (image != null) {
            int[][] raumPixel=new int[image.getWidth()][image.getHeight()];

            for(int x=0; x<image.getWidth(); x++) {
                for(int y=0; y<image.getHeight(); y++) {
                    raumPixel[x][y]=image.getRGB(x, y);
                }
            }
            return raumPixel;
        }else{
            System.err.println("FEHLER: Bild ist leer");
            return null;
        }

    }

    private FeldTyp ermittleFeldtyp(int pixel){
        /*
        Integer:    0000 0000 0000 0000 0000 0000 0000 0000 = 32 Bit
                    ---- ---- RRRR RRRR GGGG GGGG BBBB BBBB
                    ---- ---- ---- ---- ---- ---- RRRR RRRR >> nach rechts verschieben
                                                  1111 1111 & logische Und-Verknüpfung (0xff = 256)
         */
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = (pixel) & 0xff;

        if(r==255 && g==255 && b==255) return FeldTyp.Boden_1;
        if(r==0 && g==0 && b==0) return FeldTyp.Wand_1;
        if(r==0 && g==255 && b==0) return FeldTyp.SpielerSpawn;

        System.err.println("Unbekanntes Feld");
        return FeldTyp.Unbekannt;
    }

    private int[] ermittleRGB(int feld){
        int[] rgb = new int[3];
        rgb[0] = (feld >> 16) & 0xff;
        rgb[1] = (feld >> 8) & 0xff;
        rgb[2] = (feld) & 0xff;
        return rgb;
    }

    private FeldTyp findeBenachbartesFeld(int[][] felder, int x, int y){
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
    }
}
