package com.programmierbeleg.machine_mayhem.Welt;

import com.badlogic.gdx.math.Vector2;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.FeldEigenschaft;
import com.programmierbeleg.machine_mayhem.Daten.FeldTextur;
import com.programmierbeleg.machine_mayhem.Spiel;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Feld;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Fernkampf_1;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Spieler;

import java.awt.image.BufferedImage;

public class Raum {
    private Raum RaumNord;
    private Raum RaumSüd;
    private Raum RaumWest;
    private Raum RaumOst;

    private int start_x;
    private int start_y;


    private Feld[][] felder;
    private int gegnerAnzahl;
    private boolean sichtbar;
    private boolean bossRaum;
    private boolean kampfAktiv;

    public Raum(BufferedImage image, int start_x, int start_y){
        sichtbar=true;
        kampfAktiv=false;
        erstelleRaumFelder(image, start_x, start_y);
    }

    public Raum(BufferedImage image){
        sichtbar=true;
        kampfAktiv=false;
        erstelleRaumFelder(image, 0, 0);
    }

    private void erstelleRaumFelder(BufferedImage image, int start_x, int start_y) {
        felder=new Feld[image.getWidth()][image.getHeight()];
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
                                (ermittleWandTextur(image,x,y), FeldEigenschaft.Keine,
                                        (x-start_x)*16* Spiel.instanz.skalierung,
                                        (-(y-start_y))*16*Spiel.instanz.skalierung);
                    } else if (g==255 && b==0) {
                        //Tür
                        felder[x][y]=new Feld
                                (ermittleWandTextur(image,x,y), FeldEigenschaft.Tür,
                                        (x-start_x)*16* Spiel.instanz.skalierung,
                                        (-(y-start_y))*16*Spiel.instanz.skalierung);
                    } else{
                        //FEHLER
                        felder[x][y]=new Feld
                                (FeldTextur.Unbekannt, FeldEigenschaft.Keine,
                                        (x-start_x)*16* Spiel.instanz.skalierung,
                                        (-(y-start_y))*16*Spiel.instanz.skalierung);
                    }

                }else if(r==255){
                    //BODEN
                    if(g==255 && b==255){
                        //Normaler Boden
                        felder[x][y]=new Feld
                                (FeldTextur.Boden_1, FeldEigenschaft.Keine,
                                (x-start_x)*16* Spiel.instanz.skalierung,
                                (-(y-start_y))*16*Spiel.instanz.skalierung);
                    } else if (g==0 && b==0) {
                        //Normaler Boden mit Gegnerspawn
                        felder[x][y]=new Feld
                                (FeldTextur.Boden_1, FeldEigenschaft.Gegnerspawn,
                                        (x-start_x)*16* Spiel.instanz.skalierung,
                                        (-(y-start_y))*16*Spiel.instanz.skalierung);
                        SpielAnzeige.gegner.add(new Fernkampf_1((x-start_x)*16* Spiel.instanz.skalierung,(-(y-start_y))*16*Spiel.instanz.skalierung));
                    } else if (g==255 && b==0) {
                        //Normaler Boden mit Spielerspawn
                        felder[x][y]=new Feld
                                (FeldTextur.Boden_1, FeldEigenschaft.Spielerspawn,
                                        (x-start_x)*16* Spiel.instanz.skalierung,
                                        (-(y-start_y))*16*Spiel.instanz.skalierung);
                        if(SpielAnzeige.spieler.size()<1){
                            SpielAnzeige.spieler.add(new Spieler((x-start_x)*16* Spiel.instanz.skalierung,(-(y-start_y))*16*Spiel.instanz.skalierung));
                        }
                    }else{
                        //FEHLER
                        felder[x][y]=new Feld
                                (FeldTextur.Unbekannt, FeldEigenschaft.Keine,
                                        (x-start_x)*16* Spiel.instanz.skalierung,
                                        (-(y-start_y))*16*Spiel.instanz.skalierung);
                    }
                }else{
                    //FEHLER
                    felder[x][y]=new Feld
                            (FeldTextur.Unbekannt, FeldEigenschaft.Keine,
                                    (x-start_x)*16* Spiel.instanz.skalierung,
                                    (-(y-start_y))*16*Spiel.instanz.skalierung);
                }
                ///////////////////////////////////////

            }
        }
    }

    private static FeldTextur ermittleWandTextur(BufferedImage image, int x, int y){
        //ermittelt, ob es sich um eine Ecke handelt
        boolean wandN=false;
        boolean wandS=false;
        boolean wandO=false;
        boolean wandW=false;
        if(y==0 || ermittleRGB(image.getRGB(x,(y-1)))[0]==0){
            wandN=true;
        }
        if(y==image.getHeight()-1 || ermittleRGB(image.getRGB(x,(y+1)))[0]==0){
            wandS=true;
        }
        if(x==0 || ermittleRGB(image.getRGB(x-1,y))[0]==0){
            wandW=true;
        }
        if(x==image.getWidth()-1 || ermittleRGB(image.getRGB(x+1,y))[0]==0){
            wandO=true;
        }

        if(wandN && wandS && wandO && wandW){
            return FeldTextur.Wand_NSWO;
        }else if(wandN && wandS){
            return FeldTextur.Wand_WO;
        }else if(wandW && wandO){
            return FeldTextur.Wand_NS;
        } else if (wandN && wandO) {
            return FeldTextur.Wand_SW;
        } else if (wandS && wandO) {
            return FeldTextur.Wand_NW;
        } else if (wandS && wandW) {
            return  FeldTextur.Wand_NO;
        } else if (wandN && wandW) {
            return FeldTextur.Wand_SO;
        }else if (wandS){
            return FeldTextur.Wand_NWO;
        }else if(wandW){
            return FeldTextur.Wand_NSO;
        }else if(wandN){
            return FeldTextur.Wand_SWO;
        }else if(wandO){
            return FeldTextur.Wand_NSW;
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
}
