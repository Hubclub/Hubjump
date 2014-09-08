package com.hubclub.hubjump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.hubclub.hubjump.GameClass;
import com.hubclub.hubjump.helpers.InputHandler;
import com.hubclub.hubjump.worldenviroment.Enviroment;
import com.hubclub.hubjump.worldenviroment.EnviromentRenderer;

public class GameScreen implements Screen{
	
	@SuppressWarnings("unused")
	private GameClass game; // game variable of type GameClass; use this variable when you 
	  						// find necessary to change the screen of the game
	// Variables here
	private static MainMenu mainMenu = new MainMenu(); // used for main menu
	public static InputHandler inp = new InputHandler(); // used for the ninja controls
	private static boolean GG; // used to see if it's game over
	
	EnviromentRenderer renderer;
	Enviroment env;
	
	
	public GameScreen(final GameClass game) {
		// Initialize the game variable
		GG = false;
		
		this.game = game; 
		mainMenu.giveGameScreenAdress(this);
		
		env= new Enviroment();
		renderer = new EnviromentRenderer(env,true);

		Gdx.input.setInputProcessor(mainMenu);
	}
	
	public void restartGame(boolean showMenu){
		// possible memory leak here
		env= new Enviroment();
		renderer = new EnviromentRenderer(env,true);
		
		if (showMenu){
			mainMenu.switchTo(0); 
		}else{
			mainMenu.hide();
			setInputHandler();
		}
		
		GG = false;
	}
	
	@Override
	public void render(float delta) {
		renderer.render();
		env.update();
		
		if (mainMenu.isShown())
			mainMenu.render();
		
		inp.updateInput();
	}
	
	public static void gameOver (){ // gets called from the ninja class
		GG = true;
		Timer.schedule(new Task() {
			
			public void run() {
				Gdx.input.setInputProcessor(mainMenu);
				mainMenu.switchTo(1);
				mainMenu.show();
			}
		}
		, 1.5f);// 1.5 seconds delay until the retry screen appears
	}
	
	protected static void setInputHandler(){
		Gdx.input.setInputProcessor(inp);
	}
	public static boolean isGameOver(){
		return GG;
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

