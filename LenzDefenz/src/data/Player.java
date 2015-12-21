package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

import java.util.ArrayList;

import static helpers.Artist.*;

public class Player {

	private TileGrid grid;
	private WaveManager waveManager;
	private ArrayList<TowerCannon> towerList;
	private boolean leftMouseButtonDown,rightMouseButtonDown;
	
	public Player(TileGrid grid,WaveManager waveManager){
		this.grid=grid;
		this.waveManager =waveManager;
		this.towerList = new ArrayList<TowerCannon>();
		leftMouseButtonDown = false;
		rightMouseButtonDown=false;
	}
	
	
	
	public void update(){
		for (TowerCannon t :towerList)
			t.update();
		
		//Maus input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown ){
			towerList.add(new TowerCannon(QuickLoad("cannonBase"),grid.getTile(getMouseX()/zoomFactor,getMouseY()/zoomFactor),10));
		}
		//if (Mouse.isButtonDown(1) && !rightMouseButtonDown)
			//SetTile();
		/*keine Hilfsvariablen die nach dem ersten
		 * Mausklick auf true gesetzt werden um Mehrfachklicks
		 * zu vermeiden
		 */
		leftMouseButtonDown=Mouse.isButtonDown(0);
		rightMouseButtonDown=Mouse.isButtonDown(1);
		/*
		 * zoomen
		 */
		if (Mouse.hasWheel())
			zoomWithMouseWheel(Mouse.getDWheel());
		
		
		//Tastatur input
		while (Keyboard.next()){
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()){
				Clock.changeMultiplier(-0.2f);;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
				Clock.changeMultiplier(-0.2f);
			}
		}
	}
	
	public int zoomWithMouseWheel(int wheel){
		if (wheel>0){
			zoomFactor += 0.5f;
			zoom();
		}
		if (wheel<0){
			System.out.println("down");
			zoomFactor -= 0.5f;
			zoom();
		}
		return 0;
	}
	
	public int getMouseX(){
		return (int)Math.floor(Mouse.getX()/64);
	}
	
	public int getMouseY(){
		return (int)Math.floor((HEIGHT -Mouse.getY()-1)/64);
	}
	
}
