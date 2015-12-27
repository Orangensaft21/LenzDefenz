package data;

import static helpers.Artist.QuickLoad;

import org.newdawn.slick.opengl.Texture;

public enum EnemyType {
	UFO(new Texture[] {QuickLoad("ufo")}, 93, 1500);
	
	Texture[] textures;
	int speed;
	int health;
	
	EnemyType(Texture[] textures, int speed, int health){
		this.textures=textures;
		this.speed=speed;
		this.health=health;
	}
	
}
