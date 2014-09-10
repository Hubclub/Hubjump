package com.hubclub.hubjump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.hubclub.hubjump.GameClass;

public class SplashScreen implements Screen {
	GameClass game;
	Texture background = new Texture(Gdx.files.internal("SplashScreen.png"));
	SpriteBatch batch ;
	boolean trigger = false;
	
	public SplashScreen (GameClass game){
		this.batch = GameScreen.batch;
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		batch.begin();
			batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		if (trigger){
			if (Gdx.input.justTouched()){
				game.setScreen(game.theGame);
				Timer.instance().clear();
			}
		}else{
			Timer.schedule(new Task() {
	
				public void run() {
					game.setScreen(game.theGame);
				}
			}
			, 2.5f); // 2.5 second pause to admire our logo
				trigger=true;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		this.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		background.dispose();
	}
	
}
