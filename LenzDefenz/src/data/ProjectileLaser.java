package data;

import static helpers.Artist.DrawQuadTexRot;
import static helpers.Artist.WIDTH;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.QuickLoad;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.TILE_SIZE;

public class ProjectileLaser extends Projectile{

	private float angle;
	
	public ProjectileLaser(Texture tex, float x, float y, float speed, int damage, Enemy target) {
		super(QuickLoad("redlaser"), x, y, speed, damage, target);
		angle=calcAngle();
	}
	
	@Override
	public void draw(){
		DrawQuadTexRot(texture,x-256+TILE_SIZE/4,y+TILE_SIZE/4,512,8,calcAngle());
		xSpeed=0;
		ySpeed=0;
	}
	
	private float calcAngle(){
		double angleTemp = Math.atan2(target.getY()+TILE_SIZE/4-y, target.getX()+TILE_SIZE/4-x);
		return (float) Math.toDegrees(angleTemp) ;
	}
}
