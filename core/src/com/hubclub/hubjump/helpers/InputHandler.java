package com.hubclub.hubjump.helpers;

import com.badlogic.gdx.InputAdapter;

public class InputHandler extends InputAdapter {
	/*
	// Use these 2 variables to find out the input type
	private float deltaX;
	private float deltaY;
	
	// The coords of the object passed by the method getInput;
	private float xCoord;
	private float yCoord;
	*/
	
	class LastInput{
		int inp;
		float delay; //the delay is frame based! TO DO: change to time based
		
		public LastInput(){
			inp = 0;
			delay = 0;
		}
		
		void add (int t){
			inp=t;
			if (t==1)
				delay = 4;
			else delay = 0;
		}
		
		int peek(){
			if (delay == 0)
				return inp;
			
			return 0;
		}
		
		void update(){
			if (delay>0)
				delay--;
			else
				inp = 0;
		}
	}
	
	/* 0 = nada
	 * 1 = tap
	 * 2 = swipe
	 */
	private LastInput inp = new LastInput();
	
	// Occurs when you touch the screen 
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
			//make the ninja jump
		inp.add(1);
		
		return super.touchDown(screenX, screenY, pointer, button);
	}
	
	// Occurs when you stop touching the screen (when you lift the finger)
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		
		
		return super.touchUp(screenX, screenY, pointer, button);
	}
	
	// Occurs when you drag over the screen
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		inp.add(2);
		
		return super.touchDragged(screenX, screenY, pointer);
	}
	
	// Use this method to get the InputType when needed
	public int getInput(float xCoord, float yCoord) {
		// return the value
		
		return inp.peek();
	}
	public void updateInput(){
		inp.update();
	}
	
}
