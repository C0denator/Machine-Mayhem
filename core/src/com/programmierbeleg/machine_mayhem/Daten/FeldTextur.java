package com.programmierbeleg.machine_mayhem.Daten;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Spiel;

public enum FeldTextur {
    //Die Textur die ein Feld hat

    Boden_1(Spiel.instanz.atlas.findRegion("boden")),
    TürOffen(Spiel.instanz.atlas.findRegion("tür2_offen")),
    TürMitte(Spiel.instanz.atlas.findRegion("tür2_mitte")),
    TürZu(Spiel.instanz.atlas.findRegion("tür2_zu")),
    Wand_NSWO(Spiel.instanz.atlas.findRegion("wand_nswo")),

    Wand_NS(Spiel.instanz.atlas.findRegion("wand_ns")),

    Wand_WO(Spiel.instanz.atlas.findRegion("wand_wo")),
    Wand_NO(Spiel.instanz.atlas.findRegion("wand_no")),
    Wand_SO(Spiel.instanz.atlas.findRegion("wand_so")),
    Wand_SW(Spiel.instanz.atlas.findRegion("wand_sw")),
    Wand_NW(Spiel.instanz.atlas.findRegion("wand_nw")),
    Wand_NWO(Spiel.instanz.atlas.findRegion("wand_nwo")),
    Wand_NSO(Spiel.instanz.atlas.findRegion("wand_nso")),
    Wand_SWO(Spiel.instanz.atlas.findRegion("wand_swo")),
    Wand_NSW(Spiel.instanz.atlas.findRegion("wand_nsw")),
    Unbekannt(Spiel.instanz.atlas.findRegion("Fragezeichen"));
    private final TextureRegion textur;

    private FeldTextur(TextureRegion textur) {
        this.textur = textur;
    }

    public TextureRegion getTextur() {
        return textur;
    }

}
