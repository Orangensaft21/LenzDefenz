package data;

import org.newdawn.slick.opengl.Texture;

public class ProjectileBall extends Projectile{

	public ProjectileBall(Texture tex, float x, float y, float speed, int damage, Enemy target) {
		super(tex, x, y, speed, damage, target);
	}
	
}
