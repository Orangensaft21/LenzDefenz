package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.HEIGHT;
import static helpers.Leveler.saveMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Editor {
	
	private TileGrid grid;
	private TileType[] types;
	private int index;
	@SuppressWarnings("unused")
	private boolean leftMouseButtonDown,rightMouseButtonDown;
	
	public Editor(){
		grid = new TileGrid();
		types = new TileType[3];
		types[0] = TileType.Grass;
		types[1] = TileType.Dirt;
		types[2] = TileType.Water;
	}
	
	public void update(){
		grid.draw();
		if (Mouse.isButtonDown(1) && !rightMouseButtonDown)
			SetTile();
		
		
		leftMouseButtonDown=Mouse.isButtonDown(0);
		rightMouseButtonDown=Mouse.isButtonDown(1);
		//Tastatur input
		while (Keyboard.next()){
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()){
				MoveIndex();
				//Clock.changeMultiplier(0.2f);;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
				saveMap("LenzMap1",grid);
			}
		}
	}
	
	
	public void SetTile(){
		grid.setTile((int)Math.floor(Mouse.getX()/TILE_SIZE),
					 (int)Math.floor((HEIGHT -Mouse.getY()-1)/TILE_SIZE), types[index]);
	}
	
	private void MoveIndex(){
		index++;
		if (index > types.length-1){
			index =0;
		}
	}
}
