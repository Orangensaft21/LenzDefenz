package data;

import static helpers.Artist.TILE_SIZE;
import java.util.ArrayList;

import static helpers.Clock.*;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private ArrayList<Enemy> enemyList;
	private int enemiesPerWave;
	private int enemiesSpawned;
	private boolean completed;

	public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave) {
		this.enemyType = enemyType;
		this.spawnTime = spawnTime;
		timeSinceLastSpawn = 0;
		this.enemyList = new ArrayList<Enemy>();
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
			}
		}
	}
	/*
	 * der wavemanager soll nen enemy übergeben
	 */
	
	private void spawn() {
		// System.out.println("spawn");
		//enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), TILE_SIZE, TILE_SIZE, enemyType.getSpeed(),enemyType.getHealth()));
		
		enemyList.add(new Enemy(enemyType.getEnemyType(),enemyType.getStartTile()));
		enemiesSpawned++;
	}
	
	/*
	 * Welle fertig = timeSinceLastSpawn + xx Sekunden
	 * später dann selbst setzen!
	 */
	public boolean isCompleted(){
		return completed;
	}
	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
}
