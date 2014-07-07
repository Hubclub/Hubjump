package com.kalt.GDXtrash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
		public void drawBlock (SpriteBatch batch){
			batch.draw(brickwall,0,y, wallLeft.width, Gdx.graphics.getHeight() ); // left wall
			batch.draw(brickwall,Gdx.graphics.getWidth()-wallRight.width,y, wallRight.width, Gdx.graphics.getHeight() ); // right wall
			BitmapFont font = new BitmapFont();
			//to delete:
				font.draw(batch, "BLOCK NUMBER " + y/Gdx.graphics.getHeight(), 0, y+500);
		}
	}
	public static final byte NUM_OF_BLOCKS=4;
	
	Block[] blocks = new Block[NUM_OF_BLOCKS]; // block-ul 3 e o copie identica a block-ului 0
	static Rectangle wallLeft;
	static Rectangle wallRight;
	
	static int i,corridor,width,widthOLD,corridorOLD,currentBlock= 0;
	
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
		batch.draw(background,width, y-Gdx.graphics.getHeight()/2 , corridor, Gdx.graphics.getHeight());
			blocks[currentBlock].drawBlock(batch);
			blocks[nextBlock()].drawBlock(batch);
	}
	/*public void resize (){
		widthOLD = width;
		corridorOLD= corridor;
		corridor= Gdx.graphics.getHeight()*9/20;
		width= (Gdx.graphics.getWidth() - corridor) /2;
		for (i=0;i<NUM_OF_BLOCKS;i++){
			blocks[i].brickwall= new Sprite(bricktexture, width, Gdx.graphics.getHeight());
		}
	}*/
	public static int nextBlock(){
		return currentBlock+1 == NUM_OF_BLOCKS ? 0 : currentBlock+1 ;
	}
	public static Rectangle getWallRight() {
		return wallRight;
	}
	public static Rectangle getWallLeft() {
		return wallLeft;
	}
}
