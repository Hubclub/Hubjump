package com.hubclub.hubjump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hubclub.hubjump.worldenviroment.EnviromentRenderer;

public class OptionsScreen implements Screen,InputProcessor {
	SpriteBatch batch;
	
	
	public OptionsScreen() {
		// TODO Auto-generated constructor stub
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
	}
	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		batch.begin();
			batch.draw(EnviromentRenderer.brickTexture,
					0, 0,
					0, 0,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
					4, 4, // scaleXY
					0, 0, 0,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
					false, false);
		/*	batch.draw(EnviromentRenderer.brickTexture,
					0 ,0 ,
					0, 0,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );*/
		batch.end();
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
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
