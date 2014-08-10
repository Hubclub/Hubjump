package com.hubclub.hubjump.worldenviroment;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class EnviromentRenderer {
	private Enviroment env;
	private World world;
	Array<Body> bodies;
	
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
		SpriteBatch batch;
		BitmapFont font;
	
	private boolean debug;
		
	public EnviromentRenderer (Enviroment env,boolean debug){
		this.env= env;
		this.world= env.world;
		this.debug=debug;
		debugRenderer = new Box2DDebugRenderer();
		bodies = new Array<Body>();
			batch= new SpriteBatch();
			font = new BitmapFont();
		
		camera= new OrthographicCamera();
		camera.setToOrtho(false,Enviroment.VP_WIDTH,Enviroment.VP_HEIGHT);
		camera.update();
	}
	
	public void updateStage (){
		
		// get the body list and fetch an iterator
		world.getBodies(bodies);
		Iterator <Body> bi = bodies.iterator();
	
		while (bi.hasNext()){
		    Body b = bi.next();
		    
		    if (b.getUserData() != null){ // not necessary, but for safetey's sake
		    						
		    	// check if the object is of type ninja
			    if (b.getUserData().getClass().getName() == "com.hubclub.hubjump.characters.Ninja"){ 
			    	if (b.getPosition().y > camera.position.y ){ // reposition the camera if ninja goes above the camera's center
			    		camera.position.y = b.getPosition().y;
			    		camera.update();
			    	}
			    	
			    	//show character coordinates
			    	if (debug){
			    	batch.begin();
						font.draw(batch, "X: " + b.getPosition().x, 0, camera.position.y + 10 );
						font.draw(batch, "Y: " + b.getPosition().y, 0, camera.position.y  );
					batch.end();
			    	}
			    }
		  
			    //checks if wallsegments are under the screen and need deletion.
			    if (b.getPosition().y < camera.position.y- Enviroment.VP_HEIGHT/2 &&
			    	b.getUserData().getClass().getName() == "com.hubclub.hubjump.characters.WallSegment"){
			    	
			    	System.out.println("object deleted!");
			    	env.moveQueue();
			    }
		    }
		    /*
		    Entity e = (Entity) b.getUserData();
	
		    if (e != null) {
		        Update the entities/sprites position and angle
		        e.setPosition(b.getPosition().x, b.getPosition().y);
		        We need to convert our angle from radians to degrees
		        e.setRotation(MathUtils.radiansToDegrees * b.getAngle());
		        */
	}
}
	
	public void render (){
		Gdx.gl.glClearColor(0f, 0f, 0.2f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updateStage();
		
		debugRenderer.render(world, camera.combined);
	}
}
