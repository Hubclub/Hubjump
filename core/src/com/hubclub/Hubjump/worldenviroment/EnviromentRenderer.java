package com.hubclub.hubjump.worldenviroment;

import com.hubclub.hubjump.characters.Ninja;
import com.hubclub.hubjump.screens.GameScreen;
import com.hubclub.hubjump.screens.MainMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class EnviromentRenderer {
	private World world;
	
	public static float pixRatio = Gdx.graphics.getHeight() / Enviroment.VP_HEIGHT;
	public static OrthographicCamera camera;
	private static int score,initCamHeight = (int) Enviroment.VP_HEIGHT/2;
	public static Texture brickTexture = new Texture(Gdx.files.internal("BrickTexture.png") );
	public Box2DDebugRenderer debugRenderer;
		SpriteBatch batch;
		BitmapFont font,debugFont;
		private Background background;
	private Ninja ninja;
	private boolean debug;
	private String scoreString;
		
	public EnviromentRenderer (Enviroment env,boolean debug){
		this.world = env.world;
		this.ninja = env.getNinja();
		this.debug = debug;
		score = 0;
		debugRenderer = new Box2DDebugRenderer();
			batch= new SpriteBatch();
			font = new BitmapFont(Gdx.files.internal("font/scorefont.fnt"));
			font.setScale(0.9f);;
			debugFont = new BitmapFont();
		background = new Background();
		brickTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		camera= new OrthographicCamera();
		camera.setToOrtho(false,Enviroment.VP_WIDTH,Enviroment.VP_HEIGHT);
		camera.update();
	}
	
	public void updateStage (){
		if (ninja.getY() > camera.position.y ){ // reposition the camera if ninja goes above the camera's center
	  		camera.position.y = ninja.getY();
	  		camera.update();
		}
		//update & show score
		if (camera.position.y - initCamHeight> score && !GameScreen.isGameOver())
			score += 1;
		scoreString = score + " m";
		if ( !MainMenu.isShown() )
		font.draw(batch, scoreString, 
				Gdx.graphics.getWidth()/2 - font.getBounds(scoreString).width/2,
				4f/5 * Gdx.graphics.getHeight());
		//show character coordinates
		if (debug){
			debugFont.draw(batch, "X: " + ninja.getX(), 0, 40 );
			debugFont.draw(batch, "Y: " + ninja.getY(), 0, 20 );
		}
		ninja.draw(batch , pixRatio, (ninja.getY() - camera.position.y + Enviroment.VP_HEIGHT/2) *pixRatio );
		
		if (ninja.getY() < camera.position.y - Enviroment.VP_HEIGHT/2 - Ninja.NINJA_HEIGHT){
			ninja.freeze();
			
			if (!GameScreen.isGameOver() )
				GameScreen.gameOver();
		}
	}
	
	public void render (){
		Gdx.gl.glClearColor(0f, 0f, 0.2f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
			background.draw(batch,pixRatio);
			updateStage();
		batch.end();
			
		debugRenderer.render(world, camera.combined);
	}
	
	public static int getScore(){
		return score;
	}
}
