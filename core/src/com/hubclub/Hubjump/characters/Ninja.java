package com.hubclub.Hubjump.characters;

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
	public static final float JUMP_SPEED = 4f;
	public static final double JUMP_ANGLE = Math.toRadians(30);
	public static final float NINJA_WIDTH = 1.6f;
	public static final float NINJA_HEIGHT = 1.6f;
	
	Texture ninjaTexture;
	Body ninjaBody; // a reference to the object for easier manipulation...
	State state;
	boolean faceDirection ; //false = left, true = right
	boolean wallContact = true;
	
	
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
			ninjaFixtureDef.friction = 0.4f;
			ninjaFixtureDef.restitution = 0f;
		ninjaBody.createFixture(ninjaFixtureDef);
	
		ninjaShape.dispose();
	}
	
	
	public void update (){
		if (Gdx.input.justTouched() && wallContact){
			if (!faceDirection) ninjaBody.applyLinearImpulse(20, 20, 0, 0, true);
			else ninjaBody.applyLinearImpulse(-20, 20, 0, 0, true);
		
			faceDirection = !faceDirection;
		}
		
		if (wallContact)
			if (faceDirection) ninjaBody.applyForceToCenter(60f , 0, true);
			else ninjaBody.applyForceToCenter(-60f , 0, true);
	}
	
	void startContact (){ wallContact = true;}
	void endContact (){ wallContact = false;}

	
	

	public static float getNinjaWidth() {
		return NINJA_WIDTH;
	}
	public static float getNinjaHeight() {
		return NINJA_HEIGHT;
	}
}
