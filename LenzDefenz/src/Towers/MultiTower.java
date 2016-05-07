package Towers;

import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.WIDTH;

import data.Enemy;
import data.ProjectileBall;
import data.Tile;
import data.Tower;
import data.TowerType;
import data.Wave;

public class MultiTower extends Tower{
	
	protected Enemy target2, target3;
	private boolean targeted2, targeted3;
	public MultiTower(TowerType type, Tile startTile) {
		super(type, startTile);
		targeted2=false;
		targeted3=false;
	}

	@Override
	public void shoot() {
		if (targeted&&!targeted2)
			target2=acquireTarget();
		if (targeted2&&!targeted3)
			target3=acquireTarget();
		
		if(target2==null || target2.isAlive()==false || !isInRange(target2))
			targeted2=false;
		else
			targeted2=true;
		if(target3==null || target3.isAlive()==false || !isInRange(target3))
			targeted3=false;
		else 
			targeted3=true;
		
		projectiles.add(new ProjectileBall(QuickLoad("bullet"),x+TILE_SIZE/2-TILE_SIZE/4,
				   y+TILE_SIZE/2-TILE_SIZE/4,911,damage,target));
		if (targeted2)
			projectiles.add(new ProjectileBall(QuickLoad("bullet"),x+TILE_SIZE/2-TILE_SIZE/4,
					   y+TILE_SIZE/2-TILE_SIZE/4,911,damage,target2));
		if (targeted3)
			projectiles.add(new ProjectileBall(QuickLoad("bullet"),x+TILE_SIZE/2-TILE_SIZE/4,
					   y+TILE_SIZE/2-TILE_SIZE/4,444,damage,target3));
	}
	
	protected Enemy acquireTarget(){
		Enemy closest = null;
		float closestDist = WIDTH*2;
		for (Enemy e:Wave.getAllEnemyList()){
			if (isInRange(e) && findDistance(e)<closestDist){
				if(e==target||e==target2||e==target3)
					continue;
				closest=e;
				closestDist=findDistance(e);
			}
		}
		if (closest != null)
			targeted=true;
		return closest;
	}
	
	
}
