package data;

import static helpers.Artist.BeginSession;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import helpers.Clock;
import pathfinding.AStarPathFinder;
import pathfinding.Path;
import pathfinding.PathFinder;
import pathfinding.TileBasedMap;

import static helpers.Artist.*;

public class Boot {
	
	/** The path finder we'll use to search our map */
	private PathFinder finder;
	/** The last path found for the current unit */
	private Path path;
	/** The x coordinate of the target of the last path we searched for - used to cache and prevent constantly re-searching */
	private int lastFindX = -1;
	/** The y coordinate of the target of the last path we searched for - used to cache and prevent constantly re-searching */
	private int lastFindY = -1;
	
	public static int[][] map = {
			{2,2,2,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
			{2,2,2,0,0,0,1,2,2,2,1,0,0,0,0,0,0,0,0,0},
			{1,1,1,1,0,0,1,2,2,0,1,0,0,0,0,0,0,0,0,0},
			{2,2,0,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0},
			{2,2,0,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,2,2,2,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,2,2,2,2,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,2,2,1,1,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
	};
	public static TileGrid grid;
	
	Boot(){
		
		BeginSession();
		
		grid = new TileGrid(map);
		//TileBasedMap sd = new TileGrid(map);

		
		Game game = new Game();
		
		while(!Display.isCloseRequested()){
			Clock.update();
			game.update();
			Display.update();
			Display.sync(60);
			
			
		}
		
		Display.destroy();
	}

	public static void main(String[] args) {
		
		new Boot();

	}

}
