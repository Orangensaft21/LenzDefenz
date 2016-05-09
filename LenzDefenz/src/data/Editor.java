package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.WIDTH;
import static helpers.Artist.totalZoom;
import static helpers.Artist.HEIGHT;
import static helpers.Leveler.saveMap;
import java.awt.Font;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;

import UI.PickUI;
import helpers.Clock;
import helpers.GameManager;
import helpers.Leveler;

public class Editor {
	
	private TileGrid grid;
	private PickUI tileUI;
	@SuppressWarnings("unused")
	private boolean leftMouseButtonDown,rightMouseButtonDown, toggleUI;
	TrueTypeFont font;
	
	public Editor(){
		grid = Leveler.loadMap("LenzMap1");
		tileUI = new PickUI();
		tileUI.addButton("grass", TileType.Grass, (int) (WIDTH*0.45f), (int) (HEIGHT*0.45f));
		tileUI.addButton("dirt", TileType.Dirt, (int) (WIDTH*0.50f), (int) (HEIGHT*0.45f));
		tileUI.addButton("water", TileType.Water, (int) (WIDTH*0.55f), (int) (HEIGHT*0.45f));
		toggleUI = false;
		
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
	    font = new TrueTypeFont(awtFont, false);
	}
	
	public void update(){
		grid.draw();
		if (Clock.TotalTime() < 5)
			font.drawString(WIDTH*0.72f/totalZoom, HEIGHT*0.01f/totalZoom, "s drücken zum speichern");
		if (toggleUI)
			tileUI.Draw();
		
		if (Mouse.isButtonDown(0) ){
			if (toggleUI){
				tileUI.setItemPicked(tileUI.getButtonClicked());
			}
			else
				SetTile();
		}
		
		if(Mouse.isButtonDown(1)&&!rightMouseButtonDown){
			toggleUI = !toggleUI;
		}
		
		leftMouseButtonDown=Mouse.isButtonDown(0);
		rightMouseButtonDown=Mouse.isButtonDown(1);
		//Tastatur input
		while (Keyboard.next()){
			
			if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()){
				saveMap("LenzMap1",grid);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()){
				GameManager.setState(GameManager.GameState.MAINMENU);
				Boot.grid = Leveler.loadMap("LenzMap1");
			}
		}
	}
	
	
	public void SetTile(){
		TileType t=TileType.Grass;
		for (TileType type: TileType.values()){
			if (type.textureName.equals(tileUI.getItemPicked()))
				t=type;
			
		}
		
		grid.setTile((int)Math.floor(Mouse.getX()/TILE_SIZE),
					 (int)Math.floor((HEIGHT -Mouse.getY()-1)/TILE_SIZE), t);
	}
	

}
