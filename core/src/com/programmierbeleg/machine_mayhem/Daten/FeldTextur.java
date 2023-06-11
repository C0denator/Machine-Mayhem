package com.programmierbeleg.machine_mayhem.Daten;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Spiel;

public enum FeldTextur {
    //Die Textur die ein Feld hat

    Boden_1(new TextureRegion[] {Spiel.instanz.atlas.findRegion("boden")}),
    Wand_NSWO(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_nswo")}),
    Wand_NS(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_ns")}),

    Wand_WO(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_wo")}),
    Wand_NO(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_no")}),
    Wand_SO(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_so")}),
    Wand_SW(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_sw")}),
    Wand_NW(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_nw")}),
    Wand_NWO(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_nwo")}),
    Wand_NSO(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_nso")}),
    Wand_SWO(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_swo")}),
    Wand_NSW(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_nsw")}),
    Unbekannt(new TextureRegion[] {Spiel.instanz.atlas.findRegion("Fragezeichen")})
    ;
    private final TextureRegion[] texturen;

    private FeldTextur(TextureRegion[] texturen) {
        this.texturen = texturen;
    }

    public TextureRegion[] getTexturen() {
        return texturen;
    }

}
