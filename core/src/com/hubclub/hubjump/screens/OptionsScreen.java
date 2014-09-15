package com.hubclub.hubjump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hubclub.hubjump.GameClass;
import com.hubclub.hubjump.worldenviroment.EnviromentRenderer;

public class OptionsScreen implements Screen {
	@SuppressWarnings("unused")
	private GameClass game;
	private SpriteBatch batch;
	
	private Stage stage; //** stage holds the Button **//
	private BitmapFont font; 
	private Skin buttonSkin; //** images are used as skins of the button **//
	private TextButton button; //** the button - the only actor in program **//
	private Preferences prefs;
	private String highscore;
	Texture heightbar;
	
	public OptionsScreen(final GameClass game) {
		this.game = game;
		batch = GameScreen.batch;
		buttonSkin = MainMenu.buttonSkin;
		font = MainMenu.font;
		// TODO Auto-generated constructor stub
		prefs = Gdx.app.getPreferences("GamePreferences");
		highscore = prefs.getInteger("highscore", -1) + " m";
		heightbar = new Texture(Gdx.files.internal("button/heightmeter.png"));
		
		stage = new Stage(new ScreenViewport(), batch);
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		         
		
		addButton("reset score", "64X32", 5, 60, 70, 15, new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				prefs.putInteger("highscore", 0);
				prefs.flush();
				highscore = prefs.getInteger("highscore", -1) + " m";
			}
		});
		addButton("debug\npasteroni", "64X32", 5, 30, 70, 15, new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				EnviromentRenderer.debug = !EnviromentRenderer.debug;
			}
		});
		addButton("", "back", 67.5f, 2.5f , 30, 10, new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(game.theGame);
			}
		});
	}
	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		batch.begin();
			batch.draw(EnviromentRenderer.brickTexture,
					0, 0, // x and y
					0, 0,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
					5, 5, // scaleXY
					0, // rotation
					0, 0,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
					false, false);
		/*	batch.draw(EnviromentRenderer.brickTexture,
					0 ,0 ,
					0, 0,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );*/
		    batch.draw(heightbar,
		    		15/100f * Gdx.graphics.getWidth(), 50/100f * Gdx.graphics.getHeight(),
		    		60/100f * Gdx.graphics.getWidth(), 8/100f * Gdx.graphics.getHeight());
			
			font.draw(batch, highscore, 
					17/100f * Gdx.graphics.getWidth(),
					55.5f/100f * Gdx.graphics.getHeight());
		batch.end();
		
		stage.draw();
		stage.act();
	}
	
	public void addButton (String name,String path, float x, float y, float width, float height, InputListener inpl ) {
		TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
		style.up = buttonSkin.getDrawable(path);
		style.down = buttonSkin.getDrawable(path + "pressed");
		style.font = font;
		style.fontColor = Color.BLACK;
		
		button = new TextButton(name, style);
		button.setPosition(x/100 * Gdx.graphics.getWidth() , y/100 * Gdx.graphics.getHeight() ); //** Button location **//
		button.setWidth(width/100 * Gdx.graphics.getWidth());
		button.setHeight(height/100 * Gdx.graphics.getHeight());
		
		button.addListener(inpl);
		
		stage.addActor(button);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		this.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}

}
