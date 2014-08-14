package com.hubclub.hubjump.characters;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.hubclub.hubjump.worldenviroment.Enviroment;

public class WallSegment {
	public static final float WALL_WIDTH = 1.5f;
	public static final float WINDOW_WIDTH = 0.5f;
	public static final float STAIR_WIDTH = 3f;
	public static final float JUMP_HEIGHT= (Ninja.JUMP_SPEED * Ninja.JUMP_SPEED * (float)(Math.sin(Ninja.JUMP_ANGLE)*Math.sin(Ninja.JUMP_ANGLE)))
												/ (2 * -Enviroment.GRAVITATIONAL_ACCELERATION);
	public static final float DASH_HEIGHT= (Ninja.DASH_SPEED * Ninja.DASH_SPEED) 
												/ (2 * -Enviroment.GRAVITATIONAL_ACCELERATION);

	private class lastThreePoints{
		private Vector2[] pts;  // ORDER : first lastpoint, second lastpoint, lastJumpPoint;
		//boolean first = true; // true= a is first...
		public lastThreePoints(){
			pts= new Vector2[3];
		}
		public void alocate (Vector2 ad,int i){
			pts[i]= new Vector2(ad);
		}
		public Vector2 peekFirst(){
			return pts[0];
		}
		public Vector2 peekSecond(){
			return pts[1];			
		}
		public Vector2 peekLast(){
			return pts[2];
		}
		public void show(){
			System.out.println(pts[0] + "  " + pts[1] + " " + pts[2]);			
		}
		public void moveLeft(){
			pts[0].set(pts[1]);
			pts[1].set(pts[2]);
			pts[2] = new Vector2(generateNextJumpPoint(pts[2]));
			show();
		}
	}
	
	private static BodyDef wallDef;
	private static lastThreePoints lastJumpPoints;
	private Body wallsegment;
	
	
	public void initializeBodyDef (){
		wallDef = new BodyDef();
		wallDef.type = BodyType.StaticBody;
		wallDef.position.set(0, Enviroment.VP_HEIGHT);
	}
	
	public WallSegment (){}
	
	protected Vector2 generateNextJumpPoint(Vector2 prevPt){
		//System.out.println( prevPt);
		// change the x to the center of the other wall
		if (prevPt.x == WALL_WIDTH/2)
			prevPt.x = Enviroment.VP_WIDTH - WALL_WIDTH/2;
		else prevPt.x= WALL_WIDTH/2;
		
		//increment y
		prevPt.y += JUMP_HEIGHT;
		//System.out.println( prevPt);
		return prevPt;
	}
	
	public void generateNextSegment (World world){
			wallDef.position.y += Enviroment.VP_HEIGHT;
		wallsegment = world.createBody(wallDef);
		wallsegment.setUserData(this);
		
		
		PolygonShape wallShape = new PolygonShape();
		//checks if the jumpPoints fit in the wallsegment and adds the fixture.
		while (lastJumpPoints.peekLast().y < wallDef.position.y){
			System.out.println("Created nextJumpPoint" + lastJumpPoints.peekLast());
			wallShape.setAsBox(WALL_WIDTH/2 , Ninja.NINJA_HEIGHT/2, new Vector2(lastJumpPoints.peekLast().x, lastJumpPoints.peekLast().y - wallDef.position.y) , 0);
			wallsegment.createFixture(wallShape, 0);
			wallShape.setAsBox(WINDOW_WIDTH, (lastJumpPoints.peekLast().y - lastJumpPoints.peekFirst().y)/2 , new Vector2(lastJumpPoints.peekLast().x, (lastJumpPoints.peekLast().y + lastJumpPoints.peekFirst().y) /2 -wallDef.position.y ) , 0);
			wallsegment.createFixture(wallShape, 0);

			lastJumpPoints.moveLeft();
		}
		

		wallShape.dispose();
	}
	
	public void generateFirstSegment (World world){
		lastJumpPoints= new lastThreePoints();
		// the first jump points to mark the start of the path...
		lastJumpPoints.alocate( new Vector2(Enviroment.VP_WIDTH - WALL_WIDTH/2, Enviroment.VP_HEIGHT - Ninja.getNinjaHeight()/2) , 0);
		lastJumpPoints.alocate( new Vector2(WALL_WIDTH/2, Enviroment.VP_HEIGHT - Ninja.getNinjaHeight()/2) , 1);
		System.out.println("Created firstJumpPoint" + lastJumpPoints.peekFirst());
		// the next jumpPoint is obviously out of the first wallsegment, but we generate it for the next segment.
		lastJumpPoints.alocate(new Vector2 ( generateNextJumpPoint (lastJumpPoints.peekSecond().cpy() )), 2);
		
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
		wallsegment.createFixture(wallShape, 0);
		
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
