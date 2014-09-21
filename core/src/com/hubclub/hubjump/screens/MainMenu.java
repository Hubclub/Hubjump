package com.hubclub.hubjump.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import com.hubclub.hubjump.GameClass;

public class MainMenu {
	private static boolean isShown,showMessage;
	private static GameClass game;
	Texture title;
	/////
	private Stage stage; //** stage holds the Button **//
	private TextureAtlas buttonsAtlas; //** image of buttons **//
	protected static Skin buttonSkin; //** images are used as skins of the button **//
	protected static BitmapFont font;
	private TextButton button; //** the button - the only actor in program **//
	
	public MainMenu(GameClass game,SpriteBatch batch){
		MainMenu.game = game;
		title = new Texture(Gdx.files.internal("button/title.png"));
		stage = new Stage(new ScreenViewport(), batch);
		
		// load empty buttons
		buttonsAtlas = new TextureAtlas("button/Buttons.pack");
		buttonSkin = new Skin();
	    buttonSkin.addRegions(buttonsAtlas);
		
		font = new BitmapFont(Gdx.files.internal("font/LCD_Solid.fnt"));
		font.setScale(0.6f);
		font.setColor(Color.BLACK);
		
		isShown = true;
		showMessage = false;

		initializeMainMenuButtons();
		
		Gdx.input.setInputProcessor(stage);
	}
	public void initializeMainMenuButtons(){
		stage.clear(); // delete current buttons
		
		showMessage = false;
		// initialize the menu buttons:
		addButton("START", "64X32", 42.5f, 15, 40f, 10f , new InputListener(){
			 public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            System.out.println( "button pressed" );
	            return true;
	         }
	         public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	         	System.out.println( "button released" );
	         	
	        	hide();
	        	GameScreen.setInputHandler();
	         }
		});
	/*	addButton("HUBJUMP", "128X32", 15, 85, 70 , 10 , new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button pressed" );
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button released" );
			}
		});*/
		addButton("", "options",  62.5f, 27.5f, 20f, 10f , new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button pressed" );
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button released" );
				
				game.setScreen(new OptionsScreen(game));
			}
		});
		addButton("", "help", 42.5f, 27.5f, 20f, 10f, new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button pressed" );
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button released" );
				game.setScreen(new HelpScreen(game));
			}
		});
		addButton("HIGHSCORES", "128X32", 15, 40, 70 , 10 , new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button pressed" );
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button released" );
			}
		});
	}
	
	public void initializeRetryButtons(){
		stage.clear();
		
		showMessage = true;
		// initialize the retry overlay buttons
	//	buttons[0] = new MenuButton("gameover", "button/GG.png", 15, 65, 70, 25);
		addButton("main menu", "128X32", 20, 40, 60, 10 , new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button pressed" );
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button released" );
				
				game.theGame.restartGame(true);
			}
		});
		addButton("retry", "128X32", 20, 25, 60, 10 , new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button pressed" );
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button released" );
				
				showMessage = false;
				game.theGame.restartGame(false);
			}
		});
		
		isShown = true;
	}
	
	public void switchTo(int i){ // 0 for mainMenu buttons, 1 for the retry menu
		if (i == 0){
			initializeMainMenuButtons();
		}else{
			initializeRetryButtons();
		}
	}
	
	public void drawButtons (){	
		stage.act();
		stage.draw();
	}
	public void draw(SpriteBatch batch){
		if (showMessage)
			drawScore(batch, font, GameScreen.GGmessage, 50, 60);
		if (isShown && !showMessage) // draw the title
		batch.draw(title, 7/100f * Gdx.graphics.getWidth(), 80/100f * Gdx.graphics.getHeight(),
					85/100f * Gdx.graphics.getWidth(), 15/100f * Gdx.graphics.getHeight());
	}

	public void hide (){
		isShown = false;
	}
	public void show(){
		isShown = true;
		// each time we show it we change the input processor
		Gdx.input.setInputProcessor(stage);
	}
	public static boolean isShown(){
		if (isShown)
			return true;
		return false;
	}
	
	public void drawScore(SpriteBatch batch, BitmapFont font, String text, float x, float y){
		font.drawMultiLine(batch, text,
		x/100 * Gdx.graphics.getWidth() - font.getBounds(text).width/2 ,
		y/100 * Gdx.graphics.getHeight() );
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
	/*
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenY = Gdx.graphics.getHeight() - screenY; // tiny correction 
		
		for (MenuButton i : buttons){
			if (i.update(screenX, screenY) ){
				System.out.println("MAIN MENU: PRESSED THE " + i.name +" BUTTON");
				i.action();
				return true;
			}
		}
		System.out.println("MAIN MENU: NO BUTTON TOUCHED");
		return false;
	}*/
	
}
