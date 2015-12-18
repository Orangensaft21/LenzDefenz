package data;

import static helpers.Artist.QuickLoad;

public class Game {
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	
	//Temp variables

	
	public Game(){
		grid = new TileGrid(Boot.map);
		waveManager = new WaveManager(new Enemy(QuickLoad("ufo"),grid.getTile(0,2),64,64,93),
				0.5f,5);
		player = new Player(grid, waveManager);

	}
	
	public void update(){
		grid.Draw();
		waveManager.update();
		player.update();

	}
	
}
