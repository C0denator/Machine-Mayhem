package com.programmierbeleg.machine_mayhem.Sonstiges;

public class ID_Vergeber {

    public static ID_Vergeber instanz;
    private int zähler;

    private ID_Vergeber(){
        zähler=-1;
    }

    public int vergebeID(){
        zähler++;
        System.out.println("Id vergeben: "+Integer.toString(zähler));
        return zähler;
    }

    public static ID_Vergeber erstelleID_Vergeber(){
        if(instanz==null){
            instanz=new ID_Vergeber();
            return instanz;
        }else{
            throw new RuntimeException("Verkackt :(");
        }

    }

}
