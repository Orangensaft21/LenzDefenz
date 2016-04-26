package data;


import static helpers.Clock.*;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;

import helpers.Clock;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.WIDTH;
import static helpers.Artist.totalZoom;


public class Game {
	
	
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	TrueTypeFont font;
	//Temp variables
	
	
	public Game(){
		grid = Boot.grid;
		//Enemy enemy = new Enemy(QuickLoad("ufo"),grid.getTile(0,2) ,TILE_SIZE,TILE_SIZE,93,1500);
		waveManager = new WaveManager(0.5f,8);
		player = new Player(grid, waveManager);
		Player.lives= 30;
		//
		//testen schrift
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
	    font = new TrueTypeFont(awtFont, false);

	}
	
	public void update(){
		grid.draw();
		waveManager.update();
		player.update();
		Clock.update();
		//System.out.println(Delta());
		//System.out.println(TotalTime());
		if (Delta()>0.02)
				System.out.println("ruckelruckel");
		
		font.drawString(WIDTH*0.90f/totalZoom, HEIGHT*0.01f/totalZoom, "Lives: "+Player.lives);
	}
	
}
