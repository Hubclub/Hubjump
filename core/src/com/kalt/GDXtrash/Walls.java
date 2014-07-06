package com.kalt.GDXtrash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Walls {
	public class Block{
		float y;
		Sprite brickwall;
		public Block(Texture bricktexture,int i){
			y=i*Gdx.graphics.getHeight();
			bricktexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			brickwall= new Sprite(bricktexture, width, Gdx.graphics.getHeight());
		}
	}
	public final byte NUM_OF_BLOCKS=4;
	
	Block[] blocks = new Block[NUM_OF_BLOCKS];
	static Rectangle wallLeft;
	static Rectangle wallRight;
	
	int i,corridor,width,widthOLD,corridorOLD,currentBlock= 0;
	
	Texture background,bricktexture;
	public Walls (){
		corridor= Gdx.graphics.getHeight()*9/20; //width of the corridor
		width= (Gdx.graphics.getWidth() - corridor) /2; // width of each wall
		wallLeft=(new Rectangle(0,0,width,Gdx.graphics.getHeight()* NUM_OF_BLOCKS));
		wallRight=(new Rectangle(Gdx.graphics.getWidth()-width, 0, width,Gdx.graphics.getHeight()*NUM_OF_BLOCKS));
		
		background= new Texture (Gdx.files.internal("Background2.PNG"));
		bricktexture = new Texture (Gdx.files.internal("120px-BrickTex.PNG"));
		for (i=0;i<NUM_OF_BLOCKS;i++){
			blocks[i]= new Block(bricktexture,i);
		}
	}
	public void draw(SpriteBatch batch,float y){
		batch.begin();
		batch.draw(background,width, y-Gdx.graphics.getHeight()/2 , corridor, Gdx.graphics.getHeight());
			batch.draw(blocks[currentBlock].brickwall, 0, blocks[currentBlock].y);
			batch.draw(blocks[nextBlock()].brickwall, 0, blocks[nextBlock()].y);
			batch.draw(blocks[currentBlock].brickwall,Gdx.graphics.getWidth() - width, blocks[currentBlock].y);
			batch.draw(blocks[nextBlock()].brickwall,Gdx.graphics.getWidth() - width, blocks[nextBlock()].y);
		batch.end();
	}
	public void resize (){
		widthOLD = width;
		corridorOLD= corridor;
		corridor= Gdx.graphics.getHeight()*9/20;
		width= (Gdx.graphics.getWidth() - corridor) /2;
		for (i=0;i<NUM_OF_BLOCKS;i++){
			blocks[i].brickwall= new Sprite(bricktexture, width, Gdx.graphics.getHeight());
		}
	}
	public int nextBlock(){
		return currentBlock == NUM_OF_BLOCKS ? 0 : currentBlock+1 ;
	}
	public static Rectangle getWallRight() {
		return wallRight;
	}
	public static Rectangle getWallLeft() {
		return wallLeft;
	}
}
