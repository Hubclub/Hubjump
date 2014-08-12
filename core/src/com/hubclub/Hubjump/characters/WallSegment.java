package com.hubclub.hubjump.characters;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.hubclub.hubjump.worldenviroment.Enviroment;

public class WallSegment {
	public static final float WALL_WIDTH = 1.5f;
	public static final float STAIR_WIDTH = 3f;
	public static final float JUMP_HEIGHT= (Ninja.JUMP_SPEED * Ninja.JUMP_SPEED * (float)(Math.sin(Ninja.JUMP_ANGLE)*Math.sin(Ninja.JUMP_ANGLE)))
												/ (2 * -Enviroment.GRAVITATIONAL_ACCELERATION);
	public static final float DASH_HEIGHT= (Ninja.DASH_SPEED * Ninja.DASH_SPEED) 
												/ (2 * -Enviroment.GRAVITATIONAL_ACCELERATION);

	private static BodyDef wallDef;
	//
	static public Sprite wallSprite;
	private static Vector2 lastJumpPoint;
	private Body wallsegment;
	
	public void initializeBodyDef (){
		wallDef = new BodyDef();
		wallDef.type = BodyType.StaticBody;
		wallDef.position.set(0, Enviroment.VP_HEIGHT);
	}
	
	public WallSegment (){}
	
	private void generateNextJumpPoint(){
		// change the x to the center of the other wall
		if (lastJumpPoint.x == WALL_WIDTH/2)
			lastJumpPoint.x = Enviroment.VP_WIDTH - WALL_WIDTH/2;
		else lastJumpPoint.x= WALL_WIDTH/2;
		
		//increment y
		lastJumpPoint.y += JUMP_HEIGHT;
	}
	
	public void generateNextSegment (World world){
			wallDef.position.y += Enviroment.VP_HEIGHT;
		wallsegment = world.createBody(wallDef);
		wallsegment.setUserData(this);
		
		
		PolygonShape wallShape = new PolygonShape();
		//checks if the jumpPoints fit in the wallsegment and adds the fixture.
		while (lastJumpPoint.y < wallDef.position.y){
			System.out.println("Created nextJumpPoint" + lastJumpPoint);
			wallShape.setAsBox(WALL_WIDTH/2 , Ninja.NINJA_HEIGHT/2, new Vector2(lastJumpPoint.x, lastJumpPoint.y - wallDef.position.y) , 0);
			wallsegment.createFixture(wallShape, 0);

			generateNextJumpPoint();
		}
		

		wallShape.dispose();
	}
	
	public void generateFirstSegment (World world){
		// the first jump point to mark the start of the path...
		lastJumpPoint = new Vector2(WALL_WIDTH/2, Enviroment.VP_HEIGHT - Ninja.getNinjaHeight()/2);
		System.out.println("Created nextJumpPoint" + lastJumpPoint);
		// the next jumpPoint is obviously out of the first wallsegment, so we generate it for the next segment.
		generateNextJumpPoint();

		
		//magic. do not touch
		initializeBodyDef();
		wallsegment = world.createBody(wallDef);
		wallsegment.setUserData(this);
		
		PolygonShape wallShape = new PolygonShape();
		wallShape.setAsBox(WALL_WIDTH/2 , Enviroment.VP_HEIGHT/2, new Vector2(WALL_WIDTH/2, -Enviroment.VP_HEIGHT/2) , 0);
		getWallsegment().createFixture(wallShape, 0);
		wallShape.setAsBox(WALL_WIDTH/2 , Enviroment.VP_HEIGHT/2, new Vector2(Enviroment.VP_WIDTH - WALL_WIDTH/2, -Enviroment.VP_HEIGHT/2) , 0);
		getWallsegment().createFixture(wallShape, 0);
		
		wallShape.setAsBox(STAIR_WIDTH/2 , STAIR_WIDTH, new Vector2(WALL_WIDTH + STAIR_WIDTH/2, -Enviroment.VP_HEIGHT+ STAIR_WIDTH) , 0);
		getWallsegment().createFixture(wallShape, 0);
		
		wallShape.dispose();
		//end of magic
	}
	
	public float getBottomBaseY (){
		return wallsegment.getPosition().y;
		
	}
	

	public Body getWallsegment() {
		return wallsegment;
	}
	
}
