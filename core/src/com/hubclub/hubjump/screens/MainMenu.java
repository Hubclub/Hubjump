package com.hubclub.hubjump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MainMenu implements InputProcessor {
	class MenuButton {
		Texture tex;
		Vector2 pos;
		float width, height;
		String name;
		boolean pressed;
		
		public MenuButton (String name, String path, float x, float y, float ratio ) {
			tex = new Texture (Gdx.files.internal(path) );
			pos = new Vector2(x/100 * Gdx.graphics.getWidth() , y/100 * Gdx.graphics.getHeight() );
			this.width = tex.getWidth() * ratio;
			this.height = tex.getHeight() * ratio;
			pressed = false;
			this.name= name;
		}
		public void render(SpriteBatch batch){
			batch.draw(tex, pos.x, pos.y, width, height);
		}
		public boolean update(float x, float y){
			if (x>= pos.x && x <= pos.x + width && y>=pos.y && y<=pos.y + height){
				pressed = true;
				return true;
			}
			pressed = false;
			return false;
		}
		public void action() {
			// this is meant to be overridden
			System.out.println("MAIN MENU: NO ACTION ASSIGNED FOR BUTTON");
		}
	}
	private static byte NUMBER_OF_BUTTONS = 1;
	private boolean isShown;
	SpriteBatch batch;
	MenuButton[] buttons;
	
	public MainMenu(){
		buttons = new MenuButton[NUMBER_OF_BUTTONS];
		batch = new SpriteBatch();
		
		buttons[0] = new MenuButton("start", "button/start.PNG", 50, 20, 3) {
			public void action (){
				hide();
				GameScreen.setInputHandler();
			}
		};
		
		isShown=true;
	}
	
	public void render (){
		batch.begin();
		
		for (MenuButton i : buttons){
			i.render(batch);
		}
		
		batch.end();
	}

	public void hide (){
		isShown = false;
	}
	public void show(){
		isShown = true;
	}
	public boolean isShown(){
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
