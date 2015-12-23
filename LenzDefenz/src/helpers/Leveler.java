package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import data.Tile;
import data.TileGrid;
import data.TileType;

public class Leveler {
	
	public static void saveMap(String mapName, TileGrid grid){
		String mapData = "";
		
		for (int i=0;i<grid.getTilesWide();i++)
			for (int j=0; j<grid.getTilesHigh();j++){
				mapData += getTileID(grid.getTile(i, j));
			}
		
		try {
			File file = new File(mapName);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(mapData);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	public static TileGrid loadMap(String mapName){
		TileGrid grid = new TileGrid();                         						//grass
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(mapName));
			String data = br.readLine();
			for(int i=0;i<grid.getTilesWide();i++){
				for(int j=0;j<grid.getTilesHigh();j++){
					grid.setTile(i, j, getTileType(data.substring(i *grid.getTilesHigh() +j,
																  i*grid.getTilesHigh()+j+1)));
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return grid;
	}
	
	
	public static String getTileID(Tile t){
		return t.getType().getNumber();
	}
	
	public static TileType getTileType(String tileNumber){
		for (TileType t: TileType.values())
			if (t.getNumber().equals(tileNumber))
				return t;
		System.out.println("ERROR BEIM MAP LADEN");
		return null;
	}
	
}
