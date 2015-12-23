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

public class TowerCannon {
	private float x,y,timeSinceLastShot,firingSpeed, angle;
	private int width, height, damage, range;
	private Texture baseTexture, cannonTexture;
	private CopyOnWriteArrayList<Projectile> projectiles;
	private Enemy target;
	private boolean targeted;
	
	//Sound
	@SuppressWarnings("unused")
	private Audio wavEffect;
	
	//Pojectile Hilfe
	//int projOutOfArea = -1;
	
	public TowerCannon(Texture texture, Tile startTile, int damage, int range){
		this.x=startTile.getX();
		this.y=startTile.getY();
		this.baseTexture = texture;
		this.cannonTexture = QuickLoad("cannonGun");
		this.damage = damage;
		this.range = range;
		this.width = (int) startTile.getWidth();
		this.height = (int) startTile.getHeight();
		this.firingSpeed =1.5f;
		this.timeSinceLastShot=0;
		this.projectiles = new CopyOnWriteArrayList<Projectile>();
		this.targeted=false;
		/*sound
		 * 
		 */
		try {
			wavEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/awp1.wav"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Enemy acquireTarget(){
		Enemy closest = null;
		float closestDist = WIDTH*2;
		for (Enemy e:Enemy.getEnemies()){
			if (isInRange(e) && findDistance(e)<closestDist){
				closest=e;
				closestDist=findDistance(e);
			}
		}
		if (closest != null)
			targeted=true;
		return closest;
	}
		
	private float calcAngle(){
		double angleTemp = Math.atan2(target.getY()-y, target.getX()-x);
		return (float) Math.toDegrees(angleTemp) -90;
	}
	
	/*
	 * Projectiles deleten nich elegant aber funktioniert.
	 */
	public void update(){
		timeSinceLastShot += Delta();
		if (!targeted)
			target=acquireTarget();
		else
			angle = calcAngle();
		
		
		if(target==null || target.isAlive()==false || !isInRange(target)){
			targeted=false;
		}else{
			if (timeSinceLastShot > firingSpeed){
				shoot();
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
	
	public void draw(){
		DrawQuadTex(baseTexture,x,y,width,height);
		DrawQuadTexRot(cannonTexture,x,y,width,height,angle);
	}
	
	public void shoot(){
		//System.out.println(target.getID());
		timeSinceLastShot = 0;
		projectiles.add(new Projectile(QuickLoad("bullet"),x+TILE_SIZE/2-TILE_SIZE/4,
									   y+TILE_SIZE/2-TILE_SIZE/4,911,damage,target));
		//wavEffect.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	private boolean isInRange(Enemy e){
		if(findDistance(e)>range)
			return false;
		return true;
	}
	
	public float findDistance(Enemy e){
		float dx = this.x + TILE_SIZE/2  - e.getX();
		float dy = this.y + TILE_SIZE/2 - e.getY();
		return (float) Math.sqrt(dx*dx+dy*dy);
	}
}
