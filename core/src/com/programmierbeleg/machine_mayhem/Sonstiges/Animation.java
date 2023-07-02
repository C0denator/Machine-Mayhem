package com.programmierbeleg.machine_mayhem.Sonstiges;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.SpielObjekte.SpielObjekt;

/**
 * Kann als Attribut von Objekten verwendet werden, wenn diese Animationen implementieren sollen
 */
public class Animation implements EinmalProFrame {
    /**
     * die Klasse die eine Animation haben soll
     */
    private SpielObjekt objekt;

    /**
     * Alle einzelnen Texturen der Animation in geordneter Reihenfolge
     */
    private TextureRegion[] texturen;

    private int indexAktuelleTextur;
    /**
     * true: Animation wiederholt sich
     * false: Animation pausiert nach einem Durchlauf
     */
    private boolean loop;
    private boolean pausiert;
    /**
     * Animation wurde einmal durchlaufen
     */
    private boolean fertig;
    private float sekundenBisZumNächstenBild;
    private float aktuelleZeit;


    public Animation(SpielObjekt klasse, TextureRegion[] texturen, float sBisZumNächstenBild, boolean loop){
        this.objekt =klasse;
        this.texturen=texturen;
        this.sekundenBisZumNächstenBild =sBisZumNächstenBild;
        indexAktuelleTextur=0;
        aktuelleZeit=0.0f;
        pausiert=true;
        fertig=false;
        this.loop=loop;

        if(SpielAnzeige.physikObjekte==null){
            System.err.println("Fehler: SpielAnzeige.physikObjekte ist null");
        }else{
            SpielAnzeige.physikObjekte.add(this);
        }
    }

    @Override
    public void einmalProFrame(float delta) {
        if(!pausiert){
            aktuelleZeit+=delta;
            if(aktuelleZeit>sekundenBisZumNächstenBild){
                aktuelleZeit=0.0f;

                //letztes Bild im Array?
                if(indexAktuelleTextur<texturen.length-1){
                    //nein
                    indexAktuelleTextur++;
                    sendeTextur();
                }else{
                    //ja
                    if(loop){
                        indexAktuelleTextur=0;
                        sendeTextur();
                    }else{
                        stop();
                    }
                    fertig=true;
                }
            }
        }
    }

    private void sendeTextur(){
        objekt.setTextur(texturen[indexAktuelleTextur]);
    }

    public void start(){
        pausiert=false;
        sendeTextur();
    }

    public void starteVonVorn(){
        pausiert=false;
        fertig=false;
        indexAktuelleTextur=0;
        sendeTextur();
    }

    public void stop(){
        pausiert=true;
        fertig=false;
        indexAktuelleTextur=0;
    }

    public void pause(){
        pausiert=true;
    }

    public void setSekundenBisZumNächstenBild(float sekundenBisZumNächstenBild) {
        this.sekundenBisZumNächstenBild = sekundenBisZumNächstenBild;
    }

    public TextureRegion getTextur(){
        //wenn man manuell die aktuelle Textur bekommen will
        return texturen[indexAktuelleTextur];
    }

    public boolean isPausiert() {
        return pausiert;
    }

    public boolean isFertig() {
        return fertig;
    }
}
