package data;

import static helpers.Artist.TILE_SIZE;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

public class ProjectileSplash extends Projectile{

	public ProjectileSplash(Texture tex, float x, float y, float speed, int damage, Enemy target) {
		super(tex, x, y, speed, damage, target);
	}
	
	@Override
	public void damage(){
		CopyOnWriteArrayList<Enemy> enemies=Wave.getAllEnemyList();
		//100 Pixel splash
		for (Enemy e: enemies){
			if(checkCircleCollision(this.getX(),this.getY(),e,100f)){
				e.Damage(damage);
				this.alive = false;
			}
		}
	}
	
	
	public boolean checkCircleCollision(float x, float y , Enemy target, float radius){
		float xDistanceFromTarget = target.getX() -x + TILE_SIZE/2 - TILE_SIZE/4;
		float yDistanceFromTarget = target.getY() -y + TILE_SIZE/2 - TILE_SIZE/4;
		float betrag = (float) Math.sqrt(xDistanceFromTarget*xDistanceFromTarget+yDistanceFromTarget*yDistanceFromTarget);
		if (betrag <radius)
			return true;
		return false;
	}
}
