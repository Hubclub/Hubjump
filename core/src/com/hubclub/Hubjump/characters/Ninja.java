package com.hubclub.hubjump.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Ninja {
	static public enum State{
		IDLE, HANGING, JUMPING, DEAD
	}
	public static final float JUMP_SPEED = 12f;
	public static final double JUMP_ANGLE = Math.toRadians(40);
	public static final float NINJA_WIDTH = 1.6f;
	public static final float NINJA_HEIGHT = 1.6f;
	public static final String NAME = "Ninja";
	
	Texture ninjaTexture;
	Body ninjaBody; // a reference to the object for easier manipulation...
	State state;
	boolean faceDirection ; //false = left, true = right
	int wallContact = 1;
	
	
	public Ninja (){
		this.state = State.IDLE;
		faceDirection = false;
		//ninjatexture =  ???
	}
	
	
	public void setBody(World world, float x, float y){
		BodyDef ninjaDef= new BodyDef();
			
			ninjaDef.type = BodyType.DynamicBody;
			ninjaDef.position.set(x, y);
			ninjaDef.allowSleep = false;
			ninjaDef.fixedRotation= true;
			
		ninjaBody = world.createBody(ninjaDef);
		ninjaBody.setUserData(this);
		
		PolygonShape ninjaShape = new PolygonShape();
		ninjaShape.setAsBox(NINJA_WIDTH / 2, NINJA_HEIGHT / 2);
		
		FixtureDef ninjaFixtureDef = new FixtureDef();
			
			ninjaFixtureDef.shape = ninjaShape;
			ninjaFixtureDef.density = 1;
			ninjaFixtureDef.friction = 1f;
			ninjaFixtureDef.restitution = 0f;
			
		ninjaBody.createFixture(ninjaFixtureDef);
	
		ninjaShape.dispose();
	}
	public void jump (){
		if (!faceDirection) ninjaBody.applyLinearImpulse(
				ninjaBody.getMass() * JUMP_SPEED * (float)Math.cos(JUMP_ANGLE),
				ninjaBody.getMass() * JUMP_SPEED * (float)Math.sin(JUMP_ANGLE),
				0, 0, true);
		
		else ninjaBody.applyLinearImpulse(
				-ninjaBody.getMass() * JUMP_SPEED * (float)Math.cos(JUMP_ANGLE),
				ninjaBody.getMass() * JUMP_SPEED * (float)Math.sin(JUMP_ANGLE),
				0, 0, true);
	}
	
	
	public void update (){
		if (Gdx.input.justTouched() && wallContact > 0){
			ninjaBody.setLinearVelocity(0, 0);
			jump();
			faceDirection = !faceDirection;
		}
			//why  == and not != ??
		if (wallContact > 0 && state == State.IDLE )
			if (faceDirection) ninjaBody.applyForceToCenter(ninjaBody.getMass() * 15  , 0, true);
			else ninjaBody.applyForceToCenter(-ninjaBody.getMass() * 15 , 0, true);
	}
	
	void startContact (){ wallContact++;
						 this.state = State.HANGING;}
	void endContact (){ wallContact--;
						 this.state = State.JUMPING;}

	
	

	public static float getNinjaWidth() {
		return NINJA_WIDTH;
	}
	public static float getNinjaHeight() {
		return NINJA_HEIGHT;
	}
}
