package com.kalt.GDXtrash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Ninja {
	final double JUMP_ANGLE = Math.toRadians(30.0);
	final float CHARACTER_HEIGHT = 1.6f;
	float jumpSpeed;
	
	private Rectangle hitBox;
	private int wallWidth,corridor;
	private boolean jumpTrigger,jumpDirection;
	Texture texture;
		
	public Ninja (Walls walls, int x, int y) {
		this.wallWidth = walls.width;
		this.corridor = walls.corridor;
			hitBox= new Rectangle(wallWidth, 50, Gdx.graphics.getHeight()*CHARACTER_HEIGHT /20, Gdx.graphics.getHeight()*CHARACTER_HEIGHT /20);
		jumpDirection=true; // true= right, false= left
		jumpTrigger=false;
		jumpSpeed = 200;
		texture = new Texture (Gdx.files.internal("ninja/idle.PNG"),true); //mipmaps!
		}
		
	public void update (float delta){
		if (Gdx.input.justTouched() ){
			jumpTrigger=true;
		}
		
		if (jumpTrigger)
		{
			hitBox.y += jumpSpeed * delta * Math.sin(JUMP_ANGLE);
			if (jumpDirection) hitBox.x += jumpSpeed * delta;
				else hitBox.x -= jumpSpeed * delta * Math.cos(JUMP_ANGLE);
			
			if (hitBox.overlaps(Walls.getWallRight()) || hitBox.overlaps(Walls.getWallLeft())){
				if (jumpDirection) hitBox.x= wallWidth + corridor-hitBox.width;
					else hitBox.x= wallWidth;
				jumpTrigger=false;
				jumpDirection=!jumpDirection;}
		}
	}
	public void resize (Walls walls){
			wallWidth= walls.width;
			corridor= walls.corridor;
		hitBox.x += walls.width-walls.widthOLD;
		hitBox.y = hitBox.y*walls.corridor / walls.corridorOLD;
		hitBox.height= Gdx.graphics.getHeight()*CHARACTER_HEIGHT /20;
		hitBox.width= hitBox.height;
		jumpSpeed *= walls.corridor/walls.corridorOLD;
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
