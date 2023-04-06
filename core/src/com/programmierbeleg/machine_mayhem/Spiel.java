package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Spiel extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	float tmp;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		tmp=0.0f;
	}

	@Override
	public void render () {
		update();

		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
		batch.begin();
		////////////////////////////////////////////////////////////
		batch.draw(img, tmp, tmp);
		////////////////////////////////////////////////////////////
		batch.end();
	}

	public void update(){
		tmp+=1;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
