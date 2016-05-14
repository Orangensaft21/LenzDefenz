package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum TowerType {
	cannonRed(new Texture[] {QuickLoad("cannonBase"),QuickLoad("cannonGun")},10,222,2.5f,5),
	OliTower(LoadTowerTextures("Tower.png"),30,444,0.5f,5),
	towerIce(new Texture[] {QuickLoad("icetower")},30,222,0.9f,5),
	towerMulti(new Texture[] {QuickLoad("slowtowerbase"),QuickLoad("slowtowercannon")},12,222,0.5f,5);
	
	public Texture[] textures;
	int damage;
	int range;
	float attackSpeed;
	int cost;
	
	TowerType(Texture[] tex, int damage, int range,float attackSpeed, int cost){
		this.textures=tex;
		this.damage=damage;
		this.range=range;
		this.attackSpeed=attackSpeed;
		this.cost=cost;
	}
	
}
