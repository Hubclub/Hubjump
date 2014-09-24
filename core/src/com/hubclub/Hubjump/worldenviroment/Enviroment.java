package com.hubclub.hubjump.worldenviroment;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.hubclub.hubjump.characters.Ninja;
import com.hubclub.hubjump.characters.NinjaContactListener;
import com.hubclub.hubjump.characters.WallSegment;
import com.hubclub.hubjump.screens.GameScreen;

public class Enviroment {
	public final static float GRAVITATIONAL_ACCELERATION = -10f;
	public final static float VP_WIDTH = 12;
	public final static float VP_HEIGHT = 20;
	public final static byte NUMBER_OF_BLOCKS = 3;
	
	protected World world;
	private Ninja ninja;
	protected  Queue<WallSegment> segments;
	
	boolean fixtureDeletion;
	Vector2 impulse;
	Fixture fixture;
	
	public Enviroment (){
		this.world =(new World(new Vector2(0, GRAVITATIONAL_ACCELERATION), true));
			ninja = new Ninja();
			ninja.setBody(world, 2.2f, 6f);
		world.setContactListener(new NinjaContactListener(ninja,this));
		GameScreen.inp.setNinja(ninja);
		
		segments = new LinkedList<WallSegment>();
		generateFirstSegments();
	
		fixtureDeletion = false;
	}
	
	private void generateFirstSegments(){
		segments.add(new WallSegment());
		segments.peek().generateFirstSegment(world);
		
		for (int i=0 ; i<NUMBER_OF_BLOCKS-1 ; i++){
			WallSegment wll = new WallSegment();
			wll.generateNextSegment(world);
			segments.add(wll);
		}
		
	}
	
	public void moveQueue () {
		world.destroyBody(segments.poll().getWallsegment());
		
		WallSegment wll = new WallSegment();
			wll.generateNextSegment(world);
			segments.add(wll);
		System.out.println("wallsegment added!");
	}
	
	public void update (){

	    //checks if wallsegments are under the screen and need deletion.
	    if (segments.peek().getBottomBaseY() < EnviromentRenderer.camera.position.y- Enviroment.VP_HEIGHT/2) {
	    	System.out.println("object deleted!");
	    	moveQueue();
	    }
		
	    ninja.update();
		world.step(Gdx.graphics.getDeltaTime(), 12, 4);
		
		if (fixtureDeletion && fixture != null){ // acts like a trigger. deletes a window once the deletion is "queued"
			//createShards();

			fixture.getBody().destroyFixture(fixture); // must be called after the step
			fixture = null;
			fixtureDeletion = false;
			
			ninja.crashIntoWindow(impulse);
		}
	}
	@SuppressWarnings("unused")
	private void createShards(){
		PolygonShape shape = (PolygonShape) fixture.getShape();
		Vector2 v = new Vector2();
		shape.getVertex(1, v);
		float height = v.y;
		shape.getVertex(2, v);
		height = v.y - height;
		
		

	}
	
	public void queueFixtureDeletion(Fixture fix, Vector2 impulse){
		fixture = fix;
		this.impulse = impulse;
		fixtureDeletion = true;
	}
	
	public void stopContactListener(){
		world.setContactListener(null);
	}

	public World getWorld() {
		return world;
	}
	public Ninja getNinja(){
		return ninja;
	}
	
	public void dispose(){
		world.dispose();
	}

}
