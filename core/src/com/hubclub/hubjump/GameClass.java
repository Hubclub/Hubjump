package com.hubclub.hubjump;

import com.badlogic.gdx.Game;
import com.hubclub.hubjump.screens.GameScreen;

// We extend the Game class because we're going 
// to work with Screens; only a class who inherits 
// the Game class can use Screens in libGDX
// Game also implements ApplicationListener so it can be used
// to launch the application

public class GameClass extends Game {
	
	public void create() {
		setScreen(new GameScreen(this));
		
	}
	
	/*   The game does not start if i leave these:
	
	public void render() {
		
		// TODO ~ you must implement the render method from the superclass
		// THAT'S ALL!
		
	} // END OF render METHOD
	
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
	*/
}