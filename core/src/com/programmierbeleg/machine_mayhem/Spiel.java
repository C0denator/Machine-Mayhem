package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Game;

public class Spiel extends Game {
	public static Spiel instanz;

	public Spiel(){
		if(instanz==null){
			instanz=this;
		}
	}

	@Override
	public void create () {
		setScreen(new Hauptmenü());
	}

	
	@Override
	public void dispose () {
		//this.dispose();
	}
}
