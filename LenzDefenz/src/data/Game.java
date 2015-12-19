package data;

import static helpers.Artist.QuickLoad;

import static helpers.Clock.*;



public class Game {
	
	public static final int TILE_SIZE = 64;
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	
	//Temp variables

	
	public Game(){
		grid = Boot.grid;
		waveManager = new WaveManager(new Enemy(QuickLoad("ufo"),grid.getTile(0,2) ,64,64,93),
				0.5f,5);
		player = new Player(grid, waveManager);

	}
	
	public void update(){
		grid.Draw();
		waveManager.update();
		player.update();
		//System.out.println(Delta());

	}
	
}
