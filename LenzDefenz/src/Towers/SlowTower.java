package Towers;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;

import data.ProjectileSplash;
import data.Tile;
import data.Tower;
import data.TowerType;

public class SlowTower extends Tower{
	
	public SlowTower(TowerType type, Tile startTile){
		super(type, startTile);
	}
	
	@Override
	public void shoot(){
		//System.out.println(target.getID());
		projectiles.add(new ProjectileSplash(QuickLoad("bullet"),x+TILE_SIZE/2-TILE_SIZE/4,
									   y+TILE_SIZE/2-TILE_SIZE/4,911,damage,target));
	}
}
