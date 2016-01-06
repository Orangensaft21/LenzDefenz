package data;

import static helpers.Artist.QuickLoad;

import static helpers.Clock.*;

import helpers.Clock;
import static helpers.Artist.TILE_SIZE;





public class Game {
	
	
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	//Temp variables
	
	
	public Game(){
		grid = Boot.grid;
		//Enemy enemy = new Enemy(QuickLoad("ufo"),grid.getTile(0,2) ,TILE_SIZE,TILE_SIZE,93,1500);
		Enemy enemy = new Enemy(EnemyType.LenzPenz,grid.getTile(0,2));
		waveManager = new WaveManager(enemy,
				0.5f,8);
		player = new Player(grid, waveManager);
		//
		//testen schrift
		

	}
	
	public void update(){
		grid.draw();
		waveManager.update();
		player.update();
		Clock.update();
		//System.out.println(Delta());
		//System.out.println(TotalTime());
		if (Delta()>0.02)
				System.out.println("ruckelruckel");
		

	}
	
}
