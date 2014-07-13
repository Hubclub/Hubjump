package com.hubclub.Hubjump.worldenviroment;

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
	private Box2DDebugRenderer debugRenderer;
	private World world;
	private OrthographicCamera camera;
	Array<Body> bodies;
		SpriteBatch batch;
		BitmapFont font;
	
	public EnviromentRenderer (World world){
		this.world= world;
		debugRenderer = new Box2DDebugRenderer();
		bodies = new Array<Body>();
			batch= new SpriteBatch();
			font = new BitmapFont();
		
		camera= new OrthographicCamera();
		camera.setToOrtho(false,Enviroment.VP_WIDTH,Enviroment.VP_HEIGHT);
		camera.update();
	}
	
	public void updateStage (){
		world.getBodies(bodies);
	Iterator <Body> bi = bodies.iterator();

	while (bi.hasNext()){
	    Body b = bi.next();
	    
	    if (b.getUserData() != null){
	    	//System.out.println(b.getUserData().getClass().getName());
	    	
		    if (b.getUserData().getClass().getName() == "com.hubclub.Hubjump.characters.Ninja"){
		    	if (b.getPosition().y > camera.position.y ){
		    		camera.position.y = b.getPosition().y;
		    		camera.update(); }
		    	
		    	//debug
		    	batch.begin();
					font.draw(batch, "X: " + b.getPosition().x, 0, camera.position.y + 10 );
					font.draw(batch, "Y: " + b.getPosition().y, 0, camera.position.y  );
				batch.end();
		    }
	  
	    }
	    
	    // Entity e = (Entity) b.getUserData();

	 //   if (e != null) {
	        // Update the entities/sprites position and angle
	      //  e.setPosition(b.getPosition().x, b.getPosition().y);
	        // We need to convert our angle from radians to degrees
	      //  e.setRotation(MathUtils.radiansToDegrees * b.getAngle());
	}
}
	
	public void render (){
		Gdx.gl.glClearColor(0f, 0f, 0.2f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updateStage();
		
		debugRenderer.render(world, camera.combined);
	}
}
