package com.hubclub.hubjump.screens;

import com.badlogic.gdx.Screen;
import com.hubclub.hubjump.GameClass;
import com.hubclub.hubjump.worldenviroment.Enviroment;
import com.hubclub.hubjump.worldenviroment.EnviromentRenderer;

public class GameScreen implements Screen{
	
	@SuppressWarnings("unused")
	private GameClass game; // game variable of type GameClass; use this variable when you 
	  						// find necessary to change the screen of the game
	// Variables here
	Enviroment env = new Enviroment();
	EnviromentRenderer renderer= new EnviromentRenderer(env.getWorld(),true);
	
	boolean presstostart = false;

	
	public GameScreen(final GameClass game) {
		// Initialize the game variable
		this.game = game; 
	}
	
	@Override
	public void render(float delta) {
		renderer.render();
		env.update();
	}

	public void resize(int width, int height) {
		
		// TODO ~ operations that are done when resize occurs
		
	}// END OF resize METHOD
	
	public void pause() {
		
		// TODO ~ operations when the game is paused (this method is
		// applied only on android, when you press the home button
		// or you receive a call)
		
	}// END OF pause METHOD
	
	public void resume() {
		
		// TODO ~ operations when the game exits from pause (method applied only
		// on android)
		
	}// END OF resume METHOD
	
	public void dispose() {
		
		// TODO ~ dispose variables initialized in the upper create method
		
	}// END OF dispose METHOD
	
	public void show() {
		
		// Called when this screen becomes the current screen of the game
		
	}// END OF show METHOD
	
	public void hide() {
		
		// Called when this screen is no longer the current screen of the game
		
	}// END OF hide METHOD
	
}

