package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Spiel extends Game {
	public static Spiel instanz;
	private BitmapFont bitmapFont;
	private SpriteBatch batch;
	public boolean debug=true;

	public Spiel(){
		//setScreen(new Hauptmenü());
		if(instanz==null){
			instanz=this;
		}
	}

	@Override
	public void create () {
		setScreen(new Hauptmenü());
		bitmapFont = new BitmapFont();
		batch = new SpriteBatch();
	}

	public void renderDebug(){
		if(debug){
			batch.begin();
			bitmapFont.draw(batch,Integer.toString(Gdx.graphics.getFramesPerSecond()),0,Gdx.graphics.getHeight());
			bitmapFont.draw(batch,"X: "+Integer.toString(Gdx.input.getX()),0,Gdx.graphics.getHeight()-12);
			bitmapFont.draw(batch,"Y: "+Integer.toString(Gdx.input.getY()),0,Gdx.graphics.getHeight()-24);
			batch.end();
		}
	}

	@Override
	public void dispose () {
		//this.dispose();
	}
}
