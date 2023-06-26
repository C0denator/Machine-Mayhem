package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.programmierbeleg.machine_mayhem.Anzeigen.Hauptmenü;
import com.programmierbeleg.machine_mayhem.Anzeigen.SpielAnzeige;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner.Gegner;
import com.programmierbeleg.machine_mayhem.SpielObjekte.SpielObjekt;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Spieler;
import com.programmierbeleg.machine_mayhem.Welt.Raum;

public class Spiel extends Game {
	public static Spiel instanz;
	private BitmapFont bitmapFont;
	private SpriteBatch mainBatch;
	private boolean debug=true;
	public float delta;
	public TextureAtlas atlas;
	public ScreenAdapter aktiverBildschirm;

	public final int skalierung=3;

	public static Spiel starteSpiel() throws IllegalStateException{
		if(instanz==null){
			instanz=new Spiel();
		}else{
			throw new IllegalStateException("Nicht mehr als eine Spiel-Instanz erlaubt");
		}
		return instanz;
	}

	private Spiel(){
		//ist Singleton -> deswegen private
	}

	@Override
	public void create () {

		try{
			atlas = new TextureAtlas("assets/texturenAtlas.atlas");
		}catch(Exception e){
			System.err.println("FEHLER: Kein Texturen-Atlas gefunden");
			Gdx.app.exit();
		}

		bitmapFont = new BitmapFont();
		mainBatch = new SpriteBatch();
		aktiverBildschirm = (new Hauptmenü());
		aktiverBildschirm.show();
	}

	@Override
	public void render() {
		delta=Gdx.graphics.getDeltaTime();


		Gdx.gl.glClearColor(0.5f,0.4f,0.4f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		aktiverBildschirm.render(delta);

		mainBatch.begin();
		////////////////////////////////////////////////////////////
		renderDebug(mainBatch);
		////////////////////////////////////////////////////////////
		mainBatch.end();

	}

	public void renderDebug(SpriteBatch batch){
		int objekte =0;

		try{
			for(Raum r : SpielAnzeige.räume){
				objekte+=(r.getFelder().length*r.getFelder()[0].length);
			}

			objekte+=SpielAnzeige.spieler.size();
			objekte+=SpielAnzeige.gegner.size();
			objekte+=SpielAnzeige.projektile.size();
		}catch (NullPointerException e){
			objekte=-1;
		}

		if(debug){
			bitmapFont.draw(batch,Integer.toString(Gdx.graphics.getFramesPerSecond()),0,Gdx.graphics.getHeight());
			bitmapFont.draw(batch,"X: "+Integer.toString(Gdx.input.getX()),0,Gdx.graphics.getHeight()-12);
			bitmapFont.draw(batch,"Y: "+Integer.toString(Gdx.input.getY()),0,Gdx.graphics.getHeight()-24);
			bitmapFont.draw(batch,"Spielobjekte: "+objekte,0,Gdx.graphics.getHeight()-36);
		}
	}

	@Override
	public void dispose () {
		//this.dispose();
		System.out.println("Spiel wurde beendet");
	}

	@Override
	public void resize(int width, int height) {
		aktiverBildschirm.resize(width,height);
	}

}
