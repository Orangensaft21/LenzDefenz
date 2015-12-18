package data;

import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class TowerCannon {
	private float x,y,timeSinceLastShot,firingSpeed, angle;
	private int width, height, damage;
	private Texture baseTexture, cannonTexture;
	private Tile startTile;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;
	private Enemy target;
	
	public TowerCannon(Texture texture, Tile startTile, int damage,ArrayList<Enemy> enemies){
		this.x=startTile.getX();
		this.y=startTile.getY();
		this.startTile = startTile;
		this.baseTexture = texture;
		this.cannonTexture = QuickLoad("cannonGun");
		this.damage = damage;
		this.width = (int) startTile.getWidth();
		this.height = (int) startTile.getHeight();
		this.firingSpeed =1;
		this.timeSinceLastShot=0;
		this.projectiles = new ArrayList<Projectile>();
		this.enemies=enemies;
		this.target=acquireTarget();
		this.angle = calcAngle();
	}
	
	private Enemy acquireTarget(){
		return enemies.get(0);
	}
	
	private float calcAngle(){
		double angleTemp = Math.atan2(target.getY()-y, target.getX()-x);
		return (float) Math.toDegrees(angleTemp) -90;
	}
	
	public void update(){
		timeSinceLastShot += Delta();
		if (timeSinceLastShot > firingSpeed){
			Shoot();
		}
		
		for (Projectile p:projectiles)
			p.update();
		angle = calcAngle();
		Draw();
	}
	
	public void Draw(){
		DrawQuadTex(baseTexture,x,y,width,height);
		DrawQuadTexRot(cannonTexture,x,y,width,height,angle);
	}
	
	public void Shoot(){
		timeSinceLastShot = 0;
		projectiles.add(new Projectile(QuickLoad("bullet"),x+32,y+32,111,12));
	}

}
