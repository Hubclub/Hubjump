package com.hubclub.Hubjump.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.hubclub.Hubjump.worldenviroment.Enviroment;

public class WallSegment {
	public static final float WALL_WIDTH = 1.5f;
	private static BodyDef wallDef;
	//
	private Body wallsegment;
	
	public void initializeBodyDef (){
		wallDef = new BodyDef();
		wallDef.type = BodyType.StaticBody;
		wallDef.position.set(0, 0);
	}
	public WallSegment (){
		
	}
	
	public void generateNextSegment (World world){
			wallDef.position.y += Enviroment.VP_HEIGHT;
		wallsegment = world.createBody(wallDef);
		
		PolygonShape wallShape = new PolygonShape();
		wallShape.setAsBox(WALL_WIDTH/2 , Enviroment.VP_HEIGHT/2, new Vector2(WALL_WIDTH/2, Enviroment.VP_HEIGHT/2) , 0);
		wallsegment.createFixture(wallShape, 0);
		wallShape.setAsBox(WALL_WIDTH/2 , Enviroment.VP_HEIGHT/2, new Vector2(Enviroment.VP_WIDTH - WALL_WIDTH/2, Enviroment.VP_HEIGHT/2) , 0);
		wallsegment.createFixture(wallShape, 0);
		
		wallShape.dispose();
	}
	
	public void generateFirstSegment (World world){
		initializeBodyDef();
		wallsegment = world.createBody(wallDef);
		
		PolygonShape wallShape = new PolygonShape();
		wallShape.setAsBox(WALL_WIDTH/2 , Enviroment.VP_HEIGHT/2, new Vector2(WALL_WIDTH/2, Enviroment.VP_HEIGHT/2) , 0);
		wallsegment.createFixture(wallShape, 0);
		wallShape.setAsBox(WALL_WIDTH/2 , Enviroment.VP_HEIGHT/2, new Vector2(Enviroment.VP_WIDTH - WALL_WIDTH/2, Enviroment.VP_HEIGHT/2) , 0);
		wallsegment.createFixture(wallShape, 0);
		
		wallShape.dispose();
	}
	
}
