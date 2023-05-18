package com.programmierbeleg.machine_mayhem.Daten;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Spiel;

public enum GegnerTyp {

    FERNKAMPF_1(10,
            50,
            new TextureRegion[] {Spiel.instanz.atlas.findRegion("robot",1),Spiel.instanz.atlas.findRegion("robot",2),Spiel.instanz.atlas.findRegion("robot",3)})
    ;
    private final int maxLeben;
    private final int geschwindigkeit;
    private final TextureRegion[] texturen;

    private GegnerTyp(int maxLeben, int geschwindigkeit, TextureRegion[] texturen) {
        this.maxLeben = maxLeben;
        this.geschwindigkeit = geschwindigkeit;
        this.texturen = texturen;
    }

    public int getMaxLeben() {
        return maxLeben;
    }

    public int getGeschwindigkeit() {
        return geschwindigkeit;
    }

    public TextureRegion[] getTexturen() {
        return texturen;
    }

}
