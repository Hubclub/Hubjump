package com.hubclub.hubjump.characters;

import java.util.Stack;

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
	public static final float JUMP_HEIGHT = 2.9027f;
	public static final String NAME = "WallSegment";
	private static BodyDef wallDef;
	//
	static public Sprite wallSprite;
	private static Vector2 lastJumpPoint;
	private Stack<Vector2> jumpPoints;
	private Body wallsegment;
	
	public void initializeBodyDef (){
		wallDef = new BodyDef();
		wallDef.type = BodyType.StaticBody;
		wallDef.position.set(0, Enviroment.VP_HEIGHT);
	}
	
	public WallSegment (){
		jumpPoints= new Stack<Vector2>();
	}
	
	private Vector2 getNextJumpPoint(Vector2 prevPoint){
		// change the x to the center of the other wall
		if (prevPoint.x == WALL_WIDTH/2)
			prevPoint.x = Enviroment.VP_WIDTH - WALL_WIDTH/2;
		else prevPoint.x= WALL_WIDTH/2;
		
		prevPoint.y+= JUMP_HEIGHT;
		
		//System.out.println(prevPoint);
		return prevPoint;
	}
	
	public void generateNextSegment (World world){
			wallDef.position.y += Enviroment.VP_HEIGHT;
		wallsegment = world.createBody(wallDef);
		wallsegment.setUserData(this);
		
		//generate path (uses the previous generated point)
		while (lastJumpPoint.y < wallDef.position.y){
			jumpPoints.add(lastJumpPoint);
			lastJumpPoint = getNextJumpPoint(lastJumpPoint);
		}
		
		
		
		
		PolygonShape wallShape = new PolygonShape();
		wallShape.setAsBox(WALL_WIDTH/2 , Enviroment.VP_HEIGHT/2, new Vector2(WALL_WIDTH/2, -Enviroment.VP_HEIGHT/2) , 0);
		getWallsegment().createFixture(wallShape, 0);
		wallShape.setAsBox(WALL_WIDTH/2 , Enviroment.VP_HEIGHT/2, new Vector2(Enviroment.VP_WIDTH - WALL_WIDTH/2, -Enviroment.VP_HEIGHT/2) , 0);
		getWallsegment().createFixture(wallShape, 0);

		wallShape.dispose();
	}
	
	public void generateFirstSegment (World world){
		jumpPoints.add(new Vector2(WALL_WIDTH/2, Enviroment.VP_HEIGHT - Ninja.getNinjaHeight()/2) );
		lastJumpPoint = getNextJumpPoint(jumpPoints.peek());

		
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

	}
	
	public float getBottomBaseY (){
		return wallsegment.getPosition().y;
		
	}
	

	public Body getWallsegment() {
		return wallsegment;
	}
	
}
