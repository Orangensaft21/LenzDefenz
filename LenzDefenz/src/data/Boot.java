package data;

import static helpers.Artist.BeginSession;

import java.awt.Font;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import helpers.Clock;
import helpers.GameManager;
import helpers.Leveler;
import pathfinding.AStarPathFinder;
import pathfinding.Path;
import pathfinding.PathFinder;
import pathfinding.TileBasedMap;
// test font
import java.awt.Font;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
// ende test font
import static helpers.Artist.*;

public class Boot {
	
	/*public static int[][] map = {
			{2,2,2,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
			{2,2,0,0,0,0,1,2,2,2,1,0,0,0,0,0,0,0,0,0},
			{1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
			{2,2,0,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0},
			{2,2,0,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,2,2,2,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,2,2,2,2,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,2,2,1,1,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
	};*/
	public static TileGrid grid;
	
	TrueTypeFont font;
	
	Boot(){
		helpers.Artist.totalZoom=1f;
		helpers.Artist.left=0;
		helpers.Artist.top=0;
		BeginSession();
		
		//TileBasedMap sd = new TileGrid(map);
		grid = Leveler.loadMap("LenzMap1");
		
		
		//Game game = new Game();

		TextRenderer textRenderer;
		//textRenderer = new TextRenderer("Times new Roman", 32, 32);
		while(!Display.isCloseRequested()){
			Clock.update();

			

			//TextRenderer.drawString("HELLO", 0, 0, 1, 1f, 1f, 1f, TextRenderer.LEFT);
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
