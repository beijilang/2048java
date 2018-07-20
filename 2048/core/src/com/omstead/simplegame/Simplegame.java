package com.omstead.simplegame;


import java.util.Arrays;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.omstead.simplegame.T20;

public class Simplegame extends ApplicationAdapter implements InputProcessor{
	SpriteBatch batch;
	Sprite character[][];
	Sprite block;
	String  theme;
	CharSequence str,score;
	BitmapFont font;
	private static GlyphLayout glyphLayout = new GlyphLayout();
	boolean gen = false;
	Texture img[] = new Texture[14];
	int x=0;
	int size=0;
	int y =1;
	String[] data;
	int highscore;
	T20 g;
	public String score(int size){// read highscore from file
		FileHandle file = Gdx.files.internal("score.txt");
		String datas = file.readString();
		
		datas = datas.substring(1,datas.length()-1);
		System.out.println(datas);
		data = datas.split(" ");
		for(int i=0;i<data.length;i++){
			data[i] = data[i].substring(0, data[i].length()-1);
		}
		System.out.println(Arrays.toString(data));
		return data[size];

	}
	@Override
	public void create () { 
		theme = "start"; 
		//called when first create the window
		font = new BitmapFont();
		font.setColor(0,0,0,1);
		font.getData().setScale(1.5f,1f);
		str = "enter the grid size(3-6)";
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
		for(int i =0;i<13;i++){
			img[i] = new Texture("T"+i+".png");
		}
		img[13] = new Texture("block.png");
		block = new Sprite(img[13]);
		
		
	}

	
	public void update(){ // update the sprite of each character
		for(int i =0;i<size;i++){
			for(int j = 0;j<size;j++){
				if(g.display(i, j)!=-1){
					character[i][j] = new Sprite(img[(g.display(i, j)-1)]);
					character[i][j].setPosition(128*i, 128*j);			
				}
			}
		}
		glyphLayout.setText(font, "Highscore: "+highscore+"  score: "+g.getScore());
		//score = "Highscore: "+highscore+"  score: "+g.getScore();
		if(g.getScore()>highscore){ // compare highscore
			highscore = g.getScore();
		}
		
	}
	@Override
	public void render () {
		//called every few milliseconds, skip when can't perform 
		if(theme.equals("game")){
			Gdx.gl.glClearColor(1, 1, 1, 1);
			update();
		}
		
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		character[11].setPosition(x, 100);
//		character[11].rotate(10);
		batch.begin();
		if(theme.equals("start")){
			Gdx.gl.glClearColor(.2f, 0.8f, .8f, 1);
			glyphLayout.setText(font,"A,D,W,S to move");
			font.draw(batch,glyphLayout,128*3-glyphLayout.width/2, 128*3+20);
			glyphLayout.setText(font,str);
			font.draw(batch, glyphLayout, 128*3-glyphLayout.width/2, 128*3);
			if(size!=0){
				block.setPosition(128, 128);
				block.draw(batch);
				g = new T20(size);
				g.generate();
				
				character = new Sprite[size][size];
				highscore = Integer.parseInt(score(size-3));
				theme = "game";
				

			}
		}
		else if(theme.equals("game")){
			for(int i =0;i<size;i++){
				for(int j = 0;j<size;j++){
					block.setPosition(i*128, j*128);
					block.draw(batch);
					if(g.display(i, j)!=-1){
						character[i][j].draw(batch);
						
					}	
				}
			}
			font.draw(batch,glyphLayout,128*size-glyphLayout.width,128*size+13);
			if(!g.check()){
				theme = "over";
			}
		}
		else if(theme.equals("over")){
			Gdx.gl.glClearColor(1, 1, 1, 1);
			font.draw(batch, "GAME OVER", 300, 300);
			font.draw(batch,"press any button to restart",240,240);
			if(g.getScore()>Integer.parseInt(data[size-3])){
				FileHandle file = Gdx.files.local("score.txt");
				System.out.println(Arrays.toString(data));
				data[size-3] =""+highscore;
				data[data.length-1] = "0,";
				file.writeString(Arrays.toString(data), false);
			}
		}
		
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img[0].dispose();
	}


	@Override
	public boolean keyDown(int keycode) {
		if(theme.equals("start")){
			if(keycode<=13 && keycode>=10){
				size = keycode-7;
			}
		}
		else if(theme.equals("game")){
			gen = true;
			if(keycode == Keys.S){
				g.left();
			}
			else if(keycode == Keys.W){
				g.right();
			}
			else if(keycode == Keys.D){
				g.down();
			}
			else if(keycode == Keys.A){
				g.up();
			}
			else{
				gen = false;
			}
		}
		else if(theme.equals("over")){
			theme = "start";
			size = 0;
		}
		
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		if(gen){
			gen =false;
			if(g.changed()){
				g.generate();
			}
		}
		
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
		// TODO Auto-generated method stub
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