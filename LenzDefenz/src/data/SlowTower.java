package data;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;

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
