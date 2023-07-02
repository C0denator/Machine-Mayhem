package com.programmierbeleg.machine_mayhem.Daten;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Spiel;

/**
 * Eine Sammlung von Texturen
 */
public enum Texturen {
    //Die Textur die ein Feld hat

    Boden_1(Spiel.instanz.atlas.findRegion("boden")),
    TürZu(Spiel.instanz.atlas.findRegion("tür1_zu")),
    TürOffen(Spiel.instanz.atlas.findRegion("tür1_offen")),
    Wand_block(Spiel.instanz.atlas.findRegion("wand_block")),
    Wand_ecke(Spiel.instanz.atlas.findRegion("wand_ecke")),
    Wand_ende(Spiel.instanz.atlas.findRegion("wand_ende")),
    Wand_füllung(Spiel.instanz.atlas.findRegion("wand_füllung")),
    Wand_gerade(Spiel.instanz.atlas.findRegion("wand_gerade")),
    Batterie(Spiel.instanz.atlas.findRegion("Batterie")),
    Schlüssel(Spiel.instanz.atlas.findRegion("Schlüssel")),


    Unbekannt(Spiel.instanz.atlas.findRegion("Fragezeichen"));
    private final TextureRegion textur;

    private Texturen(TextureRegion textur) {
        this.textur = textur;
    }

    public TextureRegion getTexturRegion() {
        return textur;
    }

}
