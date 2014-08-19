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
	public static final float WINDOW_WIDTH = 0.25f;
	public static final float STAIR_WIDTH = 3f;
	public static final float JUMP_HEIGHT= (Ninja.JUMP_SPEED * Ninja.JUMP_SPEED * (float)(Math.sin(Ninja.JUMP_ANGLE)*Math.sin(Ninja.JUMP_ANGLE)))
												/ (2 * -Enviroment.GRAVITATIONAL_ACCELERATION);
	public static final float DASH_HEIGHT= (Ninja.DASH_SPEED * Ninja.DASH_SPEED) 
												/ (2 * -Enviroment.GRAVITATIONAL_ACCELERATION);

	private class lastThreePoints{
		private Vector2[] pts;  // ORDER : first lastpoint, second lastpoint, lastJumpPoint;
		boolean inSegment;
		boolean lastPointSide; // false = left, true = right;
		
		public lastThreePoints(){
			lastPointSide = false; //hardcoded? should have been = true
			
			pts= new Vector2[3];
			pts [0] = new Vector2(Enviroment.VP_WIDTH - WALL_WIDTH/2, - Ninja.getNinjaHeight()/2);
			pts [1] = new Vector2(WALL_WIDTH/2, - Ninja.getNinjaHeight()/2);
			pts [2] = generateNextJumpPoint(pts[1].cpy());
			show();
		}
		public Vector2 peekFirst(){
			return pts[0];
		}
		public Vector2 peekLast(){
			return pts[2];
		}
		public void show(){
			System.out.println(pts[0] + "  " + pts[1] + " " + pts[2] + "      " + inSegment);			
		}
		public boolean moveLeft(){
			pts[0].set(pts[1]);
			pts[1].set(pts[2]);
			pts[2] = new Vector2(generateNextJumpPoint(pts[2]));
			lastPointSide = !lastPointSide;
			show();
			
			return inSegment;
		}
		protected Vector2 generateNextJumpPoint(Vector2 prevPt){
			//System.out.println( prevPt);
			// change the x to the center of the other wall
			if (prevPt.x == WALL_WIDTH/2)
				prevPt.x = Enviroment.VP_WIDTH - WALL_WIDTH/2;
			else prevPt.x= WALL_WIDTH/2;
			
			//increment y
			prevPt.y += JUMP_HEIGHT;
			
			// checks if lastpoint is in the wallsegment or outside of it
			if (prevPt.y < 0 )
				inSegment = true;
			else{
				inSegment = false;
				prevPt.y -= Enviroment.VP_HEIGHT;
				pts[0].y -= Enviroment.VP_HEIGHT;
				pts[1].y -= Enviroment.VP_HEIGHT;
			}
			return prevPt;
		}
		
		private float getWindowHeight(){
			return ( pts[2].y - pts[0].y ) /2 - Ninja.NINJA_HEIGHT/2;
		}
		
		//this transforms the last point into the window's coordinates and returns them
		private Vector2 getWindowPos(){
			if (lastPointSide)
				pts[0].x = WALL_WIDTH - WINDOW_WIDTH/2;
			else
				pts[0].x = Enviroment.VP_WIDTH - WALL_WIDTH + WINDOW_WIDTH/2;
			
			
			pts[0].y = (pts[0].y + pts[2].y)/2;
			
			return pts[0];
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
	
	
	public void generateNextSegment (World world){
			wallDef.position.y += Enviroment.VP_HEIGHT;
		wallsegment = world.createBody(wallDef);
		wallsegment.setUserData(this);
		
		
		PolygonShape wallShape = new PolygonShape();
		//checks if the jumpPoints fit in the wallsegment and adds the fixture.
		do{
			System.out.println("Created nextJumpPoint" + lastJumpPoints.peekLast());
			wallShape.setAsBox(WALL_WIDTH/2 , Ninja.NINJA_HEIGHT/2, new Vector2( lastJumpPoints.peekLast() ) , 0);
			wallsegment.createFixture(wallShape, 0);
			wallShape.setAsBox(WINDOW_WIDTH/2, lastJumpPoints.getWindowHeight() , new Vector2(lastJumpPoints.getWindowPos() ) , 0);
			wallsegment.createFixture(wallShape, 0);
			
		}while ( lastJumpPoints.moveLeft() );
		

		wallShape.dispose();
	}
	
	public void generateFirstSegment (World world){
		lastJumpPoints= new lastThreePoints();
		
		//crap. do not touch
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
