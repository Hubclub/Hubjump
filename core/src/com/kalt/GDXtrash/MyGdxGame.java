package com.kalt.GDXtrash;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
/*import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;*/
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
		float delta;
		int wallWidth,corridor;
		Ninja ninja;
		//temporare:
			Rectangle wallLeft, wallRight;
	//
	OrthographicCamera camera; 
	SpriteBatch batch;
		BitmapFont font;
		Texture bricktexture,background;
		Sprite brickwall;
	
	@Override
	public void create () {
		corridor= Gdx.graphics.getHeight()*9/20; //width of the corridor
		wallWidth= (Gdx.graphics.getWidth() - corridor) /2; // width of each wall
		ninja= new Ninja(wallWidth, corridor);
			
			//temporare:
				wallLeft= new Rectangle(0,0,wallWidth,Gdx.graphics.getHeight());
				wallRight= new Rectangle(Gdx.graphics.getWidth()-wallWidth,0,wallWidth,Gdx.graphics.getHeight());
	//
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch ();
			font = new BitmapFont();
			background= new Texture (Gdx.files.internal("Background2.PNG"));
			
			bricktexture = new Texture (Gdx.files.internal("120px-BrickTex.PNG"));
			bricktexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			brickwall= new Sprite(bricktexture, wallWidth, Gdx.graphics.getHeight());
	}

	@Override
	public void render () {
		delta=Gdx.graphics.getDeltaTime();
			Gdx.gl.glClearColor(0, 0, 0, 1.0f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.position.y=ninja.getY() + Gdx.graphics.getHeight()/4;
		camera.update();
		ninja.update(delta, wallRight, wallLeft);
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
			batch.draw(background,wallWidth, 0, corridor, Gdx.graphics.getHeight());
			batch.draw(brickwall, 0, 0);
			batch.draw(brickwall,Gdx.graphics.getWidth() - wallWidth, 0);
			batch.draw(ninja.texture, ninja.getX(), ninja.getY(), ninja.getWidth(), ninja.getHeight() );
			font.draw(batch, "Y: " + ninja.getY(), 0, 15);
			font.draw(batch, "X: " + ninja.getX(), 0, 30);
		batch.end();
	}
	public void resize (int x, int y)
	{
			int wallWidthOLD = wallWidth;
			int corridorOLD= corridor;
			corridor= Gdx.graphics.getHeight()*9/20;
			wallWidth= (Gdx.graphics.getWidth() - corridor) /2;
			ninja.resize(wallWidthOLD, corridorOLD, wallWidthOLD, corridorOLD);
		//
		camera.setToOrtho(false, x, y);
			brickwall= new Sprite(bricktexture, wallWidth, Gdx.graphics.getHeight());
	}
}
