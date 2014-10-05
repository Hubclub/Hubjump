package com.hubclub.hubjump.worldenviroment;

import java.util.Iterator;

import com.hubclub.hubjump.characters.Ninja;
import com.hubclub.hubjump.characters.NinjaAnimation;
import com.hubclub.hubjump.characters.WallSegment;
import com.hubclub.hubjump.screens.GameScreen;
import com.hubclub.hubjump.screens.MainMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class EnviromentRenderer {
	@SuppressWarnings("unused")
	private World world;
	private Enviroment env;
	
	public static float pixRatio = Gdx.graphics.getHeight() / Enviroment.VP_HEIGHT;
	public static OrthographicCamera camera;
	private static int score,initCamHeight = (int) Enviroment.VP_HEIGHT/2;
	private float brickWallOriginY = Enviroment.VP_HEIGHT/2 ,brickWallY;
	
	public static Texture StairTexture = new Texture(Gdx.files.internal("stairs.png") );
	public static Texture brickTexture = new Texture(Gdx.files.internal("BrickTexture.png") );
	private static float u,v, scale = 4.5f; // brick texture related
	
	public static Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
		BitmapFont font,debugFont;
		private Background background;
	private Ninja ninja;
	private String scoreString;
	Iterator<WallSegment> it;
		
	public EnviromentRenderer (Enviroment env){
		this.env = env;
		this.world = env.world;
		this.ninja = env.getNinja();
		score = 0;
			font = new BitmapFont(Gdx.files.internal("font/LCD_Solid.fnt"));
			font.setColor(Color.WHITE);
			font.setScale(0.5f);;
			debugFont = new BitmapFont();
		background = new Background();
		brickTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		camera= new OrthographicCamera();
		camera.setToOrtho(false,Enviroment.VP_WIDTH,Enviroment.VP_HEIGHT);
		camera.update();
		
		// u and v values for the walls
		u = WallSegment.WALL_WIDTH * EnviromentRenderer.pixRatio / brickTexture.getWidth();
		v = Gdx.graphics.getHeight() / brickTexture.getHeight() + 1;
		u /= scale;
		v /= scale;
	}
	
	public void updateStage (SpriteBatch batch){
		if (ninja.getY() > camera.position.y ){ // reposition the camera if ninja goes above the camera's center
	  		camera.position.y = ninja.getY();
	  		camera.update();
	  		if (camera.position.y > brickWallOriginY + brickTexture.getHeight() * scale /pixRatio ){
	  			brickWallOriginY += brickTexture.getHeight() * scale /pixRatio;
	  		}
	  		brickWallY = (brickWallOriginY - camera.position.y)*pixRatio ;
		}
		//update & show score
		if (camera.position.y - initCamHeight> score && !GameScreen.isGameOver())
			score += 1;
		scoreString = score + " m";
		if ( !MainMenu.isShown() )
		font.draw(batch, scoreString, 
				Gdx.graphics.getWidth()/2 - font.getBounds(scoreString).width/2,
				4f/5 * Gdx.graphics.getHeight());
		
		if (ninja.getY() < camera.position.y - Enviroment.VP_HEIGHT/2 - Ninja.NINJA_HEIGHT){
			ninja.freeze();
			
			if (!GameScreen.isGameOver() ){
				GameScreen.scream.play();
				GameScreen.gameOver();
			}
		}
	}
	
	public void render (SpriteBatch batch){
		background.draw(batch,pixRatio);
		updateStage(batch);
			
		ninja.draw(batch , pixRatio, (ninja.getY() - camera.position.y + Enviroment.VP_HEIGHT/2) *pixRatio );
		drawWalls(batch);
		
		//show character coordinates
		if (GameScreen.debug){
			debugFont.draw(batch, "X: " + ninja.getX(), 0, 40 );
			debugFont.draw(batch, "Y: " + ninja.getY(), 0, 20 );
		}
	}
	
	private void drawWalls(SpriteBatch batch){
		batch.draw(brickTexture, 0, brickWallY,
				WallSegment.WALL_WIDTH * EnviromentRenderer.pixRatio, Gdx.graphics.getHeight() + brickTexture.getHeight()*scale,
				0 , 0,
				u , v );
		batch.draw(brickTexture, Gdx.graphics.getWidth() - WallSegment.WALL_WIDTH * EnviromentRenderer.pixRatio , brickWallY,
				WallSegment.WALL_WIDTH * EnviromentRenderer.pixRatio, Gdx.graphics.getHeight() + brickTexture.getHeight()*scale,
				0 , 0,
				u , v );

		it = env.segments.iterator();
		it.next().draw(batch, camera.position.y);
		it.next().draw(batch, camera.position.y);
		it.next().draw(batch, camera.position.y); //draw the windows...
	}
	
	public void debugRender(World world){
		if (GameScreen.debug)
			debugRenderer.render(world, camera.combined);
		// this somehow interferes with batch drawing so it should always be called last
	}
	
	public void dispose(){
		brickTexture.dispose();
		StairTexture.dispose();
		font.dispose();
		debugFont.dispose();
		NinjaAnimation.dispose();
	}
	
	public static int getScore(){
		return score;
	}
}
