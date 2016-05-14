package data;

import static helpers.Artist.LoadEnemyTextures;

import org.newdawn.slick.opengl.Texture;

public enum EnemyType {
	LenzPenz(LoadEnemyTextures("Wave1.png"), 93, 1500, 1,3),
	LenzKrenz(LoadEnemyTextures("Wave1.png"), 93, 1500, 2,5);
	
	Texture[][] textures;
	int speed;
	int health;
	int waveNumber;
	int goldLoot;
	
	EnemyType(Texture[][] textures, int speed, int health, int waveNumber, int goldLoot){
		this.textures=textures;
		this.speed=speed;
		this.health=health;
		this.waveNumber=waveNumber;
		this.goldLoot=goldLoot;
	}
	
}
