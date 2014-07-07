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
		
	public Ninja (int x, int y) {
		this.wallWidth = Walls.width;
		this.corridor = Walls.corridor;
			hitBox= new Rectangle(x, y, Gdx.graphics.getHeight()*CHARACTER_HEIGHT /20, Gdx.graphics.getHeight()*CHARACTER_HEIGHT /20);
		jumpDirection=true; // true= right, false= left
		jumpTrigger=false;
		jumpSpeed = 400;
		texture = new Texture (Gdx.files.internal("ninja/idle.PNG"),true); //mipmaps!
		}
		
	public void update (float delta, float camY){
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
		if (Math.floor( Math.abs(camY - Gdx.graphics.getHeight()/2) / Gdx.graphics.getHeight() ) != Walls.currentBlock )
		{                 // math.abs e pt cazul in care baza camerei ajunge sub y=0...
			Walls.currentBlock=Walls.nextBlock();
			if (Walls.currentBlock == Walls.NUM_OF_BLOCKS-1)
				this.hitBox.y -= (Walls.NUM_OF_BLOCKS -1)*Gdx.graphics.getHeight() ;
				
		}
	}
	/*public void resize (){
			wallWidth= Walls.width;
			corridor= Walls.corridor;
		hitBox.x += Walls.width-Walls.widthOLD;
		hitBox.y = hitBox.y*Walls.corridor / Walls.corridorOLD;
		hitBox.height= Gdx.graphics.getHeight()*CHARACTER_HEIGHT /20;
		hitBox.width= hitBox.height;
		jumpSpeed *= Walls.corridor/Walls.corridorOLD;
	}*/
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
