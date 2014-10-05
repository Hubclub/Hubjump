package com.hubclub.hubjump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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
	private Skin icons;
	
	public HelpScreen(final GameClass game) {
		this.game = game;
		batch = GameScreen.batch;
		
		buttonSkin = MainMenu.buttonSkin;
		font = MainMenu.font;
		
		stage = new Stage(new ScreenViewport(), batch);
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		TextureAtlas iconsAtlas = new TextureAtlas("button/icons.pack");
		icons = new Skin();
		icons.addRegions(iconsAtlas);
		
		Image black = new Image(icons.getDrawable("black"));
		black .setBounds(2.5f/100f * Gdx.graphics.getWidth(), 17f/100f * Gdx.graphics.getHeight(),
					95/100f * Gdx.graphics.getWidth(), 65/100f * Gdx.graphics.getHeight());
		stage.addActor(black );
		
		addButton("", "back", 67.5f, 2.5f , 30, 10, new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(game.theGame);
			}
		});
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		labelStyle.fontColor = Color.WHITE;
		Label instructions = new Label("Jump by tapping" + "\n"
				+ "the screen and" + "\n"
				+ "swipe to dash" + "\n"
				+ "up a wall," + "\n"
				+ "but only once " + "\n"
				+ "per wall!\n" + "\n"
				+ "Go as high" + "\n"
				+ "as you can" + "\n"
				+ "but be careful" + "\n"
				+ "not to land" + "\n"
				+ "on a window!", labelStyle);
		instructions.setBounds(5/100f * Gdx.graphics.getWidth(), 18.5f/100f * Gdx.graphics.getHeight(),
							90/100f * Gdx.graphics.getWidth(), 63/100f * Gdx.graphics.getHeight());
		stage.addActor(instructions);
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
		button.addListener(new InputListener(){	
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				GameScreen.buttonSound.play(GameScreen.sound/100f);
				return true;
			}
		});
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
