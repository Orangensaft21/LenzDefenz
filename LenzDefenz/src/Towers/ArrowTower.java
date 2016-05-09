package Towers;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;

import data.ProjectileBall;
import data.ProjectileLaser;
import data.Tile;
import data.Tower;
import data.TowerType;

public class ArrowTower extends Tower{

	public ArrowTower(TowerType type, Tile startTile) {
		super(type, startTile);
	}

	@Override
	public void shoot() {
		projectiles.add(new ProjectileLaser(QuickLoad("bullet"),x+TILE_SIZE/2-TILE_SIZE/4,
				   y+TILE_SIZE/2-TILE_SIZE/4,911,damage,target));
	}

}
