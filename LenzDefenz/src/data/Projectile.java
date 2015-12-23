package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.checkCollision;
import static helpers.Clock.Delta;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public class Projectile implements Entity{
	private Texture texture;
	private float x,y,speed,xSpeed,ySpeed;
	private int damage,width, height;
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
		width = getWidth();
		height = getHeight();
	}
	
	/* 
	 * müsste jetzt alles passen.
	 */
	
	private void calcDirection(){
		float xDistanceFromTarget = target.getX() -x + TILE_SIZE/2 - TILE_SIZE/4;
		float yDistanceFromTarget = target.getY() -y + TILE_SIZE/2 - TILE_SIZE/4;
		
		float betrag = (float) Math.sqrt(xDistanceFromTarget*xDistanceFromTarget+yDistanceFromTarget*yDistanceFromTarget);
		xSpeed=xDistanceFromTarget/betrag;
		ySpeed=yDistanceFromTarget/betrag;
		
	}
	
	public void update(){
		if (alive){
			x += xSpeed*Delta()*speed;
			y += ySpeed*Delta()*speed;
			draw();
			if (checkCollision(x,y,width,height,target.getX(),
							 target.getY(), target.getWidth(),target.getHeight())){
				//System.out.println("Kugel trifft");
				target.Damage(damage);
				alive = false;
			}
		}	
		// speed test
		//System.out.println(Math.sqrt(xSpeed*Delta()*speed*xSpeed*Delta()*speed+ySpeed*Delta()*speed*ySpeed*Delta()*speed));
	}
	public void draw(){
		DrawQuadTex(texture,x,y,32,32);
	}
	
	public boolean isOnMap(){
		/*
		 * range von Tower wäre besser
		 */
		if (!alive)
			return false;
		if (x>WIDTH||y>HEIGHT)
			return false;
		return true;
		
	}

	public float getX() {
		// TODO Auto-generated method stub
		return x;
	}

	public float getY() {
		// TODO Auto-generated method stub
		return y;
	}

	public int getWidth() {
		return (int) texture.getWidth();
	}

	public int getHeight() {
		return (int) texture.getHeight();
	}

	public void setX(float x) {
		this.x=x;
	}

	public void setY(float y) {
		this.y=y;
	}

	public void setWidth(int width) {
		this.width=width;
		
	}

	public void setHeight(int height) {
		this.height=height;
	}
	
}
