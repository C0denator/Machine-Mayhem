package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.programmierbeleg.machine_mayhem.Anzeigen.Hauptmenü;

public class Spiel extends Game {
	public static Spiel instanz;
	private BitmapFont bitmapFont;
	private SpriteBatch mainBatch;
	private boolean debug=true;
	public float delta;
	public TextureAtlas atlas;
	public ScreenAdapter aktiverBildschirm;

	public final int skalierung=3;

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
		update();
		Gdx.gl.glClearColor(0.5f,0.4f,0.4f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		aktiverBildschirm.render(delta);

		mainBatch.begin();
		////////////////////////////////////////////////////////////
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

	@Override
	public void resize(int width, int height) {
		aktiverBildschirm.resize(width,height);
	}

}
