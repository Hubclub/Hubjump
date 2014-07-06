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
		final double JUMP_ANGLE = Math.toRadians(30.0);
		float ninjaHeight,jumpSpeed,delta;
		int wallWidth,corridor;
		Rectangle ninjaBox;
		boolean jumpTrigger,jumpDirection;
		//Vector2 ninjaPos;
		//temporare:
			Rectangle wallLeft, wallRight;
	//
	OrthographicCamera camera; 
	SpriteBatch batch;
		BitmapFont font;
		Texture ninja,bricktexture,background;
		Sprite brickwall;
	
	@Override
	public void create () {
		corridor= Gdx.graphics.getHeight()*9/20;
		wallWidth= (Gdx.graphics.getWidth() - corridor) /2;
			ninjaHeight= Gdx.graphics.getHeight()*1.6f /20;
			ninjaBox= new Rectangle(wallWidth, 50, ninjaHeight, ninjaHeight);
		jumpDirection=true; // true= right, false= left
		jumpTrigger=false;
		jumpSpeed = 200;
			//temporare:
				wallLeft= new Rectangle(0,0,wallWidth,Gdx.graphics.getHeight());
				wallRight= new Rectangle(Gdx.graphics.getWidth()-wallWidth,0,wallWidth,Gdx.graphics.getHeight());
	//
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch ();
			font = new BitmapFont();
			ninja = new Texture (Gdx.files.internal("ninja/idle.PNG"),true); //mipmaps!
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
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		if (Gdx.input.justTouched() ){
			jumpTrigger=true;
		}
		
		if (jumpTrigger)
		{
			ninjaBox.y += jumpSpeed * delta * Math.sin(JUMP_ANGLE);
			if (jumpDirection) ninjaBox.x += jumpSpeed * delta;
				else ninjaBox.x -= jumpSpeed * delta * Math.cos(JUMP_ANGLE);
			
			if (ninjaBox.overlaps(wallRight) || ninjaBox.overlaps(wallLeft)){
				if (jumpDirection) ninjaBox.x= wallWidth + corridor-ninjaBox.width;
					else ninjaBox.x= wallWidth;
				jumpTrigger=false;
				jumpDirection=!jumpDirection;}
		}
		
		batch.begin();
			batch.draw(background,wallWidth, 0, corridor, Gdx.graphics.getHeight());
			batch.draw(brickwall, 0, 0);
			batch.draw(brickwall,Gdx.graphics.getWidth() - wallWidth, 0);
			batch.draw(ninja, ninjaBox.x, ninjaBox.y, ninjaBox.width, ninjaBox.height );
			font.draw(batch, "Y: " + ninjaBox.y, 0, 15);
			font.draw(batch, "X: " + ninjaBox.x, 0, 30);
		batch.end();
	}
	public void resize (int x, int y)
	{
			int wallWidthOLD = wallWidth;
			int corridorOLD= corridor;
			corridor= Gdx.graphics.getHeight()*9/20;
			wallWidth= (Gdx.graphics.getWidth() - corridor) /2;
				ninjaBox.x += wallWidth-wallWidthOLD;
				ninjaBox.y = ninjaBox.y*corridor / corridorOLD;
				//ninjaHeight= y*1.6f /20;
				ninjaBox.height= y*1.6f /20;
				ninjaBox.width= ninjaBox.height;
		//
		camera.setToOrtho(false, x, y);
			brickwall= new Sprite(bricktexture, wallWidth, Gdx.graphics.getHeight());
	}
}
