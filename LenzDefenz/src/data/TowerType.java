package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum TowerType {
	cannonRed(new Texture[] {QuickLoad("cannonBase"),QuickLoad("cannonGun")},10,222,0.5f),
	OliTower(LoadTowerTextures("Tower.png"),30,444,0.5f),
	towerIce(new Texture[] {QuickLoad("icetower")},30,222,0.9f),
	towerMulti(new Texture[] {QuickLoad("slowtowerbase"),QuickLoad("slowtowercannon")},8,222,0.5f);
	
	public Texture[] textures;
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
