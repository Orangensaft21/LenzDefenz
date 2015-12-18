package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Clock.*;
import static helpers.Artist.*;

public class Projectile {
	private Texture texture;
	private float x,y,speed,xSpeed,ySpeed;
	private int damage;
	private Enemy target;
	
	public Projectile(Texture tex, float x, float y, float speed, int damage, Enemy target){
		this.x=x;
		this.y=y;
		this.texture=tex;
		this.speed=speed;
		this.damage = damage;
		this.target = target;
		this.xSpeed = 0f;
		this.ySpeed = 0f;
		if (target.getX()< x)
			xSpeed *= -1;
		if (target.getY()< y)
			ySpeed *= -1;
		calcDirection();	//für zielsuchende geschosse ins update machen
	}
	
	private void calcDirection(){
		float totalAllowedMovement = 1.0f;
		float xDistanceFromTarget = Math.abs(target.getX() -x-Game.TILE_SIZE/4+ Game.TILE_SIZE/2);
		float yDistanceFromTarget = Math.abs(target.getY() -y-Game.TILE_SIZE/4+ Game.TILE_SIZE/2);
		//stimmt natürlich geometrisch nicht
		float totalDistanceFromTarget = xDistanceFromTarget+yDistanceFromTarget;
		float xPercentOfMovement = xDistanceFromTarget/totalDistanceFromTarget;
		xSpeed = xPercentOfMovement;
		ySpeed = totalAllowedMovement - xPercentOfMovement;
		
	}
	
	public void update(){
		x += xSpeed*Delta()*speed;
		y += ySpeed*Delta()*speed;
		Draw();
	}
	public void Draw(){
		DrawQuadTex(texture,x,y,32,32);
	}
	
	public boolean isOnMap(){
		/*
		 * range von Tower wäre besser
		 */
		if (x>20*Game.TILE_SIZE&&y>15*Game.TILE_SIZE)
			return false;
		return true;
		
	}
	
}
