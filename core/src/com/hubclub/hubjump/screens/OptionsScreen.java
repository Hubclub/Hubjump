package com.hubclub.hubjump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hubclub.hubjump.worldenviroment.EnviromentRenderer;

public class OptionsScreen implements Screen {
	SpriteBatch batch;
	private Stage stage; //** stage holds the Button **//
	private BitmapFont font; 
	private TextureAtlas buttonsAtlas; //** image of buttons **//
	private Skin buttonSkin; //** images are used as skins of the button **//
	private TextButton button; //** the button - the only actor in program **//
	
	
	public OptionsScreen() {
		// TODO Auto-generated constructor stub
		batch = GameScreen.batch;
		
		buttonsAtlas = new TextureAtlas("button/buttons.pack");
		buttonSkin = new Skin();
	    buttonSkin.addRegions(buttonsAtlas);
		font = new BitmapFont();
		
		stage = new Stage(new ScreenViewport(), batch);
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("64X32");
        style.down = buttonSkin.getDrawable("64X32pressed");
        style.font = font;
		
        button = new TextButton("asdf", style);
        button.setPosition(0, 0); //** Button location **//
        button.setHeight(32); //** Button Height **//
        button.setWidth(64);

        button.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println( "button pressed" );
                    return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	System.out.println( "button released" );
            }
        });
        
        stage.addActor(button);
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
		batch.end();
		
		stage.draw();
		stage.act();
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
		
	}

}
