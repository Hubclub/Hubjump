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
	public static final float STAIR_HEIGHT = 6f;
	public static final float SLIDE_RANGE = 2f; // required for generating the path. represents how much the ninja 
												// should slide down until he can jump again
	public static final float JUMP_HEIGHT = (float)calculateJumpHeight();
	public static final float DASH_HEIGHT = (Ninja.DASH_SPEED * Ninja.DASH_SPEED) 
												/ (2 * -Enviroment.GRAVITATIONAL_ACCELERATION);
	
	public static final float windowXleft = WALL_WIDTH/2; //optimization
	public static final float windowXright = Enviroment.VP_WIDTH - WALL_WIDTH/2;
	
	public static final float wallFriction = 1f;
	public static final float windowFriction = 0.2f;

	private class lastThreePoints{
		private Vector2[] pts;  // ORDER : first lastpoint, second lastpoint, lastJumpPoint;
		private float[] ptsHeight;
		private float windowHeight;
		boolean inSegment;
		boolean lastPointSide; // false = left, true = right;
		
		public lastThreePoints(){
			lastPointSide = false; //hardcoded? should have been = true
			
			pts= new Vector2[3];
			ptsHeight= new float[3];
			pts [0] = new Vector2(windowXright, -Ninja.NINJA_HEIGHT/2);
			pts [1] = new Vector2(windowXleft, -Ninja.NINJA_HEIGHT/2);
			ptsHeight [0] = Ninja.NINJA_HEIGHT;
			ptsHeight [1] = Ninja.NINJA_HEIGHT;
			
			pts [2] = generateNextJumpPoint(pts[1].cpy());
			//show();
		}
		public float peekHeight(){
			return ptsHeight[2];
		}
		public Vector2 peek(){
			return pts[2];
		}
		@SuppressWarnings("unused")
		public void show(){
			System.out.println(pts[0] + "  " + pts[1] + " " + pts[2] + "      " + inSegment);			
			System.out.println(ptsHeight[0] + "  " + ptsHeight[1] + " " + ptsHeight[2]);			
		}
		public boolean moveLeft(){
			ptsHeight[0]=ptsHeight[1];
			ptsHeight[1]=ptsHeight[2];
			
			pts[0].set(pts[1]);
			pts[1].set(pts[2]);
			pts[2] = new Vector2(generateNextJumpPoint(pts[2]));
			
			lastPointSide = !lastPointSide;
			//show();
			
			return inSegment;
		}
		protected Vector2 generateNextJumpPoint(Vector2 prevPt){
			//System.out.println( prevPt);
			// change the x to the center of the other wall
			if (prevPt.x == windowXleft)
				prevPt.x = windowXright;
			else prevPt.x = windowXleft;
			
			//adapt y
			prevPt.y += -ptsHeight[1]/2 + Ninja.NINJA_HEIGHT/2;
			
			//increment y
			prevPt.y += JUMP_HEIGHT;
			ptsHeight[2] = Ninja.NINJA_HEIGHT + (float)Math.random()*SLIDE_RANGE;
			prevPt.y += - ptsHeight[2] /2 + Ninja.NINJA_HEIGHT/2;
			
			// checks if lastpoint is in the wallsegment or outside of it
			if (prevPt.y < 0 )
				inSegment = true;
			else{
				inSegment = false;
				pts[0].y -= Enviroment.VP_HEIGHT;
				pts[1].y -= Enviroment.VP_HEIGHT;
				prevPt.y -= Enviroment.VP_HEIGHT;
			}
			return prevPt;
		}
		
		//has to be called before getWindowPos() !
		private float getWindowHeight(){
			windowHeight = (pts[2].y - ptsHeight[2]/2) - (pts[0].y + ptsHeight[0] /2);
			return windowHeight;
		}
		
		//this transforms the last point into the window's coordinates and returns them
		private Vector2 getWindowPos(){
			if (lastPointSide)
				pts[0].x = WALL_WIDTH - WINDOW_WIDTH/2;
			else
				pts[0].x = Enviroment.VP_WIDTH - WALL_WIDTH + WINDOW_WIDTH/2;
			
			
			pts[0].y = pts[2].y - ptsHeight[2]/2 - windowHeight/2;
			
			return pts[0];
		}
	}/////////////////////////////////////////////////////////////////////////
	
	private static BodyDef wallDef;
	private static lastThreePoints lastJumpPoints;
	private Body wallsegment;
	
	public static double calculateJumpHeight(){
		double t= (Enviroment.VP_WIDTH - 2*WALL_WIDTH - Ninja.NINJA_WIDTH) / (Ninja.JUMP_SPEED * Math.cos(Ninja.JUMP_ANGLE));
		return Ninja.JUMP_SPEED * Math.sin(Ninja.JUMP_ANGLE) * t + Enviroment.GRAVITATIONAL_ACCELERATION *t*t/2;
	}
	
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
			System.out.println("WALLSEGMENT: Created nextJumpPoint" + lastJumpPoints.peek());
			wallShape.setAsBox(WALL_WIDTH/2 , lastJumpPoints.peekHeight()/2 , new Vector2( lastJumpPoints.peek() ) , 0);
			wallsegment.createFixture(wallShape, 0).setFriction(wallFriction);
			
			wallShape.setAsBox(WINDOW_WIDTH/2, lastJumpPoints.getWindowHeight()/2 , new Vector2(lastJumpPoints.getWindowPos() ) , 0);
			wallsegment.createFixture(wallShape, 4).setFriction(windowFriction);
												//density 4 is used just to figure out in other methods that this fixture is a window
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
		
		wallShape.setAsBox(STAIR_WIDTH/2 , STAIR_HEIGHT/2, new Vector2(WALL_WIDTH + STAIR_WIDTH/2, -Enviroment.VP_HEIGHT+ STAIR_WIDTH) , 0);
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
