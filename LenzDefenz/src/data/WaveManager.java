package data;

import static helpers.Clock.Delta;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class WaveManager {
	
	private float timeSinceLastWave,timeBetweenEnemies;
	int waveNumber,enemiesPerWave;
	private CopyOnWriteArrayList<Wave> waves;
	//parameter merken zum entfernen in der liste
	
	public WaveManager(float timeBetweenEnemies, int enemiesPerWave){
		this.timeSinceLastWave=0;
		this.waveNumber = 1;
		this.enemiesPerWave = enemiesPerWave;
		this.timeBetweenEnemies=timeBetweenEnemies;
		
		this.waves = new CopyOnWriteArrayList<Wave>();
		newWave();
		
		/*
		 * muss noch geändert werden
		 */
		
		//Enemy.removeEnemy(enemyType);
	}
	
	public void update(){
		for (Wave w: waves){
			if (!w.isCompleted())
				w.update();
			else
				waves.remove(w);
		}
		
		
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
