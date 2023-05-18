package com.programmierbeleg.machine_mayhem.Daten;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Spiel;

public enum FeldTyp {

    Boden_1(new TextureRegion[] {Spiel.instanz.atlas.findRegion("boden")},
        true),
    Wand_1(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_nord")},
            false),
    Tor(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_nord")},
            true),
    Unbekannt(new TextureRegion[] {Spiel.instanz.atlas.findRegion("wand_nord")},
            true),
    SpielerSpawn(null,false)
    ;
    private final TextureRegion[] texturen;
    private final boolean laufbar;

    private FeldTyp(TextureRegion[] texturen, boolean laufbar) {

        this.texturen = texturen;
        this.laufbar = laufbar;
    }

    public TextureRegion[] getTexturen() {
        return texturen;
    }

    public boolean isLaufbar() {
        return laufbar;
    }
}
