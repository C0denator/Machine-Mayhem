package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Daten.FeldEigenschaft;
import com.programmierbeleg.machine_mayhem.Daten.FeldTextur;

public class Feld extends SpielObjekt{

    private boolean laufbar;
    public final FeldEigenschaft feldEigenschaft;
    public Feld(FeldTextur typ, FeldEigenschaft feldEigenschaft, float x, float y) {
        super(x, y, 16, 16,
                true, "Feld");
        this.feldEigenschaft = feldEigenschaft;
        texturen=typ.getTexturen();

    }

}
