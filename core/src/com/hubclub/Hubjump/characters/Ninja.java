package com.hubclub.hubjump.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.hubclub.hubjump.screens.GameScreen;

public class Ninja {
	static public enum State{
		IDLE, HANGING, JUMPING, DEAD
	}
	public static final float JUMP_SPEED = 18f;
	public static final float DASH_SPEED = 8f;
	public static final double JUMP_ANGLE = Math.toRadians(40);
	public static final float NINJA_WIDTH = 1.6f;
	public static final float NINJA_HEIGHT = 1.6f;
	public static final float FEET_POS = 0.4f;

	Texture ninjaTexture;
	Body ninjaBody; // a reference to the object for easier manipulation...
	State state;
	boolean faceDirection ; //false = left, true = right
	boolean canDash;
	boolean wallContact = false;
	
	
	
	public Ninja (){
		this.state = State.IDLE;
		faceDirection = false;
		canDash = false;
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
		System.out.println("NINJA : JUST JUMPED");
		this.state = State.JUMPING;
		wallContact = false;
		
		ninjaBody.setLinearVelocity(0, 0);
		if (!faceDirection) ninjaBody.applyLinearImpulse(
				ninjaBody.getMass() * JUMP_SPEED * (float)Math.cos(JUMP_ANGLE),
				ninjaBody.getMass() * JUMP_SPEED * (float)Math.sin(JUMP_ANGLE),
				0, 0, true);
		
		else ninjaBody.applyLinearImpulse(
				-ninjaBody.getMass() * JUMP_SPEED * (float)Math.cos(JUMP_ANGLE),
				ninjaBody.getMass() * JUMP_SPEED * (float)Math.sin(JUMP_ANGLE),
				0, 0, true);
		faceDirection = !faceDirection;
		
		canDash = true; // allow the ninja to dash next time he hits a wall.
	}
	
	public void dash(){
		System.out.println("NINJA : JUST DASHED");
		ninjaBody.setLinearVelocity(0, 0);
		ninjaBody.applyLinearImpulse(0, ninjaBody.getMass() * DASH_SPEED , 0, 0, true);
		
		canDash = false; // you dash once, you can't do it again( well, not on the same wall)
	}
	public void update (){
		if (GameScreen.inp.getInput(0, 0) == 1 && wallContact )
			jump();
		if (GameScreen.inp.getInput(0, 0) == 2 && wallContact && canDash )
			dash();
		//why  == and not != ??
		if (wallContact && state == State.IDLE )
			if (faceDirection) ninjaBody.applyForceToCenter(ninjaBody.getMass() * 15  , 0, true);
			else ninjaBody.applyForceToCenter(-ninjaBody.getMass() * 15 , 0, true);
	}
	
	public void draw (SpriteBatch batch, float ratio, float y){
		batch.draw(NinjaAnimation.getTexture(state),
				ninjaBody.getPosition().x * ratio, y,
				NINJA_WIDTH * ratio, NINJA_HEIGHT * ratio);
	}
	
	void startContact () {
        wallContact = true;
	    this.state = State.HANGING;
    }

	void endContact (){
        // this gets called whenever he switches from a window to a wall... meh
    }
	
	public void crashIntoWindow (Vector2 impulse){
		ninjaBody.applyLinearImpulse(impulse.x, impulse.y, 0, 0, true);
		ninjaBody.setFixedRotation(false);
		this.state = State.DEAD;
		
		GameScreen.gameOver();
	}

	
	

	public float getFeetPos() {
		return ninjaBody.getPosition().y - NINJA_HEIGHT/2 + FEET_POS;
	}
	public static float getNinjaWidth() {
		return NINJA_WIDTH;
	}
	public static float getNinjaHeight() {
		return NINJA_HEIGHT;
	}
}
