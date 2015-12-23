package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum TowerType {
	cannonRed(new Texture[] {QuickLoad("cannonBase"),QuickLoad("cannonGun")},10,222,0.5f),
	slowTower(new Texture[] {QuickLoad("slowtowerbase"),QuickLoad("slowtowercannon")},30,444,0.5f);
	
	Texture[] textures;
	int damage;
	int range;
	float attackSpeed;
	
	TowerType(Texture[] tex, int damage, int range,float attackSpeed){
		this.textures=tex;
		this.damage=damage;
		this.range=range;
		this.attackSpeed=attackSpeed;
	}
	
}
