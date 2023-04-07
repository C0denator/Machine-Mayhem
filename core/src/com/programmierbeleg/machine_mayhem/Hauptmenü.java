package com.programmierbeleg.machine_mayhem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Hauptmenü extends ScreenAdapter {

    SpriteBatch batch;
    Texture img;
    float tmp;
    private ArrayList<Knopf> knöpfe;


    public Hauptmenü(){
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        tmp=0.0f;
        knöpfe=new ArrayList<Knopf>();

        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.8f,300,50,"Spielen"){
            @Override
            public void action(){
                System.out.println("ACTION!!!!111");
            }
        });
        knöpfe.add(new Knopf(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()*0.7f,300,50,"Optionen"){
            @Override
            public void action(){
                System.out.println("Optionen!!!!11");
            }
        });

    }

    @Override
    public void render(float delta) {
        update();
        //ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClearColor(0.2f,0.2f,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        ////////////////////////////////////////////////////////////
        batch.draw(img, tmp, tmp);
        ////////////////////////////////////////////////////////////
        batch.end();

        for (int i=0; i<knöpfe.size(); i++){
            if(knöpfe.get(i)!=null){
                knöpfe.get(i).render();
            }
        }
    }

    private void update(){
        tmp+=100* Gdx.graphics.getDeltaTime();
    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
