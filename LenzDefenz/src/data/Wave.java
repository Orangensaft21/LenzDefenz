package data;

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
		Spawn();
	}

	

	public void update() {
		timeSinceLastSpawn += (float) Delta();
		completed = true;
		if (timeSinceLastSpawn > spawnTime && enemiesSpawned <= enemiesPerWave) {
			Spawn();
			timeSinceLastSpawn = 0;
		}

		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				e.update();
				e.Draw();
				completed=false;
			}
		}
	}

	private void Spawn() {
		// System.out.println("spawn");
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), 64, 64, enemyType.getSpeed()));
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
