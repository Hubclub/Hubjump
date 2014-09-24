package com.hubclub.hubjump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

public class HelpScreen implements Screen {
	@SuppressWarnings("unused")
	private GameClass game;
	private SpriteBatch batch;
	private BitmapFont font; 
	private Skin buttonSkin;
	private Stage stage;
	private TextButton button;
	
	public HelpScreen(final GameClass game) {
		this.game = game;
		batch = GameScreen.batch;
		
		buttonSkin = MainMenu.buttonSkin;
		font = MainMenu.font;
		
		stage = new Stage(new ScreenViewport(), batch);
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
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
		batch.end();
		
		stage.draw();
		stage.act();
	}
	
	public void addButton (String name,String path, float x, float y, float width, float height, InputListener inpl ) {
		TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
		style.up = buttonSkin.getDrawable(path);
		style.down = buttonSkin.getDrawable(path + "pressed");
		style.font = font;
		style.pressedOffsetX = 1;
		style.pressedOffsetY = -1;
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
