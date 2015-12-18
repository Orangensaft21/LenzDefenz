package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


import java.util.ArrayList;

import static helpers.Artist.*;

public class Player {

	private TileGrid grid;
	private TileType[] types;
	private int index;
	private WaveManager waveManager;
	private ArrayList<TowerCannon> towerList;
	
	public Player(TileGrid grid,WaveManager waveManager){
		this.grid=grid;
		types = new TileType[3];
		types[0] = TileType.Grass;
		types[1] = TileType.Dirt;
		types[2] = TileType.Water;
		this.index=0;
		this.waveManager =waveManager;
		this.towerList = new ArrayList<TowerCannon>();
	}
	
	public void SetTile(){
		grid.setTile((int)Math.floor(Mouse.getX()/64),
					 (int)Math.floor((HEIGHT -Mouse.getY()-1)/64), types[index]);
	}
	
	public void update(){
		for (TowerCannon t :towerList)
			t.update();
		//Maus input
		if (Mouse.isButtonDown(0)){
			SetTile();
		}
		
		
		//Tastatur input
		while (Keyboard.next()){
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
				MoveIndex();
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()){
				towerList.add(new TowerCannon(QuickLoad("cannonBase"),grid.getTile(4,0),10,waveManager.getCurrentWave().getEnemyList()));
			}
		}
	}
	
	private void MoveIndex(){
		index++;
		if (index > types.length-1){
			index =0;
		}
	}
	
}
