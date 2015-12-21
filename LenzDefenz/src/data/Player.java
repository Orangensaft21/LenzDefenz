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
			Tile tile = grid.getTile(getMouseX()/totalZoom,getMouseY()/totalZoom);
			if (tile.getType().buildable)
				towerList.add(new TowerCannon(QuickLoad("cannonBase"),tile,10));
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
		/*
		 * Beim scrollen bitte nur faktoren von 2 verwenden
		 */
		if (Keyboard.isKeyDown(Keyboard.KEY_D)){
			scrollMap(8,0);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)){
			scrollMap(-8,0);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)){
			scrollMap(0,8);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)){
			scrollMap(0,-8);
		}
		while (Keyboard.next()){
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()){
				Clock.changeMultiplier(-0.2f);;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()){
				Clock.changeMultiplier(-0.2f);
			}
			// scroll test
		}
	}
	
	
	/*public Tile getTileFromMouse(){
		return grid.getTile(getMouseX()/totalZoom+(int)Math.floor(left/(totalZoom*64)),getMouseY()/totalZoom+top/(totalZoom*64));
		
		//return null;
		
	}*/
	
	public void zoomWithMouseWheel(int wheel){
		if (wheel>0){
			zoom(0.5f);
		}
		if (wheel<0){
			System.out.println("down");
			zoom(-0.5f);
		}
	}
	
	/*
	 * funktion macht bei krummen zooms noch probleme
	 */
	
	public int getMouseX(){
		return (int)((Mouse.getX()+left*totalZoom)/64);
	}
	
	public int getMouseY(){
		return (int) ((HEIGHT -Mouse.getY()-1*totalZoom+top*totalZoom)/64);
	}
	
}
