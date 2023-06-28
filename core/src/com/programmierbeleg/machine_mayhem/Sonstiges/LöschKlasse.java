package com.programmierbeleg.machine_mayhem.Sonstiges;

import com.programmierbeleg.machine_mayhem.SpielObjekte.SpielObjekt;

import java.util.ArrayList;

public class LöschKlasse {

    public static void lösche(Object zuLöschendesObjekt, ArrayList<Object>[] übergebeneListen){
        //löscht in den übergebenen Listen die Referenzen auf das Objekt
        int debugZähler = 0;

        for(int i=0; i<übergebeneListen.length; i++){
            for(int j=0; j<übergebeneListen[i].size(); j++){

                if(übergebeneListen[i]!=null && übergebeneListen[i].get(j)!=null){
                    if(zuLöschendesObjekt.getClass().equals(übergebeneListen[i].get(j).getClass())){
                        if(zuLöschendesObjekt.equals(übergebeneListen[i].get(j))){
                            übergebeneListen[i].remove(j);
                            debugZähler++;
                        }
                    }
                }

            }
        }

        if(debugZähler!=übergebeneListen.length){
            System.err.print("Fehler beim Löschen des Objekts: "+zuLöschendesObjekt.toString()+" Debug: "+debugZähler);
        }
    }

}
