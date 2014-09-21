package com.hubclub.hubjump.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hubclub.hubjump.characters.Ninja.State;

public abstract class NinjaAnimation {
	static TextureRegion hang = new TextureRegion ( new Texture(Gdx.files.internal("ninja/hang.png")));
	static TextureRegion idle = new TextureRegion ( new Texture(Gdx.files.internal("ninja/idle.png")));
	static TextureRegion dead = new TextureRegion ( new Texture(Gdx.files.internal("ninja/dead.png")));
	static TextureRegion jump = new TextureRegion ( new Texture(Gdx.files.internal("ninja/jump.png")));
	
	
	public static TextureRegion getTexture (State state, boolean faceDirection){
		if (state == State.HANGING)
			return verifyFlip(hang,faceDirection);
		else if (state == State.JUMPING)
			return verifyFlip(jump,faceDirection);
		else if (state == State.DEAD)
			return verifyFlip(dead,faceDirection);
		
		return verifyFlip(idle,faceDirection);
	}
	
	public static TextureRegion verifyFlip(TextureRegion tex, boolean b){
		if (b == !tex.isFlipX()) // if they have the same orientation
			return tex;
		else{
			tex.flip(true, false); //if not flip the texture
			return tex;
		}
	}
	public static void dispose(){
		hang.getTexture().dispose();
		idle.getTexture().dispose();
		dead.getTexture().dispose();
		jump.getTexture().dispose();
	}
}
