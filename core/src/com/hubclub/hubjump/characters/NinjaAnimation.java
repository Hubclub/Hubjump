package com.hubclub.hubjump.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.hubclub.hubjump.characters.Ninja.State;

public abstract class NinjaAnimation {
	static Texture hang = new Texture(Gdx.files.internal("ninja/hang.png"));
	static Texture idle = new Texture(Gdx.files.internal("ninja/idle.png"));
	static Texture dead = new Texture(Gdx.files.internal("ninja/dead.png"));
	static Texture jump = new Texture(Gdx.files.internal("ninja/jump.png"));
	
	public static Texture getTexture (State state){
		if (state == State.HANGING)
			return hang;
		else if (state == State.JUMPING)
			return jump;
		else if (state == State.DEAD)
			return dead;
		
		return idle;
	}
}
