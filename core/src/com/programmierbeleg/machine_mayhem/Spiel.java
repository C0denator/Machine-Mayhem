package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.programmierbeleg.machine_mayhem.Anzeigen.Hauptmenü;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Feld;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Gegner;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Projektil;
import com.programmierbeleg.machine_mayhem.SpielObjekte.Spieler;

import java.util.ArrayList;

public class Spiel extends Game {
	public static Spiel instanz;
	private BitmapFont bitmapFont;
	private SpriteBatch mainBatch;
	private boolean debug=true;
	public float delta;
	public TextureAtlas atlas;
	public ArrayList<Feld> felder;
	public ArrayList<Spieler> spieler;
	public ArrayList<Gegner> gegner;
	public ArrayList<Projektil> projektile;
	public ScreenAdapter aktiverBildschirm;

	public Spiel(){
		//setScreen(new Hauptmenü());
		//atlas = new TextureAtlas("assets/texturenAtlas.atlas");
		if(instanz==null){
			instanz=this;
		}else {
			System.err.println("FEHLER: Spiel läuft bereits");
			Gdx.app.exit();
		}
	}

	@Override
	public void create () {
		atlas = new TextureAtlas("assets/texturenAtlas.atlas");
		bitmapFont = new BitmapFont();
		mainBatch = new SpriteBatch();
		aktiverBildschirm = (new Hauptmenü());
		aktiverBildschirm.show();
	}

	@Override
	public void render() {
		update();
		Gdx.gl.glClearColor(0.4f,0.4f,0.4f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mainBatch.begin();
		////////////////////////////////////////////////////////////
		if(felder!=null) {
			for (int i = 0; i < felder.size(); i++) ;
		}
		if(spieler!=null) {
			for (int i = 0; i < spieler.size(); i++) {
				if(spieler.get(i).isSichtbar()) mainBatch.draw(spieler.get(i).getRegions()[0], spieler.get(i).getX(), spieler.get(i).getY(), spieler.get(i).getBreite(), spieler.get(i).getHöhe());

			}
		}
		if(gegner!=null) {
			for (int i = 0; i < gegner.size(); i++) {
				if(gegner.get(i).isSichtbar()) mainBatch.draw(gegner.get(i).getRegions()[0], gegner.get(i).getX(), gegner.get(i).getY(), gegner.get(i).getBreite(), gegner.get(i).getHöhe());

			}
		}
		if(projektile!=null) {
			for (int i = 0; i < projektile.size(); i++) ;
		}

		aktiverBildschirm.render(delta);
		renderDebug(mainBatch);
		////////////////////////////////////////////////////////////
		mainBatch.end();

	}

	private void update(){

		delta=Gdx.graphics.getDeltaTime();
		instanz = this;
	}

	public void renderDebug(SpriteBatch batch){
		if(debug){
			bitmapFont.draw(batch,Integer.toString(Gdx.graphics.getFramesPerSecond()),0,Gdx.graphics.getHeight());
			bitmapFont.draw(batch,"X: "+Integer.toString(Gdx.input.getX()),0,Gdx.graphics.getHeight()-12);
			bitmapFont.draw(batch,"Y: "+Integer.toString(Gdx.input.getY()),0,Gdx.graphics.getHeight()-24);
		}
	}

	@Override
	public void dispose () {
		//this.dispose();
		System.out.println("Spiel wurde beendet");
	}
}
