package com.programmierbeleg.machine_mayhem.Sonstiges;

/**
 * Eine Singleton-Klasse, welche eine universale ID vergibt.
 * Muss zu Spielbeginn instanziiert werden.
 */
public class ID_Vergeber {

    public static ID_Vergeber instanz;
    private int zähler;

    private ID_Vergeber(){
        zähler=-1;
    }

    /**
     * @return Universelle ID
     */
    public int vergebeID(){
        zähler++;
        //System.out.println("Id vergeben: "+Integer.toString(zähler));
        return zähler;
    }

    public static ID_Vergeber erstelleID_Vergeber(){
        if(instanz==null){
            instanz=new ID_Vergeber();
            return instanz;
        }else{
            throw new RuntimeException("Nicht mehr als eine ID-Instanz erlaubt");
        }

    }

}
