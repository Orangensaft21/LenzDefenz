package data;

import static helpers.Artist.LoadEnemyTextures;

import org.newdawn.slick.opengl.Texture;

public enum EnemyType {
	LenzPenz(LoadEnemyTextures("Wave1.png"), 93, 1500, 1),
	LenzKrenz(LoadEnemyTextures("Wave1.png"), 93, 1500, 2);
	
	Texture[][] textures;
	int speed;
	int health;
	int waveNumber;
	
	EnemyType(Texture[][] textures, int speed, int health, int waveNumber){
		this.textures=textures;
		this.speed=speed;
		this.health=health;
		this.waveNumber=waveNumber;
	}
	
}
