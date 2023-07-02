package com.programmierbeleg.machine_mayhem.Sonstiges;

import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Effekte.Effekt;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Gegner;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Item;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Projektile.Projektil;

import java.util.ArrayList;

public class LöschKlasse {

    private static ArrayList<Object> zuLöschendeObjekte = new ArrayList<Object>();

    public static void lösche(Object o){
        //fügt ein Element zur Warteschlange hinzu
        zuLöschendeObjekte.add(o);
    }

    public static void löschZyklus(){
        //wird am Ende des Game-Loops aufgerufen
        //löscht alle Objekte, die eine Referenz in "zuLöschendeObjekte" haben
        int löschenZähler=0;

        for(int i=0; i<zuLöschendeObjekte.size(); i++){
            if(zuLöschendeObjekte.get(i)!=null){
                if (zuLöschendeObjekte.get(i) instanceof Gegner){
                    for(int j=0; j< SpielAnzeige.gegner.size(); j++){
                        if(SpielAnzeige.gegner.get(j)!=null && SpielAnzeige.gegner.get(j).equals(zuLöschendeObjekte.get(i))){
                            SpielAnzeige.gegner.remove(j);
                        }
                    }

                    for(int j=0; j< SpielAnzeige.physikObjekte.size(); j++){
                        if(SpielAnzeige.physikObjekte.get(j)!=null && SpielAnzeige.physikObjekte.get(j).equals(zuLöschendeObjekte.get(i))){
                            SpielAnzeige.physikObjekte.remove(j);
                        }
                    }
                }

                if (zuLöschendeObjekte.get(i) instanceof Projektil){
                    for(int j=0; j< SpielAnzeige.projektile.size(); j++){
                        if(SpielAnzeige.projektile.get(j)!=null && SpielAnzeige.projektile.get(j).equals(zuLöschendeObjekte.get(i))){
                            SpielAnzeige.projektile.remove(j);
                        }
                    }

                    for(int j=0; j< SpielAnzeige.physikObjekte.size(); j++){
                        if(SpielAnzeige.physikObjekte.get(j)!=null && SpielAnzeige.physikObjekte.get(j).equals(zuLöschendeObjekte.get(i))){
                            SpielAnzeige.physikObjekte.remove(j);
                        }
                    }
                }

                if (zuLöschendeObjekte.get(i) instanceof Item){
                    for(int j=0; j< SpielAnzeige.items.size(); j++){
                        if(SpielAnzeige.items.get(j)!=null && SpielAnzeige.items.get(j).equals(zuLöschendeObjekte.get(i))){
                            SpielAnzeige.items.remove(j);
                        }
                    }

                    for(int j=0; j< SpielAnzeige.physikObjekte.size(); j++){
                        if(SpielAnzeige.physikObjekte.get(j)!=null && SpielAnzeige.physikObjekte.get(j).equals(zuLöschendeObjekte.get(i))){
                            SpielAnzeige.physikObjekte.remove(j);
                        }
                    }
                }

                if (zuLöschendeObjekte.get(i) instanceof Effekt){
                    for(int j=0; j< SpielAnzeige.effekte.size(); j++){
                        if(SpielAnzeige.effekte.get(j)!=null && SpielAnzeige.effekte.get(j).equals(zuLöschendeObjekte.get(i))){
                            SpielAnzeige.effekte.remove(j);
                        }
                    }

                    for(int j=0; j< SpielAnzeige.physikObjekte.size(); j++){
                        if(SpielAnzeige.physikObjekte.get(j)!=null && SpielAnzeige.physikObjekte.get(j).equals(zuLöschendeObjekte.get(i))){
                            SpielAnzeige.physikObjekte.remove(j);
                        }
                    }
                }
            }
        }

        zuLöschendeObjekte.clear();

    }

}
