package com.hubclub.hubjump.worldenviroment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hubclub.hubjump.characters.WallSegment;

public class Background {
	private static Texture background = new Texture(Gdx.files.internal("background.png"));	
	
	public Background(){
	
	}
	
	public void draw(SpriteBatch batch, float ratio){
		batch.draw(background,
				WallSegment.WALL_WIDTH*ratio ,0 ,
				(Enviroment.VP_WIDTH - 2*WallSegment.WALL_WIDTH)*ratio , Enviroment.VP_HEIGHT*ratio );
		
	}
}
