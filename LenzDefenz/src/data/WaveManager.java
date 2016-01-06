package data;

import static helpers.Clock.Delta;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class WaveManager {
	
	private float timeSinceLastWave,timeBetweenEnemies;
	int waveNumber,enemiesPerWave;
	private Enemy enemyType;
	private CopyOnWriteArrayList<Wave> waves;
	//parameter merken zum entfernen in der liste
	int zwischen ;
	
	public WaveManager(Enemy enemyType, float timeBetweenEnemies, int enemiesPerWave){
		this.timeSinceLastWave=0;
		this.waveNumber = 1;
		this.enemiesPerWave = enemiesPerWave;
		this.enemyType = enemyType;
		this.timeBetweenEnemies=timeBetweenEnemies;
		
		this.waves = new CopyOnWriteArrayList<Wave>();
		newWave();
		
		/*
		 * muss noch geändert werden
		 */
		Enemy.removeEnemy(enemyType);
	}
	
	public void update(){
		zwischen = -1;
		for (Wave w: waves){
			if (!w.isCompleted())
				w.update();
		}
		if (zwischen != -1)
			waves.remove(zwischen);
		
		timeSinceLastWave += Delta();
		if (timeSinceLastWave>12){
			timeSinceLastWave = 0;
			newWave();
		}	
	}
	
	public void newWave(){
		for (EnemyType type:EnemyType.values()){
			if (type.waveNumber==waveNumber)
				waves.add(new Wave(type,timeBetweenEnemies, enemiesPerWave));
		}
		System.out.println("Welle:"+ waveNumber);
		waveNumber++;
		waveNumber = waveNumber %3;
		waveNumber++;
	}
	public Wave getCurrentWave(){
		return waves.get(waves.size());
	}
	
}
