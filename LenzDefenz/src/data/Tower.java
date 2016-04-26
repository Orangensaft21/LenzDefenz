package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRot;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.WIDTH;
import static helpers.Clock.Delta;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

public abstract class Tower implements Entity{
	
	protected float x;
	protected float y;
	private float attackSpeed;
	private float angle;
	private float timeSinceLastShot;
	private int width,height;
	protected int damage;
	private int range;
	protected Enemy target;
	private Texture[] textures;
	private boolean targeted;
	protected CopyOnWriteArrayList<Projectile> projectiles;
	
	public Tower(TowerType type, Tile startTile){
		this.textures=type.textures;
		this.x=startTile.getX();
		this.y=startTile.getY();
		this.width=startTile.getWidth();
		this.height=startTile.getHeight();
		this.damage=type.damage;
		this.targeted=false;
		this.range=type.range;
		this.projectiles = new CopyOnWriteArrayList<Projectile>();
		this.attackSpeed=type.attackSpeed;
		this.angle=0;
		this.timeSinceLastShot=0;
		
		
	}
	
	private Enemy acquireTarget(){
		Enemy closest = null;
		float closestDist = WIDTH*2;
		for (Enemy e:Wave.getAllEnemyList()){
			if (isInRange(e) && findDistance(e)<closestDist){
				closest=e;
				closestDist=findDistance(e);
			}
		}
		if (closest != null)
			targeted=true;
		return closest;
	}
	
	public void update(){
		timeSinceLastShot += Delta();
		if (!targeted)
			target=acquireTarget();
		else
			angle = calcAngle();
		
		
		if(target==null || target.isAlive()==false || !isInRange(target)){
			targeted=false;
		}else{
			if (timeSinceLastShot > attackSpeed){				
				shoot();
				timeSinceLastShot = 0;
			}
		}
		
		
		
		for (Projectile p:projectiles){											
			if (!p.isOnMap())												//auch wenn das projektil getroffen hat
				projectiles.remove(p);
			else
				p.update();
		}
		
		
		draw();
	}

	public void draw() {
		DrawQuadTex(textures[0],x,y,width,height);
		if (textures.length==2&&textures[1]!=null)
				DrawQuadTexRot(textures[1],x,y,width,height,angle);
	}
	
	private float calcAngle(){
		double angleTemp = Math.atan2(target.getY()-y, target.getX()-x);
		return (float) Math.toDegrees(angleTemp) -90;
	}
	
	private boolean isInRange(Enemy e){
		if(findDistance(e)>range)
			return false;
		return true;
	}
	
	public float findDistance(Enemy e){
		float dx = this.x + TILE_SIZE/2  - e.getX();
		float dy = this.y + TILE_SIZE/2 - e.getY();
		//System.out.println(dx);
		return (float) Math.sqrt(dx*dx+dy*dy);
	}
	
	public abstract void shoot();/*{
		//System.out.println(target.getID());
		projectiles.add(new ProjectileBall(QuickLoad("bullet"),x+TILE_SIZE/2-TILE_SIZE/4,
									   y+TILE_SIZE/2-TILE_SIZE/4,911,damage,target));
		//wavEffect.playAsSoundEffect(1.0f, 1.0f, false);
	}*/
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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

	public Enemy getTarget(){
		return target;
	}

}
