package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

import java.util.ArrayList;

import static helpers.Artist.*;

public class Player {

	private TileGrid grid;
	private TileType[] types;
	private int index;
	private WaveManager waveManager;
	private ArrayList<TowerCannon> towerList;
	private boolean leftMouseButtonDown,rightMouseButtonDown;
	
	public Player(TileGrid grid,WaveManager waveManager){
		this.grid=grid;
		types = new TileType[3];
		types[0] = TileType.Grass;
		types[1] = TileType.Dirt;
		types[2] = TileType.Water;
		this.index=0;
		this.waveManager =waveManager;
		this.towerList = new ArrayList<TowerCannon>();
		leftMouseButtonDown = false;
		rightMouseButtonDown=false;
	}
	
	public void SetTile(){
		grid.setTile((int)Math.floor(Mouse.getX()/64),
					 (int)Math.floor((HEIGHT -Mouse.getY()-1)/64), types[index]);
	}
	
	public void update(){
		for (TowerCannon t :towerList)
			t.update();
		
		//Maus input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown){
			towerList.add(new TowerCannon(QuickLoad("cannonBase"),grid.getTile(getMouseX(),getMouseY()),10,waveManager.getCurrentWave().getEnemyList()));
		}
		if (Mouse.isButtonDown(1) && !rightMouseButtonDown)
			SetTile();
		
		
		leftMouseButtonDown=Mouse.isButtonDown(0);
		rightMouseButtonDown=Mouse.isButtonDown(0);
		//Tastatur input
		while (Keyboard.next()){
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
				Clock.changeMultiplier(0.2f);;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()){
				Clock.changeMultiplier(-0.2f);;
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
	
	public int getMouseX(){
		return (int)Math.floor(Mouse.getX()/64);
	}
	
	public int getMouseY(){
		return (int)Math.floor((HEIGHT -Mouse.getY()-1)/64);
	}
	
}
