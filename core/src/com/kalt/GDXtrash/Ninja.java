package com.kalt.GDXtrash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Ninja {
	final double JUMP_ANGLE = Math.toRadians(30.0);
	float jumpSpeed;
	
	private Rectangle hitBox;
	private int wallWidth,corridor;
	private boolean jumpTrigger,jumpDirection;
	Texture texture;
		
	public Ninja (int wallWidth, int corridor) {
		this.wallWidth = wallWidth;
		this.corridor = corridor;
			hitBox= new Rectangle(wallWidth, 50, Gdx.graphics.getHeight()*1.6f /20, Gdx.graphics.getHeight()*1.6f /20);
		jumpDirection=true; // true= right, false= left
		jumpTrigger=false;
		jumpSpeed = 200;
		texture = new Texture (Gdx.files.internal("ninja/idle.PNG"),true); //mipmaps!
		}
		
	public void update (float delta, Rectangle wallRight, Rectangle wallLeft){
		if (Gdx.input.justTouched() ){
			jumpTrigger=true;
		}
		
		if (jumpTrigger)
		{
			hitBox.y += jumpSpeed * delta * Math.sin(JUMP_ANGLE);
			if (jumpDirection) hitBox.x += jumpSpeed * delta;
				else hitBox.x -= jumpSpeed * delta * Math.cos(JUMP_ANGLE);
			
			if (hitBox.overlaps(wallRight) || hitBox.overlaps(wallLeft)){
				if (jumpDirection) hitBox.x= wallWidth + corridor-hitBox.width;
					else hitBox.x= wallWidth;
				jumpTrigger=false;
				jumpDirection=!jumpDirection;}
		}
	}
	public void resize (int wallWidthOLD, int corridorOLD, int wallWidth, int corridor){
		hitBox.x += wallWidth-wallWidthOLD;
		hitBox.y = hitBox.y*corridor / corridorOLD;
		hitBox.height= Gdx.graphics.getHeight()*1.6f /20;
		hitBox.width= hitBox.height;
		jumpSpeed *= corridor/corridorOLD;
	}
	public float getX(){
		return hitBox.x;
	}
	public float getY(){
		return hitBox.y;
	}
	public float getWidth(){
		return hitBox.width;
	}
	public float getHeight(){
		return hitBox.height;
	}
}
