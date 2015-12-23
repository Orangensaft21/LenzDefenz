package data;

import static helpers.Artist.BeginSession;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;

import helpers.Clock;
import helpers.GameManager;
import helpers.Leveler;

public class Boot {
	
	
	public static TileGrid grid;
	
	TrueTypeFont font;
	
	Boot(){
		helpers.Artist.totalZoom=1f;
		helpers.Artist.left=0;
		helpers.Artist.top=0;
		BeginSession();
		
		grid = Leveler.loadMap("LenzMap1");
		
		
		
		while(!Display.isCloseRequested()){
			Clock.update();
			GameManager.update();
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
	}

	public static void main(String[] args) {
		
		new Boot();

	}
	
	

}
