package com.hubclub.Hubjump.worldenviroment;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.hubclub.Hubjump.characters.Ninja;
import com.hubclub.Hubjump.characters.NinjaContactListener;
import com.hubclub.Hubjump.characters.WallSegment;

public class Enviroment {
	private final static float GRAVITATIONAL_ACCELERATION = -10f;
	public final static float VP_WIDTH = 12;
	public final static float VP_HEIGHT = 20;
	
	private World world;
	Ninja ninja;
	Queue<WallSegment> segments;
	
	public Enviroment (){
		this.world =(new World(new Vector2(0, GRAVITATIONAL_ACCELERATION), true));
			ninja = new Ninja();
			ninja.setBody(world, 2.3f, 12f);
		world.setContactListener(new NinjaContactListener(ninja));
		
		segments = new LinkedList<WallSegment>();
			segments.add(new WallSegment());
			segments.peek().generateFirstSegment(world);
		for (int i=0;i<2;i++){
			WallSegment wll = new WallSegment();
			wll.generateNextSegment(world);
			segments.add(wll);
		}
		
		createDebugFloor(VP_WIDTH/2, 0, VP_WIDTH, 0.01f);
	}
	
	private void createDebugFloor(float x, float y, float width, float height) {
		// TODO Auto-generated method stub
		BodyDef debugFloorDef = new BodyDef();
			debugFloorDef.type= BodyType.StaticBody;
			debugFloorDef.position.set(x, y);
		Body debugFloor = getWorld().createBody(debugFloorDef);
			
		PolygonShape floorShape = new PolygonShape();
		floorShape.setAsBox(width/2, height/2);
		debugFloor.createFixture(floorShape, 0f);
		
		floorShape.dispose();
	}

	public void update (){
		
		
		
		ninja.update();
		getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);
	}

	public World getWorld() {
		return world;
	}

}
