package com.kalt.GDXtrash;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.Texture.TextureWrap;
/*import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;*/
import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
		float delta;
		Ninja ninja;
		Walls walls;
	//
	OrthographicCamera camera; 
	SpriteBatch batch;
		BitmapFont font;
	
	@Override
	public void create () {
		walls = new Walls();
		ninja= new Ninja(walls,20,0);
	//
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch ();
			font = new BitmapFont();
	}

	@Override
	public void render () {
		delta=Gdx.graphics.getDeltaTime();
			Gdx.gl.glClearColor(0, 0, 0, 1.0f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.position.y=ninja.getY() + Gdx.graphics.getHeight()/4;
		camera.update();
		ninja.update(delta);
		
		batch.setProjectionMatrix(camera.combined);
		walls.draw(batch,camera.position.y);
		batch.begin();
			batch.draw(ninja.texture, ninja.getX(), ninja.getY(), ninja.getWidth(), ninja.getHeight() );
			font.draw(batch, "Y: " + ninja.getY(), 0, camera.position.y - Gdx.graphics.getHeight()/2 + 15);
			font.draw(batch, "X: " + ninja.getX(), 0, camera.position.y - Gdx.graphics.getHeight()/2 + 30);
		batch.end();
	}
	public void resize (int x, int y)
	{
			walls.resize();
			ninja.resize(walls);
		//
		camera.setToOrtho(false, x, y);
	}
}
