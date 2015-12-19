package data;
import static helpers.Artist.*;

import pathfinding.Mover;
import pathfinding.TileBasedMap;


public class TileGrid implements TileBasedMap{
	
	public Tile[][] map;
	
	/** Indicator if a given tile has been visited during the search */
	private boolean[][] visited = new boolean[WIDTH][HEIGHT];
	
	public TileGrid(){												//Default Konstruktor macht alles zu grass
		map = new Tile[20][15];
		for(int i=0;i < map.length;i++)
			for(int j=0;j<map[i].length;j++){
				map[i][j] = new Tile(i*64,j*64,64,64,TileType.Grass);
			}
	}
	public TileGrid(int[][] newMap){
		map = new Tile[20][15];
		for(int i=0;i < map.length;i++)
			for(int j=0;j<map[i].length;j++){
		
				switch (newMap[j][i]){
				case 0:
					map[i][j] = new Tile(i*64,j*64,64,64,TileType.Grass);
					break;
				case 1:
					map[i][j] = new Tile(i*64,j*64,64,64,TileType.Dirt);
					break;
				case 2:
					map[i][j] = new Tile(i*64,j*64,64,64,TileType.Water);
					break;
				};
			}
	}
	
	public void setTile(int xCoord, int yCoord, TileType type){
		map[xCoord][yCoord] = new Tile(xCoord*64,yCoord*64,64,64,type);
	}
	
	public Tile getTile(int xCoord, int yCoord){
		return map[xCoord][yCoord];
	}
	
	public void Draw(){
		for (int i=0;i<map.length;i++)
			for (int j=0;j<map[i].length;j++){
				Tile t = map[i][j];
				t.Draw();
			}
	}
	
	/**
	 * Clear the array marking which tiles have been visted by the path 
	 * finder.
	 */
	public void clearVisited() {
		for (int x=0;x<getWidthInTiles();x++) {
			for (int y=0;y<getHeightInTiles();y++) {
				visited[x][y] = false;
			}
		}
	}
	
	/**
	 * @see TileBasedMap#visited(int, int)
	 */
	public boolean visited(int x, int y) {
		return visited[x][y];
	}
	
	@Override
	public int getWidthInTiles() {
		return map.length;
	}
	@Override
	public int getHeightInTiles() {
		return map[0].length;
	}
	@Override
	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
	}
	@Override
	public boolean blocked(Mover mover, int x, int y) {
		

		if (map[x][y].getType().textureName=="dirt"){
			//System.out.println(Math.floor(map[x][y].getX()/64)+"y"+Math.floor(map[x][y].getY()/64)+"grass");
			return false;
		}
		System.out.println(x+","+y+map[x][y].getType().textureName);
		return true;
	}
	@Override
	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
		// TODO Auto-generated method stub
		return 1;
	}
	
	
	
	
}
