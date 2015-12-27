package data;

import org.newdawn.slick.opengl.Texture;

public class ProjectileIceball extends Projectile{

	public ProjectileIceball(Texture tex, float x, float y, float speed, int damage, Enemy target) {
		super(tex, x, y, speed, damage, target);
	}
	
	private Enemy e;
	
	@Override
	public void damage(){
		super.damage();
		e = super.getTarget();
		e.setSpeed(e.getEnemyType().speed*0.7f);
	}
	
}
