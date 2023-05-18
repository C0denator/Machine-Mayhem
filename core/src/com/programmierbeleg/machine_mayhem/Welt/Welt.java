package com.programmierbeleg.machine_mayhem.Welt;

import com.programmierbeleg.machine_mayhem.Daten.FeldTyp;

import java.awt.image.BufferedImage;

public class Welt {
    private Raum startraum;
    private int RaumAnzahl;
    private int MaxRaumAnzahl;

    public void erstelleWelt(){

    }

    public void speichereWelt(Welt w){

    }

    private int[][] leseRaumPixel(BufferedImage image) {
        int w=image.getWidth();
        int h=image.getHeight();
        int[][] raum=new int[w-1][h-1];

        for(int x=0; x<w; x++) {
            for(int y=0; y<h; y++) {
                raum[x][y]=image.getRGB(x, y);
            }
        }
        return raum;
    }

    private FeldTyp ermittleFeldtyp(int pixel){
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = (pixel) & 0xff;

        if(r==255 && g==255 && b==255) return FeldTyp.Boden_1;

        System.err.println("Unbekanntes Feld");
        return FeldTyp.Unbekannt;
    }
}
