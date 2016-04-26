package data;

import static helpers.Artist.TILE_SIZE;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Clock.*;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private EnemyType enemyType;
	private  CopyOnWriteArrayList<Enemy> enemyList;
	private static CopyOnWriteArrayList<Enemy> allEnemiesList;//=new CopyOnWriteArrayList<Enemy>();
	private int enemiesPerWave;
	private int enemiesSpawned;
	private boolean completed;

	public Wave(EnemyType enemyType, float spawnTime, int enemiesPerWave) {
		this.enemyType = enemyType;
		this.spawnTime = spawnTime;
		timeSinceLastSpawn = 0;
		if (allEnemiesList==null)
			Wave.allEnemiesList = new CopyOnWriteArrayList<Enemy>();
		this.enemyList= new CopyOnWriteArrayList<Enemy>();
		this.enemiesPerWave =enemiesPerWave;
		enemiesSpawned=0;
		
		completed=false;
		spawn();
	}

	

	public void update() {
		timeSinceLastSpawn += (float) Delta();
		completed = true;
		if (timeSinceLastSpawn > spawnTime && enemiesSpawned <= enemiesPerWave) {
			spawn();
			timeSinceLastSpawn = 0;
		}

		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				e.update();
				e.draw();
				completed=false;
			}else{
				allEnemiesList.remove(e);
				enemyList.remove(e);
				
			}
			
		}
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				e.drawHealth();
				completed=false;
			}
		}
	}
	/*
	 * der wavemanager soll nen enemy übergeben
	 */
	
	private void spawn() {
		// System.out.println("spawn");
		//enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), TILE_SIZE, TILE_SIZE, enemyType.getSpeed(),enemyType.getHealth()));
		Enemy e = new Enemy(enemyType,Boot.grid.getTile(0,2),this);
		allEnemiesList.add(e);
		enemyList.add(e);
		enemiesSpawned++;
	}
	
	/*
	 * Welle fertig = timeSinceLastSpawn + xx Sekunden
	 * später dann selbst setzen!
	 */
	public boolean isCompleted(){
		return completed;
	}
	public static CopyOnWriteArrayList<Enemy> getAllEnemyList() {
		return allEnemiesList;
	}
	
}
