package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.Daten.ItemEigenschaft;
import com.programmierbeleg.machine_mayhem.Daten.Texturen;
import com.programmierbeleg.machine_mayhem.Interfaces.EinmalProFrame;
import com.programmierbeleg.machine_mayhem.Sonstiges.LöschKlasse;
import com.programmierbeleg.machine_mayhem.Spiel;

public class Item extends SpielObjekt implements EinmalProFrame {

    ItemEigenschaft itemEigenschaft;
    public Item(ItemEigenschaft eigenschaft, float x, float y) {

        super(x, y, 16, 16, 0.0f, true);

        this.itemEigenschaft=eigenschaft;

        if(eigenschaft==ItemEigenschaft.Batterie){
            textur= Texturen.Batterie.getTexturRegion();
        }else if(eigenschaft==ItemEigenschaft.Schlüssel){
            textur= Texturen.Schlüssel.getTexturRegion();
        }else{
            textur= Texturen.Unbekannt.getTexturRegion();
        }

        if(SpielAnzeige.physikObjekte==null){
            System.err.println("Fehler: SpielAnzeige.physikObjekte ist null");
        }else{
            SpielAnzeige.physikObjekte.add(this);
        }

    }

    @Override
    public void einmalProFrame(float delta) {
        if(kollidiertMit(SpielAnzeige.spieler1)){
            switch (itemEigenschaft){
                case Batterie:
                    SpielAnzeige.spieler1.heilen(25);
            }
            LöschKlasse.lösche(this);
        }
    }
}
