package com.hubclub.hubjump.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MainMenu implements InputProcessor {
	class MenuButton {
		Texture tex;
		Vector2 pos;
		float width, height;
		String name;
		boolean pressed;
		
		public MenuButton (String name, String path, float x, float y, float width, float height ) {
			tex = new Texture (Gdx.files.internal(path) );
			pos = new Vector2(x/100 * Gdx.graphics.getWidth() , y/100 * Gdx.graphics.getHeight() );
			
			this.width = width/100 * Gdx.graphics.getWidth();
			this.height = height/100 * Gdx.graphics.getHeight();
			
			pressed = false;
			this.name= name;
		}
		public void render(SpriteBatch batch){
			batch.draw(tex, pos.x, pos.y, width, height);
		}
		public void render(SpriteBatch batch, BitmapFont font, String text, float x, float y){
			font.drawMultiLine(batch, text,
							x/100 * Gdx.graphics.getWidth() - font.getBounds(text).width/2 ,
							y/100 * Gdx.graphics.getHeight() );
		}
		public boolean update(float x, float y){
			if (x>= pos.x && x <= pos.x + width && y>=pos.y && y<=pos.y + height){
				pressed = true;
				return true;
			}
			pressed = false;
			return false;
		}
		
		// this function is meant to be overridden
		public void action() {
			System.out.println("MAIN MENU: NO ACTION ASSIGNED FOR BUTTON " + name);
		}
	}
	private static byte NUMBER_OF_MAIN_MENU_BUTTONS = 5;
	private static byte NUMBER_OF_RETRY_MENU_BUTTONS = 3;
	private static boolean isShown,showMessage;
	SpriteBatch batch;
	BitmapFont font;
	MenuButton[] buttons;
	GameScreen gameScreen;
	
	public MainMenu(){
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		initializeMainMenuButtons();
		isShown = true;
		showMessage = false;
	}
	// needed to restart the game. called only once
	public void giveGameScreenAdress(GameScreen g){gameScreen = g;}
	
	public void initializeMainMenuButtons(){
		buttons = new MenuButton[NUMBER_OF_MAIN_MENU_BUTTONS];
		showMessage = false;
		// initialize the menu buttons:
		buttons[0] = new MenuButton("start", "button/start.png", 42.5f, 15, 40f, 10f) {
			public void action (){
				hide();
				GameScreen.setInputHandler();
			}
		};
		buttons[1] = new MenuButton("title", "button/titre.png", 15, 85, 70 , 10) {
			public void action (){
				System.out.println("This is the title, why would you tap it?");
			}
		};
		buttons[2] = new MenuButton("options", "button/options.png", 62.5f, 27.5f, 20f, 10f) {
			public void action (){
				
			}
		};
		buttons[3] = new MenuButton("help", "button/help.png", 42.5f, 27.5f, 20f, 10f) {
			public void action (){
				
			}
		};
		buttons[4] = new MenuButton("highscores", "button/highscores.png", 15, 40, 70 , 10) {
			public void action (){
				
			}
		};
	}
	
	public void initializeRetryButtons(){
		buttons = new MenuButton[NUMBER_OF_RETRY_MENU_BUTTONS];
		showMessage = true;
		// initialize the retry overlay buttons
		buttons[0] = new MenuButton("gameover", "button/GG.png", 15, 65, 70, 25);
		buttons[1] = new MenuButton("mainmenu", "button/mainmenu.png", 20, 40, 60, 10){
			public void action(){
				gameScreen.restartGame(true);
			}
		};
		buttons[2] = new MenuButton("retry", "button/retry.png", 20, 25, 60, 10){
			public void action(){
				gameScreen.restartGame(false);
			}
		};
		
		isShown = true;
	}
	
	public void switchTo(int i){ // 0 for mainMenu buttons, 1 for the retry menu
		if (i == 0){
			initializeMainMenuButtons();
		}else{
			initializeRetryButtons();
		}
	}
	
	public void render (){
		batch.begin();
		
		for (MenuButton i : buttons){
			i.render(batch);
		}
		if (showMessage)
			buttons[0].render(batch, font, GameScreen.GGmessage, 50, 60);
		
		batch.end();
	}

	public void hide (){
		isShown = false;
	}
	public void show(){
		isShown = true;
	}
	public static boolean isShown(){
		if (isShown)
			return true;
		return false;
	}
	//INPUT PROCCESSING METHODS (for the main menu and only the main menu)
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

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
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
