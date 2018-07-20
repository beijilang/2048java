package com.omstead.simplegame;


import java.util.Random;
public class T20 { // a class contain the grid and method to access it
	private static int[][] grid;
	private int gridSize;
	private int score =0;
	boolean change = false;
	private static boolean[][] used;
	private static Random ran = new Random();
	public T20(int x){ // intialize and create the grid size
		grid= new int[x][x];
		gridSize = x;
	}
	public int getScore(){// return score
		return score;
	}
	public void generate(){// random create a 2 or 4 in a random unfill position
		int x,y;
		boolean fill = false;
		change = false;
		for (int i = 0;i<gridSize;i++){ // check if there are empty block
			for(int j = 0;j<gridSize;j++){
				if(grid[i][j]==0){
					fill = true;
					break;
				}
			}
		}
		if(fill){ // generate numbers if there are empty block
			do{
				x = ran.nextInt(gridSize);
				y = ran.nextInt(gridSize);
			}while (grid[x][y]!=0&&fill);
			int num = ran.nextInt(2)+1;
			grid[x][y] =num;
		}
		

	}

	public boolean check(){// check if no move can be make
		boolean fill = false;
		for (int i = 0;i<gridSize;i++){// check if there are empty block and if player can make any moves
			for(int j = 0;j<gridSize;j++){
				if(grid[i][j]==0){
					fill = true;
					break;
				}
				else if(i<gridSize-1&&j<gridSize-1){
					if(grid[i][j]==grid[i+1][j]||grid[i][j]==grid[i][j+1]){
						fill = true;
						break;
					}
				}
			}
		}
		return fill;
	}
	public int display(int x, int y){// display the current position's value
		if(grid[x][y]==0){
			return -1;
		}
		else{
			return grid[x][y];
		}
	}
	public void up(){ // move up
		used = new boolean[gridSize][gridSize];
		for(int i =1;i<gridSize;i++){
			for(int j=0;j<gridSize;j++){
				if(grid[i][j]!=0){
					int x=1;
					while(grid[i-x][j]==0){
						x++;
						if(x>i){
							break;
							}
						}
						if(x!=1){
							grid[i-x+1][j]=grid[i][j];
							grid[i][j]=0;
							change = true;
							add(-1,0,(i-x+1),j);
						}
						else{
							add(-1,0,i,j);
						}
						
				}
				
			}
		}
	}
	public void left(){ // move left
		used = new boolean[gridSize][gridSize];
		for(int j =1;j<gridSize;j++){
			for(int i=0;i<gridSize;i++){
				if(grid[i][j]!=0){
					int x=1;
					while(grid[i][j-x]==0){
						x++;
						if(x>j){
							break;
							}
						}
						if(x!=1){
							change = true;
							grid[i][j-x+1]=grid[i][j];
							grid[i][j]=0;
							add(0,-1,i,(j-x+1));
						}
						else{
							add(0,-1,i,j);
						}
						
				}
				
			}
		}
	}
	public void down(){ // move down
		used = new boolean[gridSize][gridSize];
		for(int i =gridSize-2;i>-1;i--){
			for(int j=0;j<gridSize;j++){
				if(grid[i][j]!=0){
					int x=1;
					while(grid[i+x][j]==0){
						x++;
						if(x+i>gridSize-1){
							break;
							}
						}
						if(x!=1){
							change = true;
							grid[i+x-1][j]=grid[i][j];
							grid[i][j]=0;
							add(1,0,(i+x-1),j);
						}
						else{
							add(1,0,i,j);
						}
						
				}
				
			}
		}
	}
	public void right(){//move right
		used = new boolean[gridSize][gridSize];
		for(int j =gridSize-2;j>-1;j--){
			for(int i=0;i<gridSize;i++){
				if(grid[i][j]!=0){
					int x=1;
					while(grid[i][j+x]==0){
						x++;
						if(x+j>gridSize-1){
							break;
							}
						}
						if(x!=1){
							change = true;
							grid[i][j+x-1]=grid[i][j];
							grid[i][j]=0;
							add(0,1,i,(j+x-1));
						}
						else{
							add(0,1,i,(j+x-1));
						}
						
				}
				
			}
		}
	}
	public boolean changed(){
		return change;
	}
	public void add(int x,int y,int i,int j){ // add the number that are same and in the correct direction
		
		if(i+x>-1&&j+y>-1&&i+x<gridSize&&j+y<gridSize){
			if(grid[i][j]!=0&&grid[i+x][j+y]==grid[i][j]&&used[i+x][j+y]==false){
				grid[i][j]=0;
				used[i+x][j+y]=true;
				grid[i+x][j+y]=grid[i+x][j+y]+1;
				change = true;
				score+=Math.pow(2,grid[i+x][j+y]);
			
			}		
		}
	}
}

