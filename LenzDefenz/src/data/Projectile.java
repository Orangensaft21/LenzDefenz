package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.checkCollision;
import static helpers.Clock.Delta;

import org.newdawn.slick.opengl.Texture;

public class Projectile {
	private Texture texture;
	private float x,y,speed,xSpeed,ySpeed,width, height;
	private int damage;
	private Enemy target;
	private boolean alive;
	
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
		this.alive=true;
		calcDirection();	//für zielsuchende geschosse ins update machen
		width = tex.getWidth();
		height = tex.getHeight();
	}
	
	/* 
	 * müsste jetzt alles passen.
	 */
	
	private void calcDirection(){
		float xDistanceFromTarget = target.getX() -x + Game.TILE_SIZE/2 - Game.TILE_SIZE/4;
		float yDistanceFromTarget = target.getY() -y + Game.TILE_SIZE/2 - Game.TILE_SIZE/4;
		
		float betrag = (float) Math.sqrt(xDistanceFromTarget*xDistanceFromTarget+yDistanceFromTarget*yDistanceFromTarget);
		xSpeed=xDistanceFromTarget/betrag;
		ySpeed=yDistanceFromTarget/betrag;
		
	}
	
	public void update(){
		if (alive){
			x += xSpeed*Delta()*speed;
			y += ySpeed*Delta()*speed;
			Draw();
			if (checkCollision(x,y,width,height,target.getX(),
							 target.getY(), target.getWidth(),target.getHeight())){
				System.out.println("Kugel trifft");
				target.Damage(damage);
				alive = false;
			}
		}	
		// speed test
		//System.out.println(Math.sqrt(xSpeed*Delta()*speed*xSpeed*Delta()*speed+ySpeed*Delta()*speed*ySpeed*Delta()*speed));
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
