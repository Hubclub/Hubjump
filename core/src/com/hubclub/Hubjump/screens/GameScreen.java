package com.hubclub.hubjump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.hubclub.hubjump.GameClass;
import com.hubclub.hubjump.helpers.InputHandler;
import com.hubclub.hubjump.worldenviroment.Enviroment;
import com.hubclub.hubjump.worldenviroment.EnviromentRenderer;

public class GameScreen implements Screen{
	
	@SuppressWarnings("unused")
	private GameClass game; // game variable of type GameClass; use this variable when you 
	  						// find necessary to change the screen of the game
	// Variables here
	private static MainMenu mainMenu = new MainMenu();
	public static InputHandler inp = new InputHandler();
	
	EnviromentRenderer renderer;
	Enviroment env;

	
	public GameScreen(final GameClass game) {
		// Initialize the game variable
		env= new Enviroment();
		renderer = new EnviromentRenderer(env.getWorld(),true);
		this.game = game; 
		
		Gdx.input.setInputProcessor(mainMenu);
	}
	
	@Override
	public void render(float delta) {
		renderer.render();
		env.update();
		
		if (mainMenu.isShown())
			mainMenu.render();
	}
	
	protected static void setInputHandler(){
		Gdx.input.setInputProcessor(inp);
	}

	public void resize(int width, int height) {
		
		// TODO ~ operations that are done when resize occurs
		
	}
	
	public void pause() {
		
		// TODO ~ operations when the game is paused (this method is
		// applied only on android, when you press the home button
		// or you receive a call)
		
	}
	
	public void resume() {
		
		// TODO ~ operations when the game exits from pause (method applied only
		// on android)
		
	}
	
	public void dispose() {
		
		// TODO ~ dispose variables initialized in the upper create method
		
	}
	
	public void show() {
		
		// Called when this screen becomes the current screen of the game
		
	}
	
	public void hide() {
		
		// Called when this screen is no longer the current screen of the game
		
	}
	
}

