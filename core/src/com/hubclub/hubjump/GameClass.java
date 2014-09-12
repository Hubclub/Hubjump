package com.hubclub.hubjump;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.hubclub.hubjump.screens.GameScreen;
import com.hubclub.hubjump.screens.SplashScreen;

// We extend the Game class because we're going 
// to work with Screens; only a class who inherits 
// the Game class can use Screens in libGDX
// Game also implements ApplicationListener so it can be used
// to launch the application

public class GameClass extends Game {
	
	public GameScreen theGame;
	
	//first time we create the game and a splashscreen(which we show first)
	public void create() {
		theGame = new GameScreen(this);
		setScreen(new SplashScreen(this));
	}
	
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0.2f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the screen
		
		this.getScreen().render(Gdx.graphics.getDeltaTime()); //draw whatever is on the current screen
	}
	
	public void resize(int width, int height) {
		
		// TODO ~ you must implement the resize method from the superclass
		// That can be all
		
	}
	
	public void pause() {
		
		// TODO ~ you must implement the pause method from superclass
		// That can be all
		
	}
	
	public void resume() {
	
		// TODO ~ you must implement the resume method from superclass
		// That can be all
		
	}
	
	public void dispose() {
		
		// TODO ~ you must implement the dispose method from superclass
		// and dispose other variable initialized in the upper create method
		
	}
}