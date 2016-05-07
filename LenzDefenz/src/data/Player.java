package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.getMouseXCoord;
import static helpers.Artist.getMouseYCoord;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.WIDTH;
import static helpers.Artist.left;
import static helpers.Artist.scrollMap;
import static helpers.Artist.top;
import static helpers.Artist.totalZoom;
import static helpers.Artist.zoom;
import static helpers.Clock.TotalTime;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import Towers.ArrowTower;
import Towers.SlowTower;
import Towers.TowerIce;
import UI.PickUI;
import helpers.Clock;
public class Player {

	private TileGrid grid;
	@SuppressWarnings("unused")
	private WaveManager waveManager;
	private ArrayList<Tower> towerList;
	private boolean leftMouseButtonDown,rightMouseButtonDown,toggleUI;
	private PickUI towerUI;
	public static int lives;
	
	public Player(TileGrid grid,WaveManager waveManager){
		this.grid=grid;
		this.waveManager =waveManager;
		this.towerList = new ArrayList<Tower>();
		leftMouseButtonDown = false;
		rightMouseButtonDown=false;
		toggleUI=false;
		towerUI = new PickUI();
		towerUI.addButton("Eistower", TowerType.towerIce,(int) (WIDTH*0.45f), (int) (HEIGHT*0.45f));
		towerUI.addButton("Olitower", TowerType.OliTower, (int) (WIDTH*0.50f) , (int) (HEIGHT*0.45f));
		towerUI.addButton("Cannontower", TowerType.cannonRed, (int) (WIDTH*0.55f) , (int) (HEIGHT*0.45f));
		
	}
	//as
	
	
	public void update(){
		for (Tower t :towerList){
			t.update();
		}
		/*
		 * TowerUI zeichnen falls toggled
		 */
		if (toggleUI)
			towerUI.Draw();
		
		//Maus input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown ){
			if (toggleUI){
				towerUI.setTowerPicked(towerUI.getButtonClicked());;
				//System.out.println(towerUI.getTowerPicked());
			}
			else{	
				Tile tile = grid.getTile(getMouseX()/totalZoom,getMouseY()/totalZoom);
				if (tile.isBuildable()&&TotalTime()>0.2f)
					buildTower(tile, TowerType.cannonRed, false);
			}
		}
		if (Mouse.isButtonDown(1) && !rightMouseButtonDown ){
			toggleUI = !toggleUI;
			towerUI.setButtonMousePos(getMouseXCoord(), getMouseYCoord());
		}
		
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
				Clock.changeMultiplier(+0.2f);
			}
			// scroll test
		}
	}
	
	
	/*public Tile getTileFromMouse(){
		return grid.getTile(getMouseX()/totalZoom+(int)Math.floor(left/(totalZoom*TILE_SIZE)),getMouseY()/totalZoom+top/(totalZoom*TILE_SIZE));
		
		//return null;
		
	}*/
	
	public void zoomWithMouseWheel(int wheel){
		if (wheel>0){
			zoom(0.5f);
		}
		if (wheel<0){
			zoom(-0.5f);
		}
	}
	
	/*
	 * funktion macht bei krummen zooms noch probleme
	 * gibt die TILE aus nicht die Koords!!
	 */
	
	public int getMouseX(){
		
		Mouse.setClipMouseCoordinatesToWindow(false);
		return (int)((Mouse.getX()+left*totalZoom)/TILE_SIZE);
	}
	
	public int getMouseY(){
		
		//return (int)((Display.getHeight()-Mouse.getY()+top*totalZoom)/TILE_SIZE);
		return (int) ((HEIGHT -Mouse.getY()-1*totalZoom+top*totalZoom)/TILE_SIZE);
	}
	
	public void buildTower(Tile tile, TowerType type,boolean rightClick){
		switch (towerUI.getTowerPicked()){
		case "Eistower":
			towerList.add(new TowerIce(TowerType.towerIce, tile));
			break;
		case "Olitower":
			towerList.add(new SlowTower(TowerType.OliTower, tile));
			break;
		//TODO	
		case "Cannontower":
			towerList.add(new ArrowTower(TowerType.cannonRed, tile));
			break;
		}
		tile.setBuildable(false);
		
	}
	
}
