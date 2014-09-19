package com.hubclub.hubjump.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.hubclub.hubjump.characters.Ninja;

public class InputHandler extends InputAdapter {
	private static final float SWIPE_MIN_DISTANCE = Gdx.graphics.getHeight()/20;
	Vector2 lastPt = new Vector2();
	Vector2 initPt = new Vector2();
	Ninja ninja;
	
	public void setNinja(Ninja ninja){
		this.ninja = ninja;
	}
	
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		initPt.x = screenX;
		initPt.y = screenY;
		
		lastPt.x = screenX;
		lastPt.y = screenY;
		
		Timer.schedule(new Task() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				differanciate();
			}
		}, 0.1f);
		
		return super.touchDown(screenX, screenY, pointer, button);
	}
	
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		//Timer.instance().
		
		return super.touchUp(screenX, screenY, pointer, button);
	}
	
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		lastPt.x = screenX;
		lastPt.y = screenY;
		
		return super.touchDragged(screenX, screenY, pointer);
	}
	
	private void differanciate(){
		if (initPt.dst(lastPt) > SWIPE_MIN_DISTANCE){
			System.out.println("Swipe");
			if (ninja.canDash())
				ninja.dash();
		}else{
			System.out.println("Tap");
			if (ninja.canJump())
				ninja.jump();
		}
	}
	
	
}
