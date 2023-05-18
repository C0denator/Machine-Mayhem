package com.programmierbeleg.machine_mayhem.SpielObjekte;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.programmierbeleg.machine_mayhem.Daten.FeldTyp;
import com.programmierbeleg.machine_mayhem.Spiel;

public class Feld extends SpielObjekt{

    private boolean laufbar;
    public Feld(FeldTyp typ, float x, float y) {
        super(x, y, 16, 16,
                true, "Feld");
        texturen=new TextureRegion[] {typ.getTexturen()[0]};
        laufbar=typ.isLaufbar();
    }

}
