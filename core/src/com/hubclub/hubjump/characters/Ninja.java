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
		IDLE, HANGING, JUMPING, DEAD, DASHING
	}
	public static final float JUMP_SPEED = 18f;
	public static final float DASH_SPEED = 8f;
	public static final double JUMP_ANGLE = Math.toRadians(40);
	public static final float HOLD_FORCE = 2f;
	public static final float NINJA_WIDTH = 1.6f;
	public static final float NINJA_HEIGHT = 1.6f;
	public static final float FEET_POS = 0.4f; //a y relative to the bottom of the hitbox 
	public static final float ARMS_POS = 1f;

	Texture ninjaTexture;
	Body ninjaBody; // a reference to the object for easier manipulation...
	State state;
	boolean faceDirection ; //false = left, true = right
	boolean canDash;
	boolean wallContact;
	boolean firstJump;
	public boolean playLandingSound;
	float lastWallX;
	
	
	public Ninja (){
		this.state = State.IDLE;
		faceDirection = true;
		canDash = false;
		wallContact = true;
		firstJump = true;
		playLandingSound = false;
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
		ninjaShape.setAsBox(NINJA_WIDTH / 2, NINJA_HEIGHT / 2,new Vector2(NINJA_WIDTH / 2, NINJA_HEIGHT / 2) , 0);
		
		FixtureDef ninjaFixtureDef = new FixtureDef();
			
			ninjaFixtureDef.shape = ninjaShape;
			ninjaFixtureDef.density = 1;
			ninjaFixtureDef.friction = 1f;
			ninjaFixtureDef.restitution = 0f;
			
		ninjaBody.createFixture(ninjaFixtureDef);
	
		ninjaShape.dispose();
		
		lastWallX = ninjaBody.getPosition().x;
	}
	public void firstjump(){
		ninjaBody.applyLinearImpulse(
				ninjaBody.getMass() * JUMP_SPEED * (float)Math.cos(JUMP_ANGLE),
				ninjaBody.getMass() * JUMP_SPEED * (float)Math.sin(JUMP_ANGLE),
				0, 0, true);
		this.state = State.JUMPING;
		canDash = true;
		wallContact = false;
		
		firstJump = false;
	}
	
	public void jump (){
		GameScreen.jumpSound.play(GameScreen.sound/100f);
		playLandingSound = true;
		if (firstJump) firstjump();// the first jump is a little different...
		else{
		// standard jumping procedure
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
	}}
	
	public void dash(){
		//to do: find dash sound
		GameScreen.jumpSound.play(GameScreen.sound/100f);
		
		System.out.println("NINJA : JUST DASHED");
		ninjaBody.setLinearVelocity(0, 0);
		ninjaBody.applyLinearImpulse(0, ninjaBody.getMass() * DASH_SPEED , 0, 0, true);
		state = State.DASHING;
		
		canDash = false; // you dash once, you can't do it again( well, not on the same wall)
	}
	
	public void draw (SpriteBatch batch, float ratio, float y){
		batch.draw(NinjaAnimation.getTexture(state,faceDirection),
				ninjaBody.getPosition().x * ratio, y,
				0f, 0f,
				NINJA_WIDTH * ratio, NINJA_HEIGHT * ratio,
				1f, 1f, 
				(float)Math.toDegrees( ninjaBody.getAngle()) );
	}
	
	public void update(){
		if (state == State.DASHING)
			if (ninjaBody.getLinearVelocity().y < 0)
				state = State.HANGING;
	}
	
	public boolean canDash(){
		if (state == State.HANGING && canDash)
			return true;
		return false;
	}
	
	public boolean canJump(){
		return wallContact;
	}
	
	void startContact () {
		if ((faceDirection && lastWallX < ninjaBody.getPosition().x) || //to do: fix the fix
			(!faceDirection && lastWallX > ninjaBody.getPosition().x)
		){
	        lastWallX = ninjaBody.getPosition().x;
			wallContact = true;
	        if (state != State.IDLE)
	        	this.state = State.HANGING;
		}
		
		if (playLandingSound)
			GameScreen.landing.play(GameScreen.sound/100f);
		playLandingSound = false;
	}

	void endContact (){
        // this gets called whenever contact switches from a window to a wall... meh
    }
	
	public void crashIntoWindow (Vector2 impulse){
		ninjaBody.applyLinearImpulse(impulse.x, impulse.y, 0, 0, true);
		ninjaBody.setFixedRotation(false);
		this.state = State.DEAD;
		wallContact = false;
		

		GameScreen.gameOver();
	}

	public void freeze (){
		ninjaBody.setActive(false);
	}
	public float getFeetPos() {
		return ninjaBody.getPosition().y + FEET_POS;
	}
	public float getArmsPos() {
		return ninjaBody.getPosition().y + ARMS_POS;
	}
	public float getY(){
		return ninjaBody.getPosition().y;
	}
	public float getX(){
		return ninjaBody.getPosition().x;
	}
	public static float getNinjaWidth() {
		return NINJA_WIDTH;
	}
	public static float getNinjaHeight() {
		return NINJA_HEIGHT;
	}
}
