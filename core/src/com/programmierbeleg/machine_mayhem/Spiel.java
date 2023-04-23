package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;

public class Spiel extends Game {
	public static Spiel instanz;
	private BitmapFont bitmapFont;
	private SpriteBatch mainBatch;
	public boolean debug=true;
	public float delta;
	public TextureAtlas atlas;
	ArrayList<Feld> felder;
	ArrayList<Spieler> spieler;
	ArrayList<Gegner> gegner;
	ArrayList<Projektil> projektile;

	ScreenAdapter aktiverBildschirm;

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
			for (int i = 0; i < felder.size(); i++) felder.get(i).render(delta);
		}
		if(spieler!=null) {
			for (int i = 0; i < spieler.size(); i++) spieler.get(i).render(delta);
		}
		if(gegner!=null) {
			for (int i = 0; i < gegner.size(); i++) gegner.get(i).render(delta);
		}
		if(projektile!=null) {
			for (int i = 0; i < projektile.size(); i++) projektile.get(i).render(delta);
		}

		aktiverBildschirm.render(delta);
		renderDebug(mainBatch);
		////////////////////////////////////////////////////////////
		mainBatch.end();

	}

	private void update(){
		delta=Gdx.graphics.getDeltaTime();
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
