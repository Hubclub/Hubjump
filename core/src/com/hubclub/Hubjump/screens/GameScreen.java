package com.hubclub.Hubjump.screens;

import com.badlogic.gdx.Screen;
import com.hubclub.Hubjump.worldenviroment.Enviroment;
import com.hubclub.Hubjump.worldenviroment.EnviromentRenderer;

public class GameScreen implements Screen{
	
	Enviroment env = new Enviroment();
	EnviromentRenderer renderer= new EnviromentRenderer(env.getWorld());

	@Override
	public void render(float delta) {
		renderer.render();
		
		env.update();
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
	
}

