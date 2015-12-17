package data;

import java.util.ArrayList;

import static helpers.Clock.*;

public class Wave {
	
	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private ArrayList<Enemy> enemyList;
	private float test=0;
	
	
	public Wave(float spawnTime, Enemy enemyType){
		this.enemyType =enemyType;
		this.spawnTime = spawnTime;
		timeSinceLastSpawn = 0;
		this.enemyList = new ArrayList<Enemy>();
	}
	
	public void update(){
		timeSinceLastSpawn += (float) Delta();
		
		if (timeSinceLastSpawn > spawnTime){
			Spawn();
			timeSinceLastSpawn = 0;
		}
		
		for (Enemy e:enemyList){
			e.update();
			e.Draw();
		}
	}
	
	private void Spawn(){
		//System.out.println("spawn");
		enemyList.add(new Enemy(enemyType.getTexture(),enemyType.getStartTile(),64,64,enemyType.getSpeed()));
		
	}
}
