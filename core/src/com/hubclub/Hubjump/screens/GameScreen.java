package com.hubclub.hubjump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.hubclub.hubjump.GameClass;
import com.hubclub.hubjump.helpers.InputHandler;
import com.hubclub.hubjump.worldenviroment.Enviroment;
import com.hubclub.hubjump.worldenviroment.EnviromentRenderer;

public class GameScreen implements Screen{
	
	protected static GameClass game; // game variable of type GameClass; use this variable when you 
	  						// find necessary to change the screen of the game
	public static SpriteBatch batch = new SpriteBatch();// a spritebatch to be used across all the screens
	
	// Variables here
	private static MainMenu mainMenu; // used for main menu
	public static InputHandler inp = new InputHandler(); // used for the ninja controls
	private static boolean GG; // used to see if it's game over
	static private Preferences prefs;
	static public String GGmessage = "not changed, queks";
	static public boolean debug;
	
	EnviromentRenderer renderer;
	Enviroment env;
	
	
	public GameScreen(GameClass game) {
		GameScreen.game = game;
		mainMenu = new MainMenu(game,batch);
		// Initialize the game variable
		debug = false;
		GG = false;
		prefs = Gdx.app.getPreferences("GamePreferences");
		
		
		env= new Enviroment();
		renderer = new EnviromentRenderer(env);
	}
	
	public void restartGame(boolean showMenu){
		// possible memory leak here
		env= new Enviroment();
		renderer = new EnviromentRenderer(env);
		
		if (showMenu){
			mainMenu.switchTo(0); 
		}else{
			mainMenu.hide();
			setInputHandler();
		}
		
		GG = false;
	}
	
	public static void gameOver (){ // gets called from the ninja class
		// update the highscore and generate a message to be written on the retry screen
		GGmessage = "baegh";
		if (EnviromentRenderer.getScore() > prefs.getInteger("highscore", 0) ){
			prefs.putInteger("highscore", EnviromentRenderer.getScore() );
			GGmessage = "New Highscore! " + EnviromentRenderer.getScore() + " meters!";
			prefs.flush();
		}
		
		GG = true;
		Timer.schedule(new Task() {
			
			public void run() {
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
	public static SpriteBatch getSpriteBatch(){
		return batch;
		
	}
	
	@Override
	public void render(float delta) {
		batch.begin();
			renderer.render(batch);
			
			mainMenu.draw(batch);
		batch.end();
		
		if (MainMenu.isShown())
			mainMenu.drawButtons();
		
		renderer.debugRender(env.getWorld());
		
		env.update();
		//inp.updateInput();
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
		batch.dispose();
		renderer.dispose();
		env.dispose();	
	}
	
	public void show() {
		mainMenu.show();
		
	}
	
	public void hide() {
		//mainMenu.hide();
		// Called when this screen is no longer the current screen of the game
		
	}
	
}

